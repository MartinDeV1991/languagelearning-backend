package com.devteam.languagelearning.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    public String ensureTablesAndConvert(MultipartFile dbFile) throws IOException, SQLException {
        if (dbFile.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        List<String> requiredTables = Arrays.asList("LOOKUPS", "WORDS", "BOOK_INFO");

        File tempFile = Files.createTempFile("uploaded", ".db").toFile();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            out.write(dbFile.getBytes());
        }

        String csvContent;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath())) {
            Class.forName("org.sqlite.JDBC");
            if (checkRequiredTablesExist(connection, requiredTables)) {
                // All required tables exist, use the connection to convert the database to CSV
                csvContent = convertDBtoCSV(connection);
            } else {
                throw new IllegalArgumentException("Database does not contain all required tables.");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        } finally {
            if (!tempFile.delete()) {
                System.err.println("Failed to delete temporary file: " + tempFile.getAbsolutePath());
            }
        }

        return csvContent;
    }

    private boolean checkRequiredTablesExist(Connection connection, List<String> requiredTables) throws SQLException {
        DatabaseMetaData dbMetaData = connection.getMetaData();
        ResultSet rs = dbMetaData.getTables(null, null, "%", null);
        List<String> foundTables = new ArrayList<>();
        while (rs.next()) {
            foundTables.add(rs.getString(3).toUpperCase()); // Assuming table names are case insensitive
        }
        System.out.println("Tables found: " + foundTables);
        return requiredTables.stream().allMatch(requiredTable -> foundTables.contains(requiredTable.toUpperCase()));
    }

    public String convertDBtoCSV(Connection connection) throws SQLException {
        StringBuilder csvContent = new StringBuilder("word,stem,lang,usage,title,authors,isbn\n"); // Updated CSV header to include new fields

        // Updated SQL query to perform a JOIN between LOOKUPS and WORDS tables
        // Assuming word_key in LOOKUPS is the foreign key that references id in WORDS
        String sql = "SELECT w.word, w.stem, w.lang, l.usage, b.authors, b.title, b.asin " +
                "FROM LOOKUPS l " +
                "JOIN WORDS w ON l.word_key = w.id " +
                "JOIN BOOK_INFO b ON l.book_key = b.id ";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String word = resultSet.getString("word");
                String stem = resultSet.getString("stem");
                String lang = resultSet.getString("lang");
                String usage = resultSet.getString("usage");
                String title = resultSet.getString("title");
                String authors = resultSet.getString("authors");
                String asin = resultSet.getString("asin");

                // Escape double quotes for CSV and append each record
                csvContent.append("\"")
                        .append(word.replace("\"", "\"\""))
                        .append("\",\"")
                        .append(stem.replace("\"", "\"\""))
                        .append("\",\"")
                        .append(lang.replace("\"", "\"\""))
                        .append("\",\"")
                        .append(usage.replace("\"", "\"\""))
                        .append("\",\"")
                        .append(title.replace("\"", "\"\""))
                        .append("\",\"")
                        .append(authors.replace("\"", "\"\""))
                        .append("\",\"")
                        .append(asin.replace("\"", "\"\""))
                        .append("\"\n");
            }
        }

        return csvContent.toString();
    }
}
