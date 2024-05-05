package Service;

import Entities.Admin;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAdmin implements IService<Admin> {
    private Connection connection= Datasource.getConn();

    public ServiceAdmin() {
    }

    @Override
    public void add(Admin admin) throws SQLException {
        String query = "INSERT INTO admins (email, password, name) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getEmail());
            statement.setString(2, admin.getPassword());
            statement.setString(3, admin.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Admin admin) throws SQLException {
        String query = "UPDATE admins SET email = ?, password = ?, name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getEmail());
            statement.setString(2, admin.getPassword());
            statement.setString(3, admin.getName());
            statement.setInt(4, admin.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Admin admin) throws SQLException {
        String query = "DELETE FROM admins WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, admin.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public Admin findById(int id) throws SQLException {
        String query = "SELECT * FROM admins WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
                admin.setName(resultSet.getString("name"));
                return admin;
            }
            return null; // No admin found with the given ID
        }
    }

    @Override
    public List<Admin> ReadAll() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admins";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
                admin.setName(resultSet.getString("name"));
                admins.add(admin);
            }
        }
        return admins;
    }
    public Admin Login(String email, String password) throws SQLException {
        String query = "SELECT * FROM admins WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
                admin.setName(resultSet.getString("name"));
                return admin; // Admin found with the given email and password
            }
        }
        return null; // No admin found with the given email and password
    }

}
