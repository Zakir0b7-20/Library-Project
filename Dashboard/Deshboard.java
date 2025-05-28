package com.lib.library;

import com.lib.Controller.DeshboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import com.lib.Function.*;

public class Deshboard extends Application {
    DeshboardController d=new DeshboardController();
    @Override

    public void start(Stage stage) throws IOException {
        Function fun=new Function();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lib/library/Deshboard - Copy.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        String css = getClass().getResource("/com/lib/library/CSS/Dashboard.css").toExternalForm();


        scene.getStylesheets().add(css);

        stage.setTitle("Dashboard");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
