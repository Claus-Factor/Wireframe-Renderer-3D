package com.labs.cg4thlabwork.core;

public class AffineTransform {
    private Matrix transform;

    public AffineTransform() {
        transform = Matrix.identity(4);
    }

    public void translate(double x, double y, double z) {
        Matrix translation = Matrix.identity(4);
        translation.data[0][3] = x;
        translation.data[1][3] = y;
        translation.data[2][3] = z;
        transform = translation.multiply(transform);
    }

    public void scale(double sx, double sy, double sz) {
        Matrix scaling = Matrix.identity(4);
        scaling.data[0][0] = sx;
        scaling.data[1][1] = sy;
        scaling.data[2][2] = sz;
        transform = scaling.multiply(transform);
    }

    public void rotateX(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        Matrix rotation = Matrix.identity(4);
        rotation.data[1][1] = cos;
        rotation.data[1][2] = -sin;
        rotation.data[2][1] = sin;
        rotation.data[2][2] = cos;

        transform = rotation.multiply(transform);
    }

    public void rotateY(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        Matrix rotation = Matrix.identity(4);
        rotation.data[0][0] = cos;
        rotation.data[0][2] = sin;
        rotation.data[2][0] = -sin;
        rotation.data[2][2] = cos;

        transform = rotation.multiply(transform);
    }

    public void rotateZ(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        Matrix rotation = Matrix.identity(4);
        rotation.data[0][0] = cos;
        rotation.data[0][1] = sin;
        rotation.data[1][0] = -sin;
        rotation.data[1][1] = cos;

        transform = rotation.multiply(transform);
    }

    public void mapOxy() {
        Matrix scaling = Matrix.identity(4);
        scaling.data[2][2] = -1;
        transform = scaling.multiply(transform);
    }

    public void mapOyz() {
        Matrix scaling = Matrix.identity(4);
        scaling.data[0][0] = -1;
        transform = scaling.multiply(transform);
    }

    public void mapOxz() {
        Matrix scaling = Matrix.identity(4);
        scaling.data[1][1] = -1;
        transform = scaling.multiply(transform);
    }

    public Matrix getTransform() {
        return transform;
    }
}