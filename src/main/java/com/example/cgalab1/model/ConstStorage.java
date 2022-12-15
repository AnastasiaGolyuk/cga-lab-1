package com.example.cgalab1.model;

import com.example.cgalab1.operations.Preparation;
import org.ejml.simple.SimpleMatrix;

public class ConstStorage {
    public static final double[] EYE = new double[]{0, 0, 2};
    public static final double[] TARGET = new double[]{0, 0, -1};
    public static final double[] UP = new double[]{0, 1, 0};
    public static final double WIDTH = 1300;
    public static final double HEIGHT = 800;
    public static final double Z_NEAR = 1;
    public static final double Z_FAR = 100;
    public static final double X_MIN = 0;
    public static final double Y_MIN = 0;
    public static final double MOVE = 4;
    public static final double SCALE = 0.01;
    public static final double ROTATE_ANGLE = 0.1;
    public static final double FOV = Math.PI / 2;

    public static final Preparation preparation = new Preparation();

//    public static final String modelName = "head.obj";
//    public static final String normalTextureName = "head_normal.png";
//    public static final String diffuseTextureName = "head_diffuse.png";
//    public static final String specularTextureName = "head_specular.png";
//
    public static final String modelName = "knight.obj";
    public static final String normalTextureName = "knight_normal.png";
    public static final String diffuseTextureName = "knight_diffuse.png";
    public static final String specularTextureName = "knight_specular.png";

//    public static final String modelName = "pikachu.obj";
//    public static final String normalTextureName = "pikachu_normal.png";
//    public static final String diffuseTextureName = "pikachu_diffuse.png";
//    public static final String specularTextureName = "pikachu_specular.png";

//    public static final String modelName = "Chess.obj";
//    public static final String diffuseTextureName = "chess.png";

    public static final SimpleMatrix MODEL_MATRIX = preparation.prepareModelMatrix();
    public static final SimpleMatrix VIEW_MATRIX = preparation.prepareViewMatrix(EYE, TARGET, UP);
    public static final SimpleMatrix PROJECTION_MATRIX = preparation.preparePerspectiveMatrix(WIDTH / HEIGHT, FOV, Z_NEAR, Z_FAR);
    public static final SimpleMatrix VIEWPORT_MATRIX = preparation.prepareViewPortMatrix(WIDTH, HEIGHT, X_MIN, Y_MIN);
}
