package com.example.cgalab1;

import com.example.cgalab1.components.Face;
import com.example.cgalab1.components.FaceVertex;
import com.example.cgalab1.components.Vertex;
import com.example.cgalab1.model.ComponentStorage;
import com.example.cgalab1.model.PreparationStorage;
import com.example.cgalab1.operations.FaceRejection;
import com.example.cgalab1.operations.Lighting;
import com.example.cgalab1.operations.LightingPhong;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

import static com.example.cgalab1.calculation.Calculation.*;
import static com.example.cgalab1.model.ConstStorage.HEIGHT;
import static com.example.cgalab1.model.ConstStorage.WIDTH;

public class Draw {

    static FaceRejection faceRejection = FaceRejection.getInstance();
    static Lighting lighting = Lighting.getInstance();
    static LightingPhong lightingPhong = LightingPhong.getInstance();

    public static void draw(PixelWriter pixelWriter, List<Face> faces) {
        for (Face face : faces) {
            List<FaceVertex> faceVertexList = face.getFaceVertexList();
            for (int i = 0; i < faceVertexList.size() - 1; i++) {
                Vertex first = faceVertexList.get(i).getVertex();
                Vertex second = faceVertexList.get(i + 1).getVertex();
                drawDDALine(pixelWriter, first.getTranslatedX(), first.getTranslatedY(),
                        second.getTranslatedX(), second.getTranslatedY());
            }
            Vertex last = faceVertexList.get(faceVertexList.size() - 1).getVertex();
            Vertex first = faceVertexList.get(0).getVertex();
            drawDDALine(pixelWriter, last.getTranslatedX(), last.getTranslatedY(),
                    first.getTranslatedX(), first.getTranslatedY());
        }
    }


    public static void draw2(List<Face> faces,
                             PixelWriter pixelWriter, PreparationStorage preparationStorage) {


        ComponentStorage componentStorage = ComponentStorage.getInstance();
        List<Face> newFaces = faceRejection.rejectFacesFromCamera(faces);

        //2 lab
        //lighting.modelLambert(faces);

        double[][] zBuffer = new double[(int) HEIGHT][(int) WIDTH];//y x
        for (var line : zBuffer) {
            Arrays.fill(line, 100);
        }
        for (var face : newFaces) {
            //4lab
            drawFilledTriangle4(face.getFaceVertexList().get(0).getVertexWorld().toDoubleArray(),
                    face.getFaceVertexList().get(1).getVertexWorld().toDoubleArray(),
                    face.getFaceVertexList().get(2).getVertexWorld().toDoubleArray(),
                    face.getFaceVertexList().get(0).getVertex().toDoubleArray(),
                    face.getFaceVertexList().get(1).getVertex().toDoubleArray(),
                    face.getFaceVertexList().get(2).getVertex().toDoubleArray(),
                    face.getFaceVertexList().get(0).getVertexTexture().toDoubleArray(),
                    face.getFaceVertexList().get(1).getVertexTexture().toDoubleArray(),
                    face.getFaceVertexList().get(2).getVertexTexture().toDoubleArray(), componentStorage,
                    pixelWriter, zBuffer, preparationStorage);

            //3lab
//            drawFilledTriangle3(face.getFaceVertexList().get(0).getVertexWorld().toDoubleArray(),
//                    face.getFaceVertexList().get(1).getVertexWorld().toDoubleArray(),
//                    face.getFaceVertexList().get(2).getVertexWorld().toDoubleArray(),
//                    face.getFaceVertexList().get(0).getVertex().toDoubleArray(),
//                    face.getFaceVertexList().get(1).getVertex().toDoubleArray(),
//                    face.getFaceVertexList().get(2).getVertex().toDoubleArray(),
//                    face.getFaceVertexList().get(0).getVertexNormal().toDoubleArray(),
//                    face.getFaceVertexList().get(1).getVertexNormal().toDoubleArray(),
//                    face.getFaceVertexList().get(2).getVertexNormal().toDoubleArray(),
//                    pixelWriter, zBuffer);

            //2lab
//            Color color = Color.hsb(Color.GOLD.getHue(), Color.GOLD.getSaturation(), face.getFaceVertexList().get(0).getVertex().getColor());
//            drawFilledTriangle2(face.getFaceVertexList().get(0).getVertex().toDoubleArray(),
//                    face.getFaceVertexList().get(1).getVertex().toDoubleArray(),
//                    face.getFaceVertexList().get(2).getVertex().toDoubleArray(),
//                    color, pixelWriter, zBuffer);

        }
    }

