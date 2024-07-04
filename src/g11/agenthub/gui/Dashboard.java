package src.g11.agenthub.gui;

import src.audit.AuditLog;
import src.backup.DatabaseBackup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Dashboard extends JFrame {
    private JPanel mainPanel;
    private JTextArea welcomeTextArea;
    private JTextArea welcomeTextArea1;
    private JLabel menuLabel;
    private JButton manageProfileButton;
    private JButton manageProductsButton;
    private JButton makePurchasesButton;
    private JButton addCustomersButton;
    private JButton manageUsersButton;
    private JButton backButton;
    private JButton logoutButton;
    private String username;
    private JMenuBar menuBar;
    private JMenu backupMenu;
    private JMenuItem backupMenuItem;
    private JMenuItem viewAuditLogsMenuItem;

    public Dashboard(String userType, String username) {
        this.username = username;
        initComponents(username);
        setTitle("Agent Hub - A Simple Order Management System for Agent");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Make the application fullscreen
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (userType.equalsIgnoreCase("Admin")) {
            setupAdminDashboard();
        } else if (userType.equalsIgnoreCase("Agent")) {
            setupAgentDashboard();
        }
        setVisible(true);
    }

    private void initComponents(String username) {
        mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome text panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setOpaque(false);

        // Welcome text area
        welcomeTextArea = new JTextArea("Welcome, " + username + " to AgentHub!");
        welcomeTextArea1 = new JTextArea("We're excited to have you explore our Order Management System for Agent :))");
        welcomeTextArea.setFont(new Font("Recoleta", Font.BOLD, 66));
        welcomeTextArea1.setFont(new Font("Recoleta", Font.PLAIN, 30));
        welcomeTextArea.setEditable(false);
        welcomeTextArea1.setEditable(false);
        welcomeTextArea.setOpaque(false);
        welcomeTextArea1.setOpaque(false);
        welcomeTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeTextArea1.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(welcomeTextArea);
        welcomePanel.add(Box.createVerticalStrut(10)); // Spacing
        welcomePanel.add(welcomeTextArea1);

        mainPanel.add(welcomePanel, BorderLayout.NORTH);

        // Menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);

        // Menu label
        menuLabel = new JLabel("Please select your business:");
        menuLabel.setFont(new Font("Serif", Font.PLAIN, 24)); // Slightly smaller font size than welcome text
        menuLabel.setForeground(Color.WHITE);
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Buttons
        manageProfileButton = createLinkButton("1. Manage Profile");
        manageProductsButton = createLinkButton("2. Manage Products");
        makePurchasesButton = createLinkButton("3. Manage Purchases");
        addCustomersButton = createLinkButton("4. Manage Customers");
        manageUsersButton = createLinkButton("5. Manage Users");
        backButton = createLinkButton("Back");
        logoutButton = createLinkButton("Logout");

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuditLog.logEvent(username, "Logout");
                new LoginForm();
                dispose();
            }
        });

        // Adding components to the menu panel
        menuPanel.add(Box.createVerticalStrut(40));
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createVerticalStrut(30)); // Spacing
        menuPanel.add(manageProfileButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Spacing
        menuPanel.add(manageProductsButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Spacing
        menuPanel.add(makePurchasesButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Spacing
        menuPanel.add(addCustomersButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Spacing
        menuPanel.add(manageUsersButton);
        menuPanel.add(Box.createVerticalStrut(50)); // Spacing
        menuPanel.add(backButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Spacing
        menuPanel.add(logoutButton);

        mainPanel.add(menuPanel, BorderLayout.WEST);

        add(mainPanel);

        setupMenuBar();
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        backupMenu = new JMenu("Backup/View Audit Logs");
        backupMenuItem = new JMenuItem("Backup");
        viewAuditLogsMenuItem = new JMenuItem("View Audit Logs");

        backupMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(Dashboard.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String location = file.getAbsolutePath();
                    String filename = location + ".sql";
                    try {
                        DatabaseBackup.backupDatabase(filename);
                        JOptionPane.showMessageDialog(Dashboard.this, "Backup successful: " + filename);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Dashboard.this, "Backup failed: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        viewAuditLogsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAuditLogs();
            }
        });

        backupMenu.add(backupMenuItem);
        backupMenu.add(viewAuditLogsMenuItem);
        menuBar.add(backupMenu);
        setJMenuBar(menuBar);
    }

    private void setupAdminDashboard() {
        menuLabel.setText("Admin Dashboard - Please select an option:");
    }

    private void setupAgentDashboard() {
        menuLabel.setText("Agent Dashboard - Please select an option:");
    }

    private JButton createLinkButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setText("<html><u>" + text + "</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setText(text);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (text.equals("Back")) {
                    // Implement back navigation logic here
                } else {
                    showMenu(text, "");
                }
            }
        });

        return button;
    }

    private void showMenu(String menu, String username) {
        if (menu.contains("Profile")) {
            new ProfilePage(username);
        } else if (menu.contains("Products")) {
            new ProductPage(username);
        } else if (menu.contains("Purchases")) {
            new PurchasePage();
        } else if (menu.contains("Customers")) {
            new CustomerPage(username);
        } else if (menu.contains("Users")) {
            new UserPage();
        } else {
            JOptionPane.showMessageDialog(this, "You selected " + menu, "Menu", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void viewAuditLogs() {
        JTextArea auditLogTextArea = new JTextArea(20, 50);
        auditLogTextArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("audit_log.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                auditLogTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(auditLogTextArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Audit Logs", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class GradientPanel extends JPanel {
        public GradientPanel() {
            setPreferredSize(new Dimension(600, 400));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradient = new GradientPaint(0, 0, new Color(64, 64, 64), 0, getHeight(),
                    new Color(128, 128, 128));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
