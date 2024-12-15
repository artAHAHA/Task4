package com.cgvsu.math.vectors;

public class Vector2f implements Vector<Vector2f> {
    private double x;
    private double y;

    public Vector2f(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean isEqual(Vector2f other) {
        return Math.abs(x - other.x) < EPS &&
                Math.abs(y - other.y) < EPS;
    }

    @Override
    public Vector2f add(Vector2f other) {
        return new Vector2f(x + other.x,
                y + other.y);
    }
    @Override
    public void add(Vector2f var1, Vector2f var2) {
        x = var1.x + var2.x;
        y = var1.y + var2.y;
    }

    @Override
    public final void subtract(Vector2f var1, Vector2f var2) {
        x = var1.x - var2.x;
        y = var1.y - var2.y;
    }

    @Override
    public Vector2f subtract(Vector2f var1) {
        x = x - var1.x;
        y = y - var1.y;
        return this;
    }


    @Override
    public void multiplyingVectorByScalar(double scalar) {
        x = x * scalar;
        y = y * scalar;
    }

    @Override
    public void dividingVectorByScalar(double scalar) {
        if (Math.abs(scalar) < EPS) {
            throw new ArithmeticException("На 0 делить нельзя");
        }
        multiplyingVectorByScalar(1 / scalar);
    }

    @Override
    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public void normalize() {
        double length = getLength();
        if (length < EPS) {
            throw new ArithmeticException("Длина равна 0, вектор нормализовать нельзя");
        }
        x = x / length;
        y = y / length;
    }

    @Override
    public double scalarMultiplication(Vector2f other) {
        return (x * other.x + y * other.y);
    }

    @Override
    public String toString() {
        return "Vector2f(" + x + ", " + y + ")";
    }
}
