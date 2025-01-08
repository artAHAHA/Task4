package com.cgvsu.math.vectors;

public class Vector2f extends AbstractVector<Vector2f> implements Cloneable{

    public Vector2f(double... components) {
        super(components);
    }

    @Override
    public Vector2f createInstance(double... components) {
        return new Vector2f(components);
    }

    @Override
    public Vector2f clone()  {
        try {
            return (Vector2f) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
