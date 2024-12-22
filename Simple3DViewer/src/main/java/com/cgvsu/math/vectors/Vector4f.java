package com.cgvsu.math.vectors;

public class Vector4f extends AbstractVector<Vector4f> {

    public Vector4f(double... components) {
        super(components);
    }

    public Vector3f truncate() {
        if (components.length <= 1) {
            throw new UnsupportedOperationException("Cannot truncate a vector with dimension 1 or less.");
        }

        // Убираем последний компонент и создаем новый Vector3f
        return new Vector3f(components[0], components[1], components[2]);
    }

    @Override
    public Vector4f createInstance(double... components) {
        return new Vector4f(components);
    }
}
