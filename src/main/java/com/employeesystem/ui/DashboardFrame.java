package com.employeesystem.ui;

import com.employeesystem.dao.EmployeeDAO;
import com.employeesystem.model.Employee;
import com.employeesystem.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

import com.employeesystem.ui.UITheme;
import static com.employeesystem.ui.UITheme.*;

public class DashboardFrame extends JFrame {

    private final User currentUser;
    private final EmployeeDAO employeeDAO;
    private final DefaultTableModel tableModel;
    private final JTable employeeTable;

    private final JTextField empIdField;
    private final JTextField nameField;
    private final JTextField departmentField;
    private final JTextField emailField;
    private final JTextField salaryField;
    private final JTextField searchField;

    public DashboardFrame(User currentUser) {
        super("Employee Dashboard");
        this.currentUser = currentUser;
        this.employeeDAO = new EmployeeDAO();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 640);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout(GAP, GAP));

        empIdField = new JTextField(10);
        nameField = new JTextField(20);
        departmentField = new JTextField(15);
        emailField = new JTextField(20);
        salaryField = new JTextField(10);
        searchField = new JTextField(15);

        for (JTextField f : new JTextField[]{empIdField, nameField, departmentField, emailField, salaryField, searchField}) {
            f.setFont(BODY_FONT);
        }

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CARD_BG);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(PADDING_SMALL, PADDING_MEDIUM, PADDING_SMALL, PADDING_MEDIUM)));

        JLabel heading = new JLabel("Employee Management");
        heading.setFont(TITLE_FONT);
        heading.setForeground(TEXT);
        topBar.add(heading, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("Signed in as " + currentUser.getUsername());
        userLabel.setFont(LABEL_FONT);
        userLabel.setForeground(TEXT_MUTED);
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setFont(LABEL_FONT);
        logoutBtn.setForeground(PRIMARY);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        });
        userPanel.add(userLabel);
        userPanel.add(logoutBtn);
        topBar.add(userPanel, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(GAP, GAP));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM));

        JPanel formCard = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(PADDING_SMALL, PADDING_MEDIUM, PADDING_SMALL, PADDING_MEDIUM)));

        formCard.add(newLabel("ID"));
        formCard.add(empIdField);
        formCard.add(newLabel("Name"));
        formCard.add(nameField);
        formCard.add(newLabel("Department"));
        formCard.add(departmentField);
        formCard.add(newLabel("Email"));
        formCard.add(emailField);
        formCard.add(newLabel("Salary"));
        formCard.add(salaryField);
        content.add(formCard, BorderLayout.NORTH);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        actions.setBackground(BACKGROUND);
        JButton addBtn = styledButton("Add", UITheme.PRIMARY);
        JButton updateBtn = styledButton("Update", UITheme.PRIMARY_DARK);
        JButton deleteBtn = styledButton("Delete", UITheme.ERROR);
        searchField.setColumns(14);
        JButton searchBtn = styledButton("Search", UITheme.TEXT);
        JButton refreshBtn = styledButton("Refresh", UITheme.TEXT_MUTED);

        boolean isAdmin = currentUser.getRole() == User.Role.ADMIN;
        boolean isNormalUser = currentUser.getRole() == User.Role.USER;

        addBtn.setEnabled(isAdmin || isNormalUser);
        updateBtn.setEnabled(isAdmin);
        deleteBtn.setEnabled(isAdmin);

        addBtn.addActionListener(e -> handleAdd());
        updateBtn.addActionListener(e -> handleUpdate());
        deleteBtn.addActionListener(e -> handleDelete());
        searchBtn.addActionListener(e -> handleSearch());
        refreshBtn.addActionListener(e -> loadEmployees());

        actions.add(addBtn);
        actions.add(updateBtn);
        actions.add(deleteBtn);
        actions.add(new JLabel("  "));
        actions.add(new JLabel("Search:"));
        actions.add(searchField);
        actions.add(searchBtn);
        actions.add(refreshBtn);
        content.add(actions, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[]{"Emp ID", "Name", "Department", "Email", "Salary"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeTable = new JTable(tableModel);
        employeeTable.setFont(BODY_FONT);
        employeeTable.setRowHeight(28);
        employeeTable.setSelectionBackground(new Color(230, 240, 255));
        employeeTable.setSelectionForeground(TEXT);
        employeeTable.setShowGrid(false);
        employeeTable.setIntercellSpacing(new Dimension(0, 0));
        JTableHeader header = employeeTable.getTableHeader();
        header.setFont(HEADING_FONT);
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Employees", 0, 0, HEADING_FONT, TEXT)));
        scrollPane.setBackground(CARD_BG);
        content.add(scrollPane, BorderLayout.SOUTH);

        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = employeeTable.getSelectedRow();
                if (row >= 0) {
                    empIdField.setText(String.valueOf(tableModel.getValueAt(row, 0)));
                    nameField.setText(String.valueOf(tableModel.getValueAt(row, 1)));
                    departmentField.setText(String.valueOf(tableModel.getValueAt(row, 2)));
                    emailField.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
                    salaryField.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
                }
            }
        });

        add(content, BorderLayout.CENTER);
        loadEmployees();
    }

    private JLabel newLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(LABEL_FONT);
        l.setForeground(TEXT);
        return l;
    }

    private JButton styledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(LABEL_FONT);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void handleAdd() {
        Employee emp = readEmployeeFromForm();
        if (emp == null) return;
        if (employeeDAO.addEmployee(emp)) {
            JOptionPane.showMessageDialog(this, "Employee added successfully.");
            loadEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add (ID may already exist).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        Employee emp = readEmployeeFromForm();
        if (emp == null) return;
        if (employeeDAO.updateEmployee(emp)) {
            JOptionPane.showMessageDialog(this, "Employee updated successfully.");
            loadEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        String empIdText = empIdField.getText().trim();
        if (empIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select an employee or enter ID to delete.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int empId = Integer.parseInt(empIdText);
            if (employeeDAO.deleteEmployee(empId)) {
                JOptionPane.showMessageDialog(this, "Employee deleted.");
                loadEmployees();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Employee ID must be a number.", "Validation", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            loadEmployees();
            return;
        }
        populateTable(employeeDAO.searchEmployeesByIdOrName(query));
    }

    private Employee readEmployeeFromForm() {
        String empIdText = empIdField.getText().trim();
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        String email = emailField.getText().trim();
        String salaryText = salaryField.getText().trim();

        if (empIdText.isEmpty() || name.isEmpty() || department.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID, Name, Department and Salary are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            int empId = Integer.parseInt(empIdText);
            double salary = Double.parseDouble(salaryText);
            Employee e = new Employee();
            e.setEmpId(empId);
            e.setName(name);
            e.setDepartment(department);
            e.setEmail(email.isEmpty() ? null : email);
            e.setSalary(salary);
            return e;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be integer, salary must be a number.", "Validation", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void loadEmployees() {
        populateTable(employeeDAO.getAllEmployees());
    }

    private void populateTable(List<Employee> employees) {
        tableModel.setRowCount(0);
        for (Employee e : employees) {
            tableModel.addRow(new Object[]{
                    e.getEmpId(),
                    e.getName(),
                    e.getDepartment(),
                    e.getEmail() != null ? e.getEmail() : "",
                    e.getSalary()
            });
        }
    }
}
