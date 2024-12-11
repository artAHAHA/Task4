package com.cgvsu.math.matrix;


import com.cgvsu.math.vectors.Vector3f;

public class Matrix3f implements Matrix<Matrix3f, Vector3f> {
    private final double[][] elements;

    public Matrix3f(double[][] elements) {
        if (elements.length != 3 || elements[0].length != 3) {
            throw new IllegalArgumentException("Матрица должна быть 3x3");
        }
        this.elements = new double[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(elements[i], 0, this.elements[i], 0, 3);
        }
    }

    public static Matrix3f setZero() {
        return new Matrix3f(new double[][] {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
    }
    public static Matrix3f setIdentity() {
        return new Matrix3f(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
    }

    @Override
    public Matrix3f add(Matrix3f other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.elements[i][j] + other.elements[i][j];
            }
        }
        return new Matrix3f(result);
    }

    @Override
    public Matrix3f subtract(Matrix3f other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.elements[i][j] - other.elements[i][j];
            }
        }
        return new Matrix3f(result);
    }


    @Override
    public Vector3f multiplyingMatrixByVector(Vector3f vector) {
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            result[i] = elements[i][0] * vector.getX() +
                    elements[i][1] * vector.getY() +
                    elements[i][2] * vector.getZ();
        }
        return new Vector3f(result[0], result[1], result[2]);
    }

    @Override
    public Matrix3f multiply(Matrix3f other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.elements[i][0] * other.elements[0][j] +
                        this.elements[i][1] * other.elements[1][j] +
                        this.elements[i][2] * other.elements[2][j];
            }
        }
        return new Matrix3f(result);
    }

    @Override
    public Matrix3f transpose() {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[j][i] = this.elements[i][j];
            }
        }
        return new Matrix3f(result);
    }

    @Override
    public double findDeterminant() {
        double determinant =
                this.elements[0][0]*this.elements[1][1]*this.elements[2][2] +
                this.elements[0][1]*this.elements[1][2]*this.elements[2][0] +
                this.elements[1][0]*this.elements[2][1]*this.elements[0][2] -
                this.elements[0][2]*this.elements[1][1]*this.elements[2][0] -
                this.elements[0][1]*this.elements[1][0]*this.elements[2][2] -
                this.elements[0][0]*this.elements[1][2]*this.elements[2][1]
                ;
        return determinant;
    }

    @Override
    public Matrix3f findInverseMatrix() {
        double determinant = findDeterminant();

        if (determinant == 0) {
            throw new IllegalArgumentException("Матрица не имеет обратной матрицы (определитель равен нулю)");
        }

        double[][] adjugate = new double[3][3];

        // Вычисляем алгебраические дополнения
        adjugate[0][0] = minor(0, 0) * 1;
        adjugate[0][1] = minor(0, 1) * -1;
        adjugate[0][2] = minor(0, 2) * 1;

        adjugate[1][0] = minor(1, 0) * -1;
        adjugate[1][1] = minor(1, 1) * 1;
        adjugate[1][2] = minor(1, 2) * -1;

        adjugate[2][0] = minor(2, 0) * 1;
        adjugate[2][1] = minor(2, 1) * -1;
        adjugate[2][2] = minor(2, 2) * 1;

        Matrix3f adjugateMatrix = new Matrix3f(adjugate).transpose();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                adjugateMatrix.elements[i][j] /= determinant;
            }
        }

        return adjugateMatrix;
    }

    private double minor(int row, int col) {
        double[][] minorMatrix = new double[2][2];
        int minorRow = 0;

        for (int i = 0; i < 3; i++) {
            if (i == row) continue;
            int minorCol = 0;
            for (int j = 0; j < 3; j++) {
                if (j == col) continue;
                minorMatrix[minorRow][minorCol++] = this.elements[i][j];
            }
            minorRow++;
        }

        return determinantOf2x2(minorMatrix);
    }

    private double determinantOf2x2(double[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : elements) {
            sb.append("[ ");
            for (double value : row) {
                sb.append(value).append(" ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }


}
