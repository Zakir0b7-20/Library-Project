package com.lib.Buttons;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.lib.Function.*;
import com.lib.library.*;

import com.lib.Function.Function;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class LoginButton {
    LoginPage loginpage=new LoginPage();
    Function fun=new Function();


    public void login_button(TextField Nfield,PasswordField Pfield){
          String newname = null;
    if (Nfield.getText().isEmpty() || Pfield.getText().isEmpty()) {
    Alert alert = fun.alertW("Warning Dialog",
    "Please fill in all fields before submitting.");
    
}

      else{

           try {
               
              
                       ConnectionTest c=new ConnectionTest();
                       Connection con=c.getConnection();
                       String query = "SELECT username FROM admin WHERE username = ? and password=?";

                       PreparedStatement pst = con.prepareStatement(query);

                       pst.setString(1, Nfield.getText());
                       pst.setString(2, Pfield.getText());

                       ResultSet rs = pst.executeQuery();
                       
                       if (rs.next()) {
   
                          newname = rs.getString("username"); 
                          
                       }
                       
                       con.close();

    
               } catch (Exception e) {
                Alert alert=fun.alertW("Warring!", e.getMessage());
        
    }
           if(newname==null)
           {
                     Alert alert = fun.alertW("Warning! user or password wrong",
             "User Not Found");
                  Nfield.clear();
                  Pfield.clear();
                 Nfield.requestFocus();
                  
           
           
           }
         
           else
             {
                 if (newname.equals(Nfield.getText())) {
                     try {
                         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lib/library/Deshboard - Copy.fxml"));

                         String css = getClass().getResource("/com/lib/library/CSS/Dashboard.css").toExternalForm();

                         Parent root = loader.load();
                         root.getStylesheets().add(css);

                         Stage newsStage = new Stage();
                         newsStage.setScene(new Scene(root));
                         newsStage.show();

                         Stage currentStage = (Stage) Nfield.getScene().getWindow();
                         currentStage.close();
                     } catch (IOException e) {
                         e.printStackTrace();

                     }
                 }
               else{
                        JOptionPane.showMessageDialog(null, "invalid user name or password");
                         Nfield.clear();
                         Pfield.clear();
           
                        }
                          
            
              }
      }
    }
}
