package com.cgvsu.math.vectors;

public class Vector4f implements Vector<Vector4f> {
    private double x;
    private double y;
    private double z;
    private double w;
    public Vector4f(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(){

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

    public double getW(){
        return w;
    }

    @Override
    public boolean isEqual(Vector4f other) {
        return Math.abs(x - other.x) < EPS &&
                Math.abs(y - other.y) < EPS &&
                 Math.abs(z - other.z) < EPS &&
                  Math.abs(w-other.w) < EPS;
    }

    @Override
    public Vector4f add(Vector4f other) {
        return new Vector4f(x + other.x,
                y + other.y,
                z + other.z,
                w + other.w);
    }

    @Override
    public void add(Vector4f var1, Vector4f var2) {
        x = var1.x + var2.x;
        y = var1.y + var2.y;
        z = var1.z + var2.z;
        w = var1.w + var2.w;
    }

    @Override
    public void subtract(Vector4f var1, Vector4f var2) {
        x = var1.x - var2.x;
        y = var1.y - var2.y;
        z = var1.z - var2.z;
        w = var1.w - var2.w;
    }

    @Override
    public Vector4f subtract(Vector4f var1) {
        x = x - var1.x;
        y = y - var1.y;
        z = z - var1.z;
        w = w - var1.w;
        return this;
    }


    @Override
    public void multiplyingVectorByScalar(double scalar) {
        x = x * scalar;
        y = y * scalar;
        z = z * scalar;
        w = w * scalar;
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
                        Math.pow(z, 2) +
                        Math.pow(w, 2)
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
        w = w / length;
    }

    @Override
    public double scalarMultiplication(Vector4f other) {
        return (x * other.x +
                y * other.y +
                z * other.z +
                w * other.w);
    }
    @Override
    public String toString() {
        return "Vector3f(" + x + ", " + y + ", " + z + ", " + w + ")";
    }
}
