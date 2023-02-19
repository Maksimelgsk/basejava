package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public record SqlHelper(ConnectionFactory connectionFactory) {

    public void execute(String query) {
        execute(query, PreparedStatement::execute);
    }

    public <T> T execute(String query, SqlExecutor<T> sqlExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            return sqlExecutor.execute(ps);
        } catch (SQLException e) {
            throw e.getSQLState().equals("23505") ? new ExistStorageException(e.getMessage()) : new StorageException(e);
        }
    }
}
