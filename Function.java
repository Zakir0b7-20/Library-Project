package com.lib.Function;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.stage.FileChooser;

import java.io.File;

import static javafx.geometry.Pos.BOTTOM_CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;


public class Function {

    public  Label label(String name) {


        Label label = new Label(name);
        label.setAlignment(BOTTOM_CENTER);

        label.getStyleClass().add("lableformember");
        return label;
    }


    public Image image(String path){
    Image backgroundImage = new Image(path);
        return backgroundImage;
    }
    public ImageView imagev(String path){
       Image icon = new Image(path);
        ImageView imageView = new ImageView(icon);
        return imageView;
    }


    public Button button(String name){


        Button button = new Button(name);

        button.getStyleClass().add("button");

        button.setContentDisplay(ContentDisplay.LEFT);
        return button;
    }
    public void Responsive(Scene scene,  TabPane tabpane ){
        ChangeListener<Number> resizeListener = (obs, oldVal, newVal) -> {
            tabpane.setPrefWidth(scene.getWidth());
            tabpane.setPrefHeight(scene.getHeight());
        };

        scene.widthProperty().addListener(resizeListener);
        scene.heightProperty().addListener(resizeListener);
    }




    private File selectedImageFile;
    private ImageView imageView;

    public void FileChooser(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        selectedImageFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedImageFile != null) {
            System.out.println("Selected file: " + selectedImageFile.getAbsolutePath());

            Image image = new Image(selectedImageFile.toURI().toString());
            imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(300);

        }
    }

    public File getSelectedImageFile() {
        return selectedImageFile;
    }

    public ImageView getImageView() {
        return imageView;
    }
    Alert alert = new Alert(Alert.AlertType.WARNING);
    public Alert alertW(String HeaderText,String ContentText){

    alert.setTitle("Warning Dialog");
    alert.setHeaderText(HeaderText);
    alert.setContentText(ContentText);
    alert.showAndWait();
    return alert;
    }
}