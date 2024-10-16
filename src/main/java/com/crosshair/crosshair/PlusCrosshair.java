package com.crosshair.crosshair;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class PlusCrosshair implements Crosshair{

    Rectangle[] crosshairLines = new Rectangle[4];
    private Group crosshairGroup;

    double x;
    double y;
    double size;
    double thickness;
    double gap;
    double opacity;
    Color color;
    Type type;
    boolean isHollow;

    public enum Type {
        DEFAULT,
        ANGLED;
    }

    public PlusCrosshair(Type type) {
        this.size = DEFAULT_SIZE;
        this.thickness = DEFAULT_THICKNESS;
        this.gap = DEFAULT_GAP;
        this.opacity = DEFAULT_OPACITY;
        this.color = DEFAULT_COLOR;
        this.type = type;
        this.x = Constants.crosshairCenterX;
        this.y = Constants.crosshairCenterY;
        this.isHollow = false;

        createPlusCrosshair();
    }

    private void createPlusCrosshair() {
        createPlusCrosshairLines();
        rotatePlusCrosshairLines();
        setColor(color);
    }

    private void rotatePlusCrosshairLines() {
        double pivotX = Constants.crosshairSceneWidthCenter;
        double pivotY = Constants.crosshairSceneHeightCenter;
        double angle;
        double angleIncrease = 90.0;

        if (this.type == Type.DEFAULT) {
            angle = 0.0;
        } else {
            angle = 45.0;
        }

        for (Rectangle line : this.crosshairLines) {
            Rotate rotate = new Rotate();

            rotate.setPivotX(pivotX);
            rotate.setPivotY(pivotY);
            rotate.setAngle(angle);

            line.getTransforms().add(rotate);
            angle = angle + angleIncrease;
        }
    }

    private void createPlusCrosshairLines() {
        int i = 0;
        while (i < this.crosshairLines.length) {
            Rectangle line = new Rectangle
                    (Constants.crosshairCenterX, Constants.crosshairCenterY, this.thickness, this.size);
            this.crosshairLines[i] = line;
            i++;
        }
    }

    public Group createCrosshairGroup() {
        Group group = new Group();

        for (int i = 0; i < this.crosshairLines.length; i++) {
            group.getChildren().add(this.crosshairLines[i]);
        }

        crosshairGroup = group;

        return group;
    }

    public void printCoordinates() {
        for (int i = 0; i < this.crosshairLines.length; i++) {
            System.out.println("x:" + this.crosshairLines[i].getX() + ", y:" + this.crosshairLines[i].getY());
        }
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public double getThickness() {
        return thickness;
    }

    @Override
    public double getGap() {
        return gap;
    }

    @Override
    public double getOpacity() {
        return opacity;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public boolean getHollowStatus() {
        return isHollow;
    }

    @Override
    public void setSize(double newSize) {
        for (Rectangle line : this.crosshairLines) {
            line.setHeight(newSize);
        }

        this.size = newSize;
    }

    @Override
    public void setThickness(double newThickness) {
        double horizontalShift = newThickness / 2;
        double newX = Constants.crosshairSceneWidthCenter - horizontalShift;
        this.setX(newX);
        this.setWidth(newThickness);
    }

    @Override
    public void setGap(double newGap) {
        double newY = Constants.crosshairSceneHeightCenter + newGap;
        this.setY(newY);
        this.gap = newGap;
    }

    @Override
    public void setOpacity(double newOpacity) {
        this.opacity = newOpacity / 100;

        int i = 0;
        while (i < this.crosshairLines.length) {
            this.crosshairLines[i].setOpacity(this.opacity);
            i++;
        }
    }

    @Override
    public void setColor(Color newColor) {
        for (Rectangle line : this.crosshairLines) {
            line.setFill(newColor);
        }

        this.color = newColor;
    }

    @Override
    public void setX(double x) {
        for (Rectangle line : this.crosshairLines) {
            line.setX(x);
        }

        this.x = x;
    }

    @Override
    public void setY(double y) {
        for (Rectangle line : this.crosshairLines) {
            line.setY(y);
        }

        this.y = y;
    }

    @Override
    public void setWidth(double width) {
        for (Rectangle line : this.crosshairLines) {
            line.setWidth(width);
        }

        this.thickness = width;
    }

    @Override
    public void setHollow(boolean isHollow) {

    }

    @Override
    public void setStrokeColor(Color newColor) {
        for (Rectangle line : crosshairLines) {
            line.setStroke(newColor);
        }
    }

    @Override
    public void setStrokeSize(double newWidth) {
        for (Rectangle line : crosshairLines) {
            line.setStrokeWidth(newWidth);
        }
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        crosshairGroup.setVisible(isDisabled);
    }

    @Override
    public void setCornerArc(double arc) {
        for (Rectangle r : crosshairLines) {
            r.setArcWidth(arc);
            r.setArcHeight(arc);
        }
    }
}
