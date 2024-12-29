package com.cgvsu.math.points;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class Point2fTest {

    // Test the constructor
    @Test
    void testConstructor() {
        Point2f point = new Point2f(3.0f, 4.0f);

        assertEquals(3.0f, point.getX(), "X component should be 3.0");
        assertEquals(4.0f, point.getY(), "Y component should be 4.0");
    }

    // Test the setter and getter methods
    @Test
    void testSettersAndGetters() {
        Point2f point = new Point2f(1.0f, 2.0f);

        // Set new values
        point.setX(5.0f);
        point.setY(6.0f);

        // Verify that the values are set correctly
        assertEquals(5.0f, point.getX(), "X component should be 5.0");
        assertEquals(6.0f, point.getY(), "Y component should be 6.0");
    }

    // Test the distance method
    @Test
    void testDistance() {
        Point2f point1 = new Point2f(1.0f, 2.0f);
        Point2f point2 = new Point2f(4.0f, 6.0f);

        // Calculate distance between two points (1, 2) and (4, 6)
        double expectedDistance = Math.sqrt(Math.pow(4.0 - 1.0, 2) + Math.pow(6.0 - 2.0, 2));  // sqrt(9 + 16) = sqrt(25) = 5
        double actualDistance = point1.distance(point2.getX(), point2.getY());

        // Assert the distance is correct
        assertEquals(expectedDistance, actualDistance, "Distance between points should be 5.0");
    }

    // Test the distance with the same point (distance should be 0)
    @Test
    void testDistanceSamePoint() {
        Point2f point1 = new Point2f(3.0f, 3.0f);

        // Distance from a point to itself should be 0
        double actualDistance = point1.distance(point1.getX(), point1.getY());

        assertEquals(0.0, actualDistance, "Distance from the point to itself should be 0");
    }

    // Test the distance with negative coordinates
    @Test
    void testDistanceNegativeCoordinates() {
        Point2f point1 = new Point2f(-1.0f, -2.0f);
        Point2f point2 = new Point2f(-4.0f, -6.0f);

        // Calculate distance between two points (-1, -2) and (-4, -6)
        double expectedDistance = Math.sqrt(Math.pow(-4.0 - (-1.0), 2) + Math.pow(-6.0 - (-2.0), 2));  // sqrt(9 + 16) = sqrt(25) = 5
        double actualDistance = point1.distance(point2.getX(), point2.getY());

        // Assert the distance is correct
        assertEquals(expectedDistance, actualDistance, "Distance between points should be 5.0");
    }
}
