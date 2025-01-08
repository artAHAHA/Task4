package com.cgvsu.objwriter;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import com.cgvsu.math.vectors.Vector2f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ObjWriterTests {
    private final ObjWriter objWriter = new ObjWriter();

    @ParameterizedTest
    @CsvSource({ "2.3f, -4.54f, 0", "0, 0, 0", "132.2652624646f, 0.0131f, -5.5437f", "-2.8f, -0.0, 9.3" })
    public void vertexToStringTest(float x, float y, float z) {
        String result = objWriter.vertexToString(new Vector3f(x, y, z));
        String[] array = result.split(" ");
        Assertions.assertEquals("v", array[0]);
        Assertions.assertEquals(x, Float.parseFloat(array[1]));
        Assertions.assertEquals(y, Float.parseFloat(array[2]));
        Assertions.assertEquals(z, Float.parseFloat(array[3]));
    }

    @ParameterizedTest
    @CsvSource({ "2.3f, -4.54f", "0, 0", "132.2652624646f, 0.0131f", "-2.8f, -0.0" })
    public void textureVertexToStringTest(float x, float y) {
        String result = objWriter.textureVertexToString(new Vector2f(x, y));
        String[] array = result.split(" ");
        Assertions.assertEquals("vt", array[0]);
        Assertions.assertEquals(x, Float.parseFloat(array[1]));
        Assertions.assertEquals(y, Float.parseFloat(array[2]));
    }

    @ParameterizedTest
    @CsvSource({ "2.3f, -4.54f, 0", "0, 0, 0", "132.2652624646f, 0.0131f, -5.5437f", "-2.8f, -0.0, 9.3" })
    public void normalToStringTest(float x, float y, float z) {
        String result = objWriter.normalToString(new Vector3f(x, y, z));
        String[] array = result.split(" ");
        Assertions.assertEquals("vn", array[0]);
        Assertions.assertEquals(x, Float.parseFloat(array[1]));
        Assertions.assertEquals(y, Float.parseFloat(array[2]));
        Assertions.assertEquals(z, Float.parseFloat(array[3]));
    }

    @Test
    public void polygonToStringTestWithOnlyVertexIndices() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(List.of(0, 1, 2)));
        String result = objWriter.polygonToString(polygon);
        Assertions.assertEquals("f 1 2 3", result);
    }

    @Test
    public void polygonToStringTestWithTextureVertexIndices() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(List.of(0, 1, 2, 5)));
        polygon.setTextureVertexIndices(new ArrayList<>(List.of(3, 5, 4, 2)));
        String result = objWriter.polygonToString(polygon);
        Assertions.assertEquals("f 1/4 2/6 3/5 6/3", result);
    }

    @Test
    public void polygonToStringTestWithNormalIndicesAndWithoutTextureVertexIndices() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(List.of(0, 1, 2, 5)));
        polygon.setNormalIndices(new ArrayList<>(List.of(3, 5, 4, 2)));
        String result = objWriter.polygonToString(polygon);
        Assertions.assertEquals("f 1//4 2//6 3//5 6//3", result);
    }

    @Test
    public void polygonToStringTestWithNormalIndicesAndWithTextureVertexIndices() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(List.of(0, 1, 2, 5)));
        polygon.setTextureVertexIndices(new ArrayList<>(List.of(7, 4, 3, 6)));
        polygon.setNormalIndices(new ArrayList<>(List.of(3, 5, 4, 2)));
        String result = objWriter.polygonToString(polygon);
        Assertions.assertEquals("f 1/8/4 2/5/6 3/4/5 6/7/3", result);
    }
}
