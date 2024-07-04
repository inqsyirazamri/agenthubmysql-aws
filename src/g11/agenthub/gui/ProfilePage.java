package src.g11.agenthub.gui;

import src.g11.agenthub.data_access.UserDao;
import src.g11.agenthub.data_transfer.UserDto;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfilePage extends JFrame {
    private JPanel mainPanel;
    private JLabel fullNameLabel;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JLabel roleLabel;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField roleField;
    private JButton loadDetailsButton;
    private JButton updateButton;
    private JButton backButton;
    private String user_id;
    private JTable table;

    public ProfilePage(String user_id) {
        this.user_id = user_id;
        initComponents();
        setTitle("Profile Management");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        // Create GUI components
        mainPanel = new JPanel(new BorderLayout());

        fullNameLabel = new JLabel("Full Name:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");
        roleLabel = new JLabel("Role:");

        fullNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        roleField = new JTextField(20);
        roleField.setEditable(false); // Role is not editable in Profile Page

        loadDetailsButton = new JButton("Load Details");
        loadDetailsButton.addActionListener(e -> loadDatas(user_id));

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateAgentDetails());

        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());

        // Set up north panel for labels and text fields
        JPanel northPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        northPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        northPanel.add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        northPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        northPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        northPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        northPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        northPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        northPanel.add(roleField, gbc);

        // Set up south panel for buttons
        JPanel southPanel = new JPanel();
        southPanel.add(loadDetailsButton);
        southPanel.add(updateButton);
        southPanel.add(backButton);

        mainPanel.add(northPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadDetails(String agentCode) {
        UserDao userDao = new UserDao();
        try (ResultSet rs = userDao.getUser(agentCode)) {
            if (rs.next()) {
                fullNameField.setText(rs.getString("fullname"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone"));
                roleField.setText(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading agent details: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAgentDetails() {
        UserDto userDto = new UserDto();
        userDto.setUsername(user_id);
        userDto.setFullName(fullNameField.getText());
        userDto.setEmail(emailField.getText());
        userDto.setPhone(phoneField.getText());
        userDto.setRole(roleField.getText());

        UserDao userDao = new UserDao();
        userDao.editUserDao(userDto);

        JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadDatas(String userId) {
        try {
            UserDao userDao = new UserDao();
            table.setModel(userDao.buildTableModel(userDao.getUserQueryResult(userId)));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    // new ProfilePage("agentCode123"); // For testing purposes, replace
    // "agentCode123" with a valid agent code
    // }
}
