package com.crosshair.crosshair;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public interface Outline {

    double DEFAULT_THICKNESS = 1.0;
    double DEFAULT_OPACITY = 100.0;
    Color DEFAULT_COLOR = Color.BLACK;

    void setThickness(double newThickness);
    void setOpacity(double newOpacity);
    void setColor(Color newColor);
    void disableOutline(boolean isDisabled);
    void alignOutlineThickness(double thickness);
    void alignOutlineSize(double size);
    void alignOutlineYCoordinate(double y);
    void setHeight(double newHeight);
    void setCornerArc(double arc);

    boolean getStatus();
    double getOutlineBorderThickness();

    Group createOutlineGroup();

}
