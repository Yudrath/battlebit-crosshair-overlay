package com.crosshair.crosshair;

import com.sun.jna.Native;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;


import com.sun.jna.platform.win32.WinUser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class CrosshairOverlay extends Application {

    private static final KeyCombination exitOverlayMode = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);

    static Crosshair activeCrosshair;
    static Outline activeCrosshairOutline;
    static CrosshairDot activeDot;
    static Outline activeDotOutline;

    static JFrame frameCrosshair;
    static JFrame frameSettings;

    private static void initAndShowGUI() {
        frameCrosshair = new JFrame("Crosshair");
        frameCrosshair.setUndecorated(true);
        frameCrosshair.setBackground(new java.awt.Color(0, 0, 0, 0));
        final JFXPanel fxPanelCrosshair = new JFXPanel();
        frameCrosshair.add(fxPanelCrosshair);
        frameCrosshair.setSize((int)Constants.crosshairSceneWidth, (int)Constants.crosshairSceneHeight);
        frameCrosshair.setLocationRelativeTo(null);
        frameCrosshair.setVisible(true);
        frameCrosshair.setResizable(false);
        frameCrosshair.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frameSettings = new JFrame("Settings");
        final JFXPanel fxPanelSettings = new JFXPanel();
        frameSettings.add(fxPanelSettings);
        frameSettings.setSize((int)Constants.settingsSceneWidth, (int)Constants.settingsSceneHeight);
        frameSettings.setVisible(true);
        frameSettings.setResizable(false);
        frameSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fxPanelSettings.setLocation(10, 10);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanelCrosshair, fxPanelSettings);
            }
        });

        setTransparent(frameCrosshair);
    }

    private static void initFX(JFXPanel fxPanelCrosshair, JFXPanel fxPanelSettings) {
        Crosshair plusCrosshair = new PlusCrosshair(PlusCrosshair.Type.DEFAULT);
        activeCrosshair = plusCrosshair;
        Group crosshairRoot = activeCrosshair.createCrosshairGroup();

        CrosshairOutline crosshairOutline = new CrosshairOutline(new PlusCrosshair (PlusCrosshair.Type.DEFAULT));
        activeCrosshairOutline = crosshairOutline;
        Group outlineRoot = activeCrosshairOutline.createOutlineGroup();

        activeDot = new CrosshairDot();
        Group dotRoot = activeDot.createDotGroup();

        activeDotOutline = new DotOutline(new CrosshairDot());
        Group dotOutlineRoot = activeDotOutline.createOutlineGroup();

        Group crosshairOutlineAndDot = new Group(outlineRoot, crosshairRoot, dotOutlineRoot, dotRoot);

        Scene crosshairScene = new Scene(crosshairOutlineAndDot, Constants.crosshairSceneWidth, Constants.crosshairSceneHeight);
        crosshairScene.setFill(Color.TRANSPARENT);

        BorderPane menuLayout = new BorderPane();
        BorderPane settingsLayout = new BorderPane();

        menuLayout.setCenter(settingsLayout);
        menuLayout.setPadding(new Insets(10, 0, 10, 0));

        Scene settingsScene = new Scene(menuLayout, Constants.settingsSceneWidth, Constants.settingsSceneHeight);

        HBox allSettingPanes = new HBox();
        allSettingPanes.setSpacing(10);
        allSettingPanes.setAlignment(Pos.CENTER);

        VBox crosshairSettings = CrosshairSettings.createAllCrosshairSettings();
        VBox dotSettings = DotSettings.createDotSettings();
        VBox outlineSettings = OutlineSettings.createOutlineSettings();

        allSettingPanes.getChildren().addAll(crosshairSettings, dotSettings, outlineSettings);

        settingsLayout.setCenter(allSettingPanes);

        HBox buttonsHbox = new HBox();
        buttonsHbox.setPadding(new Insets(0, 0, 5, 20));
        buttonsHbox.setSpacing(10);

        menuLayout.setTop(buttonsHbox);
        Button launchButton = new Button("Launch overlay");
        buttonsHbox.getChildren().add(launchButton);

        launchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                frameCrosshair.setAlwaysOnTop(true);
                frameSettings.setVisible(false);
            }
        });

        Label quitOverlayModeLabel = new Label("Press Ctrl+Q to exit overlay mode");
        buttonsHbox.getChildren().add(quitOverlayModeLabel);

        Button quitButton = new Button("Quit app");

        buttonsHbox.getChildren().add(quitButton);

        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        crosshairScene.getAccelerators().put(exitOverlayMode,
                new Runnable() {
                    @Override
                    public void run() {
                        frameCrosshair.setAlwaysOnTop(false);
                        frameSettings.setVisible(true);
                    }
                });

        fxPanelCrosshair.setScene(crosshairScene);
        fxPanelSettings.setScene(settingsScene);
    }

    private static void setTransparent(Component w) {
        WinDef.HWND hwnd = getHWnd(w);
        int wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
        wl = wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT;
        User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl);
    }

    private static WinDef.HWND getHWnd(Component w) {
        WinDef.HWND hwnd = new WinDef.HWND();
        hwnd.setPointer(Native.getComponentPointer(w));
        return hwnd;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
}