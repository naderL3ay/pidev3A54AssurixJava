/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Service.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Entities.Admin;

/**
 * FXML Controller class
 *
 * @author medmo
 */
public class GuiLoginController implements Initializable {
    public static Admin user = new Admin();

    @FXML
    private ImageView btnReturn;
    @FXML
    private AnchorPane bord;
    @FXML
    private Button btnLogin;
    @FXML
    private Label erreur;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField emailInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnLogin.setOnMouseEntered(event -> {
            btnLogin.setEffect(new DropShadow());
        });

        // Remove drop shadow effect when mouse exits the button
        btnLogin.setOnMouseExited(event -> {
            btnLogin.setEffect(null);
        });
    }

    @FXML
    private void returnTo(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AdminDashboard.fxml"));
        try {
            Parent root = loader.load();
            bord.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void login(ActionEvent event) throws SQLException {
//        ServiceAdmin su = new ServiceAdmin();

  //      String email = emailInput.getText();
    //    String password = passwordInput.getText();

      //  user = su.Login(email, password);
        //if (user == null){
          //  erreur.setText("Email ou mot de passe incorrecte");
        //}
       // else{  System.out.println("connected");
            RouterController Router=new RouterController();
            Router.navigate("../fxml/AdminDashboard.fxml");

        //}
    }




    public void goTo(String view){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view+".fxml"));
        try {
            Parent root = loader.load();
            System.out.println(bord);
            bord.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void goToClientDash(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/ClientDashboard.fxml");
    }
}
