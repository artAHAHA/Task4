package com.cgvsu.math.vectors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector2fTest {

    @Test
    void testVector2fConstructor() {
        Vector2f v = new Vector2f(3.0, 4.0);
        assertEquals(2, v.getDimension());
        assertEquals(3.0, v.getX(), 1e-7);
        assertEquals(4.0, v.getY(), 1e-7);
    }

    @Test
    void testGet() {
        Vector2f v = new Vector2f(3.0, 4.0);
        assertEquals(3.0, v.get(0), 1e-7);
        assertEquals(4.0, v.get(1), 1e-7);
    }

    @Test
    void testAdd() {
        Vector2f v1 = new Vector2f(3.0, 4.0);
        Vector2f v2 = new Vector2f(1.0, 2.0);
        Vector2f result = v1.add(v2);
        assertEquals(4.0, result.getX(), 1e-7);
        assertEquals(6.0, result.getY(), 1e-7);
    }

    @Test
    void testAddV() {
        Vector2f v1 = new Vector2f(3.0, 4.0);
        Vector2f v2 = new Vector2f(1.0, 2.0);
        v1.addV(v2);
        assertEquals(4.0, v1.getX(), 1e-7);
        assertEquals(6.0, v1.getY(), 1e-7);
    }

    @Test
    void testSubtract() {
        Vector2f v1 = new Vector2f(3.0, 4.0);
        Vector2f v2 = new Vector2f(1.0, 2.0);
        Vector2f result = v1.subtract(v2);
        assertEquals(2.0, result.getX(), 1e-7);
        assertEquals(2.0, result.getY(), 1e-7);
    }

    @Test
    void testSubtractV() {
        Vector2f v1 = new Vector2f(3.0, 4.0);
        Vector2f v2 = new Vector2f(1.0, 2.0);
        v1.subtractV(v2);
        assertEquals(2.0, v1.getX(), 1e-7);
        assertEquals(2.0, v1.getY(), 1e-7);
    }

    @Test
    void testDot() {
        Vector2f v1 = new Vector2f(3.0, 4.0);
        Vector2f v2 = new Vector2f(1.0, 2.0);
        double result = v1.dot(v2);
        assertEquals(11.0, result, 1e-7);
    }

    @Test
    void testMultiplyByScalar() {
        Vector2f v = new Vector2f(3.0, 4.0);
        v.multiplyByScalar(2);
        assertEquals(6.0, v.getX(), 1e-7);
        assertEquals(8.0, v.getY(), 1e-7);
    }

    @Test
    void testDivideByScalar() {
        Vector2f v = new Vector2f(6.0, 8.0);
        v.divideByScalar(2);
        assertEquals(3.0, v.getX(), 1e-7);
        assertEquals(4.0, v.getY(), 1e-7);
    }

    @Test
    void testNormalize() {
        Vector2f v = new Vector2f(3.0, 4.0);
        v.normalize();
        assertEquals(1.0, v.getLength(), 1e-7);
    }

    @Test
    void testGetNormalized() {
        Vector2f v = new Vector2f(3.0, 4.0);
        Vector2f normalized = v.getNormalized();
        assertEquals(1.0, normalized.getLength(), 1e-7);
    }

    @Test
    void testIsEqual() {
        Vector2f v1 = new Vector2f(3.0, 4.0);
        Vector2f v2 = new Vector2f(3.0, 4.0);
        Vector2f v3 = new Vector2f(4.0, 5.0);
        assertTrue(v1.isEqual(v2));
        assertFalse(v1.isEqual(v3));
    }

    @Test
    void testToString() {
        Vector2f v = new Vector2f(3.0, 4.0);
        assertEquals("Vector2f[3.0, 4.0]", v.toString());
    }
}
