package com.cmpn306.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    String     schemaPath   = "./schema.sql";
    String     databaseName = "searchEngineDatabase";
    Connection connection;

    Database() throws SQLException, IOException {
        connectDatabase();
        createTables();

    }

    void connectDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
    }

    void createTables() throws IOException, SQLException {
        Path      filePath = Path.of(schemaPath);
        String    sql      = Files.readString(filePath);
        String[]  tokens   = sql.split(";");
        Statement stmt     = connection.createStatement();
        for (String token: tokens) {
            //token = token + ";";
            System.out.println(token);
            stmt.addBatch(token);
        }
        stmt.executeBatch();
    }
}