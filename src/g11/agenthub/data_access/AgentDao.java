package src.g11.agenthub.data_access;

import src.g11.agenthub.db_connect.DbConnection;
import src.g11.agenthub.data_transfer.AgentDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class AgentDao extends BuildTableModel {
    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;

    @SuppressWarnings("resource")
    public AgentDao() {
        try {
            con = new DbConnection().getConnection();
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAgentDao(AgentDto agentdto) {
        try {
            String query = "SELECT * FROM agents WHERE fullname='" + agentdto.getFullName() + "' AND Email='"
                    + agentdto.getEmail() + "' AND phone='" + agentdto.getPhone() + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Same agent has already been added!");
            } else {
                addFunction(agentdto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFunction(AgentDto agentdto) {
        try {
            String agentCode = null;
            String oldAgentCode = null;
            String query1 = "SELECT * FROM agents";
            rs = stmt.executeQuery(query1);
            if (!rs.next()) {
                agentCode = "age" + "1";
            } else {
                String query2 = "SELECT * FROM agents ORDER by sid DESC";
                rs = stmt.executeQuery(query2);
                if (rs.next()) {
                    oldAgentCode = rs.getString("agentcode");
                    Integer scode = Integer.parseInt(oldAgentCode.substring(3));
                    scode++;
                    agentCode = "age" + scode;
                }
            }
            String q = "INSERT INTO agents VALUES(null,?,?,?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(q);
            pstmt.setString(1, agentCode);
            pstmt.setString(2, agentdto.getFullName());
            pstmt.setString(3, agentdto.getEmail());
            pstmt.setString(4, agentdto.getPhone());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editAgentDao(AgentDto agentdto) {
        try {
            String query = "UPDATE agents SET fullname=?,Email=?,phone=? WHERE agentcode=?";
            pstmt = (PreparedStatement) con.prepareStatement(query);
            pstmt.setString(1, agentdto.getFullName());
            pstmt.setString(2, agentdto.getEmail());
            pstmt.setString(3, agentdto.getPhone());
            pstmt.setString(4, agentdto.getAgentCode());
            pstmt.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nothing updated! Click the table data first!");
        }
    }

    public void deleteAgentDao(String value) {
        try {
            String query = "delete from agents where agentcode=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT agentcode AS agentcode, fullname AS Name, Email as Address, phone AS Phone FROM agents";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getSearchagentsQueryResult(String searchTxt) {
        try {
            String query = "SELECT agentcode AS agentcode, fullname AS Name, Email as Address, phone AS Phone FROM agents WHERE fullname LIKE '%"
                    + searchTxt + "%' OR Email LIKE '%" + searchTxt + "%' OR agentcode LIKE '%" + searchTxt
                    + "%' OR phone LIKE '%" + searchTxt + "%'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getAgentByCode(String agentCode) throws SQLException {
        ResultSet rs = null;
        String query = "SELECT fullname, Email, phone FROM agents WHERE agentcode = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, agentCode);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rs;
    }

}
