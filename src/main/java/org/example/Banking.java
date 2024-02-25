package org.example;

import javax.swing.*;

public class Banking extends JFrame {
    public Banking() {
        this.setTitle("Banking App");
        this.setSize(400, 300);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.add(new AppPanel());
        this.add(new RegisterPanel());
        this.setVisible(true);
    }
}
