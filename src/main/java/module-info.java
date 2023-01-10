module com.example.checkers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;


    opens com.example.checkers to javafx.fxml;
    exports com.example.checkers;
    exports com.example.checkers.game;
    opens com.example.checkers.game to javafx.fxml;
}