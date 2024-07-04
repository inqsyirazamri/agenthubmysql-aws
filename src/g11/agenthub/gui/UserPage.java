package src.g11.agenthub.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import src.g11.agenthub.data_access.UserDao;
import src.g11.agenthub.data_transfer.UserDto;

public class UserPage extends JDialog {
    private JTextField userNameTxt;
    private JTextField userEmailTxt;
    private JTextField userPhoneTxt;
    private JButton addUserBtn;
    private JButton editUserBtn;
    private JButton deleteUserBtn;
    private JButton clearBtn;
    private JButton refreshBtn;
    private JTable table;
    private String user;
    @SuppressWarnings("rawtypes")
    private JComboBox userComboBox;
    private UserDto userdto;

    public UserPage() {
        initComponents();
        loadDatas();
        setTitle("User Management");
        setSize(650, 500);
        Point p = new Point(420, 200);
        setLocation(p);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setVisible(true);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void initComponents() {
        userComboBox = new javax.swing.JComboBox();
        userNameTxt = new JTextField(20);
        userEmailTxt = new JTextField(20);
        userPhoneTxt = new JTextField(20);
        addUserBtn = new JButton("Add User");
        editUserBtn = new JButton("Edit User");
        deleteUserBtn = new JButton("Delete User");
        clearBtn = new JButton("Clear");
        refreshBtn = new JButton("Refresh");
        table = new JTable();

        userComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ADMINISTRATOR", "AGENT" }));
        userComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userComboBoxActionPerformed(evt);
            }
        });

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridy = 1;
        eastPanel.add(new JLabel("Select User Role:"), gbc);

        gbc.gridy = 2;
        eastPanel.add(userComboBox, gbc);

        gbc.gridy = 3;
        eastPanel.add(new JLabel("User Fullname:"), gbc);
        gbc.gridy = 4;
        eastPanel.add(userNameTxt, gbc);

        gbc.gridy = 5;
        eastPanel.add(new JLabel("User Email:"), gbc);
        gbc.gridy = 6;
        eastPanel.add(userEmailTxt, gbc);

        gbc.gridy = 7;
        eastPanel.add(new JLabel("User Phone:"), gbc);
        gbc.gridy = 8;
        eastPanel.add(userPhoneTxt, gbc);

        gbc.gridy = 9;
        eastPanel.add(addUserBtn, gbc);
        gbc.gridy = 10;
        eastPanel.add(editUserBtn, gbc);
        gbc.gridy = 11;
        eastPanel.add(deleteUserBtn, gbc);
        gbc.gridy = 12;
        eastPanel.add(clearBtn, gbc);
        gbc.gridy = 13;
        eastPanel.add(refreshBtn, gbc);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Add listeners
        addUserBtn.addActionListener(e -> addUser());
        editUserBtn.addActionListener(e -> editUser());
        deleteUserBtn.addActionListener(e -> deleteUser());
        clearBtn.addActionListener(e -> clear());
        refreshBtn.addActionListener(e -> loadDatas());
    }

    protected void userComboBoxActionPerformed(ActionEvent evt) {
    }

    public String encryptPassword(String input) {
        String encPass = null;
        if (input == null)
            return null;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            encPass = new BigInteger(1, digest.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encPass;
    }

    private void clear() {
        userNameTxt.setText("");
        userEmailTxt.setText("");
        userPhoneTxt.setText("");
    }

    private void deleteUser() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Select a table data first!");
        } else {
            new UserDao().deleteUserDao(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
            JOptionPane.showMessageDialog(this, "User Deleted Successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadDatas();
        }
    }

    private void editUser() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(
                    null, "Select a table data first!");
        } else {
            UserDto userdto = new UserDto();
            user = (String) userComboBox.getSelectedItem();
            userdto.setFullName(userNameTxt.getText());
            userdto.setEmail(userEmailTxt.getText());
            userdto.setPhone(userPhoneTxt.getText());
            userdto.setRole(user);
            new UserDao().editUserDao(userdto);
            loadDatas();
        }
    }

    private void addUser() {
        userdto = new UserDto();
        if (userNameTxt.getText().equals("") || userEmailTxt.getText().equals("")
                || userPhoneTxt.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields!");
        } else {
            user = (String) userComboBox.getSelectedItem();
            userdto.setFullName(userNameTxt.getText());
            userdto.setEmail(userEmailTxt.getText());
            userdto.setPhone(userPhoneTxt.getText());
            userdto.setRole((String) userComboBox.getSelectedItem());
            new UserDao().addUserDao(userdto, user);
            if ("ADMINISTRATOR".equals(user)) {
                JOptionPane.showMessageDialog(null, "New user administrator added");
            } else {
                JOptionPane.showMessageDialog(null, "New user agent added");
            }
            loadDatas();
            // clear();
        }
    }

    public void loadDatas() {
        try {
            UserDao userDao = new UserDao();
            table.setModel(userDao.buildTableModel(userDao.getQueryResult1()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
