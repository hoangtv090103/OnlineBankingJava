package org.example;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class RegisterPanel extends JPanel {
    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JPasswordField confirmPasswordField = new JPasswordField();

    public RegisterPanel() {
        JLabel usernameLbl = new JLabel("Username: ");
        JLabel passwordLbl = new JLabel("Password: ");
        JLabel confirmPasswordLbl = new JLabel("Confirm password: ");

        JButton registerBtn = new JButton("Register");

        registerBtn.addActionListener(e -> {
            try {
                registerUser();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(usernameLbl);
        this.add(usernameField);
        this.add(passwordLbl);
        this.add(passwordField);
        this.add(confirmPasswordLbl);
        this.add(confirmPasswordField);
        this.add(registerBtn);
    }

    public void registerUser() throws SQLException {
        ConnectJDBC connectJDBC = new ConnectJDBC();
        Connection conn = connectJDBC.connect();
        Users users = new Users(conn);
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match");
        } else {

            ResultSet user = null;
            HashMap<String, String> valList = new HashMap<>() {{
                put("username", username);
                put("password", password);
            }};
            try {
                user = users.create(valList);
                if (user != null && user.next()) {
                    JOptionPane.showMessageDialog(null, "User created successfully");
                }
                else if (user == null) {
                    JOptionPane.showMessageDialog(null, "User already exists");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        conn.close();
    }
}