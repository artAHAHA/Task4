package com.cgvsu.math.matrix;

import com.cgvsu.math.exception.MathExceptions;
import com.cgvsu.math.vectors.Vector;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;

public class Matrix4f extends AbstractMatrix implements Matrix {

    public Matrix4f(float[][] values) {
        if (checkLengthInputValues(values)) {
            super.value = values;
            super.size = values.length;
        } else {
            throw new MathExceptions();
        }
    }

    public Matrix4f(){

    }

    public Matrix4f(Matrix4f m) {
        super.value = m.value;
        super.size = value.length;
    }

    @Override
    public void setZeroMatrix() {
        super.size = 4;
        super.value = new float[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
    }

    @Override
    public void setSingleMatrix() {
        super.size = 4;
        super.value = new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

    }

    @Override
    protected boolean checkLengthInputValues(final float[][] values) {
        return values.length == 4 && values[0].length == 4 && values[1].length == 4 &&
                values[2].length == 4 && values[3].length == 4;
    }


    @Override
    public Vector productMatrixOnVector(final Matrix m1, final Vector v1) {
        Vector vRes = new Vector4f();

        float[] tmp = super.getMatrixAfterProductMatrixOnVector(m1, v1);

        vRes.setValues(tmp);
        return vRes;

    }

}