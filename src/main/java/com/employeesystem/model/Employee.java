package com.employeesystem.model;

public class Employee {
    private int empId;
    private String name;
    private String department;
    private String email;
    private double salary;

    public Employee() {
    }

    public Employee(int empId, String name, String department, String email, double salary) {
        this.empId = empId;
        this.name = name;
        this.department = department;
        this.email = email;
        this.salary = salary;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}

