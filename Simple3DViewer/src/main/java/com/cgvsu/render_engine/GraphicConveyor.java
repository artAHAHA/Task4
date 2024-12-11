package com.cgvsu.render_engine;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.matrix.Matrix4f;

import javax.vecmath.Point2f;

public class GraphicConveyor {
    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0, 1.0, 0));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultZ = new Vector3f(eye.getX(), eye.getY(), eye.getZ());
        resultZ.subtract(eye, target);
        resultZ.normalize();

        Vector3f resultX = new Vector3f(up.getX(), up.getY(), up.getZ());
        resultX.cross(up, resultZ);
        resultX.normalize();

        Vector3f resultY = new Vector3f(resultZ.getX(), resultZ.getY(), resultZ.getZ());
        resultY.cross(resultZ, resultX);

        double[][] matrix = new double[][]{
                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
                {-resultX.scalarMultiplication(eye), -resultY.scalarMultiplication(eye), -resultZ.scalarMultiplication(eye), 1}
        };
        return new Matrix4f(matrix).transpose();
    }
    public static Matrix4f perspective(
            final double fov,
            final double aspectRatio,
            final double nearPlane,
            final double farPlane) {
        double tangentMinusOnDegree = 1.0 / Math.tan(fov * 0.5);
        double[][] matrix = new double[4][4];
        matrix[0][0] = tangentMinusOnDegree / aspectRatio;
        matrix[1][1] = tangentMinusOnDegree;
        matrix[2][2] = (farPlane + nearPlane) / (nearPlane - farPlane);
        matrix[2][3] = 1.0;
        matrix[3][2] = (2 * nearPlane * farPlane) / (nearPlane - farPlane);
        matrix[3][3] = 0.0;
        return new Matrix4f(matrix);
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3f vertex) {
        double x = (matrix.getElement(0, 0) * vertex.getX()) + (matrix.getElement(0, 1) * vertex.getY()) +
                (matrix.getElement(0, 2) * vertex.getZ()) + matrix.getElement(0, 3);
        double y = (matrix.getElement(1, 0) * vertex.getX()) + (matrix.getElement(1, 1) * vertex.getY()) +
                (matrix.getElement(1, 2) * vertex.getZ()) + matrix.getElement(1, 3);
        double z = (matrix.getElement(2, 0) * vertex.getX()) + (matrix.getElement(2, 1) * vertex.getY()) +
                (matrix.getElement(2, 2) * vertex.getZ()) + matrix.getElement(2, 3);
        double w = (matrix.getElement(3, 0) * vertex.getX()) + (matrix.getElement(3, 1) * vertex.getY()) +
                (matrix.getElement(3, 2) * vertex.getZ()) + matrix.getElement(3, 3);
        return new Vector3f(x / w, y / w, z / w);
    }


    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f((float) (vertex.getX() * width + width / 2.0), (float) (-vertex.getY() * height + height / 2.0));
    }
}
