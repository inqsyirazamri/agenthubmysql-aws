package src.g11.agenthub.gui;

import src.g11.agenthub.data_access.ProductDao;
import src.g11.agenthub.data_transfer.ProductDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class ProductPage extends JDialog {
    private ProductDto productdto;
    private JTextField productCodeTxt;
    private JTextField productNameTxt;
    private JTextField agentPriceTxt;
    private JTextField sellingPriceTxt;
    private JButton addProductBtn;
    private JButton editProductBtn;
    private JButton deleteProductBtn;
    private JButton clearBtn;
    private JButton refreshBtn;
    private JButton searchBtn;
    private JTable table;
    private JTextField searchTxt;
    private JLabel searchByLbl;

    public ProductPage(String user) {
        initComponents();
        productCodeTxt.setVisible(false);
        loadDatas();
        setTitle("Product Management");
        setSize(650, 500);
        Point p = new Point(420, 200);
        setLocation(p);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        // Create GUI components
        productCodeTxt = new JTextField(20);
        productNameTxt = new JTextField(20);
        agentPriceTxt = new JTextField(20);
        sellingPriceTxt = new JTextField(20);
        addProductBtn = new JButton("Add Product");
        editProductBtn = new JButton("Edit Product");
        deleteProductBtn = new JButton("Delete Product");
        clearBtn = new JButton("Clear");
        refreshBtn = new JButton("Refresh");
        searchBtn = new JButton("Search");
        table = new JTable();
        searchTxt = new JTextField(20);
        searchByLbl = new JLabel("Search by Product Name / Product Code");

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
        eastPanel.add(new JLabel("Product Name:"), gbc);
        gbc.gridy = 4;
        eastPanel.add(productNameTxt, gbc);

        gbc.gridy = 5;
        eastPanel.add(new JLabel("Agent Price:"), gbc);
        gbc.gridy = 6;
        eastPanel.add(agentPriceTxt, gbc);

        gbc.gridy = 7;
        eastPanel.add(new JLabel("Selling Price:"), gbc);
        gbc.gridy = 8;
        eastPanel.add(sellingPriceTxt, gbc);

        gbc.gridy = 9;
        eastPanel.add(addProductBtn, gbc);
        gbc.gridy = 10;
        eastPanel.add(editProductBtn, gbc);
        gbc.gridy = 11;
        eastPanel.add(deleteProductBtn, gbc);
        gbc.gridy = 12;
        eastPanel.add(clearBtn, gbc);
        gbc.gridy = 13;
        eastPanel.add(refreshBtn, gbc);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        table.addMouseListener(new MouseAdapter() {
            // @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });

        // Add listeners
        addProductBtn.addActionListener(e -> addProduct());
        editProductBtn.addActionListener(e -> editProduct());
        deleteProductBtn.addActionListener(e -> deleteProduct());
        clearBtn.addActionListener(e -> clear());
        refreshBtn.addActionListener(e -> loadDatas());
        searchBtn.addActionListener(e -> searchDatas());
        searchTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchDatas();
            }
        });
    }

    private void loadDatas() {
        try {
            ProductDao productdao = new ProductDao();
            table.setModel(productdao.buildTableModel(productdao.getQueryResult()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    static String productCode;

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.getSelectedRow();
        int column = table.getColumnCount();
        Object[] val = new Object[column];
        for (int i = 0; i < column; i++) {
            val[i] = table.getValueAt(row, i);
        }

        productCodeTxt.setText(val[0].toString());
        productNameTxt.setText(val[1].toString());
        agentPriceTxt.setText(val[2].toString());
        sellingPriceTxt.setText(val[3].toString());
        productCode = val[0].toString();
    }

    private void addProduct() {
        productdto = new ProductDto();
        if (productNameTxt.getText().equals("") || agentPriceTxt.getText().equals("")
                || sellingPriceTxt.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields!");
        } else {
            // productdto.setProductCode(productCodeTxt.getText());
            productdto.setProductName(productNameTxt.getText());
            productdto.setAgentPrice(Double.parseDouble(agentPriceTxt.getText()));
            productdto.setSellingPrice(Double.parseDouble(sellingPriceTxt.getText()));
            new ProductDao().addProductDao(productdto);
            JOptionPane.showMessageDialog(this, "Product Added Successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadDatas(); // refresh the table with the updated data
            clear();
        }
    }

    private void editProduct() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(
                    null, "Select a table data first!");
        } else {
            productdto = new ProductDto();
            if (productNameTxt.getText().equals("") || agentPriceTxt.getText().equals("")
                    || sellingPriceTxt.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
            } else {
                productdto.setProductCode(productCodeTxt.getText());
                productdto.setProductName(productNameTxt.getText());
                productdto.setAgentPrice(Double.parseDouble(agentPriceTxt.getText()));
                productdto.setSellingPrice(Double.parseDouble(sellingPriceTxt.getText()));
                new ProductDao().editProductDao(productdto);
                JOptionPane.showMessageDialog(this, "Product Updated Successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadDatas();
            }
        }
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        String productCode = (String) table.getValueAt(row, 0);
        ProductDao productdao = new ProductDao();
        productdao.deleteProductDao(productCode);
        JOptionPane.showMessageDialog(this, "Product Deleted Successfully", "Success",
                JOptionPane.INFORMATION_MESSAGE);
        loadDatas();
        clear();
    }

    private void searchDatas() {
        try {
            ProductDao productdao = new ProductDao();
            String query = searchTxt.getText();
            if (query.isEmpty()) {
                table.setModel(productdao.buildTableModel(productdao.getQueryResult()));
            } else {
                table.setModel(productdao.buildTableModel(productdao.getSearchProductsQueryResult(query)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clear() {
        // productCodeTxt.setText("");
        productNameTxt.setText("");
        agentPriceTxt.setText("");
        sellingPriceTxt.setText("");
    }

    @SuppressWarnings("unused")
    private void populateDatas(ProductDto productdto) {
        productNameTxt.setText(productdto.getProductName());
        agentPriceTxt.setText(String.valueOf(productdto.getAgentPrice()));
        sellingPriceTxt.setText(String.valueOf(productdto.getSellingPrice()));
    }

    @SuppressWarnings("unused")
    private class MyTableModel extends DefaultTableModel {
        public MyTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}