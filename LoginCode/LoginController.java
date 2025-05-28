package com.lib.Controller;

import com.lib.Buttons.LoginButton;
import  com.lib.Function.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;


public class LoginController {

    @FXML private ImageView backgroundView;
    @FXML private ImageView iconV;
    @FXML private ImageView loginIcon;
    @FXML private Label logoLabel;
    @FXML private TextField Nfield;
    @FXML private PasswordField Pfield;
    @FXML private Button loginButton;

    Function fun = new Function();
    LoginButton login_Button=new LoginButton();

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> {
            login_Button.login_button(Nfield,Pfield);
        });


        Nfield.setOnAction(e -> Pfield.requestFocus());
        Pfield.setOnAction(e -> loginButton.fire());


    }
}

