package Controllers;

import Entities.Demande;
import Entities.Reponse;
import Service.ServiceDemande;
import Service.ServiceReponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ViewReponseClient {

    @FXML
    private TableView<Reponse> tableView;

    private final ServiceReponse serviceReponse = new ServiceReponse();

    @FXML
    public void initialize() {
        initializeTableColumns();
        updateReponseList();
    }

    public void updateReponseList() {
        try {
            List<Reponse> reponses= serviceReponse.ReadAll();
            tableView.getItems().clear();
            tableView.getItems().addAll(reponses);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }

    private void initializeTableColumns() {
        tableView.getColumns().clear();

        TableColumn<Reponse, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Reponse, Integer> demandeIdConColumn = new TableColumn<>("demande_id");
        demandeIdConColumn.setCellValueFactory(new PropertyValueFactory<>("demande_id"));

        TableColumn<Reponse, String> titreRapportColumn = new TableColumn<>("titre_rapport ");
        titreRapportColumn.setCellValueFactory(new PropertyValueFactory<>("titre_rapport"));

        TableColumn<Reponse, String> descriptionColumn = new TableColumn<>("description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));





        TableColumn<Reponse, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());

        tableView.getColumns().addAll(idColumn, demandeIdConColumn, titreRapportColumn, descriptionColumn,  actionColumn);
    }

    private Callback<TableColumn<Reponse, Void>, TableCell<Reponse, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Reponse, Void>, TableCell<Reponse, Void>>() {
            @Override
            public TableCell<Reponse, Void> call(final TableColumn<Reponse, Void> param) {
                final TableCell<Reponse, Void> cell = new TableCell<Reponse, Void>() {
                    private final Button modifyButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                        ImageView modifyIcon = new ImageView(modifyImage);
                        modifyIcon.setFitWidth(20);
                        modifyIcon.setFitHeight(20);
                        modifyButton.setGraphic(modifyIcon);

                        modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                        ImageView deleteIcon = new ImageView(deleteImage);
                        deleteIcon.setFitWidth(16);
                        deleteIcon.setFitHeight(16);
                        deleteButton.setGraphic(deleteIcon);

                        deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        modifyButton.setOnAction((ActionEvent event) -> {
                            Reponse reponse = getTableView().getItems().get(getIndex());
                            ModifyReponse.reponse_static= reponse;
                            System.out.println(reponse.getId());
                            RouterController.navigate("/fxml/ModifyReponse.fxml", reponse.getId());
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Reponse reponse = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Reponse");
                            alert.setContentText("Vous etes sur tu veux supprimer cette Reponse?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    ServiceReponse r=new ServiceReponse();
                                    r.delete(reponse);

                                    updateReponseList();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                    errorAlert.setTitle("Error");
                                    errorAlert.setHeaderText("Error Base des données");
                                    errorAlert.setContentText("Un erreur en supprimant le reponse.");
                                    errorAlert.showAndWait();
                                }
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            VBox buttons = new VBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }

    @FXML
    public void searchquery(KeyEvent keyEvent) {
        // Implement search functionality
    }

    @FXML
    public void gotoAjouter(ActionEvent actionEvent) {

        RouterController router=new RouterController();
        router.navigate("/fxml/AddReponse.fxml");
    }
    @FXML
    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/AdminDashboard.fxml");
    }
}