package com.mycompany.ligafutbol;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 * Puedes reutilizar esta clase como clase principal de cualquier aplicación JavaFX.
 * Solo recuerda cambiar el nombre de la vista inicial (en el método start())
 */
public class App extends Application {


    private static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("vistaMain.fxml"));
        scene = new Scene(root, 640, 430);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();   
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}