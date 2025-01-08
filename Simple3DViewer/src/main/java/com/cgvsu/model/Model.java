package com.cgvsu.model;

import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;
import com.cgvsu.objreader.ObjReaderException;


import java.util.*;

public class Model implements Cloneable{

    // Списки для хранения вершин, текстурных координат и нормалей
    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector2f> textureVertices = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Polygon> polygons = new ArrayList<>();

    // Сохраненные начальные данные
    private List<Vector3f> initialVertices;
    private List<Vector2f> initialTextureVertices;
    private List<Vector3f> initialNormals;
    private boolean isInitialStateSaved = false; // Флаг для отслеживания сохранения начального состояния

    public Model(final List<Vector3f> vertices, final List<Vector2f> textureVertices, final List<Vector3f> normals, final List<Polygon> polygons) {
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.normals = normals;
        this.polygons = polygons;
    }

    public Model() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public boolean checkCorrectOfData() {
        for (int i = 0; i < polygons.size(); i++) {
            List<Integer> vertexIndices = polygons.get(i).getVertexIndices();
            List<Integer> textureVertexIndices = polygons.get(i).getTextureVertexIndices();
            List<Integer> normalIndices = polygons.get(i).getNormalIndices();

            //кол-во точек с кол-вом текстур
            if (vertexIndices.size()!=textureVertexIndices.size()
                    && vertexIndices.size() != 0 && textureVertexIndices.size() != 0) {
                throw new ObjReaderException.ObjContentException("Polygon data is incorrect.");
            }
            //кол-во точек с кол-вом нормалей
            if (vertexIndices.size()!= normalIndices.size()
                    && vertexIndices.size() != 0 && normalIndices.size()!=0) {
                throw new ObjReaderException.ObjContentException("Polygon data is incorrect.");
            }
            //кол-во нормалей и полигонов
            if (textureVertexIndices.size()!=normalIndices.size()
                    && textureVertexIndices.size()!=0 && normalIndices.size()!=0) {
                throw new ObjReaderException.ObjContentException("Polygon data is incorrect.");
            }

            //корректность листа с номерами точек
            for (Integer vertexIndex : vertexIndices) {
                if (vertexIndex > vertices.size()) {
                    throw new ObjReaderException.ObjContentException("Polygon parameter(vertex) is incorrect");
                }
            }

            for (Integer textureVertexIndex : textureVertexIndices) {
                if (textureVertexIndex > textureVertices.size()) {
                    throw new ObjReaderException.ObjContentException("Polygon parameter(texture vertex) is incorrect");
                }
            }

            for (Integer normalIndex : normalIndices) {
                if (normalIndex > normals.size()) {
                    throw new ObjReaderException.ObjContentException("Polygon parameter(normal) is incorrect");
                }
            }
        }
        return true;
    }

    // Метод клонирования вершин
    public ArrayList<Vector3f> cloneVertices() {
        ArrayList<Vector3f> clonedVertices = new ArrayList<>();
        for (Vector3f vertex : this.vertices) {
            clonedVertices.add(vertex.clone());
        }
        return clonedVertices;
    }

    // Метод клонирования текстурных вершин
    public ArrayList<Vector2f> cloneTextureVertices() {
        ArrayList<Vector2f> clonedTextureVertices = new ArrayList<>();
        for (Vector2f textureVertex : this.textureVertices) {
            clonedTextureVertices.add(textureVertex.clone());
        }
        return clonedTextureVertices;
    }

    // Метод клонирования нормалей
    public ArrayList<Vector3f> cloneNormals() {
        ArrayList<Vector3f> clonedNormals = new ArrayList<>();
        for (Vector3f normal : this.normals) {
            clonedNormals.add(normal.clone());
        }
        return clonedNormals;
    }

    // Метод клонирования полигонов
    public ArrayList<Polygon> clonePolygons() {
        ArrayList<Polygon> clonedPolygons = new ArrayList<>();
        for (Polygon polygon : this.polygons) {
            clonedPolygons.add(polygon.clone());
        }
        return clonedPolygons;
    }

    // Метод clone
    @Override
    public Model clone() {
        Model clonedModel = new Model();
        clonedModel.vertices = this.cloneVertices();
        clonedModel.textureVertices = this.cloneTextureVertices();
        clonedModel.normals = this.cloneNormals();
        clonedModel.polygons = this.clonePolygons();
        return clonedModel;
    }
    public void exportToOBJ() {
        // Устанавливаем локаль для использования точки как разделителя дробной части
        Locale.setDefault(Locale.US);

        // Вывод вершин
        for (Vector3f vertex : vertices) {
            System.out.printf("v %.6f %.6f %.6f%n", vertex.getX(), vertex.getY(), vertex.getZ());
        }

        // Вывод нормалей
        for (Vector3f normal : normals) {
            System.out.printf("vn %.6f %.6f %.6f%n", normal.getX(), normal.getY(), normal.getZ());
        }

        // Вывод текстурных координат
        for (Vector2f textureVertex : textureVertices) {
            System.out.printf("vt %.6f %.6f%n", textureVertex.getX(), textureVertex.getY());
        }

        // Вывод полигонов
        for (Polygon polygon : polygons) {
            System.out.print("f");
            for (int i = 0; i < polygon.getVertexIndices().size(); i++) {
                int vertexIndex = polygon.getVertexIndices().get(i) + 1; // Индексация в OBJ начинается с 1
                String facePart = String.valueOf(vertexIndex);

                if (!polygon.getTextureVertexIndices().isEmpty()) {
                    int textureIndex = polygon.getTextureVertexIndices().get(i) + 1;
                    facePart += "/" + textureIndex;
                }

                if (!polygon.getNormalIndices().isEmpty()) {
                    int normalIndex = polygon.getNormalIndices().get(i) + 1;
                    facePart += (facePart.contains("/") ? "" : "/") + "/" + normalIndex;
                }

                System.out.print(" " + facePart);
            }
            System.out.println();
        }
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
            Vector4f transformedVertex = (Vector4f) transformation.multiplyingMatrixByVector(tempVertex);

            // Преобразуем обратно в 3D вектор (игнорируем компоненту w)
            vertices.set(i, new Vector3f(
                    (float) transformedVertex.getX(),
                    (float) transformedVertex.getY(),
                    (float) transformedVertex.getZ()
            ));
        }
    }
    public List<Vector3f> getVertices() {
        return vertices;
    }

    public void setVertices(final List<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public List<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public void setTextureVertices(final List<Vector2f> textureVertices) {
        this.textureVertices = textureVertices;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public void setNormals(final List<Vector3f> normals) {
        this.normals = normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(final List<Polygon> polygons) {
        this.polygons = polygons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(vertices, model.vertices) && Objects.equals(textureVertices, model.textureVertices) && Objects.equals(normals, model.normals) && Objects.equals(polygons, model.polygons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, textureVertices, normals, polygons);
    }
}
