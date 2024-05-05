package Service;

import Entities.Demande;
import Utils.Datasource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceDemande implements IService<Demande> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(Demande demande) {
        String query = "INSERT INTO demande (user_id, nom_victime, num_constat, date_realisation, localisation, etat_demande, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, demande.getUser_id());
            stmt.setString(2, demande.getNom_victime());
            stmt.setString(3, demande.getNum_constat());
            java.util.Date utilDate = demande.getDate_realisation();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            stmt.setDate(4, sqlDate);
            stmt.setString(5, demande.getLocalisation());
            stmt.setString(6, demande.getEtat_demande());
            stmt.setFloat(7, demande.getLatitude());
            stmt.setFloat(8, demande.getLongitude());

            stmt.executeUpdate();

            // Retrieve the generated key (demande ID)
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    demande.setId(generatedKeys.getInt(1));
                }
            } catch (SQLException e) {
               showErrorMessage(e.getMessage());
                return;
            }
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            showErrorMessage(e.getMessage());
            return;
        } catch (SQLException e) {
           System.out.println(e.getMessage());
           return;
        }

    }
    @Override
    public void update(Demande demande) throws SQLException {
        String query = "UPDATE demande SET user_id = ?, nom_victime = ?, num_constat = ?, date_realisation = ?, localisation = ?, etat_demande = ? latitude = ?,longitude = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demande.getUser_id());
            stmt.setString(2, demande.getNom_victime());
            stmt.setString(3, demande.getNum_constat());
            java.util.Date utilDate = demande.getDate_realisation();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            stmt.setDate(4, sqlDate);
            stmt.setString(5, demande.getLocalisation());
            stmt.setString(6, demande.getEtat_demande());
            stmt.setFloat(7,demande.getLatitude());
            stmt.setFloat(8,demande.getLongitude());

            stmt.setInt(9, demande.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Demande demande) throws SQLException {
        String query = "DELETE FROM demande WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demande.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Demande findById(int id) throws SQLException {
        String query = "SELECT * FROM demande WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createDemandeFromResultSet(rs);
                }
            }
        }
        // If no demande is found, return null or throw an exception
        return null;
    }

    @Override
    public List<Demande> ReadAll() throws SQLException {
        List<Demande> demandes = new ArrayList<>();
        String query = "SELECT * FROM demande";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Demande demande = createDemandeFromResultSet(rs);
                demandes.add(demande);
            }
        }
        return demandes;
    }

    private Demande createDemandeFromResultSet(ResultSet rs) throws SQLException {
        Demande demande =  new Demande(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("nom_victime"),
                rs.getString("num_constat"),
                rs.getDate("date_realisation"),
                rs.getString("localisation"),
                rs.getString("etat_demande")
        );
        demande.setLatitude(rs.getFloat("latitude"));
        demande.setLongitude(rs.getFloat("longitude"));
        return demande;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    public List<Demande> searchByNomVictime(String nomVictime) throws SQLException {
        List<Demande> demandes = new ArrayList<>();
        Connection conn = Datasource.getConn();
        String query = "SELECT * FROM demande WHERE nom_victime LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" + nomVictime + "%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Demande demande = createDemandeFromResultSet(rs);
                    demandes.add(demande);
        }

        return demandes;
    }
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error Message");
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait();
    }
}