package com.cgvsu;

import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.math.vectors.Vector3f;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

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
    private Model mesh = null;
    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    private double prevMouseX, prevMouseY; // Для отслеживания начальных координат мыши
    private boolean isMousePressed = false; // Флаг для отслеживания нажатия мыши

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

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
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        // Обработка нажатия и отпускания кнопки мыши
        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseReleased(this::onMouseReleased);
        canvas.setOnMouseDragged(this::onMouseDragged);
        canvas.setOnScroll(this::onMouseScroll);

        canvas.setFocusTraversable(true);
    }

    // Событие для нажатия мыши
    private void onMousePressed(MouseEvent event) {
        prevMouseX = event.getSceneX();
        prevMouseY = event.getSceneY();
        isMousePressed = true; // Устанавливаем флаг нажатия
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
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void handleApplyTransformation(ActionEvent actionEvent) {
        try {
            double sx = Double.parseDouble(scaleX.getText());
            double sy = Double.parseDouble(scaleY.getText());
            double sz = Double.parseDouble(scaleZ.getText());
            double angleX = Double.parseDouble(rotateX.getText());
            double angleY = Double.parseDouble(rotateY.getText());
            double angleZ = Double.parseDouble(rotateZ.getText());
            double tx = Double.parseDouble(translateX.getText());
            double ty = Double.parseDouble(translateY.getText());
            double tz = Double.parseDouble(translateZ.getText());

            // Применяем все трансформации
            Matrix4f transformation = Matrix4f.translate(tx, ty, tz)
                    .multiply(Matrix4f.rotateX(angleX))
                    .multiply(Matrix4f.rotateY(angleY))
                    .multiply(Matrix4f.rotateZ(angleZ))
                    .multiply(Matrix4f.scale(sx, sy, sz));

            applyTransformation(transformation);

        } catch (NumberFormatException e) {
            showError("Invalid input for transformations.");
        }
    }

    // Применение трансформации
    private void applyTransformation(Matrix4f transformation) {
        if (mesh != null) {
            mesh.applyTransformationRelativeToInitial(transformation);
            canvas.requestFocus(); // Запрос фокуса для холста после изменения
        } else {
            showError("No model loaded.");
        }
    }

    // Метод для отображения ошибок
    private void showError(String message) {
        System.err.println(message); // Здесь можно заменить на графическое отображение ошибок
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

    // Событие для отпускания кнопки мыши
    private void onMouseReleased(MouseEvent event) {
        isMousePressed = false; // Сбрасываем флаг нажатия
    }

    // Событие для перетаскивания мыши (вращение камеры или перемещение камеры)
    private void onMouseDragged(MouseEvent event) {
        if (isMousePressed) {
            double deltaX = event.getSceneX() - prevMouseX;
            double deltaY = event.getSceneY() - prevMouseY;

            // Вращение камеры на основе движения мыши
            camera.rotate(deltaX, deltaY);

            prevMouseX = event.getSceneX();
            prevMouseY = event.getSceneY();
        }
    }

    // Событие для прокрутки колесика мыши (изменение зума)
    private void onMouseScroll(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            camera.zoomIn();
        } else {
            camera.zoomOut();
        }
    }

}



