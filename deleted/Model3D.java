package com.labs.cg4thlabwork;

import java.util.ArrayList;
import java.util.List;

public class Model3D {
    private List<Vector3D> vertices;
    private List<int[]> edges;
    private AffineTransform transform;

    public Model3D() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.transform = new AffineTransform();
    }

    public void addVertex(Vector3D vertex) {
        this.vertices.add(vertex);
    }

    public void addEdge(int startIndex, int endIndex) {
        this.edges.add(new int[]{startIndex, endIndex});
    }

    public List<Vector3D> getVertices() {
        List<Vector3D> transformedVertices = new ArrayList<>();
        for (Vector3D vertex : vertices) {
            transformedVertices.add(transform.getMatrix().transform(vertex));
        }
        return transformedVertices;
    }

    public List<int[]> getEdges() {
        return edges;
    }

    public void translate(double dx, double dy, double dz) {
        transform.translate(dx, dy, dz);
    }

    public void rotateX(double angle) {
        transform.rotateX(angle);
    }

    public void rotateY(double angle) {
        transform.rotateY(angle);
    }

    public void rotateZ(double angle) {
        transform.rotateZ(angle);
    }
}
