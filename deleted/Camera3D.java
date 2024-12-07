package com.labs.cg4thlabwork;

public class Camera3D {
    private Vector3D position;

    public Camera3D(Vector3D position) {
        this.position = position;
    }

    public Vector3D project(Vector3D point) {
        double scale = 500 / (point.z + 500); // Перспективная проекция
        return new Vector3D(point.x * scale + 400, point.y * scale + 300, 0);
    }
}
