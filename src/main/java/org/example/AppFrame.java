package org.example;

import javax.swing.*;

public class AppFrame extends JFrame {

    private boolean isLoginForm;
    private boolean isSignUpForm;
    public AppFrame() {
        this.setTitle("Banking App");
        this.setSize(400, 300);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new AppPanel());
        this.setVisible(true);




    }
}
