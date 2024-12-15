package com.cgvsu.math.matrix;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;

public class Matrix4f implements Matrix<Matrix4f, Vector4f> {
    private final double[][] elements;

    public Matrix4f(double[][] elements) {
        if (elements.length != 4 || elements[0].length != 4) {
            throw new IllegalArgumentException("Матрица должна быть 4x4");
        }
        this.elements = new double[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(elements[i], 0, this.elements[i], 0, 4);
        }
    }

    public static Matrix4f setZero() {
        return new Matrix4f(new double[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    public static Matrix4f setIdentity() {
        return new Matrix4f(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    @Override
    public Matrix4f add(Matrix4f other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = elements[i][j] + other.elements[i][j];
            }
        }
        return new Matrix4f(result);
    }

    public double getElement(int row, int col) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4) {
            throw new IndexOutOfBoundsException("Индексы должны быть в пределах 0-3");
        }
        return elements[row][col];
    }

    @Override
    public Matrix4f subtract(Matrix4f other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = elements[i][j] - other.elements[i][j];
            }
        }
        return new Matrix4f(result);
    }

    @Override
    public Vector4f multiplyingMatrixByVector(Vector4f vector) {
        double[] result = new double[4];
        for (int i = 0; i < 4; i++) {
            result[i] = elements[i][0] * vector.getX() +
                    elements[i][1] * vector.getY() +
                    elements[i][2] * vector.getZ() +
                    elements[i][3] * vector.getW();
        }
        return new Vector4f(result[0], result[1], result[2], result[3]);
    }

    @Override
    public Matrix4f multiply(Matrix4f other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = elements[i][0] * other.elements[0][j] +
                        elements[i][1] * other.elements[1][j] +
                        elements[i][2] * other.elements[2][j] +
                        elements[i][3] * other.elements[3][j];
            }
        }
        return new Matrix4f(result);
    }

    @Override
    public Matrix4f transpose() {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[j][i] = elements[i][j];
            }
        }
        return new Matrix4f(result);
    }

    @Override
    public double findDeterminant() {
        double determinant =
                elements[0][0] * minor(0, 0) -
                        elements[0][1] * minor(0, 1) +
                        elements[0][2] * minor(0, 2) -
                        elements[0][3] * minor(0, 3);
        return determinant;
    }

    @Override
    public Matrix4f findInverseMatrix() {
        double determinant = findDeterminant();
        if (determinant == 0) {
            throw new IllegalArgumentException("Матрица не имеет обратной матрицы (определитель равен нулю)");
        }

        double[][] adjugate = new double[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double sign = ((i + j) % 2 == 0) ? 1 : -1;
                adjugate[j][i] = sign * minor(i, j);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adjugate[i][j] /= determinant;

                if (Math.abs(adjugate[i][j]) < 1e-10) {
                    adjugate[i][j] = 0.0;
                }
            }
        }

        return new Matrix4f(adjugate);
    }


    private double minor(int row, int col) {
        double[][] minorMatrix = new double[3][3];
        int minorRow = 0, minorCol;

        for (int i = 0; i < 4; i++) {
            if (i == row) continue;
            minorCol = 0;
            for (int j = 0; j < 4; j++) {
                if (j == col) continue;
                minorMatrix[minorRow][minorCol++] = elements[i][j];
            }
            minorRow++;
        }

        return determinantOf3x3(minorMatrix);
    }

    private double determinantOf3x3(double[][] matrix) {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) -
                matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]) +
                matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
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

    //АФФИННЫЕ ПРЕОБРАЗОВАНИЯ
    public static Matrix4f scale(double sx, double sy, double sz) {
        double[][] matrix = new double[][]{
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotate(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        double[][] matrix = new double[][]{
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4f(matrix);
    }

    public static Matrix4f translate(double tx, double ty, double tz) {
        double[][] matrix = new double[][]{
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotateAroundAxis(Vector3f axis, float angle) {
        // Проверка на длину вектора
        if (axis.getLength() < 1e-3) {
            System.out.println("Axis vector length is too small, returning identity matrix.");
            return Matrix4f.setIdentity(); // Возвращаем единичную матрицу
        }

        // Нормализуем ось
        axis.normalize();
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double oneMinusCos = 1.0 - cos;

        double x = axis.getX();
        double y = axis.getY();
        double z = axis.getZ();

        // Строим матрицу вращения
        double[][] elements = new double[][]{
                {cos + x * x * oneMinusCos, x * y * oneMinusCos - z * sin, x * z * oneMinusCos + y * sin, 0},
                {y * x * oneMinusCos + z * sin, cos + y * y * oneMinusCos, y * z * oneMinusCos - x * sin, 0},
                {z * x * oneMinusCos - y * sin, z * y * oneMinusCos + x * sin, cos + z * z * oneMinusCos, 0},
                {0, 0, 0, 1}
        };

        return new Matrix4f(elements);
    }
}
