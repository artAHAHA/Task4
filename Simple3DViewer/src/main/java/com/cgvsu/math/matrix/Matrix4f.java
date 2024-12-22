package com.cgvsu.math.matrix;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;

public class Matrix4f extends AbstractMatrix<Matrix4f> {

    public Matrix4f(double... array) {
        super(array);
    }

    @Override
    protected Matrix4f createInstance(double[] elements) {
        return new Matrix4f(elements);
    }

    @Override
    protected Matrix4f createInstance(double[][] elements) {
        return new Matrix4f(flatten(elements));
    }

    @Override
    protected Matrix4f createInstance() {
        return new Matrix4f(new double[16]);
    }

    @Override
    protected int getSize() {
        return 4; // Матрица 4x4
    }

    @Override
    public double getElement(int row, int col) {
        return elements[row][col];
    }

    @Override
    public void setElement(int row, int col, float value) {
        elements[row][col] = value;
    }

    public static Matrix4f setIdentity() {
        // Создаем 4x4 единичную матрицу
        double[][] matrix = new double[4][4];

        // Заполняем диагональ единицами, остальные элементы - нулями
        for (int i = 0; i < 4; i++) {
            matrix[i][i] = 1;
        }

        // Возвращаем новый экземпляр Matrix4f с элементами матрицы
        return new Matrix4f(flatten(matrix));
    }


    public Vector4f multiplyingMatrixByVector(Vector4f vector) {
        // Проверяем размерность вектора и матрицы на совпадение
        if (vector.getDimension() != getSize()) {
            throw new IllegalArgumentException("Размер вектора должен совпадать с размером матрицы.");
        }

        // Создаем новый массив для результата
        double[] result = new double[getSize()];

        // Умножение матрицы на вектор
        for (int i = 0; i < getSize(); i++) {
            result[i] = 0;
            for (int j = 0; j < getSize(); j++) {
                result[i] += (float) (this.elements[i][j] * vector.get(j));  // Умножаем элементы
            }
        }

        // Возвращаем новый вектор Vector4f с результатом
        return new Vector4f(result[0], result[1], result[2], result[3]);
    }


    //АФФИННЫЕ ПРЕОБРАЗОВАНИЯ
    public static Matrix4f scale(double sx, double sy, double sz) {
        double[][] matrix = new double[][]{
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4f(flatten(matrix));
    }

    // Метод вращения вокруг осей
    public static Matrix4f rotateX(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        double[][] matrix = new double[][]{
                {1, 0, 0, 0},
                {0, cos, sin, 0},
                {0, -sin, cos, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4f(flatten(matrix));
    }

    public static Matrix4f rotateY(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        double[][] matrix = new double[][]{
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4f(flatten(matrix));
    }

    public static Matrix4f rotateZ(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        double[][] matrix = new double[][]{
                {cos, sin, 0, 0},
                {-sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4f(flatten(matrix));
    }

    // Метод сдвига
    public static Matrix4f translate(double tx, double ty, double tz) {
        double[][] matrix = new double[][]{
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };
        return new Matrix4f(flatten(matrix));
    }

    // Метод вращения вокруг заданной оси
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

        return new Matrix4f(flatten(elements));
    }

    public static double[] flatten(double[][] array) {
        double[] flat = new double[16];
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++, k++) {
                flat[k] = array[i][j];
            }
        }
        return flat;
    }
}
