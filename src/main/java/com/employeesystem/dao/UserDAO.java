package com.employeesystem.dao;

import com.employeesystem.db.DBConnection;
import com.employeesystem.model.User;
import com.employeesystem.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, PasswordUtil.hash(user.getPassword()));
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public User login(String emailOrUsername, String password) {
        String sql = "SELECT id, username, email, password FROM users WHERE email = ? OR username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emailOrUsername);
            stmt.setString(2, emailOrUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && PasswordUtil.verify(password, rs.getString("password"))) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(null); // never pass back stored hash
                    user.setRole(determineRole(user));
                    return user;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Determine role after successful authentication.
     * - ADMIN: username/email matches admin
     * - EMPLOYEE: email exists in employees table
     * - USER: default
     */
    private User.Role determineRole(User user) {
        String username = user.getUsername();
        String email = user.getEmail();

        if (username != null && username.equalsIgnoreCase("admin")) {
            return User.Role.ADMIN;
        }
        if (email != null && email.equalsIgnoreCase("admin@email.com")) {
            return User.Role.ADMIN;
        }

        EmployeeDAO employeeDAO = new EmployeeDAO();
        if (email != null && employeeDAO.existsEmployeeByEmail(email)) {
            return User.Role.EMPLOYEE;
        }

        return User.Role.USER;
    }
}
