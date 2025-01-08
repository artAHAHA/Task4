package com.cgvsu.math.points;

import javafx.beans.NamedArg;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2f point2f = (Point2f) o;
        return Float.compare(x, point2f.x) == 0 && Float.compare(y, point2f.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
