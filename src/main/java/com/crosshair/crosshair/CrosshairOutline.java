package com.crosshair.crosshair;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class CrosshairOutline implements Outline {
    private Crosshair outline; //The outline is basically a larger copy of the crosshair

    //These will be useful to know where to place the outline according to the crosshair's position and properties
    private double crosshairThickness;
    private double crosshairSize;
    private double crosshairY;

    private double outlineBorderThickness;
    private double outlineSize;

    private boolean isEnabled;

    CrosshairOutline(Crosshair crosshair) {
        isEnabled = false;

        outline = crosshair;
        crosshairThickness = crosshair.getThickness();
        crosshairSize = crosshair.getSize();
        crosshairY = crosshair.getY();

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
        double thicknessIncrease = newThickness * 2; //Because it needs once the amount on each side
        double sizeIncrease = newThickness * 2; //Because it needs to increase its size on both sides

        outline.setSize(crosshairSize + sizeIncrease);
        outline.setThickness(crosshairThickness + thicknessIncrease);

        outline.setY(crosshairY - newThickness);

        this.outlineBorderThickness = newThickness;
    }

    @Override
    public void alignOutlineThickness(double thickness) {
        this.crosshairThickness = thickness;
        outline.setThickness(thickness + (outlineBorderThickness * 2));
    }

    @Override
    public void alignOutlineSize(double size) {
        this.crosshairSize = size;

        outline.setSize(crosshairSize + (outlineBorderThickness * 2));

    }

    @Override
    public void alignOutlineYCoordinate(double y) {
        crosshairY = Constants.crosshairCenterY + y;
        outline.setGap(y - (this.getOutlineBorderThickness() / 2));
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
        return outline.getThickness() - crosshairThickness;
    }

    public void setOutlineBorderThickness () {
        outlineBorderThickness = getOutlineBorderThickness();
    }

    @Override
    public Group createOutlineGroup() {
        return outline.createCrosshairGroup();
    }

    @Override
    public void disableOutline(boolean isDisabled) {
        outline.setDisabled(isDisabled);
        this.isEnabled = isDisabled;
    }

    @Override
    public void setCornerArc(double arc) {
        outline.setCornerArc(arc);
    }

    @Override
    public boolean getStatus() {
        return isEnabled;
    }
}