package com.labs.cg4thlabwork;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Render3D {
    private GraphicsContext gc;
    private Camera3D camera;

    public Render3D(GraphicsContext gc, Camera3D camera) {
        this.gc = gc;
        this.camera = camera;
    }

    public void render(Model3D model) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        Vector3D[] vertices = model.getVertices();
        int[][] edges = model.getEdges();

        for (int[] edge : edges) {
            Vector3D v1 = camera.project(vertices[edge[0]]);
            Vector3D v2 = camera.project(vertices[edge[1]]);

            // Преобразование координат в экранные
            double screenX1 = 400 + v1.x; // Центрируем по X
            double screenY1 = 300 - v1.y; // Инвертируем Y
            double screenX2 = 400 + v2.x;
            double screenY2 = 300 - v2.y;

            gc.strokeLine(screenX1, screenY1, screenX2, screenY2);
        }
    }
}