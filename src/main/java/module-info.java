module com.example.checkers {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.checkers to javafx.fxml;
    exports com.example.checkers;
}