    public static void drawDDALine(PixelWriter pixelWriter, float x1, float y1, float x2, float y2) {
        double xAbs = Math.abs(x2 - x1);
        double yAbs = Math.abs(y2 - y1);
        int l = (int) (Math.max(xAbs, yAbs));
        double xGrowth = (x2 - x1) / l;
        double yGrowth = (y2 - y1) / l;
        double x = x1;
        double y = y1;
        for (int i = 0; i < l; i++, x += xGrowth, y += yGrowth) {
            pixelWriter.setColor((int) x, (int) y, Color.GREEN);
        }
    }

    //2lab
    public static void drawFilledTriangle2(double[] vertex1i, double[] vertex2i, double[] vertex3i, Color color, PixelWriter px, double[][] zBuffer) {


        int[] vertex1 = Arrays.stream(vertex1i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        int[] vertex2 = Arrays.stream(vertex2i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        int[] vertex3 = Arrays.stream(vertex3i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        double[] w;

        if (vertex2[1] < vertex1[1]) {
            swap(vertex1, vertex2);
        }
        if (vertex3[1] < vertex1[1]) {
            swap(vertex1, vertex3);
        }
        if (vertex2[1] > vertex3[1]) {
            swap(vertex2, vertex3);
        }

        double dx13 = 0, dx12 = 0, dx23 = 0;
        if (vertex3[1] != vertex1[1]) {
            dx13 = vertex3[0] - vertex1[0];
            dx13 /= vertex3[1] - vertex1[1];
        } else {
            dx13 = 0;
        }

        if (vertex2[1] != vertex1[1]) {
            dx12 = vertex2[0] - vertex1[0];
            dx12 /= (vertex2[1] - vertex1[1]);
        } else {
            dx12 = 0;
        }

        if (vertex3[1] != vertex2[1]) {
            dx23 = (vertex3[0] - vertex2[0]);
            dx23 /= (vertex3[1] - vertex2[1]);
        } else {
            dx23 = 0;
        }

        double wx1 = vertex1[0];
        double wx2 = wx1;
        double _dx13 = dx13;

        if (dx13 > dx12) {
            double tmp = dx13;
            dx13 = dx12;
            dx12 = tmp;
        }

        for (int i = vertex1[1]; i < vertex2[1]; i++) {
            for (int j = (int) Math.ceil(wx1) - 1; j <= Math.ceil(wx2) + 1; j++) {
                w = findBarycentricCoordinates(j, i,
                        vertex1i[0], vertex1i[1],
                        vertex2i[0], vertex2i[1],
                        vertex3i[0], vertex3i[1]);
                double currZ = evaluateZ(w, vertex1i[2], vertex2i[2], vertex3i[2]);
                if (j >= 0 && j < WIDTH && i >= 0 && i < HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += dx13;
            wx2 += dx12;
        }

        if (vertex1[1] == vertex2[1] && vertex1[0] > vertex2[0]) {
            wx1 = vertex2[0];
            wx2 = vertex1[0];
        } else if (vertex1[0] < vertex2[0] && vertex1[1] == vertex2[1]) {
            wx2 = vertex2[0];
            wx1 = vertex1[0];
        }

        if (_dx13 < dx23) {
            double tmp = _dx13;
            _dx13 = dx23;
            dx23 = tmp;
        }

        for (int i = vertex2[1]; i <= vertex3[1]; i++) {
            for (int j = (int) Math.ceil(wx1) - 1; j <= Math.ceil(wx2) + 1; j++) {
                w = findBarycentricCoordinates(j, i,
                        vertex1i[0], vertex1i[1],
                        vertex2i[0], vertex2i[1],
                        vertex3i[0], vertex3i[1]);
                double currZ = evaluateZ(w, vertex1i[2], vertex2i[2], vertex3i[2]);
                if (j >= 0 && j < WIDTH && i >= 0 && i < HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += _dx13;
            wx2 += dx23;
        }
    }


    public static void drawFilledTriangle3(double[] worldVertex1i, double[] worldVertex2i, double[] worldVertex3i,
                                           double[] vertex1i, double[] vertex2i, double[] vertex3i,
                                           double[] normalVertex1i, double[] normalVertex2i, double[] normalVertex3i,
                                           PixelWriter px, double[][] zBuffer) {
        int[] vertex1 = Arrays.stream(vertex1i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        int[] vertex2 = Arrays.stream(vertex2i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        int[] vertex3 = Arrays.stream(vertex3i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        double[] w;

        if (vertex2[1] < vertex1[1]) {
            swap(vertex1, vertex2);
        }
        if (vertex3[1] < vertex1[1]) {
            swap(vertex1, vertex3);
        }
        if (vertex2[1] > vertex3[1]) {
            swap(vertex2, vertex3);
        }

        double dx13 = 0, dx12 = 0, dx23 = 0;
        if (vertex3[1] != vertex1[1]) {
            dx13 = vertex3[0] - vertex1[0];
            dx13 /= vertex3[1] - vertex1[1];
        } else {
            dx13 = 0;
        }

        if (vertex2[1] != vertex1[1]) {
            dx12 = vertex2[0] - vertex1[0];
            dx12 /= (vertex2[1] - vertex1[1]);
        } else {
            dx12 = 0;
        }

        if (vertex3[1] != vertex2[1]) {
            dx23 = (vertex3[0] - vertex2[0]);
            dx23 /= (vertex3[1] - vertex2[1]);
        } else {
            dx23 = 0;
        }

        double wx1 = vertex1[0];
        double wx2 = wx1;
        double _dx13 = dx13;

        if (dx13 > dx12) {
            double tmp = dx13;
            dx13 = dx12;
            dx12 = tmp;
        }

        for (int i = vertex1[1]; i < vertex2[1]; i++) {
            for (int j = (int) Math.ceil(wx1) - 1; j <= Math.ceil(wx2) + 1; j++) {
                w = findBarycentricCoordinates(j, i,
                        vertex1i[0], vertex1i[1],
                        vertex2i[0], vertex2i[1],
                        vertex3i[0], vertex3i[1]);
                double currZ = evaluateZ(w, vertex1i[2], vertex2i[2], vertex3i[2]);
                if (j >= 0 && j < WIDTH && i >= 0 && i < HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        Color color = lightingPhong.getColor(
                                evaluateNormalVertex(w, worldVertex1i, worldVertex2i, worldVertex3i),
                                evaluateNormalVertex(w, normalVertex1i, normalVertex2i, normalVertex3i));
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += dx13;
            wx2 += dx12;
        }

        if (vertex1[1] == vertex2[1] && vertex1[0] > vertex2[0]) {
            wx1 = vertex2[0];
            wx2 = vertex1[0];
        } else if (vertex1[0] < vertex2[0] && vertex1[1] == vertex2[1]) {
            wx2 = vertex2[0];
            wx1 = vertex1[0];
        }

        if (_dx13 < dx23) {
            double tmp = _dx13;
            _dx13 = dx23;
            dx23 = tmp;
        }

        for (int i = vertex2[1]; i <= vertex3[1]; i++) {
            for (int j = (int) Math.ceil(wx1) - 1; j <= Math.ceil(wx2) + 1; j++) {
                w = findBarycentricCoordinates(j, i,
                        vertex1i[0], vertex1i[1],
                        vertex2i[0], vertex2i[1],
                        vertex3i[0], vertex3i[1]);
                double currZ = evaluateZ(w, vertex1i[2], vertex2i[2], vertex3i[2]);
                if (j >= 0 && j < WIDTH && i >= 0 && i < HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        Color color = lightingPhong.getColor(
                                evaluateNormalVertex(w, worldVertex1i, worldVertex2i, worldVertex3i),
                                evaluateNormalVertex(w, normalVertex1i, normalVertex2i, normalVertex3i));
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += _dx13;
            wx2 += dx23;
        }
    }

    public static void drawFilledTriangle4(double[] worldVertex1i, double[] worldVertex2i, double[] worldVertex3i,
                                           double[] vertex1i, double[] vertex2i, double[] vertex3i,
                                           double[] texture1i, double[] texture2i, double[] texture3i,
                                           ComponentStorage componentStorage, PixelWriter px, double[][] zBuffer, PreparationStorage preparationStorage) {
        int[] vertex1 = Arrays.stream(vertex1i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        int[] vertex2 = Arrays.stream(vertex2i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        int[] vertex3 = Arrays.stream(vertex3i).mapToInt(x -> (int) Math.ceil(x)).toArray();
        double[] w;

        if (vertex2[1] < vertex1[1]) {
            swap(vertex1, vertex2);
        }
        if (vertex3[1] < vertex1[1]) {
            swap(vertex1, vertex3);
        }
        if (vertex2[1] > vertex3[1]) {
            swap(vertex2, vertex3);
        }

        double dx13 = 0, dx12 = 0, dx23 = 0;
        if (vertex3[1] != vertex1[1]) {
            dx13 = vertex3[0] - vertex1[0];
            dx13 /= vertex3[1] - vertex1[1];
        } else {
            dx13 = 0;
        }

        if (vertex2[1] != vertex1[1]) {
            dx12 = vertex2[0] - vertex1[0];
            dx12 /= (vertex2[1] - vertex1[1]);
        } else {
            dx12 = 0;
        }

        if (vertex3[1] != vertex2[1]) {
            dx23 = (vertex3[0] - vertex2[0]);
            dx23 /= (vertex3[1] - vertex2[1]);
        } else {
            dx23 = 0;
        }

        double wx1 = vertex1[0];
        double wx2 = wx1;
        double _dx13 = dx13;

        if (dx13 > dx12) {
            double tmp = dx13;
            dx13 = dx12;
            dx12 = tmp;
        }

        for (int i = vertex1[1]; i < vertex2[1]; i++) {
            for (int j = (int) Math.ceil(wx1) - 1; j <= Math.ceil(wx2) + 1; j++) {
                w = findBarycentricCoordinates(j, i,
                        vertex1i[0], vertex1i[1],
                        vertex2i[0], vertex2i[1],
                        vertex3i[0], vertex3i[1]);
                double currZ = evaluateZ(w, vertex1i[2], vertex2i[2], vertex3i[2]);
                if (j >= 0 && j < WIDTH && i >= 0 && i < HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        Color color = lightingPhong.getColor(
                                evaluateNewVertex(w, worldVertex1i, worldVertex2i, worldVertex3i),
                                evaluateNewTexture(w, worldVertex1i[2], worldVertex2i[2], worldVertex3i[2],
                                        texture1i, texture2i, texture3i),
                                componentStorage, preparationStorage);
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += dx13;
            wx2 += dx12;
        }

        if (vertex1[1] == vertex2[1] && vertex1[0] > vertex2[0]) {
            wx1 = vertex2[0];
            wx2 = vertex1[0];
        } else if (vertex1[0] < vertex2[0] && vertex1[1] == vertex2[1]) {
            wx2 = vertex2[0];
            wx1 = vertex1[0];
        }

        if (_dx13 < dx23) {
            double tmp = _dx13;
            _dx13 = dx23;
            dx23 = tmp;
        }

        for (int i = vertex2[1]; i <= vertex3[1]; i++) {
            for (int j = (int) Math.ceil(wx1) - 1; j <= Math.ceil(wx2) + 1; j++) {
                w = findBarycentricCoordinates(j, i,
                        vertex1i[0], vertex1i[1],
                        vertex2i[0], vertex2i[1],
                        vertex3i[0], vertex3i[1]);
                double currZ = evaluateZ(w, vertex1i[2], vertex2i[2], vertex3i[2]);
                if (j >= 0 && j < WIDTH && i >= 0 && i < HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        Color color = lightingPhong.getColor(
                                evaluateNewVertex(w, worldVertex1i, worldVertex2i, worldVertex3i),
                                evaluateNewTexture(w, worldVertex1i[2], worldVertex2i[2], worldVertex3i[2],
                                        texture1i, texture2i, texture3i),
                                componentStorage, preparationStorage);
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += _dx13;
            wx2 += dx23;
        }
    }

    private static void swap(int[] first, int[] second) {
        int[] tmp = new int[]{second[0], second[1], second[2]};
        second[0] = first[0];
        second[1] = first[1];
        second[2] = first[2];
        first[0] = tmp[0];
        first[1] = tmp[1];
        first[2] = tmp[2];
    }

    private static double edgeFunction(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1);
    }

    private static double[] findBarycentricCoordinates(int x, int y,
                                                       double x1, double y1,
                                                       double x2, double y2,
                                                       double x3, double y3) {
        double[] res = new double[]{-1.0, -1.0, -1.0};
        x1 = Math.ceil(x1);
        y1 = Math.ceil(y1);
        x2 = Math.ceil(x2);
        y2 = Math.ceil(y2);
        x3 = Math.ceil(x3);
        y3 = Math.ceil(y3);
        double area = edgeFunction(x1, y1, x2, y2, x3, y3);
        res[0] = edgeFunction(x2, y2, x3, y3, x, y);
        res[1] = edgeFunction(x3, y3, x1, y1, x, y);
        res[2] = edgeFunction(x1, y1, x2, y2, x, y);
        res[0] /= area;
        res[1] /= area;
        res[2] /= area;
        return res;
    }

    private static double evaluateZ(double[] w, double z1, double z2, double z3) {
        if (w[0] >= 0.0 && w[1] >= 0.0 && w[2] >= 0.0) {
            return (z1 * w[0] + z2 * w[1] + z3 * w[2]);
        } else {
            return 100;
        }
    }

    private static float[] evaluateNormalVertex(double[] w, double[] normal1, double[] normal2, double[] normal3) {
        float[] res = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        res[0] = (float) (normal1[0] * w[0] + normal2[0] * w[1] + normal3[0] * w[2]);
        res[1] = (float) (normal1[1] * w[0] + normal2[1] * w[1] + normal3[1] * w[2]);
        res[2] = (float) (normal1[2] * w[0] + normal2[2] * w[1] + normal3[2] * w[2]);
        normalizeVector(res);
        return res;
    }

    private static float[] evaluateNewVertex(double[] w, double[] vertex1, double[] vertex2, double[] vertex3) {
        float[] res = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        res[0] = (float) (vertex1[0] * w[0] + vertex2[0] * w[1] + vertex3[0] * w[2]);
        res[1] = (float) (vertex1[1] * w[0] + vertex2[1] * w[1] + vertex3[1] * w[2]);
        res[2] = (float) (vertex1[2] * w[0] + vertex2[2] * w[1] + vertex3[2] * w[2]);
        return res;
    }

    private static double[] evaluateNewTexture(double[] w, double z1, double z2, double z3,
                                               double[] texture1, double[] texture2, double[] texture3) {
        double[] res = new double[]{0.0, 0.0, 0.0, 0.0};
        double z = 1 / (w[0] / z1 + w[1] / z2 + w[2] / z3);
        res[0] = z * (texture1[0] * w[0] / z1 + texture2[0] * w[1] / z2 + texture3[0] * w[2] / z3);
        res[1] = z * (texture1[1] * w[0] / z1 + texture2[1] * w[1] / z2 + texture3[1] * w[2] / z3);
        return res;
    }
}
