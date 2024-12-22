package com.cgvsu.math.vectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3fTest {
    @Test
    void testAdd() {
        Vector3f v1 = new Vector3f(1.0, 2.0, 3.0);
        Vector3f v2 = new Vector3f(4.0, 5.0, 6.0);
        Vector3f result = v1.add(v2);
        assertEquals(5.0, result.getX(), 1e-7);
        assertEquals(7.0, result.getY(), 1e-7);
        assertEquals(9.0, result.getZ(), 1e-7);
    }

    @Test
    void testAddInPlace() {
        Vector3f v1 = new Vector3f(1.0, 2.0, 3.0);
        Vector3f v2 = new Vector3f(4.0, 5.0, 6.0);
        v1.addV(v2);
        assertEquals(5.0, v1.getX(), 1e-7);
        assertEquals(7.0, v1.getY(), 1e-7);
        assertEquals(9.0, v1.getZ(), 1e-7);
    }

    @Test
    void testSubtract() {
        Vector3f v1 = new Vector3f(5.0, 4.0, 3.0);
        Vector3f v2 = new Vector3f(1.0, 2.0, 3.0);
        Vector3f result = v1.subtract(v2);
        assertEquals(4.0, result.getX(), 1e-7);
        assertEquals(2.0, result.getY(), 1e-7);
        assertEquals(0.0, result.getZ(), 1e-7);
    }

    @Test
    void testSubtractInPlace() {
        Vector3f v1 = new Vector3f(5.0, 4.0, 3.0);
        Vector3f v2 = new Vector3f(1.0, 2.0, 3.0);
        v1.subtractV(v2);
        assertEquals(4.0, v1.getX(), 1e-7);
        assertEquals(2.0, v1.getY(), 1e-7);
        assertEquals(0.0, v1.getZ(), 1e-7);
    }

    @Test
    void testMultiplyByScalar() {
        Vector3f v = new Vector3f(1.0, 2.0, 3.0);
        v.multiplyByScalar(2.0);
        assertEquals(2.0, v.getX(), 1e-7);
        assertEquals(4.0, v.getY(), 1e-7);
        assertEquals(6.0, v.getZ(), 1e-7);
    }

    @Test
    void testDivideByScalar() {
        Vector3f v = new Vector3f(6.0, 9.0, 12.0);
        v.divideByScalar(3.0);
        assertEquals(2.0, v.getX(), 1e-7);
        assertEquals(3.0, v.getY(), 1e-7);
        assertEquals(4.0, v.getZ(), 1e-7);
    }

    @Test
    void testDivideByScalarZero() {
        Vector3f v = new Vector3f(6.0, 9.0, 12.0);
        assertThrows(ArithmeticException.class, () -> v.divideByScalar(0.0));
    }

    @Test
    void testNormalize() {
        Vector3f v = new Vector3f(3.0, 4.0, 0.0);
        v.normalize();
        assertEquals(0.6, v.getX(), 1e-7);
        assertEquals(0.8, v.getY(), 1e-7);
        assertEquals(0.0, v.getZ(), 1e-7);
    }

    @Test
    void testNormalizeZeroVector() {
        Vector3f v = new Vector3f(0.0, 0.0, 0.0);
        assertThrows(ArithmeticException.class, v::normalize);
    }

    @Test
    void testGetNormalized() {
        Vector3f v = new Vector3f(3.0, 4.0, 0.0);
        Vector3f result = v.getNormalized();
        assertEquals(0.6, result.getX(), 1e-7);
        assertEquals(0.8, result.getY(), 1e-7);
        assertEquals(0.0, result.getZ(), 1e-7);
    }

    @Test
    void testDot() {
        Vector3f v1 = new Vector3f(1.0, 2.0, 3.0);
        Vector3f v2 = new Vector3f(4.0, 5.0, 6.0);
        double result = v1.dot(v2);
        assertEquals(32.0, result, 1e-7);
    }

    @Test
    void testIsEqual() {
        Vector3f v1 = new Vector3f(1.0, 2.0, 3.0);
        Vector3f v2 = new Vector3f(1.0, 2.0, 3.0);
        assertTrue(v1.isEqual(v2));
    }

    @Test
    void testIsNotEqual() {
        Vector3f v1 = new Vector3f(1.0, 2.0, 3.0);
        Vector3f v2 = new Vector3f(3.0, 2.0, 1.0);
        assertFalse(v1.isEqual(v2));
    }

    @Test
    void testAppend() {
        Vector3f v = new Vector3f(1.0, 2.0, 3.0);
        Vector4f result = v.append(4.0);
        assertEquals(4, result.getDimension());
        assertEquals(1.0, result.get(0), 1e-7);
        assertEquals(2.0, result.get(1), 1e-7);
        assertEquals(3.0, result.get(2), 1e-7);
        assertEquals(4.0, result.get(3), 1e-7);
    }

    @Test
    void testCross() {
        Vector3f v1 = new Vector3f(1.0, 2.0, 3.0);
        Vector3f v2 = new Vector3f(4.0, 5.0, 6.0);
        Vector3f result = v1.cross(v2);
        assertEquals(-3.0, result.getX(), 1e-7);
        assertEquals(6.0, result.getY(), 1e-7);
        assertEquals(-3.0, result.getZ(), 1e-7);
    }

}
