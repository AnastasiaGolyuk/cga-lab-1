package com.example.cgalab1.components;

public class VertexNormal {
    private float i;
    private float j;
    private float k;

    private int color;

    public VertexNormal(float i, float j, float k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public float getI() {
        return i;
    }

    public void setI(float i) {
        this.i = i;
    }

    public float getJ() {
        return j;
    }

    public void setJ(float j) {
        this.j = j;
    }

    public float getK() {
        return k;
    }

    public void setK(float k) {
        this.k = k;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float[] toFloatArray(){
        return new float[] {getI(),getJ(),getK()};
    }

    public static VertexNormal getFromFloatArray(float[] array){
        return new VertexNormal(array[0],array[1],array[2]);
    }

    public double[] toDoubleArray(){
        return new double[] {getI(),getJ(),getK()};
    }
}
