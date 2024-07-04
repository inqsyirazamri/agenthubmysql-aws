package src.g11.agenthub.gui;

import src.g11.agenthub.data_access.CustomerDao;
import src.g11.agenthub.data_transfer.CustomerDto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class CustomerPage extends JDialog {
    private CustomerDto customerdto;
    private JTextField customerCodeTxt;
    private JTextField customerNameTxt;
    private JTextField phoneTxt;
    private JTextField shippingAddressTxt;
    private JButton addCustomerBtn;
    private JButton editCustomerBtn;
    private JButton deleteCustomerBtn;
    private JButton clearBtn;
    private JButton refreshBtn;
    private JButton searchBtn;
    private JTable table;
    private JTextField searchTxt;
    private JLabel searchByLbl;
    private JTextField customerNotesTxt;

    public CustomerPage(String user) {
        initComponents();
        loadDatas();
        setTitle("Customer Management");
        setSize(650, 500);
        Point p = new Point(420, 200);
        setLocation(p);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        // Create GUI components
        customerCodeTxt = new JTextField(20);
        customerNameTxt = new JTextField(20);
        phoneTxt = new JTextField(20);
        shippingAddressTxt = new JTextField(20);
        customerNotesTxt = new JTextField(20);
        addCustomerBtn = new JButton("Add Customer");
        editCustomerBtn = new JButton("Edit Customer");
        deleteCustomerBtn = new JButton("Delete Customer");
        clearBtn = new JButton("Clear");
        refreshBtn = new JButton("Refresh");
        searchBtn = new JButton("Search");
        table = new JTable();
        searchTxt = new JTextField(20);
        searchByLbl = new JLabel("Search by Customer Name / Customer Code");

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
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridy = 3;
        eastPanel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridy = 4;
        eastPanel.add(customerNameTxt, gbc);

        gbc.gridy = 5;
        eastPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridy = 6;
        eastPanel.add(phoneTxt, gbc);

        gbc.gridy = 7;
        eastPanel.add(new JLabel("Shipping Address:"), gbc);
        gbc.gridy = 8;
        eastPanel.add(shippingAddressTxt, gbc);

        gbc.gridy = 9;
        eastPanel.add(new JLabel("Customer Notes:"), gbc);
        gbc.gridy = 10;
        eastPanel.add(customerNotesTxt, gbc);

        gbc.gridy = 11;
        eastPanel.add(addCustomerBtn, gbc);
        gbc.gridy = 12;
        eastPanel.add(editCustomerBtn, gbc);
        gbc.gridy = 13;
        eastPanel.add(deleteCustomerBtn, gbc);
        gbc.gridy = 14;
        eastPanel.add(clearBtn, gbc);
        gbc.gridy = 15;
        eastPanel.add(refreshBtn, gbc);

        JPanel centerPanel = new JPanel();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(centerPanel, BorderLayout.CENTER);

        addCustomerBtn.addActionListener(evt -> addCustomer());
        editCustomerBtn.addActionListener(evt -> editCustomer());
        deleteCustomerBtn.addActionListener(evt -> deleteCustomer());
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

    private void searchDatas() {
        try {
            CustomerDao customerdao = new CustomerDao();
            String query = searchTxt.getText();
            if (query.isEmpty()) {
                table.setModel(customerdao.buildTableModel(customerdao.getQueryResult()));
            } else {
                table.setModel(customerdao.buildTableModel(customerdao.getSearchProductsQueryResult(query)));
            }
            table.setModel(customerdao.buildTableModel(customerdao.getQueryResult()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadDatas() {
        try {
            CustomerDao customerdao = new CustomerDao();
            table.setModel(customerdao.buildTableModel(customerdao.getQueryResult()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addCustomer() {
        customerdto = new CustomerDto();
        if (customerNameTxt.getText().equals("") || phoneTxt.getText().equals("")
                || shippingAddressTxt.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields!");

        } else {
            customerdto.setCustomerName(customerNameTxt.getText());
            customerdto.setPhone(phoneTxt.getText());
            customerdto.setShippingAddress(shippingAddressTxt.getText());
            customerdto.setCustomerNotes(customerNotesTxt.getText());
            new CustomerDao().addCustomerDao(customerdto);
            JOptionPane.showMessageDialog(this, "Customer added successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadDatas();
            clear();
        }
    }

    private void editCustomer() {
        customerdto = new CustomerDto();
        customerdto.setCustomerName(customerNameTxt.getText());
        customerdto.setPhone(phoneTxt.getText());
        customerdto.setShippingAddress(shippingAddressTxt.getText());
        customerdto.setCustomerNotes(customerNotesTxt.getText());

        CustomerDao customerDao = new CustomerDao();
        customerDao.editCustomerDao(customerdto);
        loadDatas();
        JOptionPane.showMessageDialog(this, "Customer updated successfully", "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteCustomer() {
        int row = table.getSelectedRow();
        String customerCode = (String) table.getValueAt(row, 1);

        CustomerDao customerdao = new CustomerDao();
        customerdao.deleteCustomerDao(customerCode);
        loadDatas();
        JOptionPane.showMessageDialog(this, "Customer deleted successfully", "Success",
                JOptionPane.INFORMATION_MESSAGE);
        clear();
    }

    private void clear() {
        customerCodeTxt.setText("");
        customerNameTxt.setText("");
        phoneTxt.setText("");
        shippingAddressTxt.setText("");
        customerNotesTxt.setText("");
    }
}