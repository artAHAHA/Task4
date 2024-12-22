package com.cgvsu.model;

import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;

import java.util.ArrayList;

public class Model {

    // Списки для хранения вершин, текстурных координат и нормалей
    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();

    // Сохраненные начальные данные
    private ArrayList<Vector3f> initialVertices;
    private ArrayList<Vector2f> initialTextureVertices;
    private ArrayList<Vector3f> initialNormals;
    private boolean isInitialStateSaved = false; // Флаг для отслеживания сохранения начального состояния

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

    /**
     * Применяет трансформацию относительно начального состояния модели.
     *
     * @param transformation Матрица трансформации.
     */
    public void applyTransformationRelativeToInitial(Matrix4f transformation) {
        // Сначала восстанавливаем исходное состояние модели
        resetToInitialState();

        // Применяем трансформацию к каждой вершине
        for (int i = 0; i < vertices.size(); i++) {
            Vector3f vertex = vertices.get(i);
            Vector4f tempVertex = vertex.append(1); // Преобразуем в 4D вектор с w = 1.0

            // Применяем матрицу трансформации
            Vector4f transformedVertex = transformation.multiplyingMatrixByVector(tempVertex);

            // Преобразуем обратно в 3D вектор (игнорируем компоненту w)
            vertices.set(i, new Vector3f(
                    (float) transformedVertex.getX(),
                    (float) transformedVertex.getY(),
                    (float) transformedVertex.getZ()
            ));
        }
    }
}
