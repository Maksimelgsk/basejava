package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.DriverManager;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

}
