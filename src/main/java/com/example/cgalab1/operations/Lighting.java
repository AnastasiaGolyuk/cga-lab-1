package com.example.cgalab1.operations;

import com.example.cgalab1.components.Face;
import com.example.cgalab1.components.Vertex;
import java.util.List;

import static com.example.cgalab1.calculation.Calculation.findCosBetweenVectors;


public class Lighting {
    private static final Lighting INSTANCE = new Lighting();

    private final Vertex light = new Vertex(1, 1, 1, 0);

    private Lighting() {
    }

    public static Lighting getInstance() {
        return INSTANCE;
    }

    public void modelLambert(List<Face> faces) {
        light.setTranslatedX(light.getX());
        light.setTranslatedY(light.getY());
        light.setTranslatedZ(light.getZ());
        light.setTranslatedW(light.getW());
        for (var face : faces) {
            float color = 0;
            for (int i=0;i<3;i++){
                float cos=findCosBetweenVectors(face.getFaceVertexList().get(i).getVertexNormal().toFloatArray(), light.toFloatArray());
                cos++;
                cos/=2;
                color+=cos;
            }
            color/=3;
            face.getFaceVertexList().get(0).getVertex().setColor(color);
        }
    }
}
