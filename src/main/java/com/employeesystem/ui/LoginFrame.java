package com.employeesystem.ui;

import com.employeesystem.dao.UserDAO;
import com.employeesystem.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.employeesystem.ui.UITheme.*;

public class LoginFrame extends JFrame {

    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final UserDAO userDAO;

    public LoginFrame() {
        super("Employee System — Login");
        this.userDAO = new UserDAO();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 380);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setBackground(BACKGROUND);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(PADDING_LARGE, PADDING_LARGE, PADDING_LARGE, PADDING_LARGE));

        JLabel titleLabel = new JLabel("Welcome back");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(titleLabel);
        center.add(Box.createVerticalStrut(6));

        JLabel subLabel = new JLabel("Sign in to continue to the Employee System");
        subLabel.setFont(LABEL_FONT);
        subLabel.setForeground(TEXT_MUTED);
        subLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(subLabel);
        center.add(Box.createVerticalStrut(PADDING_MEDIUM));

        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM)));

        JLabel emailLbl = new JLabel("Email or username");
        emailLbl.setFont(LABEL_FONT);
        emailLbl.setForeground(TEXT);
        card.add(emailLbl);
        card.add(Box.createVerticalStrut(4));
        emailField = new JTextField(20);
        emailField.setFont(BODY_FONT);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
        card.add(emailField);
        card.add(Box.createVerticalStrut(GAP));

        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(LABEL_FONT);
        passLbl.setForeground(TEXT);
        card.add(passLbl);
        card.add(Box.createVerticalStrut(4));
        passwordField = new JPasswordField(20);
        passwordField.setFont(BODY_FONT);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(PADDING_MEDIUM));

        JButton loginButton = new JButton("Sign in");
        loginButton.setFont(HEADING_FONT);
        loginButton.setBackground(PRIMARY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, BUTTON_HEIGHT));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());
        card.add(loginButton);
        card.add(Box.createVerticalStrut(PADDING_SMALL));

        JButton registerButton = new JButton("Create an account");
        registerButton.setFont(LABEL_FONT);
        registerButton.setForeground(PRIMARY);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new RegisterFrame().setVisible(true));
        });
        card.add(registerButton);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    private void handleLogin() {
        String emailOrUsername = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (emailOrUsername.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email/username and password.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userDAO.login(emailOrUsername, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid email/username or password.",
                    "Sign in failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            switch (user.getRole()) {
                case ADMIN:
                case USER:
                    new DashboardFrame(user).setVisible(true);
                    break;
                case EMPLOYEE:
                    new EmployeeProfileFrame(user).setVisible(true);
                    break;
                default:
                    new DashboardFrame(user).setVisible(true);
            }
        });
        dispose();
    }
}
