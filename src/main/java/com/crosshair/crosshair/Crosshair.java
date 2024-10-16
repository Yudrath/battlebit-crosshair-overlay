package com.crosshair.crosshair;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public interface Crosshair {
    double DEFAULT_SIZE = 15.0;
    double DEFAULT_THICKNESS = 5.0;
    double DEFAULT_GAP = 6.0;
    double DEFAULT_OPACITY = 1.0;
    Color DEFAULT_COLOR = Color.GREEN;

    double getSize();
    double getThickness();
    double getGap();
    double getOpacity();
    Color getColor();
    double getY();
    boolean getHollowStatus();

    void setSize(double newSize);
    void setThickness(double newThickness);
    void setGap(double newGap);
    void setOpacity(double newOpacity);
    void setColor(Color newColor);
    void setX(double x);
    void setY(double y);
    void setWidth(double width);
    void setHollow(boolean isHollow);
    void setStrokeColor(Color newColor);
    void setStrokeSize(double size);
    void setDisabled(boolean isDisabled);
    void setCornerArc(double arc);

    Group createCrosshairGroup();
}
