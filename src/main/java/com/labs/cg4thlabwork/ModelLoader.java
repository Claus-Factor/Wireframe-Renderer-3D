package com.labs.cg4thlabwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

    public static Model3D loadCubeFromFile(String fileName) throws IOException {
        List<Vector3D> vertices = new ArrayList<>();
        List<int[]> edges = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("v")) {
                    // Parse vertex
                    String[] parts = line.split("\\s+");
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    vertices.add(new Vector3D(x, y, z));
                } else if (line.startsWith("e")) {
                    // Parse edge
                    String[] parts = line.split("\\s+");
                    int start = Integer.parseInt(parts[1]);
                    int end = Integer.parseInt(parts[2]);
                    edges.add(new int[] { start, end });
                }
            }
        }

        // Convert the lists to arrays
        Vector3D[] vertexArray = vertices.toArray(new Vector3D[0]);
        int[][] edgeArray = edges.toArray(new int[0][]);

        return new Model3D(vertexArray, edgeArray);
    }

}