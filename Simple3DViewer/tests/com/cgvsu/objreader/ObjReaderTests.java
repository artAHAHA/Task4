package com.cgvsu.objreader;

import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.model.Polygon;


import com.cgvsu.vertexDelete.Eraser;
import com.cgvsu.model.Model;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.*;

public class ObjReaderTests {

    private static Model model;
    private static String fileContent;

    @BeforeClass
    public static void setup() throws IOException {
        Path fileName = Path.of("D:\\Task4\\Simple3DViewer\\tests\\com\\cgvsu\\SimpleModelsForReaderTests\\caracal_cube.obj");
        fileContent = Files.readString(fileName);
        model = ObjReader.read(fileContent);
    }

    private Model deleteVertices(List<Integer> verticesToDelete, boolean newFile) {
        return Eraser.vertexDelete(model, verticesToDelete, true, false, false, newFile);
    }

    @Test
    public void testDeleteSingleVertex() {
        Model modifiedModel = deleteVertices(List.of(0), true);
        assertEquals("Вершина не была удалена!", model.vertices.size() - 1, modifiedModel.vertices.size());
    }

    @Test
    public void testDeleteMultipleVertices() {
        Model modifiedModel = deleteVertices(Arrays.asList(0, 1, 2), true);
        assertEquals("Несколько вершин не были удалены!", model.vertices.size() - 3, modifiedModel.vertices.size());
    }

    @Test
    public void testDeleteAndCheckPolygons() {
        Model modifiedModel = deleteVertices(List.of(1,2), true);
        assertTrue("Полигоны не были корректно обработаны!", modifiedModel.polygons.size() < model.polygons.size());
    }

    @Test
    public void testHangingIndices() {
        Model modifiedModel = Eraser.vertexDelete(model, List.of(0), true, true, true, true);
        assertEquals("Нормали должны были остаться!", model.normals.size(), modifiedModel.normals.size());
    }

    @Test
    public void testDeleteAllVertices() {
        List<Integer> verticesToDelete = new ArrayList<>();
        for (int i = 0; i < model.vertices.size(); i++) {
            verticesToDelete.add(i);
        }
        Model modifiedModel = deleteVertices(verticesToDelete, true);
        assertTrue("Все вершины должны быть удалены!", modifiedModel.vertices.isEmpty());
        assertTrue("Все полигоны должны быть удалены!", modifiedModel.polygons.isEmpty());
    }

    @Test
    public void testDeleteVertexWithTextureIndices() {
        Model modifiedModel = Eraser.vertexDelete(model, List.of(0), true, false, true, true);
        assertTrue("Некоторые текстурные координаты не были удалены!",
                modifiedModel.textureVertices.size() <= model.textureVertices.size());
    }

    @Test
    public void testDeleteMiddleVertex() {
        int middleVertexIndex = model.vertices.size() / 2;
        Model modifiedModel = deleteVertices(List.of(middleVertexIndex), true);
        assertEquals("Средняя вершина не была удалена!", model.vertices.size() - 1, modifiedModel.vertices.size());

    }

    @Test
    public void testDeleteVertexWithCloneMode() {
        Model modifiedModel = deleteVertices(List.of(0), true);
        assertEquals("Оригинальная модель не должна была измениться!", model.vertices.size(), ObjReader.read(fileContent).vertices.size());
        assertNotEquals("Измененная модель должна отличаться от оригинальной!", model.vertices.size(), modifiedModel.vertices.size());
    }

    @Test
    public void testDeleteNonExistingVertex() {
        Model modifiedModel = deleteVertices(List.of(model.vertices.size() + 1), true);
        assertEquals("Модель не должна была измениться!", model.vertices.size(), modifiedModel.vertices.size());
        assertEquals("Количество полигонов не должно было измениться!", model.polygons.size(), modifiedModel.polygons.size());
    }

