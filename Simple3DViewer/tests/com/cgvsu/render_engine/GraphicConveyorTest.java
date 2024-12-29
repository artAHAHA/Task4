package com.cgvsu.render_engine;

import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.points.Point2f;
import com.cgvsu.math.vectors.Vector3f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphicConveyorTest {
    @Test
    void testLookAt() {
        Vector3f position = new Vector3f(0, 0, 5);
        Vector3f target = new Vector3f(0, 0, 0);
        Vector3f up = new Vector3f(0, 1, 0);

        Matrix4f viewMatrix = GraphicConveyor.lookAt(position, target, up);

        assertNotNull(viewMatrix);
        assertEquals(1.0, viewMatrix.getElement(0, 0), 0.001);
        assertEquals(0.0, viewMatrix.getElement(0, 1), 0.001);
        assertEquals(0.0, viewMatrix.getElement(0, 2), 0.001);
        assertEquals(0.0, viewMatrix.getElement(0, 3), 0.001);

    }

    @Test
    public void testPerspective() {
        double fov = Math.toRadians(90);
        double aspectRatio = 1.77;
        double nearPlane = 0.1;
        double farPlane = 1000;

        Matrix4f projectionMatrix = GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);

        assertNotNull(projectionMatrix);

        double tangentMinusOnDegree = 1.0 / Math.tan(fov * 0.5);
        double expected_00 = tangentMinusOnDegree / aspectRatio;
        double expected_22 = (farPlane + nearPlane) / (nearPlane - farPlane);
        double expected_23 = 1.0;
        double expected_32 = (2 * nearPlane * farPlane) / (nearPlane - farPlane);
        double expected_33 = 0.0;

        assertEquals(expected_00, projectionMatrix.getElement(0, 0), 0.001);
        assertEquals(0.0, projectionMatrix.getElement(0, 1), 0.001);
        assertEquals(0.0, projectionMatrix.getElement(0, 2), 0.001);
        assertEquals(0.0, projectionMatrix.getElement(0, 3), 0.001);

        assertEquals(0.0, projectionMatrix.getElement(1, 0), 0.001);
        assertEquals(tangentMinusOnDegree, projectionMatrix.getElement(1, 1), 0.001);
        assertEquals(0.0, projectionMatrix.getElement(1, 2), 0.001);
        assertEquals(0.0, projectionMatrix.getElement(1, 3), 0.001);

        assertEquals(0.0, projectionMatrix.getElement(2, 0), 0.001);
        assertEquals(0.0, projectionMatrix.getElement(2, 1), 0.001);
        assertEquals(expected_22, projectionMatrix.getElement(2, 2), 0.001);
        assertEquals(expected_23, projectionMatrix.getElement(2, 3), 0.001);

        assertEquals(0.0, projectionMatrix.getElement(3, 0), 0.001);
        assertEquals(0.0, projectionMatrix.getElement(3, 1), 0.001);
        assertEquals(expected_32, projectionMatrix.getElement(3, 2), 0.001);
        assertEquals(expected_33, projectionMatrix.getElement(3, 3), 0.001);
    }
    @Test
    public void testMultiplyMatrixByVector() {
        double[][] matrixData = {
                {1, 0, 0, 3},  // Translate x by 3
                {0, 1, 0, 4},  // Translate y by 4
                {0, 0, 1, 5},  // Translate z by 5
                {0, 0, 0, 1}   // Homogeneous coordinate
        };

        Matrix4f matrix = new Matrix4f(Matrix4f.flatten4x4(matrixData));


        Vector3f vertex = new Vector3f(1, 1, 1);

        Vector3f result = GraphicConveyor.multiplyMatrix4ByVector3(matrix, vertex);

        Vector3f expected = new Vector3f(4, 5, 6);

        assertEquals(expected.getX(), result.getX(), 0.001);
        assertEquals(expected.getY(), result.getY(), 0.001);
        assertEquals(expected.getZ(), result.getZ(), 0.001);
    }

    @Test
    public void testVertexToPoint() {
        Vector3f transformedPoint = new Vector3f(0.5, 0.5, -1);
        Point2f screenPoint = GraphicConveyor.vertexToPoint(transformedPoint, 1920, 1080);

        assertNotNull(screenPoint);
        assertEquals(1920.0, screenPoint.getX(), 0.001);
        assertEquals(0.0, screenPoint.getY(), 0.001);
    }

}
