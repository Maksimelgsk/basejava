package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
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
        sqlHelper.execute(query);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name = ? WHERE r.uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            sqlHelper.execute(
                    "DELETE FROM contact c WHERE resume_uuid=?", ps -> {
                ps.setString(1, r.getUuid());
                ps.executeUpdate();
                return null;
            });
            insertContact(r, conn);
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
            insertContact(r, conn);
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
        String query = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid=c.resume_uuid WHERE r.uuid=?";
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(rs, resume);
                } while (rs.next());
                return resume;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY r.full_name")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    list.add(resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact r")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    for (Resume resume : list) {
                        if (resume.getUuid().equals(rs.getString("resume_uuid"))) {
                            addContact(rs, resume);
                        }
                    }
                }
                ps.addBatch();
                ps.executeBatch();
            }
            return null;
        });
        return list;
    }

    @Override
    public int size() {
        String query = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(query, ps -> {
            ResultSet rs = ps.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }


    public void insertContact(Resume r, Connection conn) throws SQLException {
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

    public void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.setContacts(type, value);
        }
    }
}
