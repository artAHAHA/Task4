package com.cgvsu.calculateNormals;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.*;

public class CalculateNormals {
    /*public static void findNormals(Model m) { // находим нормали для каждого полигона
        List<Polygon> polygons = m.polygons;
        List<Vector3f> vertices = m.vertices;
        m.normals.clear();

        ArrayList<Vector3f> temporaryNormals = new ArrayList<>();
        ArrayList<Vector3f> normals = new ArrayList<>();

        for (Polygon p : polygons) {
            temporaryNormals.add(findPolygonsNormals(vertices.get(p.getVertexIndices().get(0)),
                    vertices.get(p.getVertexIndices().get(1)), vertices.get(p.getVertexIndices().get(2))));
        }

        Map<Integer, Set<Vector3f>> vertexPolygonsMap = new HashMap<>();
        for (int j = 0; j < polygons.size(); j++) {
            List<Integer> vertexIndices = polygons.get(j).getVertexIndices();
            Vector3f vec = temporaryNormals.get(j);
            for (Integer index : vertexIndices) {
                vertexPolygonsMap.computeIfAbsent(index, k -> new HashSet<>()).add(vec);
            }
        }

        for (int i = 0; i < vertices.size(); i++) {
            normals.add(findVertexNormals(vertexPolygonsMap.get(i)));
        }

        m.normals = normals;
    }

    public static Vector3f findPolygonsNormals(Vector3f... vs) { // находим нормали полигона
        Vector3f a = Vector3f.subtraction(vs[0], vs[1]);
        Vector3f b = Vector3f.subtraction(vs[0], vs[2]);
        Vector3f c = vectorProduct(a, b);
        if (determinant(a, b, c) < 0) {
            c = vectorProduct(b, a);
        }
        return normalize(c);
    }

    public static Vector3f findVertexNormals(Set<Vector3f> vs) { //находим текстурные нормали
        float xs = 0, ys = 0, zs = 0;
        for (Vector3f v : vs) {
            xs += v.getX();
            ys += v.getY();
            zs += v.getZ();
        }
        xs /= vs.size();
        ys /= vs.size();
        zs /= vs.size();
        return normalize(new Vector3f(xs, ys, zs));
    }

    public static double determinant(Vector3f a, Vector3f b, Vector3f c) { // вычисляем определитель
        return a.getX() * (b.getY() * c.getZ()) - a.getY() * (b.getX() * c.getZ() - c.getX() * b.getZ()) + a.getZ() * (b.getX() * c.getY() - c.getX() * b.getY());
    }

    public static Vector3f normalize(Vector3f v) { // нормализуем вектор
        if (v == null) {
            return null;
        }

        double length = Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());

        if (length == 0) {
            return new Vector3f(0, 0, 0);
        }
        float v1 = v.getX(), v2 = v.getY(), v3 = v.getZ();
        v1 /= (float) length;
        v2 /= (float) length;
        v3 /= (float) length;

        return new Vector3f(v1, v2, v3);
    }

    public static Vector3f vectorProduct(Vector3f a, Vector3f b) { // вычисляем веторное проиведение
        return new Vector3f(a.getY() * b.getZ() - b.getY() * a.getZ(), -a.getX() * b.getZ() + b.getX() * a.getZ(), a.getX() * b.getY() - b.getX() * a.getY());
    }*/
}
