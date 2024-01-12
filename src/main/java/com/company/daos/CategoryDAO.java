package com.company.daos;

import com.company.domains.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDAO extends DAO<Category, Integer> {
    private static CategoryDAO instance;
    private static final String FIND_BY_ID = "select * from library.categories t where t.isDeleted = 0 and t.id = ?;";

    @Override
    protected Category get(Integer integer) {
        return null;
    }

    private static final String FIND_ALL_QUERY = "select * from library.categories;";
    private static final String UPDATE_CATEGORY_QUERY = """
            update library.categories set name = ? where id = ?;""";
    private static final String TOTAL_COUNT = "select count(0) from library.categories c where c.isDeleted = 0 ;";
    private static final String INSERT_CATEGORY_QUERY = """
            insert into library.categories (name) values (?) returning id;""";
    private static final String DELETE_BY_ID_QUERY = "update from library.categories set isDeleted = 1 where id = ?;";

    @Override
    public Category save(Category category) {
        Connection connection = getConnection();
        try (PreparedStatement pS = connection.prepareStatement(INSERT_CATEGORY_QUERY)) {
            pS.setString(1, category.getName());
            ResultSet rs = pS.executeQuery();
            if (rs.next()) {
                category.setId(rs.getInt(" id"));
                return category;
            } else {
                throw new RuntimeException("Exception");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        Connection connection = getConnection();
        try (PreparedStatement pr = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                categories.add(Category.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public Category findById(Short categoryId) {
        Connection connection = getConnection();
        try (PreparedStatement pr = connection.prepareStatement(FIND_BY_ID)) {
            pr.setLong(1, categoryId);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return Category.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        Connection connection = getConnection();
        try (PreparedStatement pr = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            pr.setInt(1, id);
            pr.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(@NonNull Category category) {
        Connection connection = getConnection();
        try (PreparedStatement pr = connection.prepareStatement(UPDATE_CATEGORY_QUERY)) {
            pr.setString(1, category.getName());
            pr.setInt(2, category.getId());
            pr.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean delete(Integer integer) {
        return false;
    }

    public Long totalCount() {
        Connection connection = getConnection();
        try (PreparedStatement pr = connection.prepareStatement(TOTAL_COUNT)) {
            ResultSet rs = pr.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

    public static CategoryDAO getInstance() {
        if (instance == null) {
            synchronized (CategoryDAO.class) {
                if (instance == null) {
                    instance = new CategoryDAO();
                }
            }
        }
        return instance;
    }
}
