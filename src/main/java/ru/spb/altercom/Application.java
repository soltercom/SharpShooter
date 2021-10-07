package ru.spb.altercom;

import javax.swing.*;

public class Application extends JFrame {

    public Application() {
        init();
    }

    private void init() {

        var canvas = new Canvas(this);
        add(canvas);
        canvas.start();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sharp shooter");
        setSize(Controller.WIDTH, Controller.HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }

}
