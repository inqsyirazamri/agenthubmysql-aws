package src.g11.agenthub.gui;

import src.g11.agenthub.data_access.ProductDao;
import src.g11.agenthub.data_transfer.ProductDto;
import src.g11.agenthub.db_connect.DbConnection;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class PurchasePage extends JDialog {
    ProductDto productdto;
    // private JComboBox<String> comboBox;
    private javax.swing.JComboBox comboBox;
    private JTable table;
    private JTextField productCodeTxt;
    // private JTextField sellingpriceTxt;
    private JTextField quantityTxt;
    private JButton addPurchaseBtn;
    private JButton deletePurchaseBtn;
    private JButton clearBtn;
    private JButton refreshBtn;
    private JButton searchBtn;
    private JTextField searchTxt;
    private JLabel searchByLbl;
    private JLabel purchasedDateLab;
    private com.toedter.calendar.JDateChooser dateChooser;
    private JTextField customersNameTxt;
    private javax.swing.JTextField agentPriceTxt;
    private javax.swing.JLabel productNameLab;
    private JTextComponent purchaseIdTxt;
    private JLabel CustomerInfoLab;

    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs1 = null;
    Statement stmt1 = null;
    ResultSet rs = null;

    public PurchasePage() {
        try {
            con = new DbConnection().getConnection();
            stmt = con.createStatement();
            stmt1 = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        comboBox.removeAllItems();
        comboBox.addItem("Select Customer's Name");
        customerName();
        purchaseIdTxt.setVisible(false);
        productNameLab.setVisible(false);
        setTitle("Purchase Management");
        setSize(1050, 700);
        Point p = new Point(200, 120);
        setLocation(p);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
        loadDatas();
    }

    private void initComponents() {
        // Initialize and set up UI components
        customersNameTxt = new JTextField(16);
        productCodeTxt = new JTextField(16);
        agentPriceTxt = new javax.swing.JTextField(16);
        // sellingpriceTxt = new JTextField(16);
        quantityTxt = new JTextField(16);
        addPurchaseBtn = new JButton("Add Purchase");
        deletePurchaseBtn = new JButton("Delete Purchase");
        clearBtn = new JButton("Clear");
        refreshBtn = new JButton("Refresh");
        searchBtn = new JButton("Search");
        table = new JTable();
        searchTxt = new JTextField(20);
        searchByLbl = new JLabel("Search by Customer Name / Product Code");
        dateChooser = new com.toedter.calendar.JDateChooser();
        purchasedDateLab = new javax.swing.JLabel();
        comboBox = new javax.swing.JComboBox();
        productNameLab = new javax.swing.JLabel();
        purchaseIdTxt = new javax.swing.JTextField();
        CustomerInfoLab = new javax.swing.JLabel();

        // Set up GUI layout
        JPanel northPanel = new JPanel();
        northPanel.add(searchByLbl);
        northPanel.add(searchTxt);
        northPanel.add(searchBtn);

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridy = 3;
        eastPanel.add(new JLabel("Purchase Date:"), gbc);
        gbc.gridy = 4;
        eastPanel.add(dateChooser, gbc);

        gbc.gridy = 5;
        eastPanel.add(new JLabel("Customer's Name:"), gbc);
        gbc.gridy = 6;
        eastPanel.add(comboBox, gbc);

        gbc.gridy = 7;
        eastPanel.add(new JLabel("Product Code:"), gbc);
        gbc.gridy = 8;
        eastPanel.add(productCodeTxt, gbc);

        gbc.gridy = 9;
        eastPanel.add(productNameLab, gbc);

        gbc.gridy = 10;
        eastPanel.add(new JLabel("Agent Price:"), gbc);
        gbc.gridy = 11;
        eastPanel.add(agentPriceTxt, gbc);

        gbc.gridy = 12;
        eastPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridy = 13;
        eastPanel.add(quantityTxt, gbc);

        gbc.gridy = 14;
        eastPanel.add(addPurchaseBtn, gbc);
        gbc.gridy = 15;
        eastPanel.add(deletePurchaseBtn, gbc);
        gbc.gridy = 16;
        eastPanel.add(clearBtn, gbc);
        gbc.gridy = 17;
        eastPanel.add(refreshBtn, gbc);

        productNameLab.setFont(new java.awt.Font("Serif", 0, 9));
        productNameLab.setForeground(java.awt.Color.GRAY);

        agentPriceTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agentPriceTxtActionPerformed(evt);
            }
        });
        agentPriceTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                agentPriceTxtKeyReleased(evt);
            }
        });

        productCodeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productCodeTxtActionPerformed(evt);
            }
        });
        productCodeTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                productCodeTxtKeyReleased(evt);
            }
        });

        comboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Customer's Name" }));
        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        // // TEMPORARY
        // CustomerInfoLab.setForeground(Color.WHITE);
        // CustomerInfoLab.setText("Account Summary by Day: " +
        // "\nTotal Customer: 1 || " +
        // "\nTotal Invoice: RM0 || " +
        // "\nTotal Sales: RM0 || " +
        // "\nTotal Income: RM0");

        // // Create the table and scroll pane
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create the west panel and add the scroll pane
        JPanel westPanel = new JPanel();
        westPanel.add(scrollPane);

        // Create the center panel and add the scroll pane
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.add(CustomerInfoLab);

        // Add the panels to the frame
        this.add(northPanel, BorderLayout.NORTH);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        table.addMouseListener(new MouseAdapter() {
            // @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });

        addPurchaseBtn.addActionListener(evt -> addPurchase());
        deletePurchaseBtn.addActionListener(evt -> deletePurchase());
        clearBtn.addActionListener(evt -> clear());
        refreshBtn.addActionListener(evt -> loadDatas());
        searchBtn.addActionListener(evt -> searchDatas());
        searchTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchDatas();
            }
        });
    }

    private void productCodeTxtActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void productCodeTxtKeyReleased(java.awt.event.KeyEvent evt) {
        productNameLab.setVisible(true);

        try {
            ResultSet rs = new ProductDao().getProductName(productCodeTxt.getText());
            if (rs.next()) {
                productNameLab.setText(rs.getString("productname"));
                Double agentPrice = new ProductDao().getProductAgentPrice(productCodeTxt.getText());
                agentPriceTxt.setText(agentPrice.toString());
            } else {
                productNameLab.setText("Not associated with any Products!");
                // JOptionPane.showMessageDialog(this, "The product code does not exist!",
                // "Error",
                // JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void agentPriceTxtActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void agentPriceTxtKeyReleased(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void customerName() {
        ResultSet rs = new ProductDto().getCustomersName();
        try {
            // Add the customer names to the comboBox
            while (rs.next()) {
                String customersName = rs.getString("customerName");
                comboBox.addItem(customersName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        comboBox.removeAllItems();
        comboBox.addItem("Select Customer's Name");
        customerName();
        customersNameTxt.setText("");
        productCodeTxt.setText("");
        agentPriceTxt.setText("");
        quantityTxt.setText("");
        dateChooser.setDate(null);
    }

    private void deletePurchase() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Select a table data first!");
        } else {
            int purchaseId = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
            new ProductDao().deletePurchaseDao(purchaseId);
            JOptionPane.showMessageDialog(this, "Purchase Deleted Successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadDatas();
        }
    }

    private void addPurchase() {
        ProductDto productdto = new ProductDto();
        if (dateChooser.getDate() == null
                || comboBox.getSelectedItem() == null
                || productCodeTxt.getText().isEmpty()
                || quantityTxt.getText().isEmpty()
                || agentPriceTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields!");
        } else {
            String customer = (String) comboBox.getSelectedItem();
            if ("SELECT CUSTOMER'S NAME".equalsIgnoreCase(customer)) {
                JOptionPane.showMessageDialog(null, "Please select a customer and try again!");
            } else {
                int customerId = new ProductDao().getCustomerIdByName(customer);
                if (customerId == -1) {
                    JOptionPane.showMessageDialog(null, "Customer does not exist!");
                    return;
                }

                productdto.setCustomerId(customerId); // Set the correct customer ID

                String productCode = productCodeTxt.getText();
                int productId = new ProductDao().getProductIdByCode(productCode);
                if (productId == -1) {
                    JOptionPane.showMessageDialog(null, "Product does not exist!");
                    return;
                }

                productdto.setProductId(productId); // Set the correct product ID
                productdto.setProductName(new ProductDao().getProductNameByCode(productCode));
                productdto.setPurchaseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateChooser.getDate()));
                productdto.setQuantity(Integer.parseInt(quantityTxt.getText()));
                Double agentPrice = Double.parseDouble(agentPriceTxt.getText());
                Double totalCost = agentPrice * Integer.parseInt(quantityTxt.getText());
                productdto.setTotalCost(totalCost);

                new ProductDao().addPurchaseDao(productdto);
                JOptionPane.showMessageDialog(this, "Purchase Added Successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadDatas();
            }
        }
    }

    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void searchDatas() {
        try {
            ProductDao productDao = new ProductDao();
            String query = searchTxt.getText();
            if (query.isEmpty()) {
                table.setModel(productDao.buildTableModel(productDao.getQueryResult()));
            } else {
                // String text;
                // table.setModel(productDao.buildTableModel(productDao.getSearchPurchaseQueryResult(text)));
            }
            table.setModel(productDao.buildTableModel(productDao.getQueryResult()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // // Method to adjust the column widths of the table based on the length of
    // // contents of its column
    // private void adjustColumnWidths(JTable table) {
    // for (int column = 0; column < table.getColumnCount(); column++) {
    // int width = 100; // Minimum width
    // for (int row = 0; row < table.getRowCount(); row++) {
    // TableCellRenderer renderer = table.getCellRenderer(row, column);
    // Component comp = table.prepareRenderer(renderer, row, column);
    // width = Math.max(comp.getPreferredSize().width + 10, width);
    // }
    // width =
    // Math.max(table.getTableHeader().getColumnModel().getColumn(column).getPreferredWidth(),
    // width);
    // table.getColumnModel().getColumn(column).setPreferredWidth(width);
    // }
    // }

    public void loadDatas() {
        try {
            ProductDao productDao = new ProductDao();
            table.setModel(productDao.buildTableModel(productDao.getPurchaseResult()));
            // adjustColumnWidths(table);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    int quantity;
    String productCode;

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.getSelectedRow();
        int column = table.getColumnCount();
        Object[] val = new Object[column];
        for (int i = 0; i < column; i++) {
            val[i] = table.getValueAt(row, i);
        }
        purchaseIdTxt.setText(val[0].toString());
        // String cust = new
        // ProductDao().getProductsCustomer(Integer.parseInt(purchaseIdTxt.getText()));
        // CustomerInfoLab.setText("Purchased by " + cust);
        String purchasedDate = new ProductDao().getPurchasedDate(Integer.parseInt(purchaseIdTxt.getText()));
        purchasedDateLab.setText(purchasedDate);
        quantity = Integer.parseInt(val[3].toString());
        productCode = val[1].toString();
    }
}