package Service;

import Entities.DossierReclamation;
import Entities.Reclamation;
import Utils.Myconnection;
import javafx.scene.control.Alert;

import java.security.Provider;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServiceDossierReclamation implements CrudInterface<DossierReclamation> {
    private Connection connection;

    public ServiceDossierReclamation() {
        this.connection = Myconnection.getInstance().getCnx();
    }
    public boolean isOneWeekPassed(Date creationDate) {
        long diffInMillies = new Date().getTime() - creationDate.getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diffInDays >= 7;
    }
    @Override
    public void create(DossierReclamation entity) {
        String query = "INSERT INTO dossier_reclamation (id, serie_vehicule, image_recto_cin, image_verso_cin, image_carte_grise, id_utilisateur, date, reclamation_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getSerieVehicule());
            preparedStatement.setString(3, entity.getImageRectoCin());
            preparedStatement.setString(4, entity.getImageVersoCin());
            preparedStatement.setString(5, entity.getImageCarteGrise());
            preparedStatement.setInt(6, entity.getIdUtilisateur());
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(entity.getDate().getTime()));
            if(entity.getReclamation()!=null)
                preparedStatement.setInt(8, entity.getReclamation().getId());
            else
                preparedStatement.setInt(8,0);
            preparedStatement.executeUpdate();
            System.out.println("Dossier de réclamation créé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void update(DossierReclamation entity) {
        DossierReclamation existingDossier = getById(entity.getId());
        if (existingDossier == null) {
            showAlert("Le dossier de réclamation n'existe pas.");
            return;
        }

        if (isOneWeekPassed(existingDossier.getDate())) {
            showAlert("Impossible de modifier le dossier après une semaine.");
            return;
        }
        String query = "UPDATE dossier_reclamation SET serie_vehicule = ?, image_recto_cin = ?, image_verso_cin = ?, image_carte_grise = ?, id_utilisateur = ?, date = ?, reclamation_id = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getSerieVehicule());
            preparedStatement.setString(2, entity.getImageRectoCin());
            preparedStatement.setString(3, entity.getImageVersoCin());
            preparedStatement.setString(4, entity.getImageCarteGrise());
            preparedStatement.setInt(5, entity.getIdUtilisateur());
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(entity.getDate().getTime()));
            if(entity.getReclamation()!=null)
                preparedStatement.setInt(7, entity.getReclamation().getId());
            else {
                preparedStatement.setInt(7, 0);
            }
            preparedStatement.setInt(8, entity.getId());
            preparedStatement.executeUpdate();
            System.out.println("Dossier de réclamation mis à jour avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM dossier_reclamation WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Dossier de réclamation supprimé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DossierReclamation getById(int id) {
        String query = "SELECT * FROM dossier_reclamation WHERE id = ?";
        DossierReclamation dossierReclamation = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                dossierReclamation = new DossierReclamation(
                        resultSet.getInt("id"),
                        resultSet.getString("serie_vehicule"),
                        resultSet.getString("image_recto_cin"),
                        resultSet.getString("image_verso_cin"),
                        resultSet.getString("image_carte_grise"),
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getTimestamp("date")
                        /* You may need to retrieve and set the Reclamation object here */
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dossierReclamation;
    }

    @Override
    public List<DossierReclamation> getAll() {
        List<DossierReclamation> dossierReclamationList = new ArrayList<>();
        String query = "SELECT * FROM dossier_reclamation";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ServiceReclamation sr=new ServiceReclamation();
            while (resultSet.next()) {
                DossierReclamation dossierReclamation = new DossierReclamation(
                        resultSet.getInt("id"),
                        resultSet.getString("serie_vehicule"),
                        resultSet.getString("image_recto_cin"),
                        resultSet.getString("image_verso_cin"),
                        resultSet.getString("image_carte_grise"),
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getTimestamp("date"),
                        sr.getById(resultSet.getInt("reclamation_id"))
                );
                dossierReclamationList.add(dossierReclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dossierReclamationList;
    }

    public List<DossierReclamation> getAllByMatricule(String matricule) {
        List<DossierReclamation> dossierReclamationList = new ArrayList<>();
        String query = "SELECT * FROM dossier_reclamation WHERE serie_vehicule LIKE ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + matricule + "%"); // Replace serieVehicule with the value you want to search
            ResultSet resultSet = preparedStatement.executeQuery();
            ServiceReclamation sr = new ServiceReclamation();
            while (resultSet.next()) {
                DossierReclamation dossierReclamation = new DossierReclamation(
                        resultSet.getInt("id"),
                        resultSet.getString("serie_vehicule"),
                        resultSet.getString("image_recto_cin"),
                        resultSet.getString("image_verso_cin"),
                        resultSet.getString("image_carte_grise"),
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getTimestamp("date"),
                        sr.getById(resultSet.getInt("reclamation_id"))
                );
                dossierReclamationList.add(dossierReclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dossierReclamationList;
    }

    public int createReturnsID(DossierReclamation entity) {
            String query = "INSERT INTO dossier_reclamation (id, serie_vehicule, image_recto_cin, image_verso_cin, image_carte_grise, id_utilisateur, date, reclamation_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1; // Initialize with a default value

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getSerieVehicule());
            preparedStatement.setString(3, entity.getImageRectoCin());
            preparedStatement.setString(4, entity.getImageVersoCin());
            preparedStatement.setString(5, entity.getImageCarteGrise());
            preparedStatement.setInt(6, entity.getIdUtilisateur());
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(entity.getDate().getTime()));
            if (entity.getReclamation() != null)
            {
                preparedStatement.setInt(8, entity.getReclamation().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1); // Retrieve the generated ID
            };
                return generatedId;

            }
                else {
                    String qry = "INSERT INTO dossier_reclamation (id, serie_vehicule, image_recto_cin, image_verso_cin, image_carte_grise, id_utilisateur, date) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = connection.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getId());
                ps.setString(2, entity.getSerieVehicule());
                ps.setString(3, entity.getImageRectoCin());
                ps.setString(4, entity.getImageVersoCin());
                ps.setString(5, entity.getImageCarteGrise());
                ps.setInt(6, entity.getIdUtilisateur());
                ps.setTimestamp(7, new java.sql.Timestamp(entity.getDate().getTime()));
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1); // Retrieve the generated ID
                };
                return generatedId;
                }

        } catch (SQLException e) {
                e.printStackTrace();
            }
        return 0;
        }
    }
