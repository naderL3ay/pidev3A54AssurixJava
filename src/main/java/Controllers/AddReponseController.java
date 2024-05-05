package Controllers;

import Entities.Demande;
import Entities.Reponse;
import Service.ServiceDemande;
import Service.ServiceReponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class AddReponseController {

    @FXML
    private ComboBox<Demande> comboBoxDemandeId;
    @FXML
    private TextField titre_rapport;
    @FXML
    private TextArea Description;

    private ServiceReponse serviceReponse = new ServiceReponse();
    private ObservableList<Reponse> reponseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            reponseList.addAll(serviceReponse.ReadAll());
            ServiceDemande sd=new ServiceDemande();
            List<Demande> demandeList = sd.ReadAll();
            // Initialize ComboBoxes
            comboBoxDemandeId.getItems().addAll(demandeList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addReponse(ActionEvent actionEvent) {
        try {
            if (comboBoxDemandeId.getValue() == null) {
                showErrorMessage("Please select a demande");
                return;
            }

            if (titre_rapport.getText().isEmpty()) {
                showErrorMessage("Titre Rapport is required");
                return;
            }

            if (Description.getText().isEmpty()) {
                showErrorMessage("Description is required");
                return;
            }

            int demandeId = comboBoxDemandeId.getSelectionModel().getSelectedItem().getId();
            String titreRapport = titre_rapport.getText();
            String description = Description.getText();

            Reponse reponse = new Reponse(demandeId, titreRapport, description);

            serviceReponse.add(reponse);
            showSuccessMessage("Reponse added successfully");
            SmsController.Sms();
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Error adding Reponse");
        }
    }

    @FXML
    private void returnTo(MouseEvent mouseEvent) {
        System.out.println("Button Clicked");
        RouterController router = new RouterController();
        router.navigate("../fxml/ReponseCRUD.fxml");
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

}