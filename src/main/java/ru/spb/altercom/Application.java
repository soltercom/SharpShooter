package ru.spb.altercom;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    private final Controller controller;

    public Application() {
        controller = new Controller();
        initUI();
        init();
    }

    private void initUI() {
        add(controller.getStatusbar(), BorderLayout.SOUTH);
        add(controller.getCanvas());
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sharp shooter");
        setSize(Controller.WIDTH, Controller.HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }

}
