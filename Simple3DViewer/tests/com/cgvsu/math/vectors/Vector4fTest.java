package com.cgvsu.math.vectors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector4fTest {

    @Test
    void testAdd() {
        Vector4f v1 = new Vector4f(1.0, 2.0, 3.0, 4.0);
        Vector4f v2 = new Vector4f(5.0, 6.0, 7.0, 8.0);
        Vector4f result = v1.add(v2);
        assertEquals(6.0, result.getX(), 1e-7);
        assertEquals(8.0, result.getY(), 1e-7);
        assertEquals(10.0, result.getZ(), 1e-7);
        assertEquals(12.0, result.getW(), 1e-7);
    }

    @Test
    void testAddInPlace() {
        Vector4f v1 = new Vector4f(1.0, 2.0, 3.0, 4.0);
        Vector4f v2 = new Vector4f(5.0, 6.0, 7.0, 8.0);
        v1.addV(v2);
        assertEquals(6.0, v1.getX(), 1e-7);
        assertEquals(8.0, v1.getY(), 1e-7);
        assertEquals(10.0, v1.getZ(), 1e-7);
        assertEquals(12.0, v1.getW(), 1e-7);
    }

    @Test
    void testSubtract() {
        Vector4f v1 = new Vector4f(10.0, 9.0, 8.0, 7.0);
        Vector4f v2 = new Vector4f(5.0, 6.0, 7.0, 8.0);
        Vector4f result = v1.subtract(v2);
        assertEquals(5.0, result.getX(), 1e-7);
        assertEquals(3.0, result.getY(), 1e-7);
        assertEquals(1.0, result.getZ(), 1e-7);
        assertEquals(-1.0, result.getW(), 1e-7);
    }

    @Test
    void testSubtractInPlace() {
        Vector4f v1 = new Vector4f(10.0, 9.0, 8.0, 7.0);
        Vector4f v2 = new Vector4f(5.0, 6.0, 7.0, 8.0);
        v1.subtractV(v2);
        assertEquals(5.0, v1.getX(), 1e-7);
        assertEquals(3.0, v1.getY(), 1e-7);
        assertEquals(1.0, v1.getZ(), 1e-7);
        assertEquals(-1.0, v1.getW(), 1e-7);
    }

    @Test
    void testMultiplyByScalar() {
        Vector4f v = new Vector4f(1.0, 2.0, 3.0, 4.0);
        v.multiplyByScalar(2.0);
        assertEquals(2.0, v.getX(), 1e-7);
        assertEquals(4.0, v.getY(), 1e-7);
        assertEquals(6.0, v.getZ(), 1e-7);
        assertEquals(8.0, v.getW(), 1e-7);
    }

    @Test
    void testDivideByScalar() {
        Vector4f v = new Vector4f(8.0, 6.0, 4.0, 2.0);
        v.divideByScalar(2.0);
        assertEquals(4.0, v.getX(), 1e-7);
        assertEquals(3.0, v.getY(), 1e-7);
        assertEquals(2.0, v.getZ(), 1e-7);
        assertEquals(1.0, v.getW(), 1e-7);
    }

    @Test
    void testDivideByScalarZero() {
        Vector4f v = new Vector4f(8.0, 6.0, 4.0, 2.0);
        assertThrows(ArithmeticException.class, () -> v.divideByScalar(0.0));
    }

    @Test
    void testNormalize() {
        Vector4f v = new Vector4f(2.0, 3.0, 0.0, 0.0);
        v.normalize();
        assertEquals(0.5547, v.getX(), 1e-4);
        assertEquals(0.8321, v.getY(), 1e-4);
        assertEquals(0.0, v.getZ(), 1e-7);
        assertEquals(0.0, v.getW(), 1e-7);
    }

    @Test
    void testNormalizeZeroVector() {
        Vector4f v = new Vector4f(0.0, 0.0, 0.0, 0.0);
        assertThrows(ArithmeticException.class, v::normalize);
    }

    @Test
    void testGetNormalized() {
        Vector4f v = new Vector4f(2.0, 3.0, 0.0, 0.0);
        Vector4f result = v.getNormalized();
        assertEquals(0.5547, result.getX(), 1e-4);
        assertEquals(0.8321, result.getY(), 1e-4);
        assertEquals(0.0, result.getZ(), 1e-7);
        assertEquals(0.0, result.getW(), 1e-7);
    }

    @Test
    void testDot() {
        Vector4f v1 = new Vector4f(1.0, 2.0, 3.0, 4.0);
        Vector4f v2 = new Vector4f(5.0, 6.0, 7.0, 8.0);
        double result = v1.dot(v2);
        assertEquals(70.0, result, 1e-7);
    }

    @Test
    void testIsEqual() {
        Vector4f v1 = new Vector4f(1.0, 2.0, 3.0, 4.0);
        Vector4f v2 = new Vector4f(1.0, 2.0, 3.0, 4.0);
        assertTrue(v1.isEqual(v2));
    }

    @Test
    void testIsNotEqual() {
        Vector4f v1 = new Vector4f(1.0, 2.0, 3.0, 4.0);
        Vector4f v2 = new Vector4f(4.0, 3.0, 2.0, 1.0);
        assertFalse(v1.isEqual(v2));
    }

    @Test
    void testTruncate() {
        // Создаем вектор с четырьмя компонентами
        Vector4f v4 = new Vector4f(1.0, 2.0, 3.0, 4.0);

        // Вызываем truncate, чтобы получить Vector3f
        Vector3f truncated = v4.truncate();

        // Проверяем, что результат имеет правильные компоненты
        assertEquals(1.0, truncated.getX(), 1e-7);
        assertEquals(2.0, truncated.getY(), 1e-7);
        assertEquals(3.0, truncated.getZ(), 1e-7);
    }
}
