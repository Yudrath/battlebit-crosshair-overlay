package com.crosshair.crosshair;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.awt.*;

public final class Constants {

    static Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
    static double scrnSizeWidth = scrnSize.getWidth();
    static double scrnSizeHeight = scrnSize.getHeight();

    public static final double crosshairSceneWidth = scrnSizeWidth;
    public static final double crosshairSceneHeight = scrnSizeHeight;
    public static final double crosshairSceneWidthCenter = crosshairSceneWidth / 2;
    public static final double crosshairSceneHeightCenter = crosshairSceneHeight / 2;

    //Used for the initialization of the crosshair
    //This ensures one half of the line is on the left side of the center and the other on the right
    //In other words, the line's center is right at the center's y-coordinate
    public static final double crosshairCenterX = crosshairSceneWidthCenter - (Crosshair.DEFAULT_THICKNESS / 2);
    public static final double crosshairCenterY = crosshairSceneHeightCenter;

    public static final double settingsSceneWidth = 850;
    public static final double settingsSceneHeight = 400;

    private Constants() {}
}
