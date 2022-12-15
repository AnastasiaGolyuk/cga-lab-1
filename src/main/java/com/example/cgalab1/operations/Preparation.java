package com.example.cgalab1.operations;

import org.ejml.simple.SimpleMatrix;
import mikera.vectorz.Vector3;

public class Preparation {

    public SimpleMatrix prepareModelMatrix() {
        return new SimpleMatrix(new float[][]{
                new float[]{1, 0, 0, 0},
                new float[]{0, 1, 0, 0},
                new float[]{0, 0, 1, 0},
                new float[]{0, 0, 0, 1}
        });
    }

    public SimpleMatrix prepareViewMatrix(double[] eye, double[] target, double[] up) {
        Vector3 zAxis = new Vector3(eye);
        Vector3 targetVector = new Vector3(target);
        zAxis.sub(targetVector);
        zAxis.normalise();
        Vector3 yAxis = new Vector3(up);
        yAxis.normalise();
        Vector3 xAxis = new Vector3(yAxis);
        xAxis.crossProduct(zAxis);
        xAxis.normalise();
        Vector3 eyeVector = new Vector3(eye);
        double x = -xAxis.dotProduct(eyeVector);
        double y = -yAxis.dotProduct(eyeVector);
        double z = -zAxis.dotProduct(eyeVector);

        return new SimpleMatrix(new double[][]{
                new double[]{xAxis.x, xAxis.y, xAxis.z, x},
                new double[]{yAxis.x, yAxis.y, yAxis.z, y},
                new double[]{zAxis.x, zAxis.y, zAxis.z, z},
                new double[]{0, 0, 0, 1}
        });
    }

    public SimpleMatrix prepareViewPortMatrix(double width, double height, double xMin, double yMin) {
        return new SimpleMatrix(new double[][]{
                new double[]{width / 2, 0, 0, xMin + width / 2},
                new double[]{0, -height / 2, 0, yMin + height / 2},
                new double[]{0, 0, 1, 0},
                new double[]{0, 0, 0, 1}
        });
    }

    public SimpleMatrix preparePerspectiveMatrix(double aspect, double fov, double zNear, double zFar) {
        return new SimpleMatrix(new double[][]{
                new double[]{1 / (aspect * Math.tan(fov / 2)), 0, 0, 0},
                new double[]{0, 1 / Math.tan(fov / 2), 0, 0},
                new double[]{0, 0, zFar / (zNear - zFar), zFar * zNear / (zNear - zFar)},
                new double[]{0, 0, -1, 0}
        });
    }

    public SimpleMatrix rotateXMatrix(double sin, double cos){
        return new SimpleMatrix(new double[][]{
                new double[] {1, 0, 0, 0},
                new double[] {0, cos, -sin, 0},
                new double[] {0, sin, cos, 0},
                new double[] {0, 0, 0, 1},
        });
    }

    public SimpleMatrix rotateYMatrix(double sin, double cos){
        return new SimpleMatrix(new double[][]{
                new double[] {cos, 0, sin, 0},
                new double[] {0, 1, 0, 0},
                new double[] {-sin, 0, cos, 0},
                new double[] {0, 0, 0, 1},
        });
    }

    public SimpleMatrix rotateZMatrix(double sin, double cos){
        return new SimpleMatrix(new double[][]{
                new double[] {cos, -sin, 0, 0},
                new double[] {sin, cos, 0, 0},
                new double[] {0, 0, 1, 0},
                new double[] {0, 0, 0, 1},
        });
    }

    public float[][] matrixToArray(SimpleMatrix matrix) {
        float[][] array = new float[matrix.numRows()][matrix.numCols()];
        for (int r = 0; r < matrix.numRows(); r++) {
            for (int c = 0; c < matrix.numCols(); c++) {
                array[r][c] = (float) matrix.get(r, c);
            }
        }
        return array;
    }
}
