package ru.spb.altercom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Canvas extends JPanel implements ActionListener {

    private final Timer timer = new Timer(DELAY, this);
    private final Controller controller = new Controller();

    private boolean isChanged;

    private final static int DELAY = 20;

    public Canvas(Application app) {
        init();
    }

    private void init() {
        setFocusable(true);
        addKeyListener(new TAdapter());
    }

    public void start() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isChanged) {
            repaint();
            isChanged = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(Target.IMAGE, 0, 0, null);
        for (var bulletHole: controller.bulletHoleList) {
            g.drawImage(BulletHole.IMAGE,
                    (int) bulletHole.getPoint().getX(),
                    (int) bulletHole.getPoint().getY(), null);
        }
        g.drawImage(Sight.IMAGE, controller.getSightX(), controller.getSightY(), null);
    }

    private class TAdapter extends KeyAdapter {

        private final Set<Integer> pressedKeys = new HashSet<>();

        @Override
        public void keyPressed(KeyEvent e) {
            pressedKeys.add(e.getKeyCode());
            if (pressedKeys.isEmpty()) {
                return;
            }

            for (var key: pressedKeys) {
                var dx = 0;
                var dy = 0;

                if (key == KeyEvent.VK_LEFT) {
                    dx--;
                    controller.moveSightLeft();
                }

                if (key == KeyEvent.VK_RIGHT) {
                    dx++;
                    controller.moveSightRight();
                }

                if (key == KeyEvent.VK_UP) {
                    dy--;
                    controller.moveSightUp();
                }

                if (key == KeyEvent.VK_DOWN) {
                    dy++;
                    controller.moveSightDown();
                }

                if (key == KeyEvent.VK_SPACE) {
                    controller.addBulletHole();
                    isChanged = true;
                }

                if (dx != 0 || dy != 0) {
                    isChanged = true;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            pressedKeys.remove(e.getKeyCode());
        }
    }

}
