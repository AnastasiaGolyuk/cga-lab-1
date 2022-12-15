package com.example.cgalab1.operations;

import com.example.cgalab1.components.Face;
import com.example.cgalab1.components.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.cgalab1.calculation.Calculation.findCosBetweenVectors;
import static com.example.cgalab1.calculation.Calculation.subtractVector;

public class FaceRejection {
    private static final FaceRejection INSTANCE = new FaceRejection();

    private FaceRejection() {
    }

    public static FaceRejection getInstance() {
        return INSTANCE;
    }

    public List<Face> rejectFacesFromCamera(List<Face> faces) {
        List<Face> res = new ArrayList<>();
        for (var face: faces) {
            List<Vertex> triangle = new ArrayList<>();
            for (int i=0;i<5;i++){
                triangle.add(new Vertex(0,0,0,1));
            }
            triangle.set(1, face.getFaceVertexList().get(0).getVertex());
            triangle.set(2, face.getFaceVertexList().get(1).getVertex());
            triangle.set(3, face.getFaceVertexList().get(2).getVertex());
            triangle.set(0,triangle.get(3));
            triangle.set(4,triangle.get(1));
            int i = 0;
            double cos = -1.0;
            Vertex vertexAB = new Vertex(0,0,0,1);
            Vertex vertexCB=new Vertex(0,0,0,1);
            while ((cos >= 1 || cos < 0) && i < 3) {
                i++;
                vertexAB = Vertex.getFromFloatArray(subtractVector(triangle.get(i-1).toFloatArray(), triangle.get(i).toFloatArray()));
                vertexCB = Vertex.getFromFloatArray(subtractVector(triangle.get(i + 1).toFloatArray(), triangle.get(i).toFloatArray()));
                cos = findCosBetweenVectors(vertexAB.toFloatArray(), vertexCB.toFloatArray());
                //System.out.println(i);
            }

            double dot = vertexAB.getY() * vertexCB.getX() - vertexAB.getX() * vertexCB.getY();
            //System.out.println(dot);
            if (dot < 0.0) {
                res.add(face);
            }
        }
        return res;
    }


}
