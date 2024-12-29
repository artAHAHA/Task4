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
        return new Matrix4f(flatten4x4(elements));
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

    //АФФИННЫЕ ПРЕОБРАЗОВАНИЯ

    /**
     * Создает и возвращает матрицу масштабирования.
     *
     * @param sx Масштаб по оси X.
     * @param sy Масштаб по оси Y.
     * @param sz Масштаб по оси Z.
     * @return Матрица масштабирования 4x4.
     */
    public static Matrix4f scale(double sx, double sy, double sz) {
        double[][] matrix = new double[][]{
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        };
        return new Matrix4f(flatten4x4(matrix));
    }

    /**
     * Создает и возвращает матрицу вращения вокруг оси X.
     *
     * @param angle Угол вращения в градусах.
     * @return Матрица вращения вокруг оси X.
     */
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
        return new Matrix4f(flatten4x4(matrix));
    }

    /**
     * Создает и возвращает матрицу вращения вокруг оси Y.
     *
     * @param angle Угол вращения в градусах.
     * @return Матрица вращения вокруг оси Y.
     */
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
        return new Matrix4f(flatten4x4(matrix));
    }

    /**
     * Создает и возвращает матрицу вращения вокруг оси Z.
     *
     * @param angle Угол вращения в градусах.
     * @return Матрица вращения вокруг оси Z.
     */
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
        return new Matrix4f(flatten4x4(matrix));
    }

    /**
     * Создает и возвращает матрицу сдвига.
     *
     * @param tx Сдвиг по оси X.
     * @param ty Сдвиг по оси Y.
     * @param tz Сдвиг по оси Z.
     * @return Матрица сдвига 4x4.
     */
    public static Matrix4f translate(double tx, double ty, double tz) {
        double[][] matrix = new double[][]{
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };
        return new Matrix4f(flatten4x4(matrix));
    }

    /**
     * Создает и возвращает матрицу вращения вокруг заданной оси.
     *
     * @param axis  Ось вращения (вектор).
     * @param angle Угол вращения в радианах.
     * @return Матрица вращения вокруг заданной оси.
     * @throws IllegalArgumentException Если длина оси слишком мала для корректной нормализации.
     */
    public static Matrix4f rotateAroundAxis(Vector3f axis, float angle) {
        // Проверка на длину вектора
        if (axis.getLength() < 1e-3) {
            System.out.println("Axis vector length is too small, returning identity matrix.");
            return new Matrix4f(1); // Возвращаем единичную матрицу
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

        return new Matrix4f(flatten4x4(elements));
    }

    /**
     * Преобразует двумерный массив в одномерный массив.
     *
     * @param matrix (матрица).
     * @return Одномерный массив, содержащий все элементы матрицы.
     */
    public static double[] flatten4x4(double[][] matrix) {
        return flatten(matrix, 4);
    }
}
