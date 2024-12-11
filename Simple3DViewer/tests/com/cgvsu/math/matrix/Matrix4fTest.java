package com.cgvsu.math.matrix;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Matrix4fTest {

    @Test
    public void testScale() {
        // Масштабирование на 2 по оси X и Y, и 1 по оси Z
        Matrix4f scaleMatrix = Matrix4f.scale(2.0, 2.0, 1.0);

        // Проверка результата на элементах матрицы
        assertEquals(2.0, scaleMatrix.getElement(0, 0), "Scaling factor on X-axis should be 2.0");
        assertEquals(2.0, scaleMatrix.getElement(1, 1), "Scaling factor on Y-axis should be 2.0");
        assertEquals(1.0, scaleMatrix.getElement(2, 2), "Scaling factor on Z-axis should be 1.0");
    }

    @Test
    void testRotateMatrix() {
        // Вращение на 90 градусов
        Matrix4f rotateMatrix = Matrix4f.rotate(90.0);

        // Проверка вращения на 90 градусов
        assertTrue(Math.abs(rotateMatrix.getElement(0, 0)) < 1e-6, "cos(90) should be approximately 0");
        assertTrue(Math.abs(rotateMatrix.getElement(0, 1) + 1.0) < 1e-6, "sin(90) should be approximately -1");
        assertTrue(Math.abs(rotateMatrix.getElement(1, 0) - 1.0) < 1e-6, "sin(90) should be approximately 1");
        assertTrue(Math.abs(rotateMatrix.getElement(1, 1)) < 1e-6, "cos(90) should be approximately 0");
    }

    @Test
    public void testTranslate() {
        // Трансляция на 5 по оси X
        Matrix4f translateMatrix = Matrix4f.translate(5.0, 0.0, 0.0);

        // Проверка результата на элементах матрицы
        assertEquals(5.0, translateMatrix.getElement(0, 3), "Translation on X-axis should be 5.0");
        assertEquals(0.0, translateMatrix.getElement(1, 3), "Translation on Y-axis should be 0.0");
        assertEquals(0.0, translateMatrix.getElement(2, 3), "Translation on Z-axis should be 0.0");
    }

    @Test
    public void testCombinedTransformations() {
        // Масштабирование, вращение и трансляция
        Matrix4f scaleMatrix = Matrix4f.scale(2.0, 2.0, 1.0);
        Matrix4f rotateMatrix = Matrix4f.rotate(90.0);
        Matrix4f translateMatrix = Matrix4f.translate(5.0, 0.0, 0.0);

        // Комбинированное преобразование: сначала масштабирование, затем вращение, затем трансляция
        Matrix4f combinedMatrix = translateMatrix
                .multiply(rotateMatrix)
                .multiply(scaleMatrix);

        // Проверка на матрице комбинации
        assertEquals(0.0, combinedMatrix.getElement(0, 0), 1e-6, "Combined rotation and scaling error on (0, 0)");
        assertEquals(-2.0, combinedMatrix.getElement(0, 1), 1e-6, "Combined rotation and scaling factor on (0, 1)");
        assertEquals(2.0, combinedMatrix.getElement(1, 0), 1e-6, "Combined rotation and scaling factor on (1, 0)");
        assertEquals(0.0, combinedMatrix.getElement(1, 1), 1e-6, "Combined rotation and scaling error on (1, 1)");
        assertEquals(5.0, combinedMatrix.getElement(0, 3), 1e-6, "Translation on X-axis should be 5.0");
    }

}