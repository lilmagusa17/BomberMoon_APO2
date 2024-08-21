module com.bomberman.bomberman {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bomberman.bomberman to javafx.fxml;
    exports com.bomberman.bomberman;
    exports com.bomberman.bomberman.control;
    opens com.bomberman.bomberman.control to javafx.fxml;
}