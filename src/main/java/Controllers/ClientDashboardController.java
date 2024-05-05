package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ClientDashboardController {
    @FXML
    private Label adminNameLabel;

    public void initialize() {
       // GuiLoginController guilogin = new GuiLoginController();
      //  String name="Bienvenue "+guilogin.user.getName()+"!";
        adminNameLabel.setText("Bienvenue Client");
    }
    public void goToLogn(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/AdminLogin.fxml");
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/ClientDashboard.fxml");
    }

    public void goToUsers(MouseEvent mouseEvent) {

        RouterController router=new RouterController();
        router.navigate("/fxml/ClientViwRep.fxml");
    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/ClientViwRep.fxml");
    }

    public void goToCommands(MouseEvent mouseEvent) {
    }

    public void goToReclamations(MouseEvent mouseEvent) {
    }

    public void goToEvent(MouseEvent mouseEvent) {
    }

    public void goToLivraisons(MouseEvent mouseEvent) {
    }

    public void goToDemandes(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/DemandeCRUD.fxml");
    }

    public void goToReponses(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/ClientViwRep.fxml");
    }
}
