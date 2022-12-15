package com.example.cgalab1.operations;

import com.example.cgalab1.components.Vertex;
import com.example.cgalab1.components.VertexNormal;
import com.example.cgalab1.model.PreparationStorage;
import org.ejml.simple.SimpleMatrix;

import java.util.List;


public class Translation {

    public void translateVertexes(List<Vertex> vertexes, PreparationStorage preparationStorage) {
        SimpleMatrix translationMatrix = preparationStorage.getProjectionMatrix()
                .mult(preparationStorage.getViewMatrix()).mult(preparationStorage.getModelMatrix());
        vertexes.parallelStream().forEach(vertex -> {
            translate(vertex, translationMatrix);
            vertex.setTranslatedX(vertex.getTranslatedX() / vertex.getTranslatedW());
            vertex.setTranslatedY(vertex.getTranslatedY() / vertex.getTranslatedW());
            vertex.setTranslatedZ(vertex.getTranslatedZ() / vertex.getTranslatedW());
            vertex.setTranslatedW(1.0f);
            viewPortTranslate(vertex, preparationStorage.getViewPortMatrix());
        });
    }

    public void translateNormals(List<VertexNormal> vertexNormals, SimpleMatrix translationMatrix) {
        SimpleMatrix finalMatrix = new SimpleMatrix(3,3);
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                finalMatrix.set(i,j,translationMatrix.get(i,j));
            }
        }
        finalMatrix.set(finalMatrix.transpose().invert());
        vertexNormals.parallelStream().forEach(vertex -> {
            translateNormal(vertex, translationMatrix);
        });
    }

    public void translateVertexesWorld(List<Vertex> vertexes, PreparationStorage preparationStorage) {
        SimpleMatrix translationMatrix = preparationStorage.getViewMatrix().mult(preparationStorage.getModelMatrix());
        vertexes.parallelStream().forEach(vertex -> {
            translate(vertex, translationMatrix);
            vertex.setTranslatedX(vertex.getTranslatedX() / vertex.getTranslatedW());
            vertex.setTranslatedY(vertex.getTranslatedY() / vertex.getTranslatedW());
            vertex.setTranslatedZ(vertex.getTranslatedZ() / vertex.getTranslatedW());
            vertex.setTranslatedW(1.0f);
        });
    }

    private void translate(Vertex vertex, SimpleMatrix translationMatrix) {
        SimpleMatrix sourceMatrix = new SimpleMatrix(new float[][]{
                new float[]{vertex.getX()},
                new float[]{vertex.getY()},
                new float[]{vertex.getZ()},
                new float[]{vertex.getW()},
        });
        setTranslatedVertex(vertex, translationMatrix, sourceMatrix);
    }

    private void translateNormal(VertexNormal vertexNormal, SimpleMatrix translationMatrix){
        SimpleMatrix sourceMatrix = new SimpleMatrix(new float[][]{
                new float[]{vertexNormal.getI()},
                new float[]{vertexNormal.getJ()},
                new float[]{vertexNormal.getK()},
                new float[]{0.0f}
        });
        setTranslatedNormal(vertexNormal,translationMatrix,sourceMatrix);
    }

    private void viewPortTranslate(Vertex vertex, SimpleMatrix viewPortMatrix) {
        SimpleMatrix sourceMatrix = new SimpleMatrix(new float[][]{
                new float[]{vertex.getTranslatedX()},
                new float[]{vertex.getTranslatedY()},
                new float[]{vertex.getTranslatedZ()},
                new float[]{vertex.getTranslatedW()},
        });
        setTranslatedVertex(vertex, viewPortMatrix, sourceMatrix);
    }

    private void setTranslatedVertex(Vertex vertex, SimpleMatrix translationMatrix, SimpleMatrix sourceMatrix) {
        SimpleMatrix result = translationMatrix.mult(sourceMatrix);
        vertex.setTranslatedX((float) result.get(0, 0));
        vertex.setTranslatedY((float) result.get(1, 0));
        vertex.setTranslatedZ((float) result.get(2, 0));
        vertex.setTranslatedW((float) result.get(3, 0));
    }

    private void setTranslatedNormal(VertexNormal vertex, SimpleMatrix translationMatrix, SimpleMatrix sourceMatrix) {
        SimpleMatrix result = translationMatrix.mult(sourceMatrix);
        vertex.setI((float) result.get(0, 0));
        vertex.setJ((float) result.get(1, 0));
        vertex.setK((float) result.get(2, 0));
    }

}

