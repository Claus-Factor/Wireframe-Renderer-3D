package com.labs.cg4thlabwork.render;

import com.labs.cg4thlabwork.core.AffineTransform;
import com.labs.cg4thlabwork.core.Matrix;
import com.labs.cg4thlabwork.core.Vector3D;

import java.util.Arrays;

public class Model3D {
    private Vector3D[] vertices;
    private int[][] edges;

    public Model3D(Vector3D[] vertices, int[][] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public void applyTransform(AffineTransform transform) {
        Matrix transformationMatrix = transform.getTransform();
        for (int i = 0; i < vertices.length; i++) {
            double[] v = {vertices[i].x, vertices[i].y, vertices[i].z, 1};
            double[] transformed = transformationMatrix.transform(v);
            vertices[i] = new Vector3D(transformed[0], transformed[1], transformed[2]);

            System.out.println("v" + i + ". " + (vertices[i]));
        }
        System.out.println();
    }

    public Vector3D[] getVertices() {
        return vertices;
    }

    public int[][] getEdges() {
        return edges;
    }
}