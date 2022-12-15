package com.example.cgalab1.model;

import org.ejml.simple.SimpleMatrix;

public class PreparationStorage {
    private SimpleMatrix modelMatrix;
    private SimpleMatrix viewMatrix;
    private SimpleMatrix projectionMatrix;
    private SimpleMatrix viewPortMatrix;

    private SimpleMatrix translationNormalsMatrix;

    public PreparationStorage(SimpleMatrix modelMatrix, SimpleMatrix viewMatrix, SimpleMatrix projectionMatrix, SimpleMatrix viewPortMatrix) {
        this.modelMatrix = modelMatrix;
        this.viewMatrix = viewMatrix;
        this.projectionMatrix = projectionMatrix;
        this.viewPortMatrix = viewPortMatrix;
    }

    public SimpleMatrix getTranslationNormalsMatrix() {
        return translationNormalsMatrix;
    }

    public void setTranslationNormalsMatrix(SimpleMatrix translationNormalsMatrix) {
        this.translationNormalsMatrix = translationNormalsMatrix;
    }

    public SimpleMatrix getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(SimpleMatrix modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public SimpleMatrix getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(SimpleMatrix viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public SimpleMatrix getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(SimpleMatrix projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public SimpleMatrix getViewPortMatrix() {
        return viewPortMatrix;
    }

    public void setViewPortMatrix(SimpleMatrix viewPortMatrix) {
        this.viewPortMatrix = viewPortMatrix;
    }

}

