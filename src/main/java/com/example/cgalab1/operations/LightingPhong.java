package com.example.cgalab1.operations;

import com.example.cgalab1.components.VertexNormal;
import com.example.cgalab1.model.ComponentStorage;
import com.example.cgalab1.model.PreparationStorage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.example.cgalab1.calculation.Calculation.*;

public class LightingPhong {

    private static final LightingPhong INSTANCE = new LightingPhong();


    private LightingPhong() {
    }

    private BufferedImage normalImage;
    private BufferedImage diffuseImage;
    private BufferedImage specularImage;

    public static LightingPhong getInstance() {
        return INSTANCE;
    }

    private static final float[] ia = new float[]{0.5f, 0.5f, 0.5f};
    private static final float[] id = new float[]{1.0f, 1.0f, 1.0f};
    private static final float[] is = new float[]{1.0f, 1.0f, 1.0f};

    private static final float[] kAmbient = new float[]{0.3f, 0.3f, 0.3f};
    private static final float[] kDiffuse = new float[]{0.5f, 0.5f, 0.5f};
    private static final float[] kSpecular = new float[]{1.0f, 1.0f, 1.0f};

    public static Color La;
    public static Color Ld;
    public static Color Ls;
    private static final float[] light = {100.0f, 100.0f, 15.0f, 0.0f};
    private static final float[] view = {0.0f, 0.0f, 5.0f, 0.0f};
    private static float[] viewDir;


    private void ambient() {
        La = Color.rgb((int) (ia[0] * 255 * kAmbient[0]),
                (int) (ia[1] * 255 * kAmbient[1]),
                (int) (ia[2] * 255 * kAmbient[2]));
    }

    private void diffuse(float[] normal) {
        float[] inLight = new float[]{-light[0], -light[1], -light[2], 0.0f};
        double temp =  0.7*Math.max(dotProduct(normal, inLight), 0.0f);
        Ld = Color.rgb(Math.min((int) (temp * id[0] * 255 * kDiffuse[0]), 255),
                Math.min((int) (temp * id[1] * 255 * kDiffuse[1]), 255),
                Math.min((int) (temp * id[2] * 255 * kDiffuse[2]), 255));
    }

    private void specular(float[] normal) {
        float LN = dotProduct(normal, light);
        float[] LNN = multiplyVectorByScalar(normal, -2 * LN);
        float[] R = normalizeVector(addVector(light, LNN));

        float a = 32;
        double temp = 0.7* Math.pow(Math.max(dotProduct(R, viewDir), 0.0f), a);
        Ls = Color.rgb(Math.min((int) (temp * is[0] * 255 * kSpecular[0]), 255),
                Math.min((int) (temp * is[1] * 255 * kSpecular[1]), 255),
                Math.min((int) (temp * is[2] * 255 * kSpecular[2]), 255));
    }

    public Color getColor(float[] vertex, float[] normal) {
        normalizeVector(light);
        normalizeVector(normal);
        viewDir = normalizeVector(subtractVector(view, vertex));

        ambient();
        diffuse(normal);
        specular(normal);
        int Lred = Math.min((int) ((La.getRed() + Ld.getRed() + Ls.getRed()) * 255), 255);

        int Lgreen = Math.min((int) ((La.getGreen() + Ld.getGreen() + Ls.getGreen()) * 255), 255);

        int Lblue = Math.min((int) ((La.getBlue() + Ld.getBlue() + Ls.getBlue()) * 255), 255);

        return Color.rgb(Lred, Lgreen, Lblue);
    }

    public Color getColor(float[] vertex, double[] texture, ComponentStorage componentStorage, PreparationStorage preparationStorage) {
        Translation translation = new Translation();
        normalizeVector(light);
        float[] normal = new float[3];
        viewDir = normalizeVector(subtractVector(view, vertex));

        diffuseImage = componentStorage.getDiffuseImage();
        int x = (int) Math.round((texture[0]) * diffuseImage.getWidth());
        int y = (int) Math.round((1 - texture[1]) * diffuseImage.getHeight());
        if (x >= diffuseImage.getWidth()) {
            x -= diffuseImage.getWidth();
        } else if (x < 0) {
            x += diffuseImage.getWidth();
        }
        if (y >= diffuseImage.getHeight()) {
            y -= diffuseImage.getHeight();
        } else if (y < 0) {
            y += diffuseImage.getHeight();
        }
        var clr = diffuseImage.getRGB(x, y);
        kAmbient[0] = (float) (((clr & 0x00ff0000) >> 16) / 255.0);
        kAmbient[1] = (float) (((clr & 0x0000ff00) >> 8) / 255.0);
        kAmbient[2] = (float) ((clr & 0x000000ff) / 255.0);
        kDiffuse[0] = kAmbient[0];
        kDiffuse[1] = kAmbient[1];
        kDiffuse[2] = kAmbient[2];

        specularImage = componentStorage.getSpecularImage();
        clr = specularImage.getRGB(x, y);
        kSpecular[0] = (float) (((clr & 0x00ff0000) >> 16) / 255.0);
        kSpecular[1] = (float) (((clr & 0x0000ff00) >> 8) / 255.0);
        kSpecular[2] = (float) ((clr & 0x000000ff) / 255.0);

        normalImage = componentStorage.getNormalImage();
        clr = normalImage.getRGB(x, y);
        normal[0] = (float) (((clr & 0x00ff0000) >> 16) / 255.0 * 2 - 1);
        normal[1] = (float) (((clr & 0x0000ff00) >> 8) / 255.0 * 2 - 1);
        normal[2] = (float) ((clr & 0x000000ff) / 255.0 * 2 - 1);

        if (preparationStorage.getTranslationNormalsMatrix() != null) {
            VertexNormal vertexNormal = VertexNormal.getFromFloatArray(normal);
            List<VertexNormal> vertexNormalList = new ArrayList<>();
            vertexNormalList.add(vertexNormal);
            translation.translateNormals(vertexNormalList, preparationStorage.getTranslationNormalsMatrix());
            normal = vertexNormalList.get(0).toFloatArray();
        }
        ambient();
        diffuse(normal);
        specular(normal);
        int Lred = Math.min((int) ((La.getRed() + Ld.getRed() + Ls.getRed()) * 255), 255);
        int Lgreen = Math.min((int) ((La.getGreen() + Ld.getGreen() + Ls.getGreen()) * 255), 255);
        int Lblue = Math.min((int) ((La.getBlue() + Ld.getBlue() + Ls.getBlue()) * 255), 255);
//        int Lred = Math.min((int) ((La.getRed()) * 255), 255);
//        int Lgreen = Math.min((int) ((La.getGreen()) * 255), 255);
//        int Lblue = Math.min((int) ((La.getBlue() ) * 255), 255);

        return Color.rgb(Lred, Lgreen, Lblue);
    }
}