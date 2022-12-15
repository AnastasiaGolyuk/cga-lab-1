package com.example.cgalab1;

import com.example.cgalab1.model.ComponentStorage;
import com.example.cgalab1.model.PreparationStorage;
import com.example.cgalab1.operations.Translation;
import com.example.cgalab1.parser.Parser;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ejml.simple.SimpleMatrix;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static com.example.cgalab1.Draw.draw2;
import static com.example.cgalab1.model.ConstStorage.*;


public class Application extends javafx.application.Application {

    Translation translation = new Translation();
    ComponentStorage componentStorage = ComponentStorage.getInstance();
    PreparationStorage preparationStorage;

    double oldX;
    double oldY;

    Scene scene;
    public static GraphicsContext gc;

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parser parser = new Parser(componentStorage);
        Path path = Paths.get(Objects.requireNonNull(getClass().getResource(modelName)).toURI());
        BufferedReader reader = Files.newBufferedReader(path);

        parser.parse(reader, String.valueOf(Paths.get(Objects.requireNonNull(getClass().getResource(normalTextureName)).toURI())),
                String.valueOf(Paths.get(Objects.requireNonNull(getClass().getResource(diffuseTextureName)).toURI())),
                String.valueOf(Paths.get(Objects.requireNonNull(getClass().getResource(specularTextureName)).toURI())));

        reader.close();

        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        root.getChildren().add(canvas);
        scene = new Scene(root, WIDTH, HEIGHT);
        preparationStorage = new PreparationStorage(MODEL_MATRIX, VIEW_MATRIX, PROJECTION_MATRIX, VIEWPORT_MATRIX);

        translation.translateVertexes(componentStorage.getVertexes(), preparationStorage);
        translation.translateVertexesWorld(componentStorage.getVertexesWorld(), preparationStorage);

