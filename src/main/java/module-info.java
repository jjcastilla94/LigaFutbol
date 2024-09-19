module com.mycompany.ligafutbol {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.ligafutbol to javafx.fxml;
    exports com.mycompany.ligafutbol;
}
