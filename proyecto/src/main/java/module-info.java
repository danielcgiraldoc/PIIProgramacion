module proyecto {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    exports org.example to javafx.graphics;
    opens org.example.viewController to javafx.fxml;
}