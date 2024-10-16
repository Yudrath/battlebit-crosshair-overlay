module com.crosshair.crosshair {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires jna.platform;
    requires javafx.swing;
    requires com.sun.jna;

    opens com.crosshair.crosshair to javafx.fxml;
    exports com.crosshair.crosshair;
}