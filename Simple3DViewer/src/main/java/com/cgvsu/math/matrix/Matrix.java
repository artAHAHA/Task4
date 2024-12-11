package com.cgvsu.math.matrix;


import com.cgvsu.math.vectors.Vector;


public interface Matrix<T extends Matrix<T, V>, V extends Vector<V>>{

    T add(T other);
    T subtract(T other);
    V multiplyingMatrixByVector(V vector);
    T multiply(T other);
    T transpose();
    double findDeterminant();
    T findInverseMatrix();

}
