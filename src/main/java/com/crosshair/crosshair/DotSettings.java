package com.crosshair.crosshair;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class DotSettings extends SettingsMethods {

    private static List<Control> dotControls = new ArrayList<Control>();

    private static final SettingParameter SIZE = new SettingParameter("Size", 0, 10, 1, DOT::setSize, DOT_OUTLINE::alignOutlineSize);

    public static VBox createDotSettings() {
        VBox outerContainer = new VBox();
        outerContainer.setPadding(elementsPadding);
        outerContainer.setSpacing(5);

        VBox sizeVbox = createCrosshairSetting(SIZE);
        ColorPicker dotColorPicker = createColorPicker(DOT::setColor);

        addControlsToList(new VBox(sizeVbox, dotColorPicker), dotControls);
        setEnabledControls(false, dotControls, DOT::setEnabledDot);

        VBox dotEnablerVbox = createOnOffCheckbox("Dot", 16);
        addDotOnOffCheckboxEventHandler(dotEnablerVbox, dotControls, DOT::setEnabledDot);

        outerContainer.getChildren().addAll(dotEnablerVbox, sizeVbox, dotColorPicker);

        return outerContainer;
    }
}
