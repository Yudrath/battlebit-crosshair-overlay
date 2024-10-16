package com.crosshair.crosshair;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CrosshairDot {
    public static final double dotCenterX = Constants.crosshairCenterX; //One half of the figure in the left half and the other in the right
    public static final double dotCenterY = Constants.crosshairSceneHeightCenter - (Crosshair.DEFAULT_THICKNESS / 2); //One half of the figure in the top half and the other in the bottom

    private final double DEFAULT_SIZE = 4.0;
    private final Color DEFAULT_COLOR = Crosshair.DEFAULT_COLOR;
    private final double DEFAULT_OPACITY = 1.0;

    double x;
    double y;

    private boolean isEnabled;

    double size; //the size of the dot is the thickness of the crosshair
    Color color;
    double opacity;
    DotShape shape;

    Shape dot;

    Group dotGroup;

    public CrosshairDot() {

        this.isEnabled = false;

        this.size = DEFAULT_SIZE;
        this.color = DEFAULT_COLOR;
        this.opacity = DEFAULT_OPACITY;

        this.x = dotCenterX;
        this.y = dotCenterY;

        this.dot = new Rectangle(this.x, this.y, this.size, this.size);
        this.dot.setOpacity(this.opacity);
        this.dot.setFill(this.color);
    }

    public enum DotShape {
        SQUARE,
        ANGLED_SQUARE, //Rotated 45 degrees
        CIRCLE
    }

    public Shape getDot() {
        return this.dot;
    }

    public void setSize(double newSize) {
        this.size = newSize;
        double shift = newSize / 2;
        this.x = Constants.crosshairSceneWidthCenter - shift;
        this.y = Constants.crosshairSceneHeightCenter - shift;

        if (this.dot instanceof Rectangle) {
            Rectangle square = (Rectangle) this.dot;
            square.setX(this.x);
            square.setY(this.y);
            square.setWidth(this.size);
            square.setHeight(this.size);
        }
    }

    public void setColor(Color color) {
        dot.setFill(color);
        this.color = color;
    }

    public void setOpacity(double opacity) {
        dot.setOpacity(opacity / 100); //Divided by 100, because the values in the UI are from 0-100
    }

    public Group createDotGroup() {
        dotGroup = new Group();

        dotGroup.getChildren().add(dot);

        return dotGroup;
    }

    public void setEnabledDot(boolean isEnabled) {
        dotGroup.setVisible(isEnabled);
        this.isEnabled = isEnabled;
    }

    public double getSize() {
        return size;
    }

    public boolean getStatus() {
        return isEnabled;
    }

    public void setCornerArc(double arc) {
        if (dot instanceof Rectangle) {
            Rectangle rect = (Rectangle) dot;
            rect.setArcWidth(arc);
            rect.setArcHeight(arc);
        }
    }

    public void setHollow(boolean isHollow) {

    }
}
