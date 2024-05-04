/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class userHomeController implements Initializable {

    @FXML
    private ImageView userImg;
    @FXML
    private Label txtUsername;
    @FXML
    private TextField filterField;
    @FXML
    private Button logout;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
  private void edit(ActionEvent event){
       try {
                Parent page = FXMLLoader.load(getClass().getResource("FXMLhome.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } 
     
}

    @FXML
 private void logout(ActionEvent event) {
     Session.current_user = null;
                 try {
                Parent page = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
     
 }

    
    @FXML
 private void gohome(MouseEvent event) {
                 try {
                Parent page = FXMLLoader.load(getClass().getResource("FXMLuserlist.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
     
 }    
    
}
