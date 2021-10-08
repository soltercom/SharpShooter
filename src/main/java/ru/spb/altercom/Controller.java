package ru.spb.altercom;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 750;

    public static final int TARGET_CIRCLE_STEP = 30;
    public static final int TARGET_CENTER = 350;

    public static final int SIGHT_RADIUS = 40;
    public static final int SIGHT_INSET = (int) (0.8 * SIGHT_RADIUS);

    public static final int BULLET_HOLE_RADIUS = 5;

    public final List<BulletHole> bulletHoleList = new ArrayList<>();

    private int sightX;
    private int sightY;

    private final Force force;

    public Controller() {
        initialPosition();
        this.force = new Force();
    }

    private void initialPosition() {
        sightX = TARGET_CENTER - TARGET_CIRCLE_STEP * 10;
        sightY = TARGET_CENTER - TARGET_CIRCLE_STEP * 10;
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
        var point = new Point2D.Double(getSightX() + SIGHT_RADIUS - BULLET_HOLE_RADIUS,
                                       getSightY() + SIGHT_RADIUS - BULLET_HOLE_RADIUS);
        bulletHoleList.add(new BulletHole(point));
        initialPosition();
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
