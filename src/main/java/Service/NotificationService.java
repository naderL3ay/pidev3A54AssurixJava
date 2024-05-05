package Service;

import Entities.Notification;
import Entities.Reclamation;
import Utils.Myconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationService{
    private Connection connection;

    public NotificationService() {
        this.connection = Myconnection.getInstance().getCnx();
    }


    public void add(Notification notification) throws SQLException {
        String query = "INSERT INTO notification (id, reclamation_id, message, link, created_at, seen) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, notification.getId());
        statement.setInt(2, notification.getReclamationId());
        statement.setString(3, notification.getMessage());
        statement.setString(4, notification.getLink());
        statement.setDate(5, new java.sql.Date(notification.getCreatedAt().getTime()));
        statement.setBoolean(6, notification.getSeen());
        System.out.println(query);

        statement.executeUpdate();
        statement.close();
    }

    public void update(Notification notification) throws SQLException {
        String query = "UPDATE notification SET reclamation_id = ?, message = ?, link = ?, created_at = ?, seen = ? "
                + "WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, notification.getReclamationId());
        statement.setString(2, notification.getMessage());
        statement.setString(3, notification.getLink());
        statement.setDate(4, new java.sql.Date(notification.getCreatedAt().getTime()));
        statement.setBoolean(5, notification.getSeen());
        statement.setInt(6, notification.getId());
        statement.executeUpdate();
        statement.close();
    }

    public void delete(Notification notification) throws SQLException {
        String query = "DELETE FROM notification WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, notification.getId());
        statement.executeUpdate();
        statement.close();
    }

    public Notification findById(int id) throws SQLException {
        String query = "SELECT * FROM notification WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Notification notification = null;
        if (resultSet.next()) {
            notification = mapResultSetToNotification(resultSet);
        }
        resultSet.close();
        statement.close();
        return notification;
    }

    public List<Notification> ReadAll() throws SQLException {
        String query = "SELECT * FROM notification";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<Notification> notificationList = new ArrayList<>();
        while (resultSet.next()) {
            Notification notification = mapResultSetToNotification(resultSet);
            notificationList.add(notification);
        }
        resultSet.close();
        statement.close();
        return notificationList;
    }

    private Notification mapResultSetToNotification(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int reclamationId = resultSet.getInt("reclamation_id");
        String message = resultSet.getString("message");
        String link = resultSet.getString("link");
        Date createdAt = resultSet.getDate("created_at");
        boolean seen = resultSet.getBoolean("seen");
        int userId = resultSet.getInt("user_id");

        ServiceReclamation rs = new ServiceReclamation();
        Reclamation reclamation = rs.getById(reclamationId);
        System.out.println(rs.getById(2));
        System.out.println(reclamationId);
        Notification notif= new Notification(id, reclamationId, message, link, createdAt, seen);
        notif.reclamation=reclamation;
        notif.userId=userId;
        return notif;
    }
    public int getTotalNotif(int userId) throws SQLException {
        String query = "SELECT COUNT(*) AS total FROM notification " +
                "JOIN reclamation ON notification.reclamation_id = reclamation.id " +
                "WHERE notification.seen = false AND reclamation.user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        int total = 0;
        if (resultSet.next()) {
            total = resultSet.getInt("total");
        }
        resultSet.close();
        statement.close();
        return total;
    }

    public List<Notification> getUserNotifications(int userid) {
        List<Notification> notifications = new ArrayList<>();
        try {
            // Assuming you have a connection object named "connection"
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM notification WHERE user_id = ?");
            if(userid!=0)
                statement.setInt(1, userid);
            else
                statement = connection.prepareStatement("SELECT * FROM notification WHERE user_id IS NULL");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Notification notification = mapResultSetToNotification(resultSet);
                notifications.add(notification);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }
    public void makeAsReadNotif(int notificationId) {
        try {
            // Assuming you have a connection object named "connection"
            PreparedStatement statement = connection.prepareStatement("UPDATE notification SET seen = true WHERE id = ?");
            statement.setInt(1, notificationId);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}