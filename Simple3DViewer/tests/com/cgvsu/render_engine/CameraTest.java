package com.cgvsu.render_engine;

import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vectors.Vector3f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CameraTest {

    @Test
    void testSetPositionAndTargetAndSetAspectRatioAndFovAndNearAndFarPlane() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        Vector3f newPosition = new Vector3f(1, 1, 1);
        Vector3f newTarget = new Vector3f(0, 1, 0);
        float newAspectRatio = 1F;
        float newFov = 10F;
        float newNearPlane = 10F;
        float newFarPlane = 1000F;

        camera.setPosition(newPosition);
        camera.setTarget(newTarget);
        camera.setAspectRatio(1);
        camera.setFov(newFov);
        camera.setNearPlane(newNearPlane);
        camera.setFarPlane(newFarPlane);


        assertEquals(newPosition, camera.getPosition(), "Метод setPosition не обновил позицию правильно.");
        assertEquals(newTarget, camera.getTarget(), "Метод setTarget не обновил цель правильно.");
        assertEquals(newAspectRatio, camera.getAspectRatio());
        assertEquals(newFov, camera.getFov());
        assertEquals(newNearPlane, camera.getNearPlane());
        assertEquals(newFarPlane, camera.getFarPlane());
    }

    @Test
    void testMovePosition() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        Vector3f translation = new Vector3f(1, 1, 0);
        camera.movePosition(translation);
        Vector3f expected = new Vector3f(1, 1, 10);
        assertTrue(expected.isEqual(camera.getPosition()), "Метод movePosition не обновил позицию правильно.");
    }

    @Test
    void testMoveTarget() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        Vector3f translation = new Vector3f(1, 1, 0);
        camera.moveTarget(translation);
        Vector3f expected = new Vector3f(1, 1, 0);
        assertTrue(expected.isEqual(camera.getTarget()), "Метод moveTarget не обновил позицию правильно.");


    }

    @Test
    void  testGetViewMatrix() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        Matrix4f viewMatrix = camera.getViewMatrix();

        double[] expectedArray = new double[] {
                1.0,	0.0,	0.0,	-0.0,
                0.0,	1.0,	0.0,	-0.0,
                0.0,	0.0,	1.0,	-10.0,
                0.0,    0.0,	0.0,	1.0
        };
        double epsilon = 1e-6;


        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {

                double actualValue = viewMatrix.getElement(row, col);
                double expectedValue = expectedArray[row * 4 + col];

                assertEquals(expectedValue, actualValue, epsilon,
                        "Матрицы не совпадают по элементу [" + row + ", " + col + "]");
            }
        }
    }
    @Test
    public void testGetProjectionMatrix() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(90),
                1.77f,
                0.1f,
                1000f
        );


        Matrix4f projectionMatrix = camera.getProjectionMatrix();


        assertNotNull(projectionMatrix);

        double fov = Math.toRadians(90);
        double aspectRatio = 1.77;
        double nearPlane = 0.1;
        double farPlane = 1000;

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
    void testZoom() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        camera.zoom(2);
        Vector3f expected = new Vector3f(0, 0, 8);

        assertTrue(expected.isEqual(camera.getPosition()), "Метод zoom не приблизил камеру правильно.");
    }

    @Test
    void testZoomIn() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        camera.zoomIn();
        Vector3f expected = new Vector3f(0, 0, 5);
        Vector3f actual = camera.getPosition();

        assertTrue(expected.isEqual(actual), "Метод zoom не приблизил камеру правильно.");
    }

    @Test
    void testZoomOut() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        camera.zoomOut();
        Vector3f expected = new Vector3f(0, 0, 15);
        Vector3f actual = camera.getPosition();

        assertTrue(expected.isEqual(actual), "Метод zoom не приблизил камеру правильно.");
    }


    @Test
    void testZoomMinDistanceConstraint() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 6),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        camera.zoom(2);
        Vector3f expected = new Vector3f(0, 0, 6);
        assertTrue(expected.isEqual(camera.getPosition()), "Камера приблизилась ближе минимального расстояния.");

    }

    @Test
    void testRotation() {
        Camera camera = new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                (float) Math.toRadians(60),
                16f / 9f,
                0.1f,
                100f
        );

        camera.rotate(90, 0);
        Vector3f rotatedPosition = camera.getPosition();
        assertNotEquals(new Vector3f(0, 0, 10), rotatedPosition, "Метод rotate не изменил позицию камеры.");
    }

}
