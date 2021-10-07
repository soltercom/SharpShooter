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
    private static final int SIGHT_VELOCITY = 2;

    public static final int BULLET_HOLE_RADIUS = 5;

    public final List<BulletHole> bulletHoleList = new ArrayList<>();

    private int sightX;
    private int sightY;

    public Controller() {
        sightX = 200;
        sightY = 200;
    }

    public void moveSightLeft() {
        moveSight(-1, 0);
    }

    public void moveSightRight() {
        moveSight(1, 0);
    }

    public void moveSightUp() {
        moveSight(0, -1);
    }

    public void moveSightDown() {
        moveSight(0, 1);
    }

    public void addBulletHole() {
        var point = new Point2D.Double(getSightX() + SIGHT_RADIUS - BULLET_HOLE_RADIUS,
                                       getSightY() + SIGHT_RADIUS - BULLET_HOLE_RADIUS);
        bulletHoleList.add(new BulletHole(point));
    }

    private void moveSight(int dx, int dy) {
        this.sightX += SIGHT_VELOCITY * dx;
        this.sightX = Math.max(10, this.sightX);
        this.sightX = Math.min(WIDTH - 2 * SIGHT_RADIUS - 20, this.sightX);

        sightY += SIGHT_VELOCITY * dy;
        sightY = Math.max(10, sightY);
        sightY = Math.min(HEIGHT - 2 * SIGHT_RADIUS - 50, sightY);
    }

    public int getSightX() {
        return sightX;
    }

    public int getSightY() {
        return sightY;
    }

}
