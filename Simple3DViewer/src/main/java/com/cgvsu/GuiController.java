package com.cgvsu;

import com.cgvsu.calculateNormals.CalculateNormals;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.model.Polygon;
import com.cgvsu.rasterization.DrawUtilsJ;
import com.cgvsu.rasterization.MyColor;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.render_engine.RenderRasterization;
import com.cgvsu.triangle.Triangle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    public static boolean isLight = true;
    private boolean isStructure = true;
    private BufferedImage image = null;
    final private float TRANSLATION = 3F;

    @FXML
    private TextField scaleX, scaleY, scaleZ;

    @FXML
    private TextField rotateX, rotateY, rotateZ;

    @FXML
    private TextField translateX, translateY, translateZ;
    @FXML
    private VBox controlPanel;
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;
    private Model mesh;
    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        DrawUtilsJ graphicsUtils = new DrawUtilsJ(canvas);

        controlPanel.toFront();

        scaleX.setText("1");
        scaleY.setText("1");
        scaleZ.setText("1");

        rotateX.setText("0");
        rotateY.setText("0");
        rotateZ.setText("0");

        translateX.setText("0");
        translateY.setText("0");
        translateZ.setText("0");


        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                try {
                    MyColor  mc = new MyColor(0.5, 0.5, 0.5);
                    RenderRasterization.render(canvas.getGraphicsContext2D(), graphicsUtils,
                                camera, mesh, (int) width, (int) height, image, mc);
                    if (isStructure) {
                        RenderEngine.render(canvas.getGraphicsContext2D(), graphicsUtils, camera, mesh, (int) width, (int) height);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        canvas.setFocusTraversable(true);
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            mesh.saveInitialState();
            CalculateNormals.findNormals(mesh);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        ArrayList<Polygon> triangles = Triangle.triangulateModel(mesh.polygons); //создаём список для хранения треугольных полигонов
        mesh.setPolygons(triangles); // заменяем в модели полигоны на треугольные
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }
}



