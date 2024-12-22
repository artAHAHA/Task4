package com.cgvsu.math.vectors;

public class Vector2f extends AbstractVector<Vector2f> {

    public Vector2f(double... components) {
        super(components);
    }

    @Override
    public Vector2f createInstance(double... components) {
        return new Vector2f(components);
    }
}
