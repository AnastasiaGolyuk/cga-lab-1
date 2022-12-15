package com.example.cgalab1.components;

public class VertexTexture {
    private float u;
    private float v;
    private float w;

    public VertexTexture(float u, float v, float w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public float getU() {
        return u;
    }

    public void setU(float u) {
        this.u = u;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float[] toFloatArray() {
        return new float[]{getU(), getV(), getW()};
    }

    public static VertexTexture getFromFloatArray(float[] array) {
        return new VertexTexture(array[0], array[1], array[2]);
    }

    public double[] toDoubleArray() {
        return new double[]{getU(), getV(), getW()};
    }

}
