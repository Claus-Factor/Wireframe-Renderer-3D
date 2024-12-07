package com.labs.cg4thlabwork;

public class Matrix {
    public final double[][] data; // ПОТОМ ЗАМЕНИ НА ПРИВАТНОЕ ПОЛЕ!!!

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
    }

    public static Matrix identity(int size) {
        Matrix matrix = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            matrix.data[i][i] = 1.0;
        }
        return matrix;
    }

    public Matrix multiply(Matrix other) {
        int rows = this.data.length;
        int cols = other.data[0].length;
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < this.data[0].length; k++) {
                    result.data[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }
        return result;
    }

    public double[] transform(double[] vector) {
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += data[i][j] * vector[j];
            }
        }
        return result;
    }
}