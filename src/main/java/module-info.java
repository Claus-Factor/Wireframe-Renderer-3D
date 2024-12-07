module com.labs.cg4thlabwork {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.labs.cg4thlabwork to javafx.fxml;
    exports com.labs.cg4thlabwork;
    exports com.labs.cg4thlabwork.core;
    opens com.labs.cg4thlabwork.core to javafx.fxml;
    exports com.labs.cg4thlabwork.render;
    opens com.labs.cg4thlabwork.render to javafx.fxml;
}