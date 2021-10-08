package ru.spb.altercom;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public enum GAME_STATE {
        INIT,
        STARTED,
        OVER
    }

    public static final int WIDTH = 720;
    public static final int HEIGHT = 750;

    public static final int TARGET_CIRCLE_STEP = 30;
    public static final int TARGET_CENTER = 350;

    public static final int SIGHT_RADIUS = 40;
    public static final int SIGHT_INSET = (int) (0.8 * SIGHT_RADIUS);

    public static final int BULLET_HOLE_RADIUS = 5;

    public final List<BulletHole> bulletHoleList = new ArrayList<>();

    private final static int MAX_SHOOTING_ATTEMPTS = 10;

    private GAME_STATE gameState = GAME_STATE.INIT;

    private int sightX;
    private int sightY;
    private int shootCounter;
    private double lastScore;
    private double totalScore;

    private final JLabel statusbar;
    private final Canvas canvas;

    private final Force force;

    public Controller() {
        this.statusbar = new JLabel();
        this.canvas = new Canvas(this);
        this.force = new Force();

        initGame();
    }

    public JLabel getStatusbar() {
        return statusbar;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void initialPosition() {
        sightX = TARGET_CENTER - TARGET_CIRCLE_STEP * 10;
        sightY = TARGET_CENTER - TARGET_CIRCLE_STEP * 10;
    }

    private void updateStatusbar() {
        switch (gameState) {
            case INIT:
                statusbar.setText("Press SPACE button to start the game.");
                break;
            case STARTED:
                statusbar.setText(String.format("Shootings left: %d, Your score: %.1f (%.1f). Use: \u2192 \u2190 \u2191 \u2193 SPACE buttons.",
                        MAX_SHOOTING_ATTEMPTS - shootCounter, totalScore, lastScore));
                break;
            case OVER:
                statusbar.setText(String.format("Game over. Your score: %.1f.", totalScore));
                break;
        }
    }

    private void initGame() {
        initialPosition();
        shootCounter = 0;
        updateStatusbar();
    }

    private void startGame() {
        gameState = GAME_STATE.STARTED;
        updateStatusbar();
        canvas.start();
    }

    private void stopGame() {
        gameState = GAME_STATE.OVER;
        updateStatusbar();
        canvas.stop();
    }

    private void calcScore(Point2D.Double point) {
        var dist = new Point2D.Double(TARGET_CENTER, TARGET_CENTER).distance(point);
        var str = String.format("%.1f", Math.max(11 - dist / TARGET_CIRCLE_STEP, 0));

        lastScore = Double.parseDouble(str.replace(",", "."));
        totalScore += lastScore;

        updateStatusbar();
    }

    public void moveSightLeft() {
        force.addLeftMovement();
    }

    public void moveSightRight() {
        force.addRightMovement();
    }

    public void moveSightUp() {
        force.addUpMovement();
    }

    public void moveSightDown() {
        force.addDownMovement();
    }

    public void addBulletHole() {
        if (gameState == GAME_STATE.INIT) {
            startGame();
            return;
        }

        var point = new Point2D.Double(getSightX() + SIGHT_RADIUS - BULLET_HOLE_RADIUS,
                                       getSightY() + SIGHT_RADIUS - BULLET_HOLE_RADIUS);
        bulletHoleList.add(new BulletHole(point));
        shootCounter++;
        calcScore(new Point2D.Double(getSightX() + SIGHT_RADIUS, getSightY() + SIGHT_RADIUS));
        initialPosition();
        updateStatusbar();

        if (shootCounter >= MAX_SHOOTING_ATTEMPTS) {
            stopGame();
        }
    }

    public boolean addRandomMovement() {
        return force.addRandomMovement();
    }

    public boolean moveSight() {
        var velocity = force.getVelocity();

        this.sightX += (int) velocity.getX();
        this.sightX = Math.max(10, getSightX());
        this.sightX = Math.min(WIDTH - 2 * SIGHT_RADIUS - 20, getSightX());

        sightY += (int) velocity.getY();
        sightY = Math.max(10, getSightY());
        sightY = Math.min(HEIGHT - 2 * SIGHT_RADIUS - 50, getSightY());

        return force.hasForces();
    }

    public int getSightX() {
        return sightX;
    }

    public int getSightY() {
        return sightY;
    }

}
