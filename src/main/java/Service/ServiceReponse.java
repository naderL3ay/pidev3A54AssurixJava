package Service;

import Entities.Reponse;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements IService<Reponse> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(Reponse reponse) throws SQLException {
        String query = "INSERT INTO reponse (demande_id, titre_rapport, description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reponse.getDemande_id());
            stmt.setString(2, reponse.getTitre_rapport());
            stmt.setString(3, reponse.getDescription());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Reponse reponse) throws SQLException {
        String query = "UPDATE reponse SET demande_id = ?, titre_rapport = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reponse.getDemande_id());
            stmt.setString(2, reponse.getTitre_rapport());
            stmt.setString(3, reponse.getDescription());
            stmt.setInt(4, reponse.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Reponse reponse) throws SQLException {
        String query = "DELETE FROM reponse WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reponse.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Reponse findById(int id) throws SQLException {
        String query = "SELECT * FROM reponse WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createReponseFromResultSet(rs);
                }
            }
        }
        // If no reponse is found, return null or throw an exception
        return null;
    }

    @Override
    public List<Reponse> ReadAll() throws SQLException {
        List<Reponse> reponses = new ArrayList<>();
        String query = "SELECT * FROM reponse";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Reponse reponse = createReponseFromResultSet(rs);
                reponses.add(reponse);
            }
        }
        return reponses;
    }

    private Reponse createReponseFromResultSet(ResultSet rs) throws SQLException {
        return new Reponse(
                rs.getInt("id"),
                rs.getInt("demande_id"),
                rs.getString("titre_rapport"),
                rs.getString("description")
        );
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    public Reponse getReponseByDemandeId(int demandeId) throws SQLException {
        String query = "SELECT * FROM reponse WHERE demande_id = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demandeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createReponseFromResultSet(rs);
                }
            }
        }
        // If no response is found, return null or throw an exception
        return null;
    }

}