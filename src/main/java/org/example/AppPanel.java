package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AppPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private boolean isRegisterMode = false;
    JButton registerModeButton = new JButton("Create Account");
    JButton loginModeButton = new JButton("Already have an account? Login");

    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");

    private Connection conn;

    public AppPanel() {
        this.usernameField = new JTextField(20);
        this.passwordField = new JPasswordField(20);
        this.confirmPasswordField = new JPasswordField(20);

        this.add(new JLabel("Username:"));
        this.add(this.usernameField);
        this.add(new JLabel("Password:"));
        this.add(this.passwordField);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Users users = new Users(conn);
            try {
                ResultSet res = users.login(username, password);

                // Update the UI
                this.revalidate();
                this.repaint();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        loginModeButton.addActionListener(e -> {
            isRegisterMode = false;
            this.remove(loginModeButton);
            this.add(registerModeButton);
            this.add(loginButton);
            this.remove(registerButton);
            this.remove(confirmPasswordField);
            this.revalidate();
            this.repaint();
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                // show error message
                return;
            }

            Users users = new Users(conn);
            HashMap<String, String> valList = new HashMap<>();
            valList.put("username", username);
            valList.put("password", password);
            try {
                ResultSet res = users.create(valList);
                // handle the result

                // Update the UI
                this.revalidate();
                this.repaint();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        registerModeButton.addActionListener(e -> {
            isRegisterMode = true;
            this.remove(registerModeButton);
            this.add(loginModeButton);
            this.add(new JLabel("Confirm Password:"));
            this.add(confirmPasswordField);
            this.remove(loginButton);
            this.add(registerButton);
            this.revalidate();
            this.repaint();
        });

        if (isRegisterMode) {
            this.add(registerModeButton);
            this.add(new JLabel("Confirm Password:"));
            this.add(confirmPasswordField);
            this.add(registerButton);
        } else {
            this.add(loginModeButton);
            this.add(loginButton);
        }

    }
}
