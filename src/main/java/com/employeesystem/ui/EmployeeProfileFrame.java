package com.employeesystem.ui;

import com.employeesystem.dao.EmployeeDAO;
import com.employeesystem.model.Employee;
import com.employeesystem.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.employeesystem.ui.UITheme.*;

public class EmployeeProfileFrame extends JFrame {

    private final User currentUser;
    private final EmployeeDAO employeeDAO;

    public EmployeeProfileFrame(User currentUser) {
        super("Employee Profile");
        this.currentUser = currentUser;
        this.employeeDAO = new EmployeeDAO();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 360);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setBackground(BACKGROUND);
        root.setLayout(new BorderLayout());
        root.setBorder(new EmptyBorder(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CARD_BG);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(PADDING_SMALL, PADDING_MEDIUM, PADDING_SMALL, PADDING_MEDIUM)));

        JLabel title = new JLabel("My Profile");
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT);
        topBar.add(title, BorderLayout.WEST);

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

        root.add(topBar, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 6, 6, 6);

        Employee employee = employeeDAO.existsEmployeeByEmail(currentUser.getEmail())
                ? fetchEmployeeByEmail(currentUser.getEmail())
                : null;

        addRow(card, gbc, 0, "Employee ID", employee != null ? String.valueOf(employee.getEmpId()) : "-");
        addRow(card, gbc, 1, "Name", employee != null ? employee.getName() : "-");
        addRow(card, gbc, 2, "Department", employee != null ? employee.getDepartment() : "-");
        addRow(card, gbc, 3, "Email", employee != null ? employee.getEmail() : currentUser.getEmail());
        addRow(card, gbc, 4, "Salary", employee != null ? String.valueOf(employee.getSalary()) : "-");

        root.add(card, BorderLayout.CENTER);

        JLabel note = new JLabel("This view is read-only. Employees cannot modify records.");
        note.setFont(LABEL_FONT);
        note.setForeground(TEXT_MUTED);
        note.setBorder(new EmptyBorder(PADDING_SMALL, 0, 0, 0));
        root.add(note, BorderLayout.SOUTH);

        add(root, BorderLayout.CENTER);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel l = new JLabel(label + ":");
        l.setFont(LABEL_FONT);
        l.setForeground(TEXT);
        panel.add(l, gbc);

        gbc.gridx = 1;
        JLabel v = new JLabel(value != null ? value : "-");
        v.setFont(BODY_FONT);
        v.setForeground(TEXT);
        panel.add(v, gbc);
    }

    private Employee fetchEmployeeByEmail(String email) {
        // small helper to return the first employee row for that email
        for (Employee e : employeeDAO.searchEmployeesByIdOrName(email)) {
            if (email.equalsIgnoreCase(e.getEmail())) {
                return e;
            }
        }
        return null;
    }
}

