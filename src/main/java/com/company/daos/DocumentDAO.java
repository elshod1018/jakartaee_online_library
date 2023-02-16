package com.company.daos;

import com.company.domains.Document;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentDAO extends DAO<Document, Integer> {
    public static DocumentDAO instance;

    private final String INSERT_DOCUMENT = """
            insert into library.documents ( 
            generatedFileName,
            originalFileName,
            mimeType,
            filePath,
            fileSize,
            extension) values (?,?,?,?,?,?) returning id;""";
    private final String SELECT_BY_NEW_NAME = """
            select * from library.documents t where  t.generatedFileName = ?;""";
    private final String SELECT_BY_ID = """
            select * from library.documents t where  t.id = ?;""";

    @Override
    public Document save(Document document) {
        Connection connection = getConnection();
        try (var pr = connection.prepareStatement(INSERT_DOCUMENT)) {
            pr.setString(1, document.getGeneratedFileName());
            pr.setString(2, document.getOriginalFileName());
            pr.setString(3, document.getMimeType());
            pr.setString(4, document.getFilePath());
            pr.setLong(5, document.getFileSize());
            pr.setString(6, document.getExtension());
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                document.setId(rs.getInt("id"));
                return document;
            } else {
                throw new RuntimeException("Internal server error");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Document get(Integer integer) {
        return null;
    }

    @Override
    public boolean update(Document document) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    public Document findByGeneratedName(@NonNull String filename) {
        Connection connection = getConnection();
        try (var pr = connection.prepareStatement(SELECT_BY_NEW_NAME)) {
            pr.setString(1, filename);
            ResultSet resultSet = pr.executeQuery();
            Document document = null;
            if (resultSet.next()) {
                document = Document.builder()
                        .id(resultSet.getInt("id"))
                        .generatedFileName(resultSet.getString("generatedFileName"))
                        .originalFileName(resultSet.getString("originalFileName"))
                        .mimeType(resultSet.getString("mimeType"))
                        .filePath(resultSet.getString("filePath"))
                        .extension(resultSet.getString("extension"))
                        .fileSize(resultSet.getLong("fileSize"))
                        .build();
            }
            return document;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DocumentDAO getInstance() {
        if (instance == null) {
            synchronized (DocumentDAO.class) {
                if (instance == null) {
                    instance = new DocumentDAO();
                }
            }
        }
        return instance;
    }
}
