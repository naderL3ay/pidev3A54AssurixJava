package Controllers;

import Entities.Notification;
import Service.NotificationService;
import Entities.Notification;
import Service.NotificationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NotifItemController {

    public Text newReclamation;
    @FXML
    private Text date;

    @FXML
    private Circle img;

    @FXML
    private Label title;

    @FXML
    private VBox markNotifAsReadBtn;

 //   User user = null;

    public void setNotifData(Notification notif) {
        // afficher reclamation image
        this.notification = notif;

        // Set the initial appearance of the notification item
        updateAppearance();

        Image imag = new Image("/assets/reclamation.png");
       img.setFill(new ImagePattern(imag));

        title.setText(notif.getMessage());

        date.setText(String.valueOf(notif.getCreatedAt()));

        // recuperer user connecté

     //   UserService userService = new UserService();

     //   user = new User();
       /* if (UserSession.getInstance().getEmail() == null) {

            try {
                user = userService.getOneUser("samirtem06@gmail.com");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(user.getId());

        } else {
            try {
                user = userService.getOneUser(UserSession.getInstance().getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(user.getId());

        }
        */
        // markNotifAsReadBtn btn click
        NotificationService nf=new NotificationService();
        markNotifAsReadBtn.setOnMouseClicked(event -> {
            if(notification.getSeen()) {
               // utils.TrayNotificationAlert.notif("Notification", "Notification already greyed out.",
                 //       NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            }else {
                nf.makeAsReadNotif(notif.getId());
                notification.setSeen(true);
                updateAppearance();

                //utils.TrayNotificationAlert.notif("Notification", "Notification maked as read successfully.",
                  //      NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
            }
            // end
        });
        // END markNotifAsReadBtn btn click

    }
    private Notification notification;

    private void updateAppearance() {
        System.out.println("Updating appreaance for "+notification.getSeen());
        if (notification.getSeen()) {
            img.setOpacity(0.5);
            title.setOpacity(0.5);
            markNotifAsReadBtn.setOpacity(0.5);
            date.setOpacity(0.5);
            newReclamation.setOpacity(0.5);
        } else {
            img.setOpacity(1);
            title.setOpacity(1);
            markNotifAsReadBtn.setOpacity(1);
            date.setOpacity(1);
            newReclamation.setOpacity(1);
        }
    }


}
