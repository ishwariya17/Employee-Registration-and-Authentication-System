database.sql-- Create Database
CREATE DATABASE IF NOT EXISTS employee_system;
-- Use Database
USE employee_system;
-- Users Table
CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL
);
-- Employees Table
CREATE TABLE employees (
emp_id INT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
department VARCHAR(100) NOT NULL,
email VARCHAR(100),
salary DOUBLE
);
-- Sample Data (Optional)
INSERT INTO users (username, email, password)
VALUES
('admin', 'admin@email.com', 'admin123');
INSERT INTO employees (emp_id, name, department, email, salary)
VALUES
(101, 'John Doe', 'IT', 'john@email.com', 50000),
(102, 'Jane Smith', 'HR', 'jane@email.com', 45000),
(103, 'Robert Brown', 'Finance', 'robert@email.com', 6000
