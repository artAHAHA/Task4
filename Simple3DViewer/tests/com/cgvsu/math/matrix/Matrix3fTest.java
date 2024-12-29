package com.cgvsu.math.matrix;

import com.cgvsu.math.vectors.Vector3f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Matrix3fTest {

    @Test
    void testMatrixCreationWithArray() {
        double[] elements = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Matrix3f matrix = new Matrix3f(elements);
        assertEquals(1.0, matrix.getElement(0, 0));
        assertEquals(9.0, matrix.getElement(2, 2));
    }

    @Test
    void testMatrixCreationWithVarArgs() {
        Matrix3f matrix = new Matrix3f(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
        assertEquals(1.0, matrix.getElement(0, 0));
        assertEquals(9.0, matrix.getElement(2, 2));
    }

    @Test
    void testMatrixAddition() {
        double[] elements = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        double[] elements2 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Matrix3f matrix1 = new Matrix3f(elements);
        Matrix3f matrix2 = new Matrix3f(elements2);

        Matrix3f result = matrix1.add(matrix2);

        assertEquals(10.0, result.getElement(0, 0));
        assertEquals(10.0, result.getElement(2, 2));
    }

    @Test
    public void testCreateIdentityMatrix3x3() {
        Matrix3f matrix3x3 = new Matrix3f(0,0,0,0,0,0,0,0,0);
        Matrix3f identityMatrix = matrix3x3.createIdentityMatrix(3);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    assertEquals(1.0, identityMatrix.getElement(i, j), "Диагональные элементы должны быть равны 1");
                } else {
                    assertEquals(0.0, identityMatrix.getElement(i, j), "Недиагональные элементы должны быть равны 0");
                }
            }
        }
    }

    @Test
    public void testSetElement() {
        Matrix3f matrix = new Matrix3f(0,0,0,0,0,0,0,0,0);
        matrix.setElement(0, 0, 10.0F);

        assertEquals(10.0, matrix.getElement(0, 0));
    }

    @Test
    void testMatrixMultiplication() {
        double[] elements = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        double[] elements2 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Matrix3f matrix1 = new Matrix3f(elements);
        Matrix3f matrix2 = new Matrix3f(elements2);

        Matrix3f result = matrix1.multiply(matrix2);

        assertEquals(30.0, result.getElement(0, 0));
        assertEquals(90.0, result.getElement(2, 2));
    }


    @Test
    public void testMatrixCreationFromArray() {
        // Проверка правильности создания матрицы из массива
        double[] elements = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Matrix3f matrix = new Matrix3f(elements);

        // Проверка элементов матрицы
        assertEquals(1.0, matrix.getElement(0, 0));
        assertEquals(5.0, matrix.getElement(1, 1));
        assertEquals(9.0, matrix.getElement(2, 2));
    }

    @Test
    public void testMatrixAdd() {
        // Тестирование сложения матриц
        Matrix3f matrix1 = new Matrix3f(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Matrix3f matrix2 = new Matrix3f(9, 8, 7, 6, 5, 4, 3, 2, 1);

        Matrix3f result = matrix1.add(matrix2);

        assertEquals(10.0, result.getElement(0, 0));
        assertEquals(10.0, result.getElement(1, 1));
        assertEquals(10.0, result.getElement(2, 2));
    }

    @Test
    public void testMatrixMultiply() {
        // Тестирование умножения матриц
        Matrix3f matrix1 = new Matrix3f(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Matrix3f matrix2 = new Matrix3f(9, 8, 7, 6, 5, 4, 3, 2, 1);

        Matrix3f result = matrix1.multiply(matrix2);

        // Проверка результата умножения
        assertEquals(30.0, result.getElement(0, 0), 1e-7);
        assertEquals(24.0, result.getElement(0, 1), 1e-7);
        assertEquals(18.0, result.getElement(0, 2), 1e-7);
    }

    @Test
    public void testMatrixTransposition() {
        // Тестирование транспонирования матрицы
        Matrix3f matrix = new Matrix3f(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Matrix3f transposed = matrix.transposition();

        assertEquals(1.0, transposed.getElement(0, 0));
        assertEquals(4.0, transposed.getElement(0, 1));
        assertEquals(7.0, transposed.getElement(0, 2));
        assertEquals(2.0, transposed.getElement(1, 0));
        assertEquals(5.0, transposed.getElement(1, 1));
        assertEquals(8.0, transposed.getElement(1, 2));
        assertEquals(3.0, transposed.getElement(2, 0));
        assertEquals(6.0, transposed.getElement(2, 1));
        assertEquals(9.0, transposed.getElement(2, 2));
    }

    @Test
    public void testMatrixMultiplyByVector() {
        // Тестирование умножения матрицы на вектор
        Matrix3f matrix = new Matrix3f(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Vector3f vector = new Vector3f(1, 1, 1);

        Vector3f result = (Vector3f) matrix.multiplyingMatrixByVector(vector);

        assertEquals(6.0, result.get(0), 1e-7);
        assertEquals(15.0, result.get(1), 1e-7);
        assertEquals(24.0, result.get(2), 1e-7);
    }

    @Test
    void testMatrixToString() {
        Matrix3f matrix = new Matrix3f(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // Expected string representation of the matrix
        String expected = "1,00\t2,00\t3,00\t\n4,00\t5,00\t6,00\t\n7,00\t8,00\t9,00\t\n";

        // Assert that the matrix's toString() matches the expected string
        assertEquals(expected, matrix.toString());
    }


}
