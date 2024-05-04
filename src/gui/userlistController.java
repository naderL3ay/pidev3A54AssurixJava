/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.UserService;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author MEGA PC
 */
public class userlistController implements Initializable {

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> colFirstname;
    @FXML
    private TableColumn<User, String> colLastname;
    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colPhone;
    
    @FXML
    private ImageView userImg;
             @FXML
    private TextField filterField;
     UserService sp = new UserService();
     User user = new User();
     ObservableList<User> list = FXCollections.observableArrayList();
    @FXML
    private Label txtUsername;
    @FXML
    private Button logout;
    @FXML
    private TableColumn<User, User> colStatut;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
      user = sp.DisplayById(Session.current_user.getId());
        
         try {
                  InputStream stream = new FileInputStream("C:\\xampp\\htdocs\\pictures\\imagesuser/");
                   Image image = new Image(stream);
             
                    userImg.setImage(image);

              } catch (FileNotFoundException ex) {
                  System.out.println(ex.getMessage());
              }
         table.setItems(refresh());
          
    
        searsh();
        
    }  
     
    public  ObservableList<User> refresh()
    {

        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
         list = sp.getAll();
        return list;
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
 private void delete(ActionEvent event) {

 
sp.supprimer(table.getSelectionModel().getSelectedItem().getId());
               
               
               Image img = new Image(getClass().getResourceAsStream("images/ok.png"));
                Notifications notification = pushNotify("Succès", "L'utilisateur a été supprimé");
                notification.graphic(new ImageView(img));
                notification.show();
    
       table.setItems(refresh());
        
 searsh();
 }
      

 
  @FXML
  private void edit(ActionEvent event){
       try {
                Parent page = FXMLLoader.load(getClass().getResource("FXMLprofil.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } 
     
}
public void searsh(){
            FilteredList<User> filteredData = new FilteredList<>(refresh(), b -> true);
		
		
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(user -> {
				
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (user.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; 
				} else if (user.getFirstname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}else if (user.getLastname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (String.valueOf(user.getPhone()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; 
			});
		});
		
		SortedList<User> sortedData = new SortedList<>(filteredData);
		
		
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		
		
		table.setItems(sortedData);
       }
       

 public Notifications pushNotify(String title, String text) {
        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(7))
                .position(Pos.TOP_RIGHT)
                .onAction((ActionEvent e) -> {
                    System.out.println("clicked on notification");
                });
        return notification;
    }

    @FXML
    private void isBlock(ActionEvent event) {
        sp.changerBlockStatus(table.getSelectionModel().getSelectedItem().getId());
               
               
               Image img = new Image(getClass().getResourceAsStream("images/ok.png"));
                Notifications notification = pushNotify("Succès", "Le statut d'utilisateur a été changer");
                notification.graphic(new ImageView(img));
                notification.show();
    
       table.setItems(refresh());
        
 searsh();
        System.out.println("buuton click ");
    }

    
          

}
