package Controllers;
import com.mysql.cj.jdbc.CallableStatement;
import javafx.event.ActionEvent;
import Entities.User;
import Entities.Demande;
import Service.ServiceUser;
import Service.ServiceDemande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import Utils.sendMail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.stage.Stage;
import testconnection.MainApp;

public class AddDemandeController {

    @FXML
    private ComboBox<User> user_id;
    @FXML
    private TextField nom_vic;
    @FXML
    private TextField num_con;
    @FXML
    private TextField localisation;
    @FXML
    private ComboBox<String> etat_de;
    @FXML
    private DatePicker date;

    private ServiceDemande serviceDemande = new ServiceDemande();
    private ObservableList<User> clientList = FXCollections.observableArrayList();
    private CallableStatement rs;

    @FXML
    public void initialize() {
        try {
            ServiceUser spoo = new ServiceUser();

            // Initialize ComboBoxes
            user_id.getItems().addAll(spoo.ReadAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addDemande(ActionEvent actionEvent) {
        try {
            if (user_id.getValue() == null) {
                showErrorMessage("Please select a user");
                return;
            }

            if (nom_vic.getText().isEmpty()) {
                showErrorMessage("Nom & prénom is required");
                return;
            }

            if (num_con.getText().isEmpty()) {
                showErrorMessage("Num Constat is required");
                return;
            }


            String numConstat = num_con.getText();
            if (!numConstat.matches("\\d{1,8}")) {
                showErrorMessage("Num Constat must contain only digits and no more than 8");
                return;
            }
            String numConPattern = "\\d+";

            if (!num_con.getText().matches(numConPattern)) {
                showErrorMessage("Num Constat must contain only numbers");
                return;
            }

            if (localisation.getText().isEmpty()) {
                showErrorMessage("Localisation is required");
                return;
            }

            if (etat_de.getSelectionModel().getSelectedIndex() == -1) {
                showErrorMessage("Etat Demande is required");
                return;
            }


            if (AddDemandeController.latitude == 0 || AddDemandeController.longitude ==0)
            {
                showErrorMessage("Location is required, Please locate the incident");
                return;
            }

            int userId = user_id.getValue().getId();
            String nomVictime = nom_vic.getText();
             numConstat = num_con.getText();
        //    Date dateRealisation = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date currentDate = new Date();

            java.sql.Date dateRealisation = new java.sql.Date(currentDate.getTime());
            String localisationDemande = localisation.getText();
            String etatDemande = etat_de.getSelectionModel().getSelectedItem();

            Demande demande = new Demande();
            demande.setNom_victime(nomVictime);
            demande.setNum_constat(numConstat);
            demande.setDate_realisation(dateRealisation);
            demande.setLocalisation(localisationDemande);
            demande.setEtat_demande(etatDemande);
            demande.setUser_id(userId);
            demande.setLongitude(AddDemandeController.longitude);
            demande.setLatitude(AddDemandeController.latitude);
            serviceDemande.add(demande);
            showSuccessMessage("Demande added successfully");
            sendMail.send ();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void returnTo(MouseEvent mouseEvent) {
        System.out.println("Button Clicked");
        RouterController router = new RouterController();
        router.navigate("../fxml/DemandeCRUD.fxml");
        System.out.println("Button Clicked");
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait();
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
    private static float latitude;
    private static float longitude;
    public void savecoords(String latitude, String longitude) throws IOException {

        AddDemandeController.latitude =Float.parseFloat(latitude);
        AddDemandeController.longitude =Float.parseFloat(longitude);
        String apiKey = "AIzaSyA2Rfnw4b0XfjAKIOTwKGcTKwngzOEfnE4";

        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + AddDemandeController.latitude + "," + AddDemandeController.longitude + "&key=" + apiKey;

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStreamReader in = new InputStreamReader(conn.getInputStream());

        JsonObject json = new JsonParser().parse(in).getAsJsonObject();
        String address = json.getAsJsonArray("results").get(0).getAsJsonObject().get("formatted_address").getAsString();

        System.out.println("Address: " + address);
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