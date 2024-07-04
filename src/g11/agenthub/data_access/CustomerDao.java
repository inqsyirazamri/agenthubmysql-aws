package src.g11.agenthub.data_access;

import src.g11.agenthub.db_connect.DbConnection;
import src.g11.agenthub.data_transfer.CustomerDto;

import src.g11.agenthub.gui.CustomerPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CustomerDao extends BuildTableModel {
    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;

    @SuppressWarnings("resource")
    public CustomerDao() {
        try {
            con = new DbConnection().getConnection();
            stmt = con.createStatement();
            // stmt1 = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public ResultSet getCustomersInfo() {
    // try {
    // String query = "SELECT * FROM customers";
    // rs = stmt.executeQuery(query);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return rs;
    // }

    public String getCustomerName(String customerCodeTxt) {
        String customerName = null;
        try {
            String query = "SELECT customerName FROM customers WHERE customerCode=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, customerCodeTxt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                customerName = rs.getString("customerName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerName;
    }

    // public String getCustomerPhone(String customerCodeTxt) {
    // String customerPhone = null;
    // try {
    // String query = "SELECT phone FROM customers WHERE customerCode=?";
    // pstmt = con.prepareStatement(query);
    // pstmt.setString(1, customerCodeTxt);
    // rs = pstmt.executeQuery();
    // if (rs.next()) {
    // customerPhone = rs.getString("phone");
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return customerPhone;
    // }

    // public String getCustomerShippingAddress(String customerCodeTxt) {
    // String customerShippingAddress = null;
    // try {
    // String query = "SELECT shippingAddress FROM customers WHERE customerCode=?";
    // pstmt = con.prepareStatement(query);
    // pstmt.setString(1, customerCodeTxt);
    // rs = pstmt.executeQuery();
    // if (rs.next()) {
    // customerShippingAddress = rs.getString("shippingAddress");
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return customerShippingAddress;
    // }

    public void addCustomerDao(CustomerDto customerdto) {
        try {
            // String query = "SELECT * FROM customers WHERE customerCode='" +
            // CustomerDto.getCustomerCode() + "' AND email='"
            // + UserDto.getEmail() + "' AND phone='" + UserDto.getPhone() + "' AND role='"
            // + UserDto.getRole() + "'";
            // rs = stmt.executeQuery(query);
            String query = "SELECT * FROM customers WHERE customerName=? AND phone=? AND shippingAddress=? AND sid=? AND customerNotes=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, customerdto.getCustomerName());
            pstmt.setString(3, customerdto.getPhone());
            pstmt.setString(2, customerdto.getShippingAddress());
            pstmt.setString(3, customerdto.getSalesAgentId());
            pstmt.setString(4, customerdto.getCustomerNotes());
            pstmt.setString(5, customerdto.getCustomerCode());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Customer already exists");
            } else {
                addFunction(customerdto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFunction(CustomerDto customerdto) {
        try {
            String CustomerCode = null;
            String oldCustomerCode = null;
            String query1 = "SELECT * FROM customers";
            rs = stmt.executeQuery(query1);
            if (!rs.next()) {
                CustomerCode = "cust" + "1";
            } else {
                String query2 = "SELECT * FROM customers ORDER by cid DESC";
                rs = stmt.executeQuery(query2);
                if (rs.next()) {
                    oldCustomerCode = rs.getString("customerCode");
                    Integer ucode = Integer.parseInt(oldCustomerCode.substring(4));
                    ucode++;
                    CustomerCode = "cust" + ucode;
                }
            }
            String query = "INSERT INTO customers VALUES (null,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, customerdto.getCustomerCode());
            pstmt.setString(2, customerdto.getCustomerName());
            pstmt.setString(3, customerdto.getPhone());
            pstmt.setString(4, customerdto.getShippingAddress());
            pstmt.setString(5, customerdto.getSalesAgentId());
            pstmt.setString(6, customerdto.getCustomerNotes());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Customer added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editCustomerDao(CustomerDto customerdto) {
        try {
            String query = "UPDATE customers SET customerName=?, phone=?, shippingAddress=?, sid=?, customerNotes=? WHERE customerCode=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, customerdto.getCustomerName());
            pstmt.setString(2, customerdto.getPhone());
            pstmt.setString(3, customerdto.getShippingAddress());
            pstmt.setString(4, customerdto.getSalesAgentId());
            pstmt.setString(5, customerdto.getCustomerNotes());
            pstmt.setString(6, customerdto.getCustomerCode());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomerDao(String value) {
        try {
            String query = "DELETE FROM customers WHERE customerCode=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted..");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT cid, customerName, phone, shippingAddress FROM customers ORDER BY cid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getSearchProductsQueryResult(String query) {
        try {
            String searchQuery = "SELECT cid, customerName, phone, shippingAddress FROM customers WHERE customerName LIKE ? OR phone LIKE ? OR shippingAddress LIKE ? ORDER BY cid";
            pstmt = con.prepareStatement(searchQuery);
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            pstmt.setString(3, "%" + query + "%");
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        Vector<String> columnNames = getColumnNames(rs);
        Vector<Vector<Object>> data = tableModel(rs, columnNames);

        return new DefaultTableModel(data, columnNames);
    }

    public Vector<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        return columnNames;
    }

    public Vector<Vector<Object>> tableModel(ResultSet rs, Vector<String> columnNames) throws SQLException {
        int columnCount = columnNames.size();
        Vector<Vector<Object>> data = new Vector<>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return data;
    }
}
