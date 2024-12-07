package com.labs.cg4thlabwork;

public class ModelLoader {
    public static Model3D createCube(double size) {
        double half = size / 2;

        // Вершины куба
        Vector3D[] vertices = {
                new Vector3D(-half, -half, -half),
                new Vector3D(half, -half, -half),
                new Vector3D(half, half, -half),
                new Vector3D(-half, half, -half),
                new Vector3D(-half, -half, half),
                new Vector3D(half, -half, half),
                new Vector3D(half, half, half),
                new Vector3D(-half, half, half)
        };

        // Ребра куба (индексы вершин)
        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0}, // Задняя грань
                {4, 5}, {5, 6}, {6, 7}, {7, 4}, // Передняя грань
                {0, 4}, {1, 5}, {2, 6}, {3, 7}  // Соединения между передней и задней гранью
        };

        return new Model3D(vertices, edges);
    }
}