package com.crosshair.crosshair;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class DotOutline implements Outline {
    private CrosshairDot outline;

    private double dotSize;

    private double outlineBorderThickness;
    private double outlineSize;

    private boolean isEnabled;

    DotOutline(CrosshairDot dot) {
        isEnabled = false;

        outline = dot;
        dotSize = dot.getSize();

        setupOutline();

        outlineBorderThickness = getOutlineBorderThickness();
        outlineSize = outline.getSize();
    }

    private void setupOutline() {
        setColor(DEFAULT_COLOR);
        setThickness(DEFAULT_THICKNESS);
        setOpacity(DEFAULT_OPACITY);
    }

    @Override
    public void setThickness(double newThickness) {
        double sizeIncrease = newThickness * 2;

        outline.setSize(dotSize + sizeIncrease);

        this.outlineBorderThickness = newThickness;
    }

    @Override
    public void alignOutlineThickness(double thickness) {
        dotSize = thickness;
        outline.setSize(thickness + (outlineBorderThickness * 2));
    }

    @Override
    public void alignOutlineSize(double size) {
        alignOutlineThickness(size);
    }

    @Override
    public void alignOutlineYCoordinate(double y) {

    }

    @Override
    public void setHeight(double newHeight) {
        outline.setSize(newHeight);
    }

    @Override
    public void setColor(Color newColor) {
        outline.setColor(newColor);
    }

    @Override
    public void setOpacity(double newOpacity) {
        outline.setOpacity(newOpacity);
    }

    @Override
    public double getOutlineBorderThickness() {
        return outline.getSize() - dotSize;
    }

    public void setOutlineBorderThickness () {
        outlineBorderThickness = getOutlineBorderThickness();
    }

    @Override
    public Group createOutlineGroup() {
        return outline.createDotGroup();
    }

    @Override
    public void disableOutline(boolean isDisabled) {
        outline.setEnabledDot(isDisabled);
        this.isEnabled = isDisabled;
    }

    @Override
    public boolean getStatus() {
        return isEnabled;
    }

    @Override
    public void setCornerArc(double arc) {
        outline.setCornerArc(arc);
    }
}
