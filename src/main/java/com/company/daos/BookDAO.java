package com.company.daos;

import com.company.domains.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.dtos.BookDetailsDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookDAO extends DAO<Book, Integer> {
    private static BookDAO instance;

    private static final String SELECT_ALL = """
            select b.id, b.title, b.author,b.description, b.views,b.likes, b.dislikes, b.downloads,b.pages,b.publisher,
             d.originalFileName as coverOriginalFileName ,d.generatedFileName as coverGeneratedFileName,d.fileSize as coverFileSize, 
             d2.originalFileName as documentOriginalFileName ,d2.generatedFileName as documentGeneratedFileName,d2.fileSize as documentFileSize,
             c.name as category from library.books b 
             inner join library.documents d on b.coverId = d.id 
             inner join library.documents d2 on b.documentId = d2.id 
             inner join library.categories c on b.categoryId = c.id
             where b.isDeleted = 0;""";
    private static final String SELECT_BY_ID = """
            select b.id, b.title, b.author,b.description, b.views,b.likes, b.dislikes, b.downloads,b.pages,b.publisher,
             d.originalFileName as coverOriginalFileName ,d.generatedFileName as coverGeneratedFileName,d.fileSize as coverFileSize, 
             d2.originalFileName as documentOriginalFileName ,d2.generatedFileName as documentGeneratedFileName,d2.fileSize as documentFileSize,
             c.name as category from library.books b 
             inner join library.documents d on b.coverId = d.id 
             inner join library.documents d2 on b.documentId = d2.id 
             inner join library.categories c on b.categoryId = c.id
             where b.isDeleted = 0 and b.categoryId = ?;""";
    private static final String INSERT_QUERY = """
            insert into library.books (
                        title,
                        author,
                        categoryId,
                        pages,
                        publisher,
                        publishedAt,
                        coverId,
                        documentId,
                        description
            ) values (?, ?, ?, ?, ?, ?, ?, ?,?) returning id;""";


    @Override
    public Book save(Book book) {
        Connection connection = getConnection();
        try (var pr = connection.prepareStatement(INSERT_QUERY)) {
            pr.setString(1, book.getTitle());
            pr.setString(2, book.getAuthor());
            pr.setInt(3, book.getCategoryId());
            pr.setInt(4, book.getPages());
            pr.setString(5, book.getPublisher());
            pr.setDate(6, Date.valueOf(book.getPublishedAt()));
            pr.setInt(7, book.getCoverId());
            pr.setInt(8, book.getDocumentId());
            pr.setString(9, book.getDescription());
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                book.setId(rs.getInt("id"));
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book get(Integer integer) {
        return null;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }


    public List<BookDetailsDTO> findAll() {
        Connection connection = getConnection();
        List<BookDetailsDTO> books = new ArrayList<>();
        try (var pr = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()) {
                books.add(BookDetailsDTO.builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .author(resultSet.getString("author"))
                        .description(resultSet.getString("description"))
                        .pages(resultSet.getInt("pages"))
                        .likes(resultSet.getInt("likes"))
                        .dislikes(resultSet.getInt("dislikes"))
                        .views(resultSet.getInt("views"))
                        .category(resultSet.getString("category"))
                        .publisher(resultSet.getString("publisher"))
                        .coverOriginalFileName(resultSet.getString("coverOriginalFileName"))
                        .coverGeneratedFileName(resultSet.getString("coverGeneratedFileName"))
                        .coverFileSize((resultSet.getLong("coverFileSize") / Math.pow(2, 20)) + "MB")
                        .documentOriginalFileName(resultSet.getString("documentOriginalFileName"))
                        .documentGeneratedFileName(resultSet.getString("documentGeneratedFileName"))
                        .documentFileSize("%.5s%s".formatted(resultSet.getLong("documentFileSize") / Math.pow(2, 20), " MB"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<BookDetailsDTO> findById(Integer id) {
        Connection connection = getConnection();
        List<BookDetailsDTO> books = new ArrayList<>();
        try (var pr = connection.prepareStatement(SELECT_BY_ID)) {
            pr.setLong(1, id);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()) {
                books.add(BookDetailsDTO.builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .author(resultSet.getString("author"))
                        .description(resultSet.getString("description"))
                        .pages(resultSet.getInt("pages"))
                        .likes(resultSet.getInt("likes"))
                        .dislikes(resultSet.getInt("dislikes"))
                        .views(resultSet.getInt("views"))
                        .category(resultSet.getString("category"))
                        .publisher(resultSet.getString("publisher"))
                        .coverOriginalFileName(resultSet.getString("coverOriginalFileName"))
                        .coverGeneratedFileName(resultSet.getString("coverGeneratedFileName"))
                        .coverFileSize((resultSet.getLong("coverFileSize") / Math.pow(2, 20)) + "MB")
                        .documentOriginalFileName(resultSet.getString("documentOriginalFileName"))
                        .documentGeneratedFileName(resultSet.getString("documentGeneratedFileName"))
                        .documentFileSize("%.5s%s".formatted(resultSet.getLong("documentFileSize") / Math.pow(2, 20), " MB"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static BookDAO getInstance() {
        if (instance == null) {
            synchronized (BookDAO.class) {
                if (instance == null) {
                    instance = new BookDAO();
                }
            }
        }
        return instance;
    }
}
