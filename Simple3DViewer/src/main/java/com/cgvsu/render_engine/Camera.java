package com.cgvsu.render_engine;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vectors.Vector4f;


public class Camera {
    private final double MIN_DISTANCE = 5;
    private final double MIN_ANGLE = Math.toRadians(10);
    private final double MAX_ANGLE = Math.toRadians(170);
    private final double SMOOTH_BOUNDARY = Math.toRadians(5);


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
        this.position = this.position.add(translation);
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
        Vector3f right = up.cross(direction).getNormalized();

        double angleWithUp = calculateAngle(direction, up);
        deltaY = clampDeltaY(deltaY, angleWithUp);

        direction = rotationAroundAxis(right, deltaY, direction);
        direction = rotationAroundAxis(up, deltaX, direction);

        position = target.add(direction);
    }

    private double calculateAngle(Vector3f vec1, Vector3f vec2) {
        double dot = vec1.dot(vec2);
        double lengths = vec1.getLength() * vec2.getLength();
        return Math.acos(dot / lengths);
    }

    private double clampDeltaY(double deltaY, double angleWithUp) {

        // Жестко ограничиваем deltaY
        if (angleWithUp <= MIN_ANGLE && deltaY > 0) {
            deltaY = 0; // Останавливаем вращение при приближении к минимальному углу
        } else if (angleWithUp >= MAX_ANGLE && deltaY < 0) {
            deltaY = 0; // Останавливаем вращение при приближении к максимальному углу
        }


        // Плавное замедление вращения при приближении к пределам
        if (angleWithUp <= MIN_ANGLE + SMOOTH_BOUNDARY && deltaY > 0) {
            return deltaY * (angleWithUp - MIN_ANGLE) / SMOOTH_BOUNDARY;
        }
        if (angleWithUp >= MAX_ANGLE - SMOOTH_BOUNDARY && deltaY < 0) {
            return deltaY * (MAX_ANGLE - angleWithUp) / SMOOTH_BOUNDARY;
        }

        return deltaY;
    }

    private Vector3f rotationAroundAxis(Vector3f axis, double delta, Vector3f vector) {
        if (delta == 0) {
            return vector;
        }
        Matrix4f rotation = Matrix4f.rotateAroundAxis(axis, (float) (-delta * 0.01));
        return applyMatrixToVector(rotation, vector);
    }

    private Vector3f applyMatrixToVector(Matrix4f matrix, Vector3f vector) {
        Vector4f vec4 = new Vector4f(vector.getX(), vector.getY(), vector.getZ(), 1.0);
        vec4 = matrix.multiplyingMatrixByVector(vec4);
        return new Vector3f(vec4.getX(), vec4.getY(), vec4.getZ());
    }

    // Методы для увеличения и уменьшения зума
    public void zoom(double scaleFactor) {
        Vector3f direction = position.subtract(target).getNormalized();
        direction.multiplyByScalar(-scaleFactor);
        Vector3f newPosition = position.add(direction);

        // Проверка, что камера не приблизилась слишком близко
        double distanceToTarget = newPosition.subtract(target).getLength();
        if (distanceToTarget >= MIN_DISTANCE) {
            position = newPosition;  // Обновление позиции только если расстояние допустимо
        }
    }
    public void zoomIn() {
        zoom(5);
    }

    public void zoomOut() {
        zoom(-5);
    }



}




