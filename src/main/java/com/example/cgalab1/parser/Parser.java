package com.example.cgalab1.parser;

import com.example.cgalab1.model.ComponentStorage;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final String VERTEX_START = "v";
    private static final String VERTEX_TEXTURE_START = "vt";
    private static final String VERTEX_NORMAL_START = "vn";
    private static final String FACE_START = "f";
    private static final String COMMENT_START = "#";

    private final ComponentStorage componentStorage;

    public Parser(ComponentStorage componentStorage) {
        this.componentStorage = componentStorage;
    }

    public void parse(BufferedReader reader, String normalPath, String diffusePath, String specularPath) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.startsWith(COMMENT_START) && !line.isBlank()) {
                if (line.startsWith(VERTEX_TEXTURE_START)) {
                    parseVertexTexture(line.trim().substring(VERTEX_TEXTURE_START.length() + 1));
                } else if (line.startsWith(VERTEX_NORMAL_START)) {
                    parseVertexNormal(line.trim().substring(VERTEX_NORMAL_START.length() + 1));
                } else if (line.startsWith(VERTEX_START)) {
                    parseVertex(line.trim().substring(VERTEX_START.length() + 1));
                } else if (line.startsWith(FACE_START)) {
                    parseFace(line.trim().substring(FACE_START.length() + 1));
                }
            }
        }
        parseImagesTexture(normalPath, diffusePath, specularPath);
    }

    private void parseImagesTexture(String normalPath, String diffusePath, String specularPath) throws IOException {
        componentStorage.setNormalImage(ImageIO.read(new File(normalPath)));
        componentStorage.setDiffuseImage(ImageIO.read(new File(diffusePath)));
        componentStorage.setSpecularImage(ImageIO.read(new File(specularPath)));
    }

    private void parseVertex(String line) {
        componentStorage.addVertex(getFloatCoords(line));
    }

    private void parseVertexTexture(String line) {
        componentStorage.addVertexTexture(getFloatCoords(line));
    }

    private void parseVertexNormal(String line) {
        componentStorage.addVertexNormal(getFloatCoords(line));
    }

    private void parseFace(String line) {
        String[] splittedLine = line.split("\\s+");
        int[][] indexes = new int[splittedLine.length][3];
        for (int i = 0; i < indexes.length; i++) {
            String[] stringIndexes = splittedLine[i].trim().split("/");
            if (stringIndexes.length > 1 && "".equals(stringIndexes[1])) {
                stringIndexes[1] = "0";
            }
            for (int j = 0; j < stringIndexes.length; j++) {
                indexes[i][j] = Integer.parseInt(stringIndexes[j]);
            }
        }
        componentStorage.addFace(indexes);
    }

    private float[] getFloatCoords(String line) {
        String[] stringCoords = line.trim().split("\\s+");
        float[] coords = new float[stringCoords.length];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = Float.parseFloat(stringCoords[i]);
        }
        return coords;
    }
}
