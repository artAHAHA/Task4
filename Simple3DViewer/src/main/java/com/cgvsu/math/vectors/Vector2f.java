package com.cgvsu.math.vectors;

import com.cgvsu.math.exception.MathExceptions;

public class Vector2f extends AbstractVector implements Vector {

    public Vector2f(float[] values) {
        if (checkLengthInputValues(values)) {
            super.values = values;
            super.size = values.length;
        } else throw new MathExceptions();
    }

    public Vector2f(float v1, float v2) {
        super.values = new float[2];

        super.size = 2;

        super.values[0] = v1;
        super.values[1] = v2;
    }

    public Vector2f() {
    }

    @Override
    public void vectorCrossProduct(Vector v2) {
    }

    @Override
    public Vector vectorCrossProduct(Vector v1, Vector v2) {
        return new Vector3f();
    }

    @Override
    protected boolean checkLengthInputValues(float[] values) {
        return values.length == 2;
    }
}