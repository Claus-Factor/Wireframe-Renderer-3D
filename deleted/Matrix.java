package com.labs.cg4thlabwork;

public class Matrix {
    public double[][] data;
    private int rows, cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public static Matrix identity(int size) {
        Matrix matrix = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            matrix.data[i][i] = 1.0;
        }
        return matrix;
    }

    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows)
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    result.data[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }
        return result;
    }

    public Vector3D transform(Vector3D vector) {
        double[] result = new double[4];
        double[] vecData = {vector.x, vector.y, vector.z, 1};
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result[i] += this.data[i][j] * vecData[j];
            }
        }
        return new Vector3D(result[0], result[1], result[2]);
    }
}
