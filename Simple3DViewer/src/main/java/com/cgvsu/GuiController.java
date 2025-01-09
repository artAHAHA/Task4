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
import javafx.scene.control.ComboBox;
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
import java.util.Arrays;
import java.util.List;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

import javax.imageio.ImageIO;

public class GuiController {

    public static boolean isLight = false;
    private boolean isStructure = false;
    private BufferedImage image = null;
    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;
    @FXML
    private ComboBox<String> chooseCamera;
    private Model mesh;
    private ArrayList<Camera> camera = new ArrayList<>(Arrays.asList(new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100)));
    private List<String> namesCamera = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private int numberCamera = 0;
    private String selectedValueCamera;
    private Timeline timeline;

    @FXML
    private void initialize() {
        chooseCamera.getItems().add(String.valueOf(numberCamera));
        namesCamera.add(String.valueOf(numberCamera));

        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        DrawUtilsJ graphicsUtils = new DrawUtilsJ(canvas);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.get(numberCamera).setAspectRatio((float) (width / height));

            if (mesh != null) {
                try {
                    MyColor  mc = new MyColor(0.5,0, 1);
                    RenderRasterization.render(canvas.getGraphicsContext2D(), graphicsUtils,
                                camera.get(numberCamera), mesh, (int) width, (int) height, image, mc);
                    if (isStructure) {
                        RenderEngine.render(canvas.getGraphicsContext2D(), graphicsUtils, camera.get(numberCamera), mesh, (int) width, (int) height);
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

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            mesh.saveInitialState();
            CalculateNormals.findNormals(mesh); // вычисление нормалей после загрузки модели
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        ArrayList<Polygon> triangles = Triangle.triangulateModel(mesh.polygons); //создаём список для хранения треугольных полигонов
        mesh.setPolygons(triangles); // заменяем в модели полигоны на треугольные
    }

    @FXML
    private void loadTexture() throws IOException {

        if (!mesh.isTexture) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"));
            fileChooser.setTitle("Load png");
            File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());

            if (file == null) {
                return;
            }
            image = ImageIO.read(file);
        }
        mesh.isTexture = !mesh.isTexture;

    }
    @FXML
    private void loadLight() {
        isLight = !isLight;
    }

    @FXML
    private void loadStructure() {
        isStructure = !isStructure;
    }
    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.get(numberCamera).scalePosition(new Vector3f(0.98f, 0.98f, 0.98f));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.get(numberCamera).scalePosition(new Vector3f(1.05f, 1.05f, 1.05f));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.get(numberCamera).movePosition(new Vector3f(TRANSLATION, 0, 0));
        camera.get(numberCamera).moveTarget(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.get(numberCamera).movePosition(new Vector3f(-TRANSLATION, 0, 0));
        camera.get(numberCamera).moveTarget(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.get(numberCamera).movePosition(new Vector3f(0, TRANSLATION, 0));
        camera.get(numberCamera).moveTarget(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.get(numberCamera).movePosition(new Vector3f(0, -TRANSLATION, 0));
        camera.get(numberCamera).moveTarget(new Vector3f(0, -TRANSLATION, 0));
    }

    @FXML
    public void handleCameraLeftAroundTarget(ActionEvent actionEvent) {
        camera.get(numberCamera).rotationAroundChangedY(Math.PI / 30);
    }

    @FXML
    public void handleCameraRightAroundTarget(ActionEvent actionEvent) {
        camera.get(numberCamera).rotationAroundChangedY(-Math.PI / 30);
    }

    @FXML
    public void handleCameraAroundX(ActionEvent actionEvent) {
        camera.get(numberCamera).rotationAroundChangedX(Math.PI / 20);
    }

    @FXML
    public void handleCameraAroundXBack(ActionEvent actionEvent) {
        camera.get(numberCamera).rotationAroundChangedX(-Math.PI / 20);
    }

    @FXML
    public void addCamera() {
        camera.add(new Camera(
                new Vector3f(0, 0, 100),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100));
        numberCamera++;
        namesCamera.add(String.valueOf(numberCamera));
        chooseCamera.getItems().add(String.valueOf(numberCamera));
    }

    @FXML
    public void deleteCamera() {
        if (camera.size() > 1) {
            if (numberCamera == camera.size() - 1) {
                numberCamera--;
                camera.remove(camera.size() - 1);
                names.remove(camera.size() - 1);
                chooseCamera.getItems().remove(numberCamera + 1);
            }
        }
    }

    @FXML
    public void choosingCamera(ActionEvent actionEvent) {
        selectedValueCamera = chooseCamera.getSelectionModel().getSelectedItem();
        for (int i = 0; i < namesCamera.size(); i++) {
            if (namesCamera.get(i).equals(selectedValueCamera)) {
                numberCamera = i;
            }
        }

    }
}



