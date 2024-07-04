package src.g11.agenthub.gui;

import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import src.g11.agenthub.data_access.AgentDao;
import src.g11.agenthub.data_transfer.AgentDto;

public class AgentPage extends JDialog {
    private AgentDto agentdto;
    // private int userId = 1;
    private JTextField agentNameTxt;
    private JTextField agentEmailTxt;
    private JTextField agentPhoneTxt;
    private JButton addAgentBtn;
    private JButton editAgentBtn;
    private JButton deleteAgentBtn;
    private JButton clearBtn;
    private JButton refreshBtn;
    private JTextField agentCodeTxt;
    private JTable table;

    public AgentPage() {
        initComponents();
        agentCodeTxt.setVisible(false);
        loadDatas();
        setTitle("Agent Management");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        agentCodeTxt = new JTextField(20);
        agentNameTxt = new JTextField(20);
        agentEmailTxt = new JTextField(20);
        agentPhoneTxt = new JTextField(20);
        addAgentBtn = new JButton("Add Agent");
        editAgentBtn = new JButton("Edit Agent");
        deleteAgentBtn = new JButton("Delete Agent");
        clearBtn = new JButton("Clear");
        refreshBtn = new JButton("Refresh");
        table = new JTable();

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridy = 3;
        eastPanel.add(new JLabel("Agent Name:"), gbc);
        gbc.gridy = 4;
        eastPanel.add(agentNameTxt, gbc);

        gbc.gridy = 5;
        eastPanel.add(new JLabel("Agent Email:"), gbc);
        gbc.gridy = 6;
        eastPanel.add(agentEmailTxt, gbc);

        gbc.gridy = 7;
        eastPanel.add(new JLabel("Agent Phone:"), gbc);
        gbc.gridy = 8;
        eastPanel.add(agentPhoneTxt, gbc);

        gbc.gridy = 9;
        eastPanel.add(addAgentBtn, gbc);
        gbc.gridy = 10;
        eastPanel.add(editAgentBtn, gbc);
        gbc.gridy = 11;
        eastPanel.add(deleteAgentBtn, gbc);
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

        table.addMouseListener(new MouseAdapter() {
            // @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });

        // Add listeners
        addAgentBtn.addActionListener(e -> addAgent());
        editAgentBtn.addActionListener(e -> editAgent());
        deleteAgentBtn.addActionListener(e -> deleteAgent());
        clearBtn.addActionListener(e -> clear());
        refreshBtn.addActionListener(e -> loadDatas());
    }

    static String agentCode;

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.getSelectedRow();
        int column = table.getColumnCount();
        Object[] val = new Object[column];
        for (int i = 0; i < column; i++) {
            val[i] = table.getValueAt(row, i);
        }
        agentCodeTxt.setText(val[0].toString());
        agentNameTxt.setText(val[1].toString());
        agentEmailTxt.setText(val[2].toString());
        agentPhoneTxt.setText(val[3].toString());
        agentCode = val[0].toString();
    }

    private void clear() {
        agentNameTxt.setText("");
        agentEmailTxt.setText("");
        agentPhoneTxt.setText("");
    }

    private void deleteAgent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String agentCode = (String) table.getValueAt(selectedRow, 0);

            AgentDao agentdao = new AgentDao();
            agentdao.deleteAgentDao(agentCode);
            loadDatas();
            JOptionPane.showMessageDialog(this, "Agent Deleted Successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clear();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editAgent() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(
                    null, "Select a table data first!");
        } else {
            agentdto = new AgentDto();
            if (agentNameTxt.getText().equals("") || agentEmailTxt.getText().equals("")
                    || agentPhoneTxt.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
            } else {
                agentdto.setAgentCode(agentCodeTxt.getText());
                agentdto.setFullName(agentNameTxt.getText());
                agentdto.setEmail(agentEmailTxt.getText());
                agentdto.setPhone(agentPhoneTxt.getText());
                new AgentDao().editAgentDao(agentdto);
                JOptionPane.showMessageDialog(this, "Agent Updated Successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadDatas();
            }
        }
    }

    private void addAgent() {
        agentdto = new AgentDto();
        if (agentNameTxt.getText().equals("") || agentEmailTxt.getText().equals("")
                || agentPhoneTxt.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields!");
        } else {
            String agentCode = generateNewAgentCode();
            agentdto.setAgentCode(agentCode);
            agentdto.setFullName(agentNameTxt.getText());
            agentdto.setEmail(agentEmailTxt.getText());
            agentdto.setPhone(agentPhoneTxt.getText());

            AgentDao agentdao = new AgentDao();
            agentdao.addAgentDao(agentdto);
            loadDatas(); // refresh the table with the updated data

            JOptionPane.showMessageDialog(this, "Agent Added Successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clear();
        }
    }

    private String generateNewAgentCode() {
        try {
            AgentDao agentDao = new AgentDao();
            ResultSet rs = agentDao.getQueryResult();

            if (!rs.next()) {
                return "age1";
            } else {
                String oldAgentCode = rs.getString("agentcode");
                Integer scode = Integer.parseInt(oldAgentCode.substring(3)) + 1;
                return "age" + scode;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadDatas() {
        try {
            AgentDao agentDao = new AgentDao();
            table.setModel(agentDao.buildTableModel(agentDao.getQueryResult()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
