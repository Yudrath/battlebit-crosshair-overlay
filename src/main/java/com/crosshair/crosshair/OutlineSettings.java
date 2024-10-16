package com.crosshair.crosshair;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class OutlineSettings extends SettingsMethods {

    private static List<Control> outlineControls = new ArrayList<>();

    private static final SettingParameter OUTLINE_THICKNESS = new SettingParameter("Thickness (outline)", 0, 10, (int)Outline.DEFAULT_THICKNESS, CROSSHAIR_OUTLINE::setThickness, DOT_OUTLINE::setThickness);

    public static VBox createOutlineSettings() {
        VBox outerVbox = new VBox();
        outerVbox.setPadding(elementsPadding);
        outerVbox.setSpacing(5);

        VBox outlineThicknessVbox = createCrosshairSetting(OUTLINE_THICKNESS);
        ColorPicker outlineColorPicker = createColorPicker(CROSSHAIR_OUTLINE::setColor, DOT_OUTLINE::setColor);

        addControlsToList(new VBox(outlineThicknessVbox, outlineColorPicker), outlineControls);
        setEnabledControls(false, outlineControls, CROSSHAIR_OUTLINE::disableOutline, DOT_OUTLINE::disableOutline); //Add a third argument with the dot's disableOutline method

        VBox outlineEnablerVbox = createOnOffCheckbox("Outline", 16);
        addOutlineOnOffCheckboxEventHandler(outlineEnablerVbox, outlineControls);

        outerVbox.getChildren().addAll(outlineEnablerVbox, outlineThicknessVbox, outlineColorPicker);

        return outerVbox;
    }
}
