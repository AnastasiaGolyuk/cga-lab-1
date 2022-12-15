package com.example.cgalab1.components;

import java.util.ArrayList;
import java.util.List;

public class Face {
    private final List<FaceVertex> faceVertexList = new ArrayList<>();

    public void addFaceVertex(FaceVertex faceVertex) {
        faceVertexList.add(faceVertex);
    }

    public List<FaceVertex> getFaceVertexList() {
        return faceVertexList;
    }
}
