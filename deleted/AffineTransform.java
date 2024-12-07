package com.labs.cg4thlabwork;

public class AffineTransform {
    private Matrix transformMatrix;

    public AffineTransform() {
        this.transformMatrix = Matrix.identity(4);
    }

    public void rotate(double angleX, double angleY, double angleZ) {
        rotateX(angleX);
        rotateY(angleY);
        rotateZ(angleZ);
    }


    public void translate(double dx, double dy, double dz) {
        Matrix translation = Matrix.identity(4);
        translation.data[0][3] = dx;
        translation.data[1][3] = dy;
        translation.data[2][3] = dz;
        this.transformMatrix = this.transformMatrix.multiply(translation);
    }

    public void rotateX(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        Matrix rotation = Matrix.identity(4);
        rotation.data[1][1] = cos;
        rotation.data[1][2] = -sin;
        rotation.data[2][1] = sin;
        rotation.data[2][2] = cos;
        this.transformMatrix = this.transformMatrix.multiply(rotation);
    }

    public void rotateY(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        Matrix rotation = Matrix.identity(4);
        rotation.data[0][0] = cos;
        rotation.data[0][2] = sin;
        rotation.data[2][0] = -sin;
        rotation.data[2][2] = cos;
        this.transformMatrix = this.transformMatrix.multiply(rotation);
    }

    public void rotateZ(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        Matrix rotation = Matrix.identity(4);
        rotation.data[0][0] = cos;
        rotation.data[0][1] = -sin;
        rotation.data[1][0] = sin;
        rotation.data[1][1] = cos;
        this.transformMatrix = this.transformMatrix.multiply(rotation);
    }

    public Matrix getMatrix() {
        return this.transformMatrix;
    }
}
