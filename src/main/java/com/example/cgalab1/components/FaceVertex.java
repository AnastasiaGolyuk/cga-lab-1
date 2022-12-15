package com.example.cgalab1.components;

public class FaceVertex {
    private Vertex vertex;

    private Vertex vertexWorld;
    private VertexTexture vertexTexture;
    private VertexNormal vertexNormal;

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertexWorld(Vertex vertexWorld) {
        this.vertexWorld = vertexWorld;
    }

    public Vertex getVertexWorld() {
        return vertexWorld;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public VertexTexture getVertexTexture() {
        return vertexTexture;
    }

    public void setVertexTexture(VertexTexture vertexTexture) {
        this.vertexTexture = vertexTexture;
    }

    public VertexNormal getVertexNormal() {
        return vertexNormal;
    }

    public void setVertexNormal(VertexNormal vertexNormal) {
        this.vertexNormal = vertexNormal;
    }
}
