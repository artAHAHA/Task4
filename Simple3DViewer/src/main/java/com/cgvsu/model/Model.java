package com.cgvsu.model;

import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;

import java.util.ArrayList;

public class Model {

    // Списки для хранения вершин, текстурных координат и нормалей
    public ArrayList<Vector3f> vertices;
    public ArrayList<Vector2f> textureVertices;
    public ArrayList<Vector3f> normals;
    public ArrayList<Polygon> polygons;
    public boolean isTexture;

    // Сохраненные начальные данные
    private ArrayList<Vector3f> initialVertices;
    private ArrayList<Vector2f> initialTextureVertices;
    private ArrayList<Vector3f> initialNormals;
    private boolean isInitialStateSaved = false; // Флаг для отслеживания сохранения начального состояния

    public Model() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
        this.isTexture=false;
    }

    public void setPolygons(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    /**
     * Сохраняет начальное состояние модели.
     */
    public void saveInitialState() {
        if (!isInitialStateSaved) {
            initialVertices = new ArrayList<>(vertices);
            initialTextureVertices = new ArrayList<>(textureVertices);
            initialNormals = new ArrayList<>(normals);
            isInitialStateSaved = true;
        }
    }

    /**
     * Восстанавливает модель в начальное состояние.
     */
    public void resetToInitialState() {
        if (isInitialStateSaved) {
            vertices = new ArrayList<>(initialVertices);
            textureVertices = new ArrayList<>(initialTextureVertices);
            normals = new ArrayList<>(initialNormals);
        }
    }
}