    @Test
    public void testParseVertex01() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assert.assertEquals(expectedResult, result);
    }
    //не подходящие под формат символы
    @Test
    public void testParseVertex02() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у точки меньше, чем 3 координаты
    @Test
    public void testParseVertex03() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of vertex coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у точки задают 4 координаты (больше 3х)
    @Test
    public void testParseVertex04() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of vertex coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    //стандартная ситуация
    @Test
    public void testParseTextureVertex01() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.7500", "2.0000"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(1.7500f, 2.0000f);
        Assert.assertEquals(expectedResult, result);
    }
    //не подходящие под формат символы
    @Test
    public void testParseTextureVertex02() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("abc", "ofg"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у текстуры меньше, чем 2 передаваемые координаты
    @Test
    public void testParseTextureVertex03() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(List.of("1.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of texture vertex coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у текстуры задают 4 координаты (больше 3х)
    @Test
    public void testParseTextureVertex04() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of texture vertex coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    //стандартная ситуация
    @Test
    public void testParseNormalVertex01() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.2649", "-0.7209", "-0.6404"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 3);
        Vector3f expectedResult = new Vector3f(0.2649f, -0.7209f, -0.6404f);
        Assert.assertEquals(expectedResult, result);
    }
    //не подходящие под формат символы
    @Test
    public void testParseNormalVertex02() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("abc", "ofg", "nm"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у текстуры меньше, чем 3 передаваемые координаты
    @Test
    public void testParseNormalVertex03() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(List.of("1.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of normal coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у текстуры задают 4 координаты (больше 2х)
    @Test
    public void testParseNormalVertex04() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "4.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of normal coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFace01() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("2/2/5", "7/7/6", "8/8/7", "3/3/8"));
        Polygon polygon = ObjReader.parseFace(wordsInLineWithoutToken, 10);
        Polygon expectedResult = new Polygon();
        expectedResult.setVertexIndices(Arrays.asList(1, 6, 7, 2));
        expectedResult.setTextureVertexIndices(Arrays.asList(1, 6, 7, 2));
        expectedResult.setNormalIndices(Arrays.asList(4, 5, 6, 7));
        Assert.assertEquals(expectedResult, polygon);
    }

    @Test
    public void testParseFace02() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("2/2/5", "7/7/6"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Not enough information! A polygon requires at least three points!";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    //предотвращение
    @Test
    public void testParseFace03() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/2/3", "1", "1/4/3", "1/2"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Incorrect format in the face description. Unreal situation!";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFace04() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("o/j/o", "oim[okm", "plk"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFace05() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("-2/2/5", "7/7/-6", "8/8/5"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error! Impossible format! Index cannot be negative!";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord01() {
        String wordsInLineWithoutToken = "3/4/1/3";
        try {
            List<Integer> verticesIndices = new ArrayList<>();
            List<Integer> textureVerticesIndices = new ArrayList<>();
            List<Integer> normalVerticesIndices = new ArrayList<>();
            ObjReader.parseFaceWord(wordsInLineWithoutToken, verticesIndices, textureVerticesIndices, normalVerticesIndices, 5);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. Invalid element size.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord02() {
        String wordsInLineWithoutToken = "3/-4/1";
        try {
            List<Integer> verticesIndices = new ArrayList<>();
            List<Integer> textureVerticesIndices = new ArrayList<>();
            List<Integer> normalVerticesIndices = new ArrayList<>();
            ObjReader.parseFaceWord(wordsInLineWithoutToken, verticesIndices, textureVerticesIndices, normalVerticesIndices, 5);
        } catch (ObjReaderException exception) {
            String expectedError = "Error! Impossible format! Index cannot be negative!";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testReader() {
        List<Vector3f> vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0.3f, 0.1f, 0.1f), new Vector3f(0.3f, 0.3f, 0.2f),
                new Vector3f(0.4f, 0.5f, 0.4f), new Vector3f(0.5f, 0.5f, 0.4f),
                new Vector3f(0.7f, 0.6f, 0.5f)));
        List<Vector2f> textureVertices = new ArrayList<>(Arrays.asList(
                new Vector2f(0.2f, 0.2f), new Vector2f(0.8f, 0.3f),
                new Vector2f(0.5f, 0.5f), new Vector2f(0.3f, 0.3f),
                new Vector2f(0.5f, 0.7f)));
        List<Vector3f> normalVertices = new ArrayList<>(Arrays.asList(
                new Vector3f(-0.9f, -0.2f, 0.0f), new Vector3f(-0.8f, -0.3f, -0.4f),
                new Vector3f(-0.8f, 0.2f, -0.3f), new Vector3f(-0.9f, 0.5f, 0.1f),
                new Vector3f(-0.8f, 0.2f, 0.2f)));
        List<Polygon> polygons = new ArrayList<>(Arrays.asList(
                new Polygon( Arrays.asList(1,3,1,4), Arrays.asList(3,3,2,5), Arrays.asList(5,5,2,5)),
                new Polygon( Arrays.asList(2,4,1,3), Arrays.asList(3,3,2,4), Arrays.asList(5,3,2,4)),
                new Polygon( Arrays.asList(3,4,2,4), Arrays.asList(3,5,2,3), Arrays.asList(3,5,2,2)),
                new Polygon(Arrays.asList(4,1,2,1),  Arrays.asList(3,1,3,2), Arrays.asList(3,1,4,5)),
                new Polygon( Arrays.asList(4,2,1,3), Arrays.asList(5,2,2,4), Arrays.asList(5,2,3,5))));
        Model model = ObjReader.read("""
                v  0.3 0.1 0.1
                v  0.3 0.3 0.2
                v  0.4 0.5 0.4
                v  0.5 0.5 0.4
                v  0.7 0.6 0.5

                vt 0.2 0.2
                vt 0.8 0.3
                vt 0.5 0.5
                vt 0.3 0.3
                vt 0.5 0.7

                vn -0.9 -0.2 0.0
                vn -0.8 -0.3 -0.4
                vn -0.8 0.2 -0.3
                vn -0.9 0.5 0.1
                vn -0.8 0.2 0.2
                
                f 2/4/6 4/4/6 2/3/3 5/6/6
                f 3/4/6 5/4/4 2/3/3 4/5/5\s
                f 4/4/4 5/6/6 3/3/3 5/4/3\s
                f 5/4/4 2/2/2 3/4/5 2/3/6\s
                f 5/6/6 3/3/3 2/3/4 4/5/6

                """);
        Assert.assertEquals(vertices, model.getVertices());
        Assert.assertEquals(textureVertices, model.getTextureVertices());
        Assert.assertEquals(normalVertices, model.getNormals());
        Assert.assertEquals(polygons, model.getPolygons());
    }
}
