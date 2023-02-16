package com.company.daos;

import com.company.domains.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class UserDAO extends DAO<User, Integer> {
    private static UserDAO instance;

    private static final String SELECT_BY_ID = """
            select * from library.users u where u.id = ?""";
    private static final String SELECT_BY_EMAIL = """
            select * from library.users u where u.email = ?""";

    @Override
    protected User save(User user) {
        return null;
    }

    @Override
    public User get(Integer id) {
        Connection connection = getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return User.builder()
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User get(String email) {
        Connection connection = getConnection();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return User.builder()
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .role(resultSet.getString("role"))
                        .build();
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean update(User user) {
        return false;
    }

    @Override
    protected boolean delete(Integer integer) {
        return false;
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            synchronized (UserDAO.class) {
                if (instance == null) {
                    instance = new UserDAO();
                }
            }
        }
        return instance;
    }
}
