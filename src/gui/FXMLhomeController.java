/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.User;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.UserService;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class FXMLhomeController implements Initializable {

    @FXML
    private ImageView userImg;
    @FXML
    private Label txtUsername;
    @FXML
    private Button logout;
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txt_tel;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnSave;
      UserService udao = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDis();
      User user = new User();
      user = udao.DisplayById(Session.current_user.getId());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d"); 
       txtFirstname.setText(user.getFirstname());
       txtLastname.setText(user.getLastname());
       txtEmail.setText(user.getEmail());
       txt_tel.setText(user.getPhone());
       
    }    

    @FXML
    
    private void gohome(MouseEvent event) {
                 try {
                Parent page = FXMLLoader.load(getClass().getResource("FXMLuserHome.fxml"));
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
    private void save(ActionEvent event)  {
   

     
     if("Clicker ici".equals(btnSave.getText())){
              btnSave.setText("Modifier");
        txtFirstname.setDisable(false);
  txtLastname.setDisable(false);
txtEmail.setDisable(false);
txt_tel.setDisable(false);

     }
     else
     {
         if (!txtEmail.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            
                //lblStatus.setText("Enter valid Email");
                txtEmail.setStyle("-fx-border-clor: red; -fx-text-box-border: red ; -fx-focus-color: red ; -fx-text-fill: red; -fx-font-size: 16px;");

            } else if (!txt_tel.getText().matches("[0-9]+")) {
            
                //lblStatus.setText("Enter valid phone number");
                txt_tel.setStyle("-fx-border-clor: red; -fx-text-box-border: red ; -fx-focus-color: red ; -fx-text-fill: red; -fx-font-size: 16px;");

            } else { 
             User u = new User();
            u.setFirstname(txtFirstname.getText());
            u.setLastname(txtLastname.getText());
            u.setEmail(txtEmail.getText());
            u.setPhone(txt_tel.getText());
            udao.modifier(u,Session.current_user.getId());
setDis();
            
            
            }
        
         
         
         
     }
 }
    
    public void setDis(){
 btnSave.setText("Clicker ici");
txtFirstname.setDisable(true);
txtLastname.setDisable(true);
txtEmail.setDisable(true);
txt_tel.setDisable(true);
}
    
}
