module com.example.checkers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;


    opens com.example.checkers.client to javafx.fxml, javafx.graphics;
    opens com.example.checkers.client.controllers to javafx.fxml, javafx.graphics;
    opens com.example.checkers.game to javafx.fxml;
}