package Controllers;

import Entities.Notification;
import Service.NotificationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class AdminDashboardController {
    public VBox notifModel;
    public GridPane notifContainer;
    public Text totalNotif;
    @FXML
    private Label adminNameLabel;

    public void initialize() {
        String name="Bienvenue Admin";
        adminNameLabel.setText(name);
        if (notifModel != null) {
            notifModel.setVisible(false);
        }       // notifModel.setVisible(false);
        NotificationService ns = new NotificationService();

        List<Notification> notifList = ns.getUserNotifications(0);
        int unreadNotifCount = 0;

        for (Notification notification : notifList) {
            if (!notification.getSeen()) {
                unreadNotifCount++;
            }
        }
        notifList.sort(new Comparator<Notification>() {
            @Override
            public int compare(Notification n1, Notification n2) {
                boolean seen1 = n1.getSeen();
                boolean seen2 = n2.getSeen();

                if (seen1 == seen2) {
                    return 0;
                } else if (seen1) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        if(totalNotif!=null)
            totalNotif.setText(String.valueOf(unreadNotifCount));
        if(notifContainer!=null) {
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < notifList.size(); i++) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/notifItem.fxml"));
                    HBox notifItem = fxmlLoader.load();
                    NotifItemController notifController = fxmlLoader.getController();
                    notifController.setNotifData(notifList.get(i));

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }
                    notifContainer.add(notifItem, column++, row);
                    // GridPane.setMargin(notifItem, new Insets(10));
                    GridPane.setMargin(notifItem, new Insets(0, 20, 20, 10));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Notifications are ready");
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/AdminDashboard.fxml");
    }




    public void goToLogn(MouseEvent mouseEvent) {
    }

    public void goToDossiers(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/DossiersReclamationsCrud.fxml");
    }
    private int notifModel_isOpen= 0;
    public void open_notifModel(MouseEvent mouseEvent) {
        if (notifModel_isOpen == 0) {
            notifModel.setVisible(true);
            notifModel_isOpen = 1;
            return;
        }

        if (notifModel_isOpen == 1) {
            notifModel.setVisible(false);
            notifModel_isOpen = 0;
        }
    }
}
