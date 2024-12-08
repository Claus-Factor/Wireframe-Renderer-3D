package com.labs.cg4thlabwork.render;

import com.labs.cg4thlabwork.core.Vector3D;

public class Camera3D {
    private Vector3D position;

    public Camera3D(Vector3D position) {
        this.position = position;
    }

    public Vector3D project(Vector3D vertex) {
        double focalLength = Math.abs(position.z);

        double x = (vertex.x - position.x) * focalLength / Math.abs(position.z - vertex.z);
        double y = (vertex.y - position.y) * focalLength / Math.abs(position.z - vertex.z);
        return new Vector3D(x, y, 0);
    }
}