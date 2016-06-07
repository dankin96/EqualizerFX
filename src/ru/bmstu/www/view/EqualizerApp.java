package ru.bmstu.www.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EqualizerApp extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
  
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
 (getClass().getResource("cssMetro.css").toExternalForm());
        stage.setScene(scene);
        stage.setWidth(1110);
        stage.setHeight(580);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
