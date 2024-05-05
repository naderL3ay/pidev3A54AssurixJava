package Controllers;

import Entities.DossierReclamation;
import Service.ServiceDossierReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditDossierReclamationController implements Initializable {
    public ImageView imageVersoCIN;
    public ImageView imageCarteGrise;
    public TextField descriptionPack;
    public ImageView imageRectoCIN;
    public TextField serieVeh;
    private File selectedimageVersoCINFile;
    private File selectedImageRectoCINFile;
    private File selectedImageCarteGriseFile;
    private File selectedimageVersoCINFile_new;
    private File selectedImageRectoCINFile_new;
    private File selectedImageCarteGriseFile_new;

    public static File selectedimageVersoCINFile_static;
    public static File selectedImageRectoCINFile_static;
    public static File selectedImageCarteGriseFile_static;
    ServiceDossierReclamation sdr=new ServiceDossierReclamation();

    public static DossierReclamation dossierReclamation_static;
    public void AddDossierReclamation(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        if(!invalidateInputsAndProceed()){
            return;
        }
        DossierReclamation DossierReclamation=dossierReclamation_static;

        System.out.println("Image carte grise file"+this.selectedImageCarteGriseFile_new);
        System.out.println("Image versp CIN file"+this.selectedimageVersoCINFile_new);
        System.out.println("image recto cin file"+this.selectedImageRectoCINFile_new);


        if(this.selectedimageVersoCINFile_new!=null) {
            System.out.println("selectedimageVersoCINFile: "+selectedimageVersoCINFile_new.getName());
            uploadImage(this.selectedimageVersoCINFile_new);
            DossierReclamation.setImageVersoCin(selectedimageVersoCINFile_new.getName());
        }
        if(this.selectedImageCarteGriseFile_new!=null) {
            System.out.println("selectedImageCarteGriseFile: "+selectedImageCarteGriseFile_new.getName());
            DossierReclamation.setImageCarteGrise(selectedImageCarteGriseFile_new.getName());
            uploadImage(this.selectedImageCarteGriseFile_new);
        }
        if(this.selectedImageRectoCINFile_new!=null) {
            System.out.println("selectedImageRectoCINFile: "+selectedImageRectoCINFile_new.getName());
            uploadImage(this.selectedImageRectoCINFile_new);
            DossierReclamation.setImageRectoCin(selectedImageRectoCINFile_new.getName());
        }
        DossierReclamation.setSerieVehicule(this.serieVeh.getText());
        DossierReclamation.setDate(new Date());
        System.out.println(DossierReclamation);
        DossierReclamation.setId(dossierReclamation_static.getId());
        sdr.update(DossierReclamation);
        showSuccessMessage("Un dossier réclamation à été ajouté avec succées");
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean invalidateInputsAndProceed() {

        String regex = "^\\d{3} [A-Za-z]{2} \\d{4}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(this.serieVeh.getText());

        if (!matcher.matches()) {
            showAlert("Le format de la série du véhicule est incorrect.");
            return false;
        }


        return true;
    }
    public void uploadImage(File imageFile) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        System.out.println(imageFile);
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
        System.out.println("upload image recto cin");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.selectedImageRectoCINFile_new = fileChooser.showOpenDialog(stage);
        if (this.selectedImageRectoCINFile_new != null) {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(this.selectedImageRectoCINFile_new));
                imageRectoCIN.setImage(image);
                System.out.println(this.selectedImageRectoCINFile_new);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void uploadimageVersoCIN(ActionEvent actionEvent) {
        System.out.println("upload image verso cin");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.selectedimageVersoCINFile_new = fileChooser.showOpenDialog(stage);
        if (this.selectedimageVersoCINFile_new != null) {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(this.selectedimageVersoCINFile_new));
                imageVersoCIN.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImageCarteGrise(ActionEvent actionEvent) {
        System.out.println("upload image carte grise");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.selectedImageCarteGriseFile_new = fileChooser.showOpenDialog(stage);
        if (this.selectedImageCarteGriseFile_new != null) {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(this.selectedImageCarteGriseFile_new));
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
        this.serieVeh.setText(dossierReclamation_static.getSerieVehicule());
        //this.selectedImageCarteGriseFile = new File("http://localhost:8000/images/"+dossierReclamation_static.getImageCarteGrise());
        //this.selectedImageRectoCINFile = new File("http://localhost:8000/images/"+dossierReclamation_static.getImageRectoCin());
       // this.selectedimageVersoCINFile = new File("http://localhost:8000/images/"+dossierReclamation_static.getImageVersoCin());

        this.imageRectoCIN.setImage(new Image("http://localhost:8000/images/"+dossierReclamation_static.getImageRectoCin()));
        this.imageVersoCIN.setImage(new Image("http://localhost:8000/images/"+dossierReclamation_static.getImageVersoCin()));
        this.imageCarteGrise.setImage(new Image("http://localhost:8000/images/"+dossierReclamation_static.getImageCarteGrise()));
    }

}
