package com.crosshair.crosshair;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class SettingsMethods {
    protected static final Crosshair CROSSHAIR = CrosshairOverlay.activeCrosshair;
    protected static final CrosshairDot DOT = CrosshairOverlay.activeDot;
    protected static final Outline CROSSHAIR_OUTLINE = CrosshairOverlay.activeCrosshairOutline;
    protected static final Outline DOT_OUTLINE = CrosshairOverlay.activeDotOutline;


    protected static final Insets elementsPadding = new Insets(0, 15, 0, 15);

    protected SettingsMethods() {};

    protected static void addControlsToList(Node node, List<Control> list) {
        if (node instanceof ColorPicker
                || node instanceof Slider
                || node instanceof TextField) {

            list.add((Control) node);

            return;
        }

        if (node instanceof Pane) {
            Pane pane = (Pane) node;

            int i = 0;

            while (i < pane.getChildren().size()) {
                addControlsToList(pane.getChildren().get(i), list);
                i++;
            }
        }
    }

    protected static VBox createOnOffCheckbox(String labelString, int fontSize) {
        VBox outerContainer = new VBox();

        Label label = new Label(labelString);
        label.setFont(new Font(fontSize));

        CheckBox onOffCheckbox = new CheckBox("Off");

        outerContainer.getChildren().add(label);
        outerContainer.getChildren().add(onOffCheckbox);

        return outerContainer;
    }

    protected static void addOutlineOnOffCheckboxEventHandler(VBox checkVbox, List<Control> controlList) {
        for (Node n : checkVbox.getChildren()) {
            if (n instanceof CheckBox) {

                CheckBox cb = (CheckBox) n;

                cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                        boolean dotIsEnabled = DOT.getStatus();

                        if (dotIsEnabled) {
                            setEnabledControls(newValue, controlList, CROSSHAIR_OUTLINE::disableOutline, DOT_OUTLINE::disableOutline);
                        } else {
                            setEnabledControls(newValue, controlList, CROSSHAIR_OUTLINE::disableOutline);
                        }

                        if (newValue) {
                            cb.setText("On");
                        } else {
                            cb.setText("Off");
                        }
                    }
                });
            }
        }
    }

    protected static void addDotOnOffCheckboxEventHandler(VBox checkVbox, List<Control> controlList, Consumer<Boolean> disableControlsMethodOne) {
        for (Node n : checkVbox.getChildren()) {
            if (n instanceof CheckBox) {

                CheckBox cb = (CheckBox) n;

                cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                        if (!newValue) {
                            DOT_OUTLINE.disableOutline(false);
                        }

                        if (CROSSHAIR_OUTLINE.getStatus() && newValue) {
                            DOT_OUTLINE.disableOutline(true);
                        }

                        setEnabledControls(newValue, controlList, disableControlsMethodOne);

                        if (newValue) {
                            cb.setText("On");
                        } else {
                            cb.setText("Off");
                        }
                    }
                });
            }
        }
    }

    protected static void addOnOffCheckboxEventHandler(VBox checkVbox, List<Control> controlList, Consumer<Boolean> disableControlsMethodOne) {
        for (Node n : checkVbox.getChildren()) {
            if (n instanceof CheckBox) {

                CheckBox cb = (CheckBox) n;

                cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                        setEnabledControls(newValue, controlList, disableControlsMethodOne);

                        if (newValue) {
                            cb.setText("On");
                        } else {
                            cb.setText("Off");
                        }
                    }
                });
            }
        }
    }

    protected static void setEnabledControls(boolean isEnabled, List<Control> controlsList, Consumer<Boolean> disableControlsMethodOne, Consumer<Boolean> disableControlsMethodTwo) {
        for (Control c : controlsList) {
            c.setDisable(!isEnabled);
        }

        disableControlsMethodOne.accept(isEnabled);
        if (disableControlsMethodTwo != null) {
            disableControlsMethodTwo.accept(isEnabled);
        }
    }

    protected static void setEnabledControls(boolean isEnabled, List<Control> controlsList, Consumer<Boolean> disableControlsMethodOne) {
        for (Control c : controlsList) {
            c.setDisable(!isEnabled);
        }

        disableControlsMethodOne.accept(isEnabled);
    }

    protected static VBox createCrosshairSetting(SettingParameter parameters) {

        VBox outerContainer = new VBox();

        HBox sliderAndTextHBox = createSliderTextFieldHBox();

        Label settingLabel = createLabel(parameters.getLabelText());

        Slider settingSlider = createSlider(parameters);

        TextField settingTextField = createTextField(parameters);
        addTextFormatter(settingTextField);

        addDependenciesAndListeners(settingSlider, settingTextField, parameters);

        sliderAndTextHBox.getChildren().add(settingSlider);
        sliderAndTextHBox.getChildren().add(settingTextField);

        outerContainer.getChildren().add(settingLabel);
        outerContainer.getChildren().add(sliderAndTextHBox);

        return outerContainer;
    }

    private static void addTextFormatter(TextField textField) {
        TextFormatter<String> textFormatter = new TextFormatter<>(createTextFieldFilter());
        textField.setTextFormatter(textFormatter);
    }

    private static HBox createSliderTextFieldHBox() {
        HBox hbox = new HBox();

        hbox.setSpacing(8);

        return hbox;
    }

    protected static ColorPicker createColorPicker(Consumer<Color> method) {
        ColorPicker colorPicker = new ColorPicker();

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color c = colorPicker.getValue();
                method.accept(c);
            }
        });

        return colorPicker;
    }

    protected static ColorPicker createColorPicker(Consumer<Color> firstMethod, Consumer<Color> secondMethod) {
        ColorPicker colorPicker = new ColorPicker();

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color c = colorPicker.getValue();
                firstMethod.accept(c);
                secondMethod.accept(c);
            }
        });

        return colorPicker;
    }

    private static Slider createSlider(SettingParameter parameters) {
        int minValue = parameters.getMinValue();
        int maxValue = parameters.getMaxValue();
        int defaultValue = parameters.getDefaultValue();

        Slider slider = new Slider(minValue, maxValue, defaultValue);

        slider.setMaxWidth(150);
        slider.setPrefWidth(150);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);

        return slider;
    }

    private static TextField createTextField(SettingParameter parameters) {
        int defaultValue = parameters.getDefaultValue();

        TextField textField = new TextField(String.valueOf(defaultValue));

        textField.setPrefWidth(50);

        return textField;
    }

    private static Label createLabel(String labelText) {
        Label label = new Label(labelText);

        return label;
    }

    private static UnaryOperator<TextFormatter.Change> createTextFieldFilter () {

        return change -> {
            String text = change.getText();

//            if (text.matches("^00$")) {
//                change.setText("0");
//                return change;
//            }

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
    }

    private static void addDependenciesAndListeners(Slider slider, TextField textField, SettingParameter parameters) {
        Consumer<Double> firstMethod = parameters.getFirstMethod();
        Consumer<Double> secondMethod = parameters.getSecondMethod();
        Consumer<Double> thirdMethod = parameters.getThirdMethod();
        Consumer<Double> fourthMethod = parameters.getFourthMethod();

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double sliderValue = slider.getValue();

                textField.setText(String.valueOf((int)sliderValue));
                firstMethod.accept(sliderValue);

                if (secondMethod != null) {
                    secondMethod.accept(sliderValue);
                }

                if (thirdMethod != null) {
                    thirdMethod.accept(sliderValue);
                }

                if (fourthMethod != null) {
                    fourthMethod.accept(sliderValue);
                }
            }
        });

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {

                double textFieldMaxValue = parameters.getMaxValue();

                if (newValue.isEmpty()) {
                    textField.setText(String.valueOf(parameters.getMinValue()));
                    slider.setValue(parameters.getMinValue());

                    return;
                }

                if (Double.parseDouble(newValue) > textFieldMaxValue) {
                    newValue = String.valueOf(parameters.getMaxValue());
                }

                if (newValue.equals("00")) {
                    textField.setText("0");

                    return;
                }

                textField.setText(newValue);
                slider.setValue(Double.parseDouble(newValue));

            }
        });
    }
}
