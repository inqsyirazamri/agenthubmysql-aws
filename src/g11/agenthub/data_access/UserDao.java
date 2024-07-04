package src.g11.agenthub.data_access;

import src.g11.agenthub.db_connect.DbConnection;
import src.g11.agenthub.data_transfer.UserDto;
import src.g11.agenthub.gui.UserPage;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class UserDao extends BuildTableModel {

    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;

    @SuppressWarnings("resource")
    public UserDao() {
        try {
            con = new DbConnection().getConnection();
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserDao(UserDto UserDto, String user) {
        try {
            String query = "SELECT * FROM users WHERE fullname='" + UserDto.getFullName() + "' AND email='"
                    + UserDto.getEmail() + "' AND phone='" + UserDto.getPhone() + "' AND role='"
                    + UserDto.getRole() + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Same User has already been added!");
            } else {
                addFunction(UserDto, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUserQueryResult(String userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, userId); // Set the parameter value after initialization
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public void addFunction(UserDto UserDto, String user) {
        try {
            String username = null;
            String password = null;
            String oldUsername = null;
            String encPass = null;
            String query1 = "SELECT * FROM users";
            rs = stmt.executeQuery(query1);
            if (!rs.next()) {
                username = "user" + "1";
                password = "user" + "1";
            } else {
                String query2 = "SELECT * FROM users ORDER by id DESC";
                rs = stmt.executeQuery(query2);
                if (rs.next()) {
                    oldUsername = rs.getString("username");
                    Integer ucode = Integer.parseInt(oldUsername.substring(4));
                    ucode++;
                    username = "user" + ucode;
                    password = "user" + ucode;
                }
                encPass = new UserPage().encryptPassword(password);
            }

            String query = "INSERT INTO users VALUES(null,?,?,?,?,?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(query);
            pstmt.setString(1, UserDto.getFullName());
            pstmt.setString(2, UserDto.getEmail());
            pstmt.setString(3, UserDto.getPhone());
            pstmt.setString(4, username);
            pstmt.setString(5, encPass);
            pstmt.setString(6, UserDto.getRole());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void editUserDao(UserDto UserDto) {
        try {
            String query = "UPDATE users SET fullname=?,email=?,phone=?,role=? WHERE username=?";
            pstmt = (PreparedStatement) con.prepareStatement(query);
            pstmt.setString(1, UserDto.getFullName());
            pstmt.setString(2, UserDto.getEmail());
            pstmt.setString(3, UserDto.getPhone());
            pstmt.setString(4, UserDto.getRole());
            pstmt.setString(5, UserDto.getUsername());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editFunction(UserDto UserDto, String imgLink, File file) {

        try {
            if (imgLink.equals("")) {

            } else {
                String query = "UPDATE users SET fullname=?,email=?,phone=?,username=?,password=?,role=?,image=? WHERE id=?";
                FileInputStream fis = new FileInputStream(file);
                pstmt = (PreparedStatement) con.prepareStatement(query);
                pstmt.setString(1, UserDto.getFullName());
                pstmt.setString(2, UserDto.getEmail());
                pstmt.setString(3, UserDto.getPhone());
                pstmt.setString(4, UserDto.getUsername());
                pstmt.setString(5, UserDto.getPassword());
                pstmt.setString(6, UserDto.getRole());
                pstmt.setBinaryStream(7, fis, (int) file.length());
                pstmt.setInt(8, UserDto.getId());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUserDao(String value) {
        try {
            String query = "delete from users where username=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
        new UserPage().loadDatas();
    }

    public ResultSet getQueryResult1() {
        try {
            String query = "SELECT fullname,email,phone,username,role FROM users";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getUser(String user) {
        try {
            String query = "SELECT * FROM users WHERE username='" + user + "'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getPassword(String user, String pass) {
        try {
            String query = "SELECT password FROM users WHERE username='" + user + "' AND password='" + pass + "'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void changePassword(String user, String pass) {
        try {
            String query = "UPDATE users SET password=? WHERE username=?";
            pstmt = con.prepareStatement(query);
            String encPass = new UserPage().encryptPassword(pass);
            pstmt.setString(1, encPass);
            pstmt.setString(2, user);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
