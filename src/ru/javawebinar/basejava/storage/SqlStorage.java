package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;
    public static final String DB_DRIVER = "org.postgresql.Driver";

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName(DB_DRIVER).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        String query = "DELETE FROM resume r";
        sqlHelper.execute(query, ps -> {
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        String query = "UPDATE resume r SET full_name = ? WHERE r.uuid = ?";
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(r, conn);
            deleteSections(r, conn);
            insertContacts(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        String query = "INSERT INTO resume(uuid, full_name) VALUES (?,?)";
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.executeUpdate();
            }
            insertContacts(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        String query = "DELETE FROM resume r WHERE r.uuid=?";
        sqlHelper.execute(query, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String query = "SELECT * FROM resume r WHERE r.uuid=?";
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                addContacts(conn, resume);
                addSections(conn, resume);
                return resume;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            String query = "SELECT * FROM resume ORDER BY full_name, uuid";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = resumes.get(uuid);
                    if (resume == null) {
                        resume = new Resume(uuid, rs.getString("full_name"));
                        resumes.put(uuid, resume);
                    }
                }
            }
            insertContact(conn, resumes);
            insertSection(conn, resumes);
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        String query = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(query, ps -> {
            ResultSet rs = ps.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }

    private static void deleteContacts(Resume r, Connection conn) throws SQLException {
        String query = "DELETE FROM contact WHERE resume_uuid = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private static void deleteSections(Resume r, Connection conn) throws SQLException {
        String query = "DELETE FROM section WHERE resume_uuid = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
        }
    }

    private static void insertContacts(Resume r, Connection conn) throws SQLException {
        String query = "INSERT INTO contact(resume_uuid, type, value) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static void insertSections(Resume r, Connection conn) throws SQLException {
        String query = "INSERT INTO section(resume_uuid, type, content) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                AbstractSection section = entry.getValue();
                ps.setString(3, JsonParser.write(section, AbstractSection.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.setContacts(type, value);
        }
    }

    private static void addSections(Connection conn, Resume r) throws SQLException {
        String query = "SELECT * FROM section WHERE resume_uuid = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addSection(rs, r);
            }
        }
    }

    private static void addContacts(Connection conn, Resume r) throws SQLException {
        String query = "SELECT * FROM contact WHERE resume_uuid = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContact(rs, r);
            }
        }
    }

    private static void addSection(ResultSet rs, Resume r) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            r.setSections(type, JsonParser.readString(content, AbstractSection.class));
        }
    }

    private static void insertSection(Connection conn, Map<String, Resume> resumes) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addSection(rs, resumes.get(rs.getString("resume_uuid")));
            }
        }
    }

    private static void insertContact(Connection conn, Map<String, Resume> resumes) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContact(rs, resumes.get(rs.getString("resume_uuid")));
            }
        }
    }
}
