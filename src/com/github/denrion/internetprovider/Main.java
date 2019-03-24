package com.github.denrion.internetprovider;

import com.github.denrion.internetprovider.utils.ContractsFilter;
import com.github.denrion.internetprovider.controllers.ContractController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
        Parent root = loader.load();

        ContractController controller = loader.getController();
        controller.disableButtons();
        controller.listContracts(ContractsFilter.NONE, "");

        primaryStage.setTitle("Internet Provider Manager");
        primaryStage.setScene(new Scene(root, 1400, 800));
        primaryStage.show();
    }

    @Override
    public void init() {
        try {
            Datasource.getInstance().connect();
            System.out.println("Connection with database established");
        } catch (SQLException e) {
            System.out.printf("Could not connect to database! Reason: %s", e.getMessage());
            Platform.exit();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            Datasource.getInstance().disconnect();
        } catch (SQLException e) {
            System.out.printf("Could not connect to database! Reason: %s", e.getMessage());
            Platform.exit();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
