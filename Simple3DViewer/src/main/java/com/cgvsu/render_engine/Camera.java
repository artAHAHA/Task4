package com.cgvsu.render_engine;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vectors.Vector4f;

public class Camera {

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }


    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target = this.target.add(translation);
    }

    public Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target, new Vector3f(0F, 1.0F, 0F));
    }

    Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;

        public void rotate(double deltaX, double deltaY) {

            Vector3f direction = position.subtract(target);

            Vector3f up = new Vector3f(0, 1, 0);
            Vector3f right = up.cross(direction);
            right.normalize();
            up.normalize();


            // Создаем матрицы вращения вокруг осей "right" и "up"
            Matrix4f rotationX = Matrix4f.rotateAroundAxis(right, (float) (-deltaY * 0.01));
            Matrix4f rotationY = Matrix4f.rotateAroundAxis(up, (float) (-deltaX * 0.01));


            // Преобразуем direction (Vector3f) в Vector4f для умножения
            Vector4f direction4f = new Vector4f(direction.getX(), direction.getY(), direction.getZ(), 1.0);
            direction4f = rotationX.multiplyingMatrixByVector(direction4f);
            direction4f = rotationY.multiplyingMatrixByVector(direction4f);
            direction = new Vector3f(direction4f.getX(), direction4f.getY(), direction4f.getZ());

            position = target.add(direction);
        }


    // Методы для увеличения и уменьшения зума
    public void zoomIn() {
        Vector3f direction = new Vector3f(position.getX(), position.getY(), position.getZ());
        direction.subtract(position, target);
        direction.normalize();
        direction.multiplyingVectorByScalar(-100);
        position = position.add(direction);
    }

    public void zoomOut() {
        Vector3f direction = new Vector3f(position.getX(), position.getY(), position.getZ());
        direction.subtract(position, target);
        direction.normalize();
        direction.multiplyingVectorByScalar(100);
        position = position.add(direction);


    }


}