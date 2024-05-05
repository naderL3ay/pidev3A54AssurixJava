package Controllers;

import Entities.Demande;
import Entities.User;
import Service.ServiceDemande;
import Service.ServiceUser;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import testconnection.MainApp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ModifyDemande implements Initializable, InitializableController {

    public TextField nom_vic;
    private int Id;

    @FXML
    private TextField num_con;

    @FXML
    private TextField localisation;

    @FXML
    private ComboBox<String> etat_de;

    @FXML
    private ComboBox<User> user_id;

    @FXML
    private DatePicker date;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private ServiceDemande serviceDemande = new ServiceDemande();

    private static float latitude;
    private static float longitude;
    public void savecoords(String latitude, String longitude) throws IOException {

        ModifyDemande.latitude =Float.parseFloat(latitude);
        ModifyDemande.longitude =Float.parseFloat(longitude);
        String apiKey = "AIzaSyA2Rfnw4b0XfjAKIOTwKGcTKwngzOEfnE4";

        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + ModifyDemande.latitude + "," + ModifyDemande.longitude + "&key=" + apiKey;

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStreamReader in = new InputStreamReader(conn.getInputStream());

        JsonObject json = new JsonParser().parse(in).getAsJsonObject();
        String address = json.getAsJsonArray("results").get(0).getAsJsonObject().get("formatted_address").getAsString();

        System.out.println("Address: " + address);
      //  showSuccessMessage("Address recommendée:"+address);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Adresse");
        alert.setHeaderText("Adresse détécté");
        alert.setContentText("adresse recommendée"+address);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceUser su = new ServiceUser();
            userList.addAll(su.ReadAll());
            Demande demande = serviceDemande.findById(Id);

            if (demande != null) {
                int userId = demande.getUser_id();
                User selectedUser = su.findById(userId);
                user_id.setValue(selectedUser);
                num_con.setText(demande.getNum_constat());
                localisation.setText(demande.getLocalisation());
                etat_de.getSelectionModel().select(demande.getEtat_demande());
                ModifyDemande.longitude=demande.getLongitude();
                ModifyDemande.latitude=demande.getLatitude();
                java.util.Date date = demande.getDate_realisation();
                this.nom_vic.setText(demande.getNom_victime());
                user_id.getItems().addAll(userList);
                user_id.getSelectionModel().select(selectedUser);


            } else {
                System.out.println("Demande non trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifierDemande(ActionEvent event) {
        try {
            if (user_id.getValue() == null) {
                showErrorMessage("Selectionner un utilisateur svp");
                return;
            }

            if (num_con.getText().isEmpty()) {
                showErrorMessage("Numéro Constat est requis");
                return;
            }

            if (localisation.getText().isEmpty()) {
                showErrorMessage("Localisation est requis");
                return;
            }

            if (etat_de.getSelectionModel().getSelectedIndex()==-1) {
                showErrorMessage("Etat Demande est requis");
                return;
            }


            int userId = user_id.getValue().getId();
            String numConstat = num_con.getText();
            String localisationDemande = localisation.getText();
            String etatDemande = etat_de.getSelectionModel().getSelectedItem();
           // java.sql.Date dateRealisation = java.sql.Date.valueOf(date.getValue());

            Date currentDate = new Date();

            java.sql.Date dateRealisation = new java.sql.Date(currentDate.getTime());
            Demande updatedDemande = new Demande(Id, userId, numConstat, dateRealisation, localisationDemande, etatDemande);
            updatedDemande.setEtat_demande(etatDemande);
            updatedDemande.setLocalisation(localisationDemande);
            updatedDemande.setNum_constat(numConstat);
            updatedDemande.setUser_id(userId);
            updatedDemande.setDate_realisation(dateRealisation);
            updatedDemande.setNom_victime(nom_vic.getText());
            updatedDemande.setNum_constat(numConstat);
            updatedDemande.setDate_realisation(dateRealisation);
            updatedDemande.setLocalisation(localisationDemande);
            updatedDemande.setEtat_demande(etatDemande);
            updatedDemande.setUser_id(userId);
            updatedDemande.setId(this.Id);
            serviceDemande.update(updatedDemande);

            showSuccessMessage("Demande modifié avec succès");

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Error updating Demande");
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okayButton) {
                try {
                    RouterController router = new RouterController();
                    router.navigate("/fxml/DemandeCRUD.fxml");
                    System.out.println("Button Clicked");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void returnTo(MouseEvent mouseEvent) {
        System.out.println("RETURN TO EXECUTED");
        RouterController router = new RouterController();
        router.navigate("/fxml/DemandeCRUD.fxml");
    }

    @Override
    public void init(Integer Id) {
        System.out.println("Id from ModifyDemande:"+Id);
        this.Id = Id;
        initialize(null, null);
    }

    public void LocaliserIncident(ActionEvent event) throws IOException {
        MapController m=new MapController();
        m.showWindow();
        Stage stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader((MainApp.class.getResource("/fxml/webview.fxml")));
        Scene scene = new Scene(fxmlLoader. load());
        stage.setTitle("localiser l'incident");
        stage.setScene(scene);
        stage.show();
    }
}