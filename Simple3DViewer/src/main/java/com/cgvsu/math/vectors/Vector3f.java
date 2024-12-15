package com.cgvsu.math.vectors;

public class Vector3f implements Vector<Vector3f> {
    private double x;
    private double y;
    private double z;

    public Vector3f(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f() {

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean isEqual(Vector3f other) {
        return Math.abs(x - other.x) < EPS &&
                Math.abs(y - other.y) < EPS &&
                 Math.abs(z - other.z) < EPS;
    }


    @Override
    public Vector3f add(Vector3f other) {
        return new Vector3f(x + other.x,
                            y + other.y,
                            z + other.z);
    }

    @Override
    public void add(Vector3f var1, Vector3f var2) {
        x = var1.x + var2.x;
        y = var1.y + var2.y;
        z = var1.z + var2.z;
    }

    @Override
    public final void subtract(Vector3f var1, Vector3f var2) {
        x = var1.x - var2.x;
        y = var1.y - var2.y;
        z = var1.z - var2.z;
    }
    @Override
    public final Vector3f subtract(Vector3f var1) {
        x = x - var1.x;
        y = y - var1.y;
        z = z - var1.z;
        return this;
    }


    @Override
    public void multiplyingVectorByScalar(double scalar) {
        x = x * scalar;
        y = y * scalar;
        z = z * scalar;
    }

    @Override
    public void dividingVectorByScalar(double scalar) {
        if(Math.abs(scalar) < EPS){
            throw new ArithmeticException("На 0 делить нельзя");
        }
        multiplyingVectorByScalar(1/scalar);
    }

    @Override
    public double getLength() {
        return Math.sqrt(
                        Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)
                        );
    }

    @Override
    public void normalize() {
        double length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Длина равна 0, вектор нормализовать нельзя");
        }
        x = x / length;
        y = y / length;
        z = z / length;
    }

    @Override
    public double scalarMultiplication(Vector3f other) {
        return (x * other.x +
                y * other.y +
                z * other.z);
    }

    public final void cross(Vector3f var1, Vector3f var2) {
        double tempX = var1.getY() * var2.getZ() - var1.getZ() * var2.getY();
        double tempY = var1.getZ() * var2.getX() - var1.getX() * var2.getZ();
        z = var1.getX() * var2.getY() - var1.getY() * var2.getX();
        x = tempX;
        y = tempY;
    }

    public Vector3f cross(Vector3f other) {
        return new Vector3f(y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x);
    }



    @Override
    public String toString() {
        return "Vector3f(" + x + ", " + y + ", " + z + ")";
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    public void setZ(float z) {
        this.z = z;
    }
}