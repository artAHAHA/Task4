package com.cgvsu.triangle;

import com.cgvsu.model.Polygon;

import java.util.ArrayList;

public class Triangle {
    public static ArrayList<Polygon> triangulatePolygon(Polygon poly) {
        int vertexNum = poly.getVertexIndices().size(); // получаем число точек в исходном полигоне
        ArrayList<Polygon> polygons = new ArrayList<Polygon>(); // создаём список, в котором будут храниться треугольные полигоны
        if (poly.getVertexIndices().size() == 3) { // проверяем, является ли исходный полигон треугольником
            polygons.add(poly);
            return polygons;
        }
        for (int i = 2; i < vertexNum - 1; i++) { // цикл для создания треугольников для основной части полигона
            Polygon currPoly = getPolygon(poly, i);
            polygons.add(currPoly);
        }
        if (vertexNum > 3) { // проверяем, нужно ли добавить ещё один треугольник
            ArrayList<Integer> vertex = new ArrayList<>();
            vertex.add(poly.getVertexIndices().get(0));
            vertex.add(poly.getVertexIndices().get(vertexNum - 2));
            vertex.add(poly.getVertexIndices().get(vertexNum - 1));

            ArrayList<Integer> textureVertex = new ArrayList<>();
            if (!poly.getTextureVertexIndices().isEmpty()) {
                textureVertex.add(poly.getTextureVertexIndices().get(0));
                textureVertex.add(poly.getTextureVertexIndices().get(vertexNum-1));
                textureVertex.add(poly.getTextureVertexIndices().get(vertexNum-2));
            }

            ArrayList<Integer> normals = new ArrayList<>();
            if (!poly.getNormalIndices().isEmpty()) {
                normals.add(poly.getNormalIndices().get(0));
                normals.add(poly.getNormalIndices().get(vertexNum-1));
                normals.add(poly.getNormalIndices().get(vertexNum-2));
            }

            Polygon currPoly = new Polygon();

            currPoly.setVertexIndices(vertex);
            currPoly.setTextureVertexIndices(textureVertex);
            currPoly.setNormalIndices(normals);

            polygons.add(currPoly);
        }
        return polygons;
    }
    private static Polygon getPolygon(Polygon poly, int i) { // создаём новый полигон
        ArrayList<Integer> vertex = new ArrayList<>(); // список для хранения индексов вершин текущего треугольника
        vertex.add(poly.getVertexIndices().get(0));
        vertex.add(poly.getVertexIndices().get(i - 1));
        vertex.add(poly.getVertexIndices().get(i));

        ArrayList<Integer> textureVertex = new ArrayList<>(); // список для хранения индексов текстурных вершин текущего треугольника
        if (!poly.getTextureVertexIndices().isEmpty()) {
            textureVertex.add(poly.getTextureVertexIndices().get(0));
            textureVertex.add(poly.getTextureVertexIndices().get(i-1));
            textureVertex.add(poly.getTextureVertexIndices().get(i));
        }

        ArrayList<Integer> normals = new ArrayList<>(); // список для хранения индексов нормалей
        if (!poly.getNormalIndices().isEmpty()) {
            normals.add(poly.getNormalIndices().get(0));
            normals.add(poly.getNormalIndices().get(i-1));
            normals.add(poly.getNormalIndices().get(i));
        }

        Polygon currPoly = new Polygon();
        currPoly.setVertexIndices(vertex);// устанавливаем индексы вершин для текущего треугольника
        currPoly.setTextureVertexIndices(textureVertex); // устанавливаем индексы текстурных вершин для текущего треугольника
        currPoly.setNormalIndices(normals); // устанавливаем индексы нормалей для текущего треугольника

        return currPoly;
    }
    public static ArrayList<Polygon> triangulateModel(ArrayList<Polygon> polygons) { // метод для триангуляции всех полигонов
        ArrayList<Polygon> newModelPoly = new ArrayList<>(); // создаём список, где будут храниться треугольные полигоны
        for (Polygon polygon : polygons) {
            newModelPoly.addAll(triangulatePolygon(polygon)); // добавялем все полученные треугольные полигоны
        }
        return newModelPoly;
    }
}