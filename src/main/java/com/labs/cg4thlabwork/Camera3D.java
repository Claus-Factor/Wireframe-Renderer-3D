package com.labs.cg4thlabwork;

public class Camera3D {
    private Vector3D position;
    private double focalLength;

    public Camera3D(Vector3D position, double focalLength) {
        this.position = position;
        this.focalLength = focalLength;
    }

    public Vector3D project(Vector3D vertex) {
        double x = (vertex.x - position.x) * focalLength / (vertex.z - position.z);
        double y = (vertex.y - position.y) * focalLength / (vertex.z - position.z);
        return new Vector3D(x, y, 0);
    }
}