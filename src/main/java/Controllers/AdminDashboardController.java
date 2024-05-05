package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdminDashboardController {
    @FXML
    private Label adminNameLabel;

    public void initialize() {
       // GuiLoginController guilogin = new GuiLoginController();
      //  String name="Bienvenue "+guilogin.user.getName()+"!";
        adminNameLabel.setText("Bienvenue Admin");
    }
    public void goToLogn(MouseEvent mouseEvent) {
        System.out.println("GOING TO LOGN");
        RouterController.navigate("/fxml/AdminLogin.fxml");
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/AdminDashboard.fxml");
    }

    public void goToUsers(MouseEvent mouseEvent) {

        RouterController router=new RouterController();
        router.navigate("/fxml/DemandeCRUD.fxml");
    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/ReponseCRUD.fxml");
    }

    public void goToCommands(MouseEvent mouseEvent) {
    }

    public void goToReclamations(MouseEvent mouseEvent) {
    }

    public void goToEvent(MouseEvent mouseEvent) {
    }

    public void goToLivraisons(MouseEvent mouseEvent) {
    }
}
