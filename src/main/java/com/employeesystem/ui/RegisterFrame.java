package com.employeesystem.ui;

import com.employeesystem.dao.UserDAO;
import com.employeesystem.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.employeesystem.ui.UITheme.*;

public class RegisterFrame extends JFrame {

    private final JTextField usernameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final UserDAO userDAO;

    public RegisterFrame() {
        super("Employee System — Register");
        this.userDAO = new UserDAO();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 480);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setBackground(BACKGROUND);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(PADDING_LARGE, PADDING_LARGE, PADDING_LARGE, PADDING_LARGE));

        JLabel titleLabel = new JLabel("Create account");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(titleLabel);
        center.add(Box.createVerticalStrut(6));

        JLabel subLabel = new JLabel("Register to access the Employee System");
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

        addField(card, "Username", usernameField = new JTextField(20));
        addField(card, "Email", emailField = new JTextField(20));
        addField(card, "Password", passwordField = new JPasswordField(20));
        addField(card, "Confirm password", confirmPasswordField = new JPasswordField(20));

        card.add(Box.createVerticalStrut(PADDING_MEDIUM));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(HEADING_FONT);
        registerButton.setBackground(PRIMARY);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setOpaque(true);
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, BUTTON_HEIGHT));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> handleRegister());
        card.add(registerButton);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    private void addField(JPanel card, String labelText, JComponent field) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(TEXT);
        card.add(lbl);
        card.add(Box.createVerticalStrut(4));
        field.setFont(BODY_FONT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
        card.add(field);
        card.add(Box.createVerticalStrut(GAP));
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        if (userDAO.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "Account created. You can sign in now.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email or username may already exist.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
