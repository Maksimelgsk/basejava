package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        String query = "DELETE FROM resume";
        sqlHelper.execute(query);
    }

    @Override
    public void update(Resume r) {
        String query = "UPDATE resume r SET full_name = ? WHERE r.uuid = ?";
        sqlHelper.execute(query, ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        String query = "INSERT INTO resume(uuid, full_name) VALUES (?,?)";
        sqlHelper.execute(query, ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
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
        return sqlHelper.execute(query, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String query = "SELECT * FROM resume ORDER BY uuid";
        return sqlHelper.execute(query, ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public int size() {
        String query = "SELECT count(*) FROM resume";
        return sqlHelper.execute(query, ps -> {
            ResultSet rs = ps.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }
}
