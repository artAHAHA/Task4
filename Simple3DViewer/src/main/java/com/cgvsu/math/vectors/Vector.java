package com.cgvsu.math.vectors;

public interface Vector<T> {

    float EPS = 1e-7f;

    boolean isEqual(T other);

    T add(T other);

    void add(T var1, T var2);

    void subtract(T var1, T var2);

    T subtract(T var1);

    void multiplyingVectorByScalar(double scalar);

    void dividingVectorByScalar(double scalar) throws ArithmeticException;

    double getLength();
    void normalize();

    double scalarMultiplication(T other);

}

