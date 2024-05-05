package Service;

import Entities.User;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {

    private Connection conn = Datasource.getConn();

    @Override
    public void add(User user) throws SQLException {
        String query = "INSERT INTO user(email, roles, password, firstname, lastname, tel_num, adresse, is_verified, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getRoles());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getFirstname());
            pstmt.setString(5, user.getLastname());
            // pstmt.setDate(6, user.getDate_d_n());
            pstmt.setString(7, user.getTel_num());
            pstmt.setString(8, user.getAdresse());
            pstmt.setBoolean(9, user.isIs_verified());
            pstmt.setString(10, user.getImage());

            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE user SET email=?, roles=?, password=?, firstname=?, lastname=?, tel_num=?, adresse=?, is_verified=?, image=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getRoles());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getFirstname());
            pstmt.setString(5, user.getLastname());
           // pstmt.setDate(6, user.getDate_d_n());
            pstmt.setString(7, user.getTel_num());
            pstmt.setString(8, user.getAdresse());
            pstmt.setBoolean(9, user.isIs_verified());
            pstmt.setString(10, user.getImage());
            pstmt.setInt(11, user.getId());

            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        String query = "DELETE FROM user WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, user.getId());

            pstmt.executeUpdate();
        }
    }

    @Override
    public User findById(int id) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        }

        return user;
    }

    @Override
    public List<User> ReadAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = createUserFromResultSet(rs);
                userList.add(user);
            }
        }
        System.out.println("userList: " + userList);
        return userList;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setRoles(rs.getString("roles"));
        user.setPassword(rs.getString("password"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setDate_d_n(rs.getDate("date_d_n"));
        user.setTel_num(rs.getString("tel_num"));
        user.setAdresse(rs.getString("adresse"));
        user.setIs_verified(rs.getBoolean("is_verified"));
        user.setImage(rs.getString("image"));

        return user;
    }
}
