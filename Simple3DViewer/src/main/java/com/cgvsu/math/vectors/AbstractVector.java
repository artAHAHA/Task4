package com.cgvsu.math.vectors;

import java.util.Arrays;

public abstract class AbstractVector<T extends AbstractVector<T>> {
    protected final double[] components;
    public static final double EPS = 1e-7;
    private double x;
    private double y;
    private double z;
    private double w;

    public AbstractVector(double... components) {
        this.components = Arrays.copyOf(components, components.length);
    }

    public int getDimension() {
        return components.length;
    }

    public double get(int index) {
        return components[index];
    }

    public double getX() {
        return components[0];
    }

    public double getY() {
        return components[1];
    }

    public double getZ() {
        return components[2];
    }

    public double getW() {
        return components[3];
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double[] getComponents() {
        return components.clone();
    }

    public void set(int index, double value) {
        components[index] = value;
    }

    public boolean isEqual(T other) {
        if (getDimension() != other.getDimension()) {
            return false;
        }
        for (int i = 0; i < components.length; i++) {
            if (Math.abs(components[i] - other.get(i)) >= EPS) {
                return false;
            }
        }
        return true;
    }

    public T add(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double[] result = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            result[i] = components[i] + other.get(i);
        }
        return createInstance(result);
    }

    public void addV(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double[] result = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            result[i] = components[i] + other.get(i);
        }
    }

    public T subtract(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double[] result = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            result[i] = components[i] - other.get(i);
        }
        return createInstance(result);
    }

    public void subtractV(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double[] result = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            result[i] = components[i] - other.get(i);
        }

    }


    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < getDimension(); i++) {
            components[i] *= scalar;
        }
    }

    public void divideByScalar(double scalar) {
        if (Math.abs(scalar) < EPS) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        for (int i = 0; i < getDimension(); i++) {
            components[i] /= scalar;
        }
    }

    public double getLength() {
        double sum = 0;
        for (double component : components) {
            sum += component * component;
        }
        return Math.sqrt(sum);
    }

    public void normalize() {
        double length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a zero-length vector");
        }
        for (int i = 0; i < getDimension(); i++) {
            components[i] /= length;
        }
    }

    public T getNormalized() {
        double length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a zero-length vector");
        }
        double[] normalizedComponents = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            normalizedComponents[i] = components[i] / length;
        }
        return createInstance(normalizedComponents);
    }

    public double dot(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double result = 0;
        for (int i = 0; i < getDimension(); i++) {
            result += components[i] * other.get(i);
        }
        return result;
    }


    public abstract T createInstance(double... components);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + Arrays.toString(components);
    }
}