        draw2(componentStorage.getFaces(), gc.getPixelWriter(), preparationStorage);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                MODEL_MATRIX.set(0, 3, MODEL_MATRIX.get(0, 3) + MOVE);
                clearAndDraw();
            } else if (e.getCode() == KeyCode.LEFT) {
                MODEL_MATRIX.set(0, 3, MODEL_MATRIX.get(0, 3) - MOVE);
                clearAndDraw();
            } else if (e.getCode() == KeyCode.UP) {
                MODEL_MATRIX.set(1, 3, MODEL_MATRIX.get(1, 3) + MOVE);
                clearAndDraw();
            } else if (e.getCode() == KeyCode.DOWN) {
                MODEL_MATRIX.set(1, 3, MODEL_MATRIX.get(1, 3) - MOVE);
                clearAndDraw();
            } else if (e.getCode() == KeyCode.Z) {
                MODEL_MATRIX.set(2, 3, MODEL_MATRIX.get(2, 3) + MOVE);
                clearAndDraw();
            } else if (e.getCode() == KeyCode.A) {
                MODEL_MATRIX.set(2, 3, MODEL_MATRIX.get(2, 3) - MOVE);
                clearAndDraw();
            } else if (e.getCode() == KeyCode.Q) {
                xRotate(ROTATE_ANGLE);
            } else if (e.getCode() == KeyCode.W) {
                xRotate(-ROTATE_ANGLE);
            } else if (e.getCode() == KeyCode.E) {
                yRotate(ROTATE_ANGLE);
            } else if (e.getCode() == KeyCode.R) {
                yRotate(-ROTATE_ANGLE);
            } else if (e.getCode() == KeyCode.T) {
                zRotate(ROTATE_ANGLE);
            } else if (e.getCode() == KeyCode.Y) {
                zRotate(-ROTATE_ANGLE);
            }
        });
        scene.setOnScroll(scrollEvent -> {
            SimpleMatrix translationMatrix;
            if (scrollEvent.getDeltaY() < 0) {
                translationMatrix = new SimpleMatrix(new double[][]{
                        new double[]{1 - SCALE, 0, 0, 0},
                        new double[]{0, 1 - SCALE, 0, 0},
                        new double[]{0, 0, 1 - SCALE, 0},
                        new double[]{0, 0, 0, 1},
                });
            } else {
                translationMatrix = new SimpleMatrix(new double[][]{
                        new double[]{1 + SCALE, 0, 0, 0},
                        new double[]{0, 1 + SCALE, 0, 0},
                        new double[]{0, 0, 1 + SCALE, 0},
                        new double[]{0, 0, 0, 1},
                });
            }
            MODEL_MATRIX.set(translationMatrix.mult(MODEL_MATRIX));
            clearAndDraw();
        });
        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                oldX = e.getSceneX();
                oldY = e.getSceneY();
                scene.setOnMouseDragged(e1 -> {
                    double x = e1.getX();
                    if (Math.abs(oldX - x) > WIDTH / 96) {
                        if (oldX - x > 0) {
                            yRotate(-ROTATE_ANGLE);
                        } else {
                            yRotate(ROTATE_ANGLE);
                        }
                        oldX = x;
                    }

                    double y = e1.getY();
                    if (Math.abs(oldY - y) > HEIGHT / 96) {
                        if (oldY - y > 0) {
                            xRotate(-ROTATE_ANGLE);
                        } else {
                            xRotate(ROTATE_ANGLE);
                        }
                        oldY = y;
                    }
                });
            } else if (e.getButton() == MouseButton.PRIMARY) {
                oldX = e.getSceneX();
                oldY = e.getSceneY();
                scene.setOnMouseDragged(e1 -> {
                    double x = e1.getX();
                    if (Math.abs(oldX - x) > 5) {
                        if (oldX - x > 0) {
                            MODEL_MATRIX.set(0, 3, MODEL_MATRIX.get(0, 3) - MOVE);
                            clearAndDraw();
                        } else {
                            MODEL_MATRIX.set(0, 3, MODEL_MATRIX.get(0, 3) + MOVE);
                            clearAndDraw();
                        }
                        oldX = x;
                    }

                    double y = e1.getY();
                    if (Math.abs(oldY - y) > 5) {
                        if (oldY - y > 0) {
                            MODEL_MATRIX.set(1, 3, MODEL_MATRIX.get(1, 3) + MOVE);
                            clearAndDraw();
                        } else {
                            MODEL_MATRIX.set(1, 3, MODEL_MATRIX.get(1, 3) - MOVE);
                            clearAndDraw();
                        }
                        oldY = y;
                    }
                });
            }
        });

        stage.setScene(scene);
        stage.show();
    }


    private void clearAndDraw() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        translation.translateVertexes(componentStorage.getVertexes(), preparationStorage);
        draw2(componentStorage.getFaces(), gc.getPixelWriter(), preparationStorage);
    }

    private void xRotate(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        SimpleMatrix translationMatrix = preparation.rotateXMatrix(sin, cos);
        MODEL_MATRIX.set(translationMatrix.mult(MODEL_MATRIX));
        preparationStorage.setTranslationNormalsMatrix(preparationStorage.getViewMatrix().mult(preparationStorage.getModelMatrix()));
        translation.translateVertexesWorld(componentStorage.getVertexesWorld(), preparationStorage);
        translation.translateNormals(componentStorage.getVertexNormals(), translationMatrix);
        clearAndDraw();
    }

    private void yRotate(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        SimpleMatrix translationMatrix = preparation.rotateYMatrix(sin, cos);
        MODEL_MATRIX.set(translationMatrix.mult(MODEL_MATRIX));
        preparationStorage.setTranslationNormalsMatrix(preparationStorage.getViewMatrix().mult(preparationStorage.getModelMatrix()));
        translation.translateVertexesWorld(componentStorage.getVertexesWorld(), preparationStorage);
        translation.translateNormals(componentStorage.getVertexNormals(), translationMatrix);
        clearAndDraw();
    }


    private void zRotate(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        SimpleMatrix translationMatrix = preparation.rotateZMatrix(sin, cos);
        MODEL_MATRIX.set(translationMatrix.mult(MODEL_MATRIX));
        preparationStorage.setTranslationNormalsMatrix(preparationStorage.getViewMatrix().mult(preparationStorage.getModelMatrix()));
        translation.translateVertexesWorld(componentStorage.getVertexesWorld(), preparationStorage);
        translation.translateNormals(componentStorage.getVertexNormals(), translationMatrix);
        clearAndDraw();
    }
}