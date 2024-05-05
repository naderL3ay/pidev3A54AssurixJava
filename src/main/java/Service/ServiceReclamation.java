package Service;

import Entities.Reclamation;

import java.sql.*;
import java.util.List;

import Entities.Reclamation;
import Utils.Myconnection;

import java.util.ArrayList;
import java.util.List;


public class ServiceReclamation implements CrudInterface<Reclamation> {

    private Connection connection;

    public ServiceReclamation() {
        this.connection = Myconnection.getInstance().getCnx();
    }
    public int createReturnsID(Reclamation entity) {
        String query = "INSERT INTO reclamation (titre, reclamation) VALUES (?, ?)";
        int generatedId = -1; // Initialize to an invalid ID
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getTitre());
            preparedStatement.setString(2, entity.getReclamation());
            preparedStatement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            System.out.println("Reclamation created successfully with ID: " + generatedId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public void create(Reclamation entity) {
        String query = "INSERT INTO reclamation (id, titre, reclamation) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getTitre());
            preparedStatement.setString(3, entity.getReclamation());
            preparedStatement.executeUpdate();
            System.out.println("Reclamation created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Reclamation entity) {
        String query = "UPDATE reclamation SET titre = ?, reclamation = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getTitre());
            preparedStatement.setString(2, entity.getReclamation());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
            System.out.println("Reclamation updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM reclamation WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Reclamation deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reclamation getById(int id) {
        String query = "SELECT * FROM reclamation WHERE id = ?";
        Reclamation reclamation = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reclamation = new Reclamation(resultSet.getInt("id"), resultSet.getString("titre"), resultSet.getString("reclamation"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamation;
    }

    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> reclamationList = new ArrayList<>();
        String query = "SELECT * FROM reclamation";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation(resultSet.getInt("id"), resultSet.getString("titre"), resultSet.getString("reclamation"));
                reclamationList.add(reclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamationList;
    }
}
