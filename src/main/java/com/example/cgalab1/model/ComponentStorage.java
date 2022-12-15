package com.example.cgalab1.model;

import com.example.cgalab1.components.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ComponentStorage {
    private static final ComponentStorage INSTANCE = new ComponentStorage();

    public static ComponentStorage getInstance() {
        return INSTANCE;
    }

    private final List<Vertex> vertexes = new ArrayList<>();

    private final List<Vertex> vertexesWorld = new ArrayList<>();
    private final List<VertexTexture> vertexTextures = new ArrayList<>();
    private final List<VertexNormal> vertexNormals = new ArrayList<>();
    private final List<Face> faces = new ArrayList<>();

    private BufferedImage normalImage;
    private BufferedImage diffuseImage;
    private BufferedImage specularImage;

    public void setNormalImage(BufferedImage normalImage) {
        this.normalImage = normalImage;
    }

    public void setDiffuseImage(BufferedImage diffuseImage) {
        this.diffuseImage = diffuseImage;
    }

    public void setSpecularImage(BufferedImage specularImage) {
        this.specularImage = specularImage;
    }

    public BufferedImage getDiffuseImage() {
        return diffuseImage;
    }

    public BufferedImage getNormalImage() {
        return normalImage;
    }

    public BufferedImage getSpecularImage() {
        return specularImage;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<VertexNormal> getVertexNormals() {
        return vertexNormals;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public List<Vertex> getVertexesWorld() {
        return vertexesWorld;
    }

    public void addVertex(float[] coordinates) {
        float w = coordinates.length > 3 ? coordinates[3] : 1;
        vertexes.add(new Vertex(coordinates[0], coordinates[1], coordinates[2], w));
        vertexesWorld.add(new Vertex(coordinates[0], coordinates[1], coordinates[2], w));
    }

    public void addVertexTexture(float[] coordinates) {
        float v = coordinates.length > 1 ? coordinates[1] : 0;
        float w = coordinates.length > 2 ? coordinates[2] : 0;
        vertexTextures.add(new VertexTexture(coordinates[0], v, w));
    }

    public void addVertexNormal(float[] coordinates) {
        vertexNormals.add(new VertexNormal(coordinates[0], coordinates[1], coordinates[2]));
    }

    public void addFace(int[][] indexes) {
        for (int i = 1; i < indexes.length - 1; i++) {
            Face face = new Face();
            FaceVertex faceVertex = new FaceVertex();
            setVertex(faceVertex, indexes[0][0]);
            setVertexesWorld(faceVertex, indexes[0][0]);
            setVertexTexture(faceVertex, indexes[0][1]);
            setVertexNormal(faceVertex, indexes[0][2]);
            face.addFaceVertex(faceVertex);
            faceVertex = new FaceVertex();
            setVertex(faceVertex, indexes[i][0]);
            setVertexesWorld(faceVertex, indexes[i][0]);
            setVertexTexture(faceVertex, indexes[i][1]);
            setVertexNormal(faceVertex, indexes[i][2]);
            face.addFaceVertex(faceVertex);
            faceVertex = new FaceVertex();
            setVertex(faceVertex, indexes[i + 1][0]);
            setVertexesWorld(faceVertex, indexes[i + 1][0]);
            setVertexTexture(faceVertex, indexes[i + 1][1]);
            setVertexNormal(faceVertex, indexes[i + 1][2]);
            face.addFaceVertex(faceVertex);
            faces.add(face);
        }
    }

    private void setVertex(FaceVertex faceVertex, int index) {
        if (index > 0) {
            faceVertex.setVertex(vertexes.get(index - 1));
        } else {
            faceVertex.setVertex(vertexes.get(vertexes.size() + index));
        }
    }

    private void setVertexesWorld(FaceVertex faceVertex, int index) {
        if (index > 0) {
            faceVertex.setVertexWorld(vertexesWorld.get(index - 1));
        } else {
            faceVertex.setVertexWorld(vertexesWorld.get(vertexesWorld.size() + index));
        }
    }

    private void setVertexTexture(FaceVertex faceVertex, int index) {
        if (index != 0) {
            if (index > 0) {
                faceVertex.setVertexTexture(vertexTextures.get(index - 1));
            } else {
                faceVertex.setVertexTexture(vertexTextures.get(vertexTextures.size() + index));
            }
        }
    }

    private void setVertexNormal(FaceVertex faceVertex, int index) {
        if (index != 0) {
            if (index > 0) {
                faceVertex.setVertexNormal(vertexNormals.get(index - 1));
            } else {
                faceVertex.setVertexNormal(vertexNormals.get(vertexNormals.size() + index));
            }
        }
    }
}
