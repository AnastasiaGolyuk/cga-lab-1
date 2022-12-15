package com.example.cgalab1.components;

public class Vertex {
    private float x;
    private float y;
    private float z;
    private float w;
    private float translatedX;
    private float translatedY;
    private float translatedZ;
    private float translatedW;

    private float color;

    public Vertex(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getTranslatedX() {
        return translatedX;
    }

    public void setTranslatedX(float translatedX) {
        this.translatedX = translatedX;
    }

    public float getTranslatedY() {
        return translatedY;
    }

    public void setTranslatedY(float translatedY) {
        this.translatedY = translatedY;
    }

    public float getTranslatedZ() {
        return translatedZ;
    }

    public void setTranslatedZ(float translatedZ) {
        this.translatedZ = translatedZ;
    }

    public float getTranslatedW() {
        return translatedW;
    }

    public void setTranslatedW(float translatedW) {
        this.translatedW = translatedW;
    }

    public float getColor() {
        return color;
    }

    public void setColor(float color) {
        this.color = color;
    }

    public float[] toFloatArray(){
        return new float[] {getTranslatedX(),getTranslatedY(),getTranslatedZ(),getTranslatedW()};
    }

    public double[] toDoubleArray(){
        return new double[] {getTranslatedX(),getTranslatedY(),getTranslatedZ(),getTranslatedW()};
    }

    public static Vertex getFromFloatArray(float[] array){
        return new Vertex(array[0],array[1],array[2],array[3]);
    }
}
