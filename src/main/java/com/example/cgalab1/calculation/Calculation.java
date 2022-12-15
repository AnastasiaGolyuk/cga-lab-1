package com.example.cgalab1.calculation;

public class Calculation {

    public static float dotProduct(float[] vector1, float[] vector2) {
        float res = 0.0f;
        for (int i = 0; i < vector1.length - 1; i++) {
            res += vector1[i] * vector2[i];
        }
        return res;
    }

    public static float[] crossProduct(float[] vector1, float[] vector2) {
        float[] res = new float[4];
        res[0] = vector1[1] * vector2[2] - vector1[2] * vector2[1];
        res[1] = -(vector1[0] * vector2[2] - vector1[2] * vector2[0]);
        res[2] = vector1[0] * vector2[1] - vector1[1] * vector2[0];
        res[3] = 1.0f;
        return res;
    }

    public static float[] matrixVectorProduct(float[][] matrix, float[] vector) {
        float[] res = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                res[i] += vector[i] * matrix[i][j];
            }
        }
        return res;
    }

    public static float[][] matrixesProduct(float[][] matrix1, float[][] matrix2) {
        float[][] res = new float[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2.length; j++) {
                res[i][j] = matrix1[i][0] * matrix2[0][j]
                        + matrix1[i][1] * matrix2[1][j]
                        + matrix1[i][2] * matrix2[2][j]
                        + matrix1[i][3] * matrix2[3][j];
            }
        }
        return res;
    }

    public static float[] multiplyVectorByScalar(float[] vector, float scalar) {
        float[] res = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        for (int i = 0; i < vector.length - 1; i++) {
            res[i] = scalar * vector[i];
        }
        return res;
    }

    public static float[] normalizeVector(float[] vector) {
        float normal = 0.0f;
        for (int i = 0; i < vector.length - 1; i++) {
            normal += Math.pow(vector[i], 2.0f);
        }
        normal = (float) Math.sqrt(normal);
        for (int i = 0; i < vector.length - 1; i++) {
            vector[i] /= normal;
        }
        return vector;
    }

    public static float[] subtractVector(float[] minuend, float[] subtrahend) {
        float[] res = new float[4];
        for (int i = 0; i < minuend.length - 1; i++) {
            res[i] = minuend[i] - subtrahend[i];
        }
        return res;
    }

    public static float[] addVector(float[] vector1, float[] vector2) {
        float[] res = new float[4];
        for (int i = 0; i < vector1.length - 1; i++) {
            res[i] = vector1[i] + vector2[i];
        }
        return res;
    }

    public static float findVertexLength(float[] vector) {
        float res = 0.0f;
        int len = 3;
        for (int i = 0; i < len; i++) {
            res += Math.pow(vector[i], 2);
        }
        res = (float) Math.sqrt(res);
        return res;
    }

    public static void normalizeNormal(float[] vector) {
        final double len = findVertexLength(vector);
        if (len == 0f || len == 1f) return;
        for (int i = 0; i < 3; i++) {
            vector[i] = (float) (vector[0] / Math.sqrt(len));
        }
    }

    public static float findCosBetweenVectors(float[] vector1, float[] vector2) {
        float res;
        float numerator = dotProduct(vector1, vector2);
        float denumerator = findVertexLength(vector1) * findVertexLength(vector2);
        res = numerator / denumerator;
        return res;
    }
}
