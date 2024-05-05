package Controllers;

import Entities.DossierReclamation;
import Entities.Notification;
import Service.NotificationService;
import Service.ServiceDossierReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDosierReclamationController implements Initializable {
    public ImageView imageVersoCIN;
    public ImageView imageCarteGrise;
    public TextField descriptionPack;
    public ImageView imageRectoCIN;
    public TextField serieVeh;
    private File selectedimageVersoCINFile;
    private File selectedImageRectoCINFile;
    private File selectedImageCarteGriseFile;
    ServiceDossierReclamation sdr=new ServiceDossierReclamation();


    public void AddDossierReclamation(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, SQLException {
        if(!invalidateInputsAndProceed()){
            return;
        }
        uploadImage(selectedimageVersoCINFile);
        uploadImage(selectedImageRectoCINFile);
        uploadImage(selectedImageCarteGriseFile);
        DossierReclamation DossierReclamation=new DossierReclamation();
        DossierReclamation.setSerieVehicule(this.serieVeh.getText());
        DossierReclamation.setDate(new Date());
        DossierReclamation.setImageRectoCin(selectedImageRectoCINFile.getName());
        DossierReclamation.setImageVersoCin(selectedimageVersoCINFile.getName());
        DossierReclamation.setImageCarteGrise(selectedImageCarteGriseFile.getName());
        int id = sdr.createReturnsID(DossierReclamation);
        NotificationService ns=new NotificationService();
        Notification newnotif=new Notification();
        newnotif.setCreatedAt(new java.util.Date());
        newnotif.setMessage("Vous avez réçu un nouveau dossier réclamation");
        newnotif.userId=0;
        newnotif.setReclamationId(id);
        System.out.println(newnotif);
        ns.add(newnotif);
        showSuccessMessage("Un dossier réclamation à été ajouté avec succées");

    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
        MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, false, false, false, null);
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean invalidateInputsAndProceed() {
        if (selectedimageVersoCINFile == null) {
            showAlert("Sélectionner une image s'il vous plaît.");
            return false;
        }
        if (selectedImageRectoCINFile == null) {
            showAlert("Sélectionner une image s'il vous plaît.");
            return false;
        }
        if (selectedImageCarteGriseFile == null) {
            showAlert("Sélectionner une image s'il vous plaît.");
            return false;
        }
        String regex = "^\\d{3} [A-Za-z]{2} \\d{4}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(this.serieVeh.getText());

        // Check if the input matches the pattern
        if (!matcher.matches()) {
            showAlert("Le format de la série du véhicule est incorrect.");
            return false;
        }


    return true;
    }
    public void uploadImage(File imageFile) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpPost httpPost = new HttpPost("http://localhost:8000/upload-image");

        HttpEntity requestEntity = MultipartEntityBuilder.create()
                .addBinaryBody("image", imageFile, ContentType.APPLICATION_OCTET_STREAM, imageFile.getName())
                .build();

        httpPost.setEntity(requestEntity);
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            Header contentDispositionHeader = response.getFirstHeader("Content-Disposition");
            if (contentDispositionHeader != null) {
                String contentDisposition = contentDispositionHeader.getValue();
                String filename = extractFilenameFromContentDisposition(contentDisposition);
                System.out.println("Success upload. Filename: " + filename);
            } else {
                System.out.println("Success upload, but filename not found in the response");
            }
        } else {
            System.out.println("Failed upload");
        }
    }
    private String extractFilenameFromContentDisposition(String contentDisposition) {
        String filename = null;
        if (contentDisposition != null && contentDisposition.contains("filename=")) {
            String[] parts = contentDisposition.split(";");
            for (String part : parts) {
                if (part.trim().startsWith("filename=")) {
                    filename = part.substring(part.indexOf('=') + 1).trim().replace("\"", "");
                    break;
                }
            }
        }
        return filename;
    }

    public void uploadimageRectoCIN(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        selectedImageRectoCINFile = fileChooser.showOpenDialog(stage);
        if (selectedImageRectoCINFile != null) {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(selectedImageRectoCINFile));
                imageRectoCIN.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void uploadimageVersoCIN(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        selectedimageVersoCINFile = fileChooser.showOpenDialog(stage);
        if (selectedimageVersoCINFile != null) {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(selectedimageVersoCINFile));
                imageVersoCIN.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImageCarteGrise(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        selectedImageCarteGriseFile = fileChooser.showOpenDialog(stage);
        if (selectedImageCarteGriseFile != null) {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(selectedImageCarteGriseFile));
                imageCarteGrise.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void returnTo(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/DossiersReclamationsCrud.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
