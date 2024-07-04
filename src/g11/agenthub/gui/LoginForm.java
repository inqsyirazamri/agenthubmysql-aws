package src.g11.agenthub.gui;

import src.audit.AuditLog;
import src.backup.DatabaseBackup;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.io.File;

import src.g11.agenthub.db_connect.DbConnection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class LoginForm extends javax.swing.JDialog {
    // constants
    private static final String USERNAME_LABEL = "Username";
    private static final String PASSWORD_LABEL = "Password";

    // variables
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField usernameField;
    private JPasswordField passwordField;
    private javax.swing.JButton loginButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> userTypeBox;
    private JLabel userTypeLabel;

    public LoginForm() {
        initComponents();
    }

    private void initComponents() {
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        userTypeBox = new javax.swing.JComboBox<>();
        userTypeLabel = new javax.swing.JLabel("What are you?");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");

        createUserTypeLabel();
        createUserTypeBox();
        createUsernameComponent();
        createPasswordComponent();
        createLoginButton();
        createCancelButton();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(userTypeBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(usernameLabel)
                                                        .addComponent(passwordLabel)
                                                        .addComponent(userTypeLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(usernameField)
                                                        .addComponent(passwordField,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                188, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cancelButton)
                                                .addGap(22, 22, 22)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(userTypeLabel)
                                .addGap(18, 18, 18)
                                .addComponent(userTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(usernameLabel)
                                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLabel)
                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }

    private void createUserTypeBox() {
        userTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrator", "Agent" }));
    }

    private void createUserTypeLabel() {
        userTypeLabel = new javax.swing.JLabel("Who are you?");
    }

    private void createUsernameComponent() {
        usernameLabel = new javax.swing.JLabel(USERNAME_LABEL);
        usernameField = new javax.swing.JTextField();
        usernameField.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });
    }

    private void createPasswordComponent() {
        passwordLabel = new javax.swing.JLabel(PASSWORD_LABEL);
        passwordField = new javax.swing.JPasswordField();
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });
    }

    private void createLoginButton() {
        loginButton = new javax.swing.JButton("Login");
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
        });
    }

    private void createCancelButton() {
        cancelButton = new javax.swing.JButton("Cancel");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });
    }

    private void usernameFieldActionPerformed(ActionEvent evt) {
        // swing have default action for this. Press "Enter" after typing username will
        // trigger this event
        // so, the pointer now will move to password field
        // code to validate username here (if sempat)
    }

    private void passwordFieldKeyPressed(KeyEvent evt) {
        // swing have default action for this. Every character of your password will be
        // masking with"*"
        // code to trigger login button when Enter key is pressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loginButtonMouseClicked(null);
        }
    }

    private void loginButtonMouseClicked(MouseEvent evt) {
        String username = usernameField.getText();
        String password = encryptPassword(new String(passwordField.getPassword()));
        String userType = (String) userTypeBox.getSelectedItem();

        try (DbConnection dbConnection = new DbConnection()) {
            Connection conn = dbConnection.getConnection();
            if (conn != null) {
                if (dbConnection.checkLogin(username, password)) {
                    String role = dbConnection.getRole(username);
                    if (role != null && role.equalsIgnoreCase(userType)) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        triggerBackup(); // Trigger backup on successful login
                        AuditLog.logEvent(username, "Login"); // Log the login event
                        dispose();
                        new Dashboard(userType, username);
                    } else {
                        JOptionPane.showMessageDialog(null, "User role does not match the selected type", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed to connect to the database", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String encryptPassword(String input) {
        String encryptedPassword = null;
        if (input == null)
            return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            encryptedPassword = new BigInteger(1, digest.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    private void cancelButtonMouseClicked(MouseEvent evt) {
        dispose();
    }

    private void triggerBackup() {
        String backupFilePath = "path_to_backup_directory" + File.separator + "backup_" + System.currentTimeMillis()
                + ".sql";
        try {
            DatabaseBackup.backupDatabase(backupFilePath);
            JOptionPane.showMessageDialog(this, "Backup successful: " + backupFilePath);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Backup failed: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}