package com.labs.cg4thlabwork;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Render3D {
    private Camera3D camera;

    public Render3D(Camera3D camera) {
        this.camera = camera;
    }

    public void render(GraphicsContext gc, Model3D model) {
        gc.setStroke(Color.BLACK);
        for (int[] edge : model.getEdges()) {
            Vector3D start = camera.project(model.getVertices().get(edge[0]));
            Vector3D end = camera.project(model.getVertices().get(edge[1]));
            gc.strokeLine(start.x, start.y, end.x, end.y);
        }
    }
}
