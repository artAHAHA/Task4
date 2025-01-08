package com.cgvsu.math.vectors;

import com.cgvsu.math.exception.MathExceptions;

import javax.swing.plaf.SplitPaneUI;

public class Vector3f extends AbstractVector implements Vector {
    public Vector3f() {
    }
    public Vector3f(Vector3f v){
        super.values = v.values;
        super.size = values.length;
    }
    public Vector3f(float[] values) {
        if (checkLengthInputValues(values)) {
            super.values = values;
            super.size = values.length;
        } else throw new MathExceptions();
    }

    public Vector3f(float v1, float v2, float v3) {
        super.values = new float[3];

        super.size = 3;

        super.values[0] = v1;
        super.values[1] = v2;
        super.values[2] = v3;
    }

    public void vectorCrossProduct(Vector v2) {
        Vector3f vRes = new Vector3f();
        float[] tmp = new float[3];
        if (this.getSize() != v2.getSize() || !checkLengthInputValues(this.getValues())
                || !checkLengthInputValues(v2.getValues())) {
            throw new MathExceptions();
        }

        tmp[0] = this.getValues()[1] * v2.getValues()[2] - this.getValues()[2] * v2.getValues()[1];
        tmp[1] = -(this.getValues()[0] * v2.getValues()[2] - this.getValues()[2] * v2.getValues()[0]);
        tmp[2] = this.getValues()[0] * v2.getValues()[1] - this.getValues()[1] * v2.getValues()[0];
        vRes.setValues(tmp);

        this.values = vRes.values;
        this.size = vRes.size;
    }

    public Vector vectorCrossProduct(Vector v1, Vector v2) {

        float[] tmp = new float[3];
        if (v1.getSize() != v2.getSize() && !checkLengthInputValues(v1.getValues())
                && !checkLengthInputValues(v2.getValues())) {
            throw new MathExceptions();
        }
        tmp[0] = v1.getValues()[1] * v2.getValues()[2] - v1.getValues()[2] * v2.getValues()[1];
        tmp[1] = -(v1.getValues()[0] * v2.getValues()[2] - v1.getValues()[2] * v2.getValues()[0]);
        tmp[2] = v1.getValues()[0] * v2.getValues()[1] - v1.getValues()[1] * v2.getValues()[0];

        this.setValues(tmp);
        this.size = tmp.length;
        return this;
    }
    public void add(Vector3f other) {
        if (other != null) {
            this.values[0] += other.values[0];
            this.values[1] += other.values[1];
            this.values[2] += other.values[2];
        }
    }
    public void add(float dx, float dy, float dz) {
        this.values[0] += dx;
        this.values[1] += dy;
        this.values[2] += dz;
    }

    // Получение значения по индексу
    public float getX() {
        return this.values[0];
    }

    public float getY() {
        return this.values[1];
    }

    public float getZ() {
        return this.values[2];
    }

    public void setX(float x) {
        this.values[0] = x;
    }

    public void setY(float y) {
        this.values[1] = y;
    }

    public void setZ(float z) {
        this.values[2] = z;
    }
    @Override
    protected boolean checkLengthInputValues(float[] values) {
        return values.length == 3;
    }

    public static Vector3f subtraction(Vector3f v1, Vector3f v2) {
        return new Vector3f(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
    }
}
