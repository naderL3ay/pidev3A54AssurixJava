package Controllers;

import Controllers.API.BadWordFilter;
import Entities.DossierReclamation;
import Entities.Reclamation;
import Service.ServiceDossierReclamation;
import Service.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditReclamationsController implements Initializable {
    public TextField titre;
    public TextField reclamation;
    public Button btnSignup;
    public Label PageLabel;
    private ServiceReclamation serviceReclamation=new ServiceReclamation();
    public static DossierReclamation Dossierreclamation_static;
    public static DossierReclamation Dossierreclamation_static_with_no_reclamation;

    private boolean invalidateInputsAndProceed() {
        if (this.titre.getText().isEmpty()) {
            showAlert("Le titre ne peut pas être vide.");
            return false;
        } else if (this.titre.getText().length() > 15) {
            showAlert("Le titre ne doit pas dépasser 15 caractères.");
            return false;
        } else if (!this.titre.getText().matches("^[a-zA-Z0-9\\s'\"àáâãäåçèéêëìíîïðòóôõöùúûüýÿ]+$")) {
            showAlert("Le titre ne doit pas contenir des caractères spéciaux.");
            return false;
        }

        if (this.reclamation.getText().isEmpty()) {
            showAlert("Le contenu de la réclamation ne peut pas être vide.");
            return false;
        }

        return true;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Dossierreclamation_static!=null){
            titre.setText(Dossierreclamation_static.getReclamation().getTitre());
            reclamation.setText(Dossierreclamation_static.getReclamation().getReclamation());
            btnSignup.setText("Modifier");
            PageLabel.setText("Modifier Réclamation");
        }

    }

    public void AddReclamation(ActionEvent event) throws Exception {
        if(!invalidateInputsAndProceed()){
            return;
        }
        Reclamation reclamation=new Reclamation();
        String description = BadWordFilter.filterText(this.reclamation.getText().trim());
        int colonIndex = description.indexOf(":");
        int braceIndex = description.indexOf("}");
        System.out.println(description);
        String extractedContent = description.substring(colonIndex + 2, braceIndex - 1).trim();

        reclamation.setReclamation(extractedContent);
        reclamation.setTitre(this.titre.getText());
        if(Dossierreclamation_static!=null){
            reclamation.setId(Dossierreclamation_static.getReclamation().getId());
            serviceReclamation.update(reclamation);
        }
        else{
           int generatedID= serviceReclamation.createReturnsID(reclamation);
           System.out.println(generatedID);
            Dossierreclamation_static_with_no_reclamation.setReclamation(serviceReclamation.getById(generatedID));
            ServiceDossierReclamation sdr=new ServiceDossierReclamation();
            sdr.update(Dossierreclamation_static_with_no_reclamation);
        }
        showSuccessMessage("Réclamation modifiée avec succès");
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
        MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, false, false, false, null);
    }
    public void returnTo(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/DossiersReclamationsCrud.fxml");
    }
}
