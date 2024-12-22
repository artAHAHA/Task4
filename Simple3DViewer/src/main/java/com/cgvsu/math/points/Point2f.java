package com.cgvsu.math.points;

import javafx.beans.NamedArg;

public class Point2f {
    private float x;
    private float y;

    public Point2f(@NamedArg("x") float x, @NamedArg("y") float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double distance(double x, double y) {
        double distanceForX = this.getX() - x;
        double distanceForY = this.getY() - y;
        return Math.sqrt(distanceForX * distanceForX + distanceForY * distanceForY);
    }
}
