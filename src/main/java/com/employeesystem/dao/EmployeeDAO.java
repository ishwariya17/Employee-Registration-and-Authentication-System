package com.employeesystem.dao;

import com.employeesystem.db.DBConnection;
import com.employeesystem.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (emp_id, name, department, email, salary) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employee.getEmpId());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getDepartment());
            stmt.setString(4, employee.getEmail());
            stmt.setDouble(5, employee.getSalary());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET name = ?, department = ?, email = ?, salary = ? WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getDepartment());
            stmt.setString(3, employee.getEmail());
            stmt.setDouble(4, employee.getSalary());
            stmt.setInt(5, employee.getEmpId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee e = new Employee();
                e.setEmpId(rs.getInt("emp_id"));
                e.setName(rs.getString("name"));
                e.setDepartment(rs.getString("department"));
                e.setEmail(rs.getString("email"));
                e.setSalary(rs.getDouble("salary"));
                employees.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    public List<Employee> searchEmployeesByIdOrName(String query) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE emp_id = ? OR name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int id;
            try {
                id = Integer.parseInt(query);
            } catch (NumberFormatException e) {
                id = -1;
            }
            stmt.setInt(1, id);
            stmt.setString(2, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEmpId(rs.getInt("emp_id"));
                    e.setName(rs.getString("name"));
                    e.setDepartment(rs.getString("department"));
                    e.setEmail(rs.getString("email"));
                    e.setSalary(rs.getDouble("salary"));
                    employees.add(e);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    /**
     * Returns true if there is at least one employee row with the given email.
     * Used for determining whether a logged in user is an EMPLOYEE role.
     */
    public boolean existsEmployeeByEmail(String email) {
        String sql = "SELECT 1 FROM employees WHERE email = ? LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

