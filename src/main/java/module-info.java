module com.labs.cg4thlabwork {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.labs.cg4thlabwork to javafx.fxml;
    exports com.labs.cg4thlabwork;
}