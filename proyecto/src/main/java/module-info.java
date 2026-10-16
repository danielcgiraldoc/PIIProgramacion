open module proyecto {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires static org.junit.jupiter.api;

    exports org.example to javafx.graphics;
}
