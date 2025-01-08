package com.cgvsu.rasterization;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class DrawUtilsJ {
    private final Canvas graphics;
    public DrawUtilsJ(Canvas graphics) {
        this.graphics = graphics;
    }

    public void setPixel(int x, int y, MyColor myColor) {
        graphics.getGraphicsContext2D().getPixelWriter().setColor(x, y, toColor(myColor));
    }

    private Color toColor(MyColor myColor) {
        return Color.color(myColor.getRed(), myColor.getGreen(), myColor.getBlue());
    }
}
