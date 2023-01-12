module com.example.checkers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;


    opens com.example.checkers.client to javafx.fxml, javafx.graphics;
    exports com.example.checkers.game;
    opens com.example.checkers.game to javafx.fxml;
}