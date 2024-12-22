package com.cgvsu.math.matrix;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;
import org.junit.jupiter.api.Test;

import static com.cgvsu.math.matrix.Matrix4f.flatten4x4;
import static org.junit.jupiter.api.Assertions.*;

public class Matrix4fTest {

    // Тест умножения матрицы на вектор
    @Test
    public void testMultiplyingMatrixByVector() {
        // Создаем матрицу 4x4
        double[][] matrixElements = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        Matrix4f matrix = new Matrix4f(flatten4x4(matrixElements));

        // Вектор для умножения
        Vector4f vector = new Vector4f(1, 2, 3, 1);

        // Ожидаемый результат
        Vector4f expected = new Vector4f(1, 2, 3, 1);

        // Тестируем метод умножения
        Vector4f result = matrix.multiplyingMatrixByVector(vector);

        assertTrue(expected.isEqual(result), "Vectors should be equal");
    }

    // Тест матрицы масштабирования
    @Test
    public void testScaleMatrix() {
        double sx = 2.0, sy = 3.0, sz = 4.0;

        Matrix4f scaleMatrix = Matrix4f.scale(sx, sy, sz);

        double[][] expected = {
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        };

        // Проверяем элементы матрицы
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], scaleMatrix.getElement(i, j), "Матрица масштабирования некорректна.");
            }
        }
    }

    // Тест матрицы вращения вокруг оси X
    @Test
    public void testRotateXMatrix() {
        double angle = 90.0;

        Matrix4f rotationMatrix = Matrix4f.rotateX(angle);

        // Проверка корректности значений в матрице вращения
        double[][] expected = {
                {1, 0, 0, 0},
                {0, Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)), 0},
                {0, -Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0},
                {0, 0, 0, 1}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], rotationMatrix.getElement(i, j), "Матрица вращения вокруг оси X некорректна.");
            }
        }
    }

    // Тест матрицы вращения вокруг оси Y
    @Test
    public void testRotateYMatrix() {
        double angle = 90.0;

        Matrix4f rotationMatrix = Matrix4f.rotateY(angle);

        // Проверка корректности значений в матрице вращения
        double[][] expected = {
                {Math.cos(Math.toRadians(angle)), 0, Math.sin(Math.toRadians(angle)), 0},
                {0, 1, 0, 0},
                {-Math.sin(Math.toRadians(angle)), 0, Math.cos(Math.toRadians(angle)), 0},
                {0, 0, 0, 1}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], rotationMatrix.getElement(i, j), "Матрица вращения вокруг оси Y некорректна.");
            }
        }
    }

    // Тест матрицы вращения вокруг оси Z
    @Test
    public void testRotateZMatrix() {
        double angle = 90.0;

        Matrix4f rotationMatrix = Matrix4f.rotateZ(angle);

        // Проверка корректности значений в матрице вращения
        double[][] expected = {
                {Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)), 0, 0},
                {-Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], rotationMatrix.getElement(i, j), "Матрица вращения вокруг оси Z некорректна.");
            }
        }
    }

    // Тест матрицы сдвига
    @Test
    public void testTranslateMatrix() {
        double tx = 1.0, ty = 2.0, tz = 3.0;

        Matrix4f translateMatrix = Matrix4f.translate(tx, ty, tz);

        double[][] expected = {
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], translateMatrix.getElement(i, j), "Матрица сдвига некорректна.");
            }
        }
    }

    @Test
    public void testRotationAroundAxis() {
        Vector3f axis = new Vector3f(1, 0, 0); // Ось X
        float angle = (float) Math.PI / 2; // Поворот на 90 градусов

        Matrix4f rotationMatrix = Matrix4f.rotateAroundAxis(axis, angle);

        // Ожидаем, что после поворота компоненты будут изменены с учетом вращения на 90 градусов
        // Например, проверим элемент [0][0], который должен быть близким к 1 после вращения вокруг оси X
        double result = rotationMatrix.getElement(0, 0);

        // Используем более широкий допуск для сравнения
        assertEquals(1.0, result, 1e-7, "Matrix rotation element is incorrect");

        // Другие проверки для других элементов матрицы
    }

    // Проверка на выброс исключения при некорректной оси вращения
    @Test
    public void testRotateAroundAxisInvalidAxis() {
        Vector3f axis = new Vector3f(0, 0, 0);  // Нулевой вектор (некорректная ось)
        assertThrows(IllegalArgumentException.class, () -> {
            Matrix4f.rotateAroundAxis(axis, 90.0f);
        }, "Ожидалось исключение при передаче некорректной оси вращения.");
    }
}
