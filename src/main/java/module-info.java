module com.example.cgalab1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires ejml.simple;
    requires net.mikera.vectorz;
    requires java.desktop;


    opens com.example.cgalab1 to javafx.fxml;
    exports com.example.cgalab1;
}