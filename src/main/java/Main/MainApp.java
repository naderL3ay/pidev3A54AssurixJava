package Main;

import Controllers.RouterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        RouterController.setPrimaryStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loadingscreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Image icon = new Image(getClass().getResourceAsStream("/assets/logo.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Assurex");

        primaryStage.show();
    }
    public static void main(String[] args) throws GeneralSecurityException, IOException {

        launch(args);
    }
}
