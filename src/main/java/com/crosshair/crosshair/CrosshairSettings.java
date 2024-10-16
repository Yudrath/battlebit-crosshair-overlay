package com.crosshair.crosshair;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.function.Consumer;

public class CrosshairSettings extends SettingsMethods {

    private static final SettingParameter SIZE = new SettingParameter("Size", 0, 400, (int)PlusCrosshair.DEFAULT_SIZE, CROSSHAIR::setSize, CROSSHAIR_OUTLINE::alignOutlineSize);
    private static final SettingParameter THICKNESS = new SettingParameter("Thickness", 0, 15, (int)Crosshair.DEFAULT_THICKNESS, CROSSHAIR::setThickness, CROSSHAIR_OUTLINE::alignOutlineThickness);
    private static final SettingParameter GAP = new SettingParameter("Gap", 0, 50, (int)Crosshair.DEFAULT_GAP, CROSSHAIR::setGap, CROSSHAIR_OUTLINE::alignOutlineYCoordinate);
    private static final SettingParameter OPACITY = new SettingParameter("Opacity", 0, 100, 100, CROSSHAIR::setOpacity, CROSSHAIR_OUTLINE::setOpacity, DOT::setOpacity, DOT_OUTLINE::setOpacity);
    private static final SettingParameter ARC = new SettingParameter("Curve", 0, 150, 0, CROSSHAIR::setCornerArc, CROSSHAIR_OUTLINE::setCornerArc, DOT::setCornerArc, DOT_OUTLINE::setCornerArc);

    private CrosshairSettings() {};

    public static VBox createAllCrosshairSettings() {
        VBox outerContainer = new VBox();
        outerContainer.setPadding(elementsPadding);
        outerContainer.setSpacing(5);

        Label sectionLabel = new Label("Crosshair");
        sectionLabel.setFont(new Font(16));

        VBox sizeVBox = createCrosshairSetting(SIZE);
        VBox thicknessVBox = createCrosshairSetting(THICKNESS);
        VBox gapVBox = createCrosshairSetting(GAP);
        VBox opacityVBox = createCrosshairSetting(OPACITY);
        VBox arcVBox = createCrosshairSetting(ARC);

        ColorPicker colorPickerCrosshair = createColorPicker(CROSSHAIR::setColor);

        outerContainer.getChildren().addAll(sectionLabel, sizeVBox, thicknessVBox, gapVBox, opacityVBox, arcVBox, colorPickerCrosshair);

        return outerContainer;
    }
}
