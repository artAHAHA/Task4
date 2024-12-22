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
        return new Matrix3f(flatten(elements));
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

    private double[] flatten(double[][] array) {
        double[] flat = new double[9];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++, k++) {
                flat[k] = array[i][j];
            }
        }
        return flat;
    }
}
