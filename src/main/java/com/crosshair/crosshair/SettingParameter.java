package com.crosshair.crosshair;

import java.util.function.Consumer;

public class SettingParameter {
    private final String labelText;
    private final int minValue;
    private final int maxValue;
    private final int defaultValue;

    private final Consumer<Double> firstMethod;
    private final Consumer<Double> secondMethod;
    private final Consumer<Double> thirdMethod;
    private final Consumer<Double> fourthMethod;

    public SettingParameter(String labelText, int minValue, int maxValue, int defaultValue,
                            Consumer<Double> firstMethod, Consumer<Double> secondMethod, Consumer<Double> thirdMethod, Consumer<Double> fourthMethod) {
        this.labelText = labelText;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
        this.firstMethod = firstMethod;
        this.secondMethod = secondMethod;
        this.thirdMethod = thirdMethod;
        this.fourthMethod = fourthMethod;
    }

    public SettingParameter(String labelText, int minValue, int maxValue, int defaultValue,
                            Consumer<Double> firstMethod, Consumer<Double> secondMethod, Consumer<Double> thirdMethod) {
        this.labelText = labelText;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
        this.firstMethod = firstMethod;
        this.secondMethod = secondMethod;
        this.thirdMethod = thirdMethod;
        this.fourthMethod = null;
    }

    public SettingParameter(String labelText, int minValue, int maxValue, int defaultValue, Consumer<Double> firstMethod) {
        this.labelText = labelText;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
        this.firstMethod = firstMethod;
        this.secondMethod = null;
        this.thirdMethod = null;
        this.fourthMethod = null;
    }

    public SettingParameter(String labelText, int minValue, int maxValue, int defaultValue, Consumer<Double> firstMethod, Consumer<Double> secondMethod) {
        this.labelText = labelText;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
        this.firstMethod = firstMethod;
        this.secondMethod = secondMethod;
        this.thirdMethod = null;
        this.fourthMethod = null;
    }

    public String getLabelText() {
        return labelText;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public Consumer<Double> getFirstMethod() {
        return firstMethod;
    }

    public Consumer<Double> getSecondMethod() {
        return secondMethod;
    }

    public Consumer<Double> getThirdMethod() {
        return thirdMethod;
    }

    public Consumer<Double> getFourthMethod() {
        return fourthMethod;
    }
}
