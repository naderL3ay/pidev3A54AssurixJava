package Controllers;

import Entities.Demande;
import Entities.Reponse;
import Service.ServiceDemande;
import Service.ServiceReponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyReponse implements Initializable, InitializableController {

    private int Id;

    @FXML
    private TextField titre_rapport;

    @FXML
    private TextArea description;

    @FXML
    private ComboBox<Demande> demande_id;

    private ObservableList<Reponse> reponseList = FXCollections.observableArrayList();

    @FXML
    private ServiceReponse serviceReponse = new ServiceReponse();
    public static Reponse reponse_static;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceDemande sd=new ServiceDemande();
        try {
            List<Demande> demandeList = sd.ReadAll();
            demande_id.getItems().addAll(demandeList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
             this.titre_rapport.setText(reponse_static.getTitre_rapport());
             this.description.setText(reponse_static.getDescription());
        try {
            Demande selectedDemande = sd.findById(reponse_static.getDemande_id());
          //  demande_id.getSelectionModel().setSelectedItem(selectedDemande);
            demande_id.getSelectionModel().select(selectedDemande);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifierReponse(ActionEvent event) {
        try {
            if (demande_id.getValue() == null) {
                showErrorMessage("Please select a demande");
                return;
            }

            if (titre_rapport.getText().isEmpty()) {
                showErrorMessage("Titre Rapport is required");
                return;
            }

            if (description.getText().isEmpty()) {
                showErrorMessage("Description is required");
                return;
            }

            int demandeId = demande_id.getValue().getId();
            String titreRapport = titre_rapport.getText();
            String descriptionReponse = description.getText();

            Reponse updatedReponse = new Reponse(Id, demandeId, titreRapport, descriptionReponse);
            serviceReponse.update(updatedReponse);

            showSuccessMessage("Reponse modifié avec succès");

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Error updating Reponse");
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
                    router.navigate("/fxml/ReponseCRUD.fxml");
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
        router.navigate("/fxml/ReponseCRUD.fxml");
    }

    @Override
    public void init(Integer Id) {
        System.out.println("Id from ModifyReponse:"+Id);
        this.Id = Id;
        initialize(null, null);
    }
}