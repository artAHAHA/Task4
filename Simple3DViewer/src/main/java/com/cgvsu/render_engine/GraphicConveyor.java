package com.cgvsu.render_engine;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.points.Point2f;

public class GraphicConveyor {

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {

        Vector3f resultZ = eye.subtract(target);

        resultZ.normalize();

        Vector3f resultX = up.cross(resultZ);

        resultX.normalize();

        Vector3f resultY = resultZ.cross(resultX);

        double[][] matrix = new double[][]{
                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
                {-resultX.dot(eye), -resultY.dot(eye), -resultZ.dot(eye), 1}
        };

        return new Matrix4f(Matrix4f.flatten4x4(matrix)).transposition();
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
        return new Matrix4f(Matrix4f.flatten4x4(matrix));
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


