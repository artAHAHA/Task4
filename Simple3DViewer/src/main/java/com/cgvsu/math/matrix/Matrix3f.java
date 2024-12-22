package com.cgvsu.math.matrix;

public class Matrix3f extends AbstractMatrix<Matrix3f> {

    public Matrix3f(double... array) {
        super(array);
    }

    @Override
    protected Matrix3f createInstance(double[] elements) {
        return new Matrix3f(elements);
    }

    @Override
    protected Matrix3f createInstance(double[][] elements) {
        return new Matrix3f(flatten3x3(elements));
    }

    @Override
    protected Matrix3f createInstance() {
        return new Matrix3f(new double[9]); // Матрица 3x3 (9 элементов)
    }

    @Override
    protected int getSize() {
        return 3; // Матрица 3x3
    }

    @Override
    public double getElement(int row, int col) {
        return elements[row][col];
    }

    @Override
    public void setElement(int row, int col, float value) {
        elements[row][col] = value;
    }

    public static double[] flatten3x3(double[][] matrix) {
        return flatten(matrix, 3);  // Передаем размер 3 для 3x3 матрицы
    }
}
