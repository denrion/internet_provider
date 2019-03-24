module InternetProvider {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.github.denrion.internetprovider;
    opens com.github.denrion.internetprovider.controllers;
    opens com.github.denrion.internetprovider.fxml;
    opens com.github.denrion.internetprovider.entities;
}