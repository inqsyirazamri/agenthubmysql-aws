package src.g11.agenthub.data_access;

import src.g11.agenthub.db_connect.DbConnection;
import src.g11.agenthub.data_transfer.ProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductDao {
    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs1 = null;
    Statement stmt1 = null;
    ResultSet rs = null;

    @SuppressWarnings("resource")
    public ProductDao() {
        try {
            con = new DbConnection().getConnection();
            stmt = con.createStatement();
            stmt1 = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getCustomersInfo() {
        try {
            String query = "SELECT * FROM customers";
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getProductInfo() {
        try {
            String query = "SELECT * FROM currentstocks";
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getProductsName() {
        try {
            String query = "SELECT * FROM products";
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public Double getProductAgentPrice(String productCodeTxt) {
        Double agentPrice = null;
        try {
            String query = "SELECT agentprice FROM products WHERE productcode='" + productCodeTxt + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                agentPrice = rs.getDouble("agentprice");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agentPrice;
    }

    public Double getProductSellingPrice(String productCodeTxt) {
        Double sellingPrice = null;
        try {
            String query = "SELECT sellingprice FROM products WHERE productcode='" + productCodeTxt + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                sellingPrice = rs.getDouble("sellingprice");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sellingPrice;
    }

    String productCode;

    public String getProductCode(String productsName) {
        try {
            String query = "SELECT productcode FROM products WHERE productname='" + productsName + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                productCode = rs.getString("productcode");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productCode;
    }

    public int getCustomerCode(String customerName) {
        int customerCode = -1;
        String query = "SELECT cid FROM customers WHERE customername = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, customerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customerCode = rs.getInt("cid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCode;
    }

    public void addProductDao(ProductDto productdto) {
        try {
            String query = "SELECT * FROM products WHERE productname='" + productdto.getProductName()
                    + "' AND agentprice='" + productdto.getAgentPrice() + "' AND sellingprice='"
                    + productdto.getSellingPrice() + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Same Product has already been added!");
            } else {
                addFunction(productdto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addFunction(ProductDto productdto) {
        try {
            String productCode = null;
            String oldProductCode = null;
            String query1 = "SELECT * FROM products";
            rs = stmt.executeQuery(query1);
            if (!rs.next()) {
                productCode = "prod" + "1";
            } else {
                String query2 = "SELECT * FROM products ORDER by pid DESC";
                rs = stmt.executeQuery(query2);
                if (rs.next()) {
                    oldProductCode = rs.getString("productcode");
                    Integer pcode = Integer.parseInt(oldProductCode.substring(4));
                    pcode++;
                    productCode = "prod" + pcode;
                }
            }
            String q = "INSERT INTO products VALUES(null,?,?,?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(q);
            pstmt.setString(1, productCode);
            pstmt.setString(2, productdto.getProductName());
            pstmt.setDouble(3, productdto.getAgentPrice());
            pstmt.setDouble(4, productdto.getSellingPrice());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPurchaseDao(ProductDto productdto) {
        try {
            String q = "INSERT INTO purchaseinfo (cid, pid, productname, quantity, purchaseDate, totalcost) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(q);
            pstmt.setInt(1, productdto.getCustomerId()); // Assuming ProductDto has a customerId field
            pstmt.setInt(2, productdto.getProductId()); // Assuming ProductDto has a productId field
            pstmt.setString(3, productdto.getProductName());
            pstmt.setInt(4, productdto.getQuantity());
            pstmt.setString(5, productdto.getPurchaseDate());
            pstmt.setDouble(6, productdto.getTotalCost());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getProductNameByCode(String productCode) {
        String productName = null;
        String query = "SELECT productname FROM products WHERE productCode = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, productCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                productName = rs.getString("productname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productName;
    }

    public int getCustomerIdByName(String customerName) {
        int customerId = -1;
        String query = "SELECT cid FROM customers WHERE customername = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, customerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getInt("cid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }

    public int getProductIdByCode(String productCode) {
        int productId = -1;
        String query = "SELECT pid FROM products WHERE productcode = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, productCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                productId = rs.getInt("pid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productId;
    }

    public void editProductDao(ProductDto productdto) {
        try {
            String query = "UPDATE products SET productname=?,agentprice=?,sellingprice=? WHERE productcode=?";
            pstmt = (PreparedStatement) con.prepareStatement(query);
            // pstmt.setString(1, productdto.getProductCode());
            pstmt.setString(1, productdto.getProductName());
            pstmt.setDouble(2, productdto.getAgentPrice());
            pstmt.setDouble(3, productdto.getSellingPrice());
            pstmt.setString(4, productdto.getProductCode());
            // pstmt.setInt(5, productdto.getProductId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editStock(String val, int q) {
        try {
            String query = "SELECT * FROM currentstocks WHERE productcode = '" + val + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                String qry = "UPDATE currentstocks SET quantity=quantity-? WHERE productcode=?";
                pstmt = (PreparedStatement) con.prepareStatement(qry);
                pstmt.setDouble(1, q);
                pstmt.setString(2, val);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProductDao(String value) {
        try {
            String query = "delete from products where productcode=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deletePurchaseDao(int purchaseId) {
        try {
            String q = "DELETE FROM purchaseinfo WHERE purchaseid = ?";
            pstmt = con.prepareStatement(q);
            pstmt.setInt(1, purchaseId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT productcode,productname,agentprice,sellingprice FROM products ORDER BY pid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getPurchaseResult() { // modify the purchaseinfo table for easier viewing. Eg: instead of viewing
                                           // the id, view the name.
        try {
            String query = "SELECT purchaseinfo.purchaseid, products.productname, customers.customerName, purchaseinfo.quantity, purchaseinfo.purchaseDate, purchaseinfo.totalcost "
                    +
                    "FROM purchaseinfo " +
                    "INNER JOIN products ON purchaseinfo.pid = products.pid " +
                    "INNER JOIN customers ON purchaseinfo.cid = customers.cid " +
                    "ORDER BY purchaseinfo.purchaseid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getSearchProductsQueryResult(String searchTxt) {
        try {
            String query = "SELECT pid,productcode,productname,agentprice,sellingprice FROM products WHERE productname LIKE '%"
                    + searchTxt + "%' OR productcode LIKE '%" + searchTxt + "%'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getSearchPurchaseQueryResult(String searchTxt) {
        try {
            String query = "SELECT purchaseid,purchaseinfo.productcode,productname,quantity,totalcost FROM purchaseinfo INNER JOIN products ON products.productcode=purchaseinfo.productcode WHERE purchaseinfo.productcode LIKE '%"
                    + searchTxt + "%' OR productname LIKE '%" + searchTxt + "%' ORDER BY purchaseid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getProductName(String pcode) {
        try {
            String query = "SELECT productname FROM products WHERE productcode='" + pcode + "'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // public String getProductsCustomer(int id) {
    // String cus = null;
    // try {
    // String query = "SELECT fullname FROM customers INNER JOIN salesreport ON
    // customers.customercode=salesreport.customercode WHERE salesid='"
    // + id + "'";
    // rs = stmt.executeQuery(query);
    // if (rs.next()) {
    // cus = rs.getString("fullname");
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // return cus;
    // }

    public String getPurchasedDate(int pur) {
        String p = null;
        try {
            String query = "SELECT purchaseDate FROM purchaseinfo WHERE purchaseid='" + pur + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                p = rs.getString("purchaseDate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        Vector<String> columnNames = getColumnNames(rs);
        Vector<Vector<Object>> data = tableModel(rs, columnNames);

        return new DefaultTableModel(data, columnNames);
    }

    public Vector<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData(); // resultset ko metadata
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        return columnNames;
    }

    public Vector<Vector<Object>> tableModel(ResultSet rs, Vector<String> columnNames) throws SQLException {
        int columnCount = columnNames.size();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return data;
    }
}
