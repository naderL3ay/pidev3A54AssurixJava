package Controllers;




import Entities.Demande;
import Service.ServiceDemande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import testconnection.MainApp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ViewDemande {

    @FXML
    private TableView<Demande> tableView;

    private final ServiceDemande serviceDemandeService = new ServiceDemande();

    @FXML
    public void initialize() {
        initializeTableColumns();
        updateDemandeList();
    }

    public void updateDemandeList() {
        try {
            ServiceDemande serviceDemande= new ServiceDemande();
            List<Demande> demandes= serviceDemande.ReadAll();
            tableView.getItems().clear();
            tableView.getItems().addAll(demandes);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }

    private void initializeTableColumns() {
        tableView.getColumns().clear();


        TableColumn<Demande, String> numConColumn = new TableColumn<>("Num Constat");
        numConColumn.setCellValueFactory(new PropertyValueFactory<>("num_constat"));

        TableColumn<Demande, String> nomColumn = new TableColumn<>("nom_victime");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom_victime"));
        nomColumn.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn<Demande, String> localisationColumn = new TableColumn<>("Localisation");
        localisationColumn.setCellValueFactory(new PropertyValueFactory<>("localisation"));

        TableColumn<Demande, String> etatDeColumn = new TableColumn<>("Etat Demande");
        etatDeColumn.setCellValueFactory(new PropertyValueFactory<>("etat_demande"));

        TableColumn<Demande, String> userIdColumn = new TableColumn<>("User Id");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<Demande, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_realisation"));

        TableColumn<Demande, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableView.getSortOrder().add(nomColumn);
        tableView.getColumns().addAll(numConColumn,nomColumn, localisationColumn, etatDeColumn, userIdColumn, dateColumn, actionColumn);
    }

    private Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>>() {
            @Override
            public TableCell<Demande, Void> call(final TableColumn<Demande, Void> param) {
                final TableCell<Demande, Void> cell = new TableCell<Demande, Void>() {
                    private final Button modifyButton = new Button();
                    private final Button deleteButton = new Button();

                    private final Button mapButton = new Button();
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
                            Demande demande = getTableView().getItems().get(getIndex());
                            System.out.println(demande.getId());
                            RouterController.navigate("/fxml/ModifyDemande.fxml", demande.getId());
                        });
                        Button mapButton = new Button("Localiser");

                        ImageView mapIcon = new ImageView(new Image(getClass().getResourceAsStream("../assets/mapsicon.png")));
                        mapButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        mapButton.setGraphic(mapIcon);
                        mapIcon.setFitWidth(20);
                        mapIcon.setFitHeight(20);
                        mapButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        mapIcon.setOnMouseClicked(event -> {
                            try {
                                Demande demande = getTableView().getItems().get(getIndex());
                                openMap(demande.getLatitude(), demande.getLongitude());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Demande demande = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Demande");
                            alert.setContentText("Vous etes sur tu veux supprimer cette Demande?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    ServiceDemande r=new ServiceDemande();
                                    r.delete(demande);

                                    updateDemandeList();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                    errorAlert.setTitle("Error");
                                    errorAlert.setHeaderText("Error Base des données");
                                    errorAlert.setContentText("Un erreur en supprimant le produit.");
                                    errorAlert.showAndWait();
                                }
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        ImageView mapIcon = new ImageView(new Image(getClass().getResourceAsStream("../assets/mapsicon.png")));
                        mapButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                        mapIcon.setFitWidth(20);
                        mapIcon.setFitHeight(20);
                        mapButton.setGraphic(mapIcon);

                        mapButton.setOnAction(event -> {
                            try {
                                    Demande demande = getTableView().getItems().get(getIndex());
                                openMap(demande.getLatitude(), demande.getLongitude());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        mapIcon.setOnMouseClicked(event -> {
                            try {
                                Demande demande = getTableView().getItems().get(getIndex());
                                openMap(demande.getLatitude(), demande.getLongitude());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        if (empty) {
                            setGraphic(null);
                        } else {
                            VBox buttons = new VBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton,mapButton);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }

    private void openMap(float latitude, float longitude) throws IOException {
        LocationView.lat=latitude;
        LocationView.lon=longitude;
        Stage stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader((MainApp.class.getResource("../fxml/locationView.fxml")));
        //    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Hotels/HotelsCRUD.fxml"));

        System.out.println(LocationView.lat);
        System.out.println(LocationView.lon);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Voir sur la map");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void gotoAjouter(ActionEvent actionEvent) {

        RouterController router=new RouterController();
        router.navigate("/fxml/AddDemande.fxml");
    }
    @FXML
    public void goToNavigate(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/AdminDashboard.fxml");
    }
    @FXML
    private TextField searchField;

    @FXML
    public void searchquery(KeyEvent keyEvent) {
        String nomVictime = searchField.getText();
        try {
            List<Demande> demandes = serviceDemandeService.searchByNomVictime(nomVictime);
            tableView.getItems().clear();
            tableView.getItems().addAll(demandes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void sortTable(ActionEvent actionEvent) {
        TableColumn<Demande, ?> nomColumn = tableView.getColumns().get(2); // Replace 2 with the index of the nom_victime column
        nomColumn.setSortType(nomColumn.getSortType() == TableColumn.SortType.ASCENDING ? TableColumn.SortType.DESCENDING : TableColumn.SortType.ASCENDING);
        tableView.getSortOrder().setAll(nomColumn);
    }
}