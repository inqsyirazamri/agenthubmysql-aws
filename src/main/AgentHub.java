package src.main;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

import src.g11.agenthub.gui.LoginForm;

import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AgentHub {
    public static void main(String[] args) {
        setupLookAndFeel();
        LoginForm loginForm = new LoginForm();
        loginForm.setLocationRelativeTo(null);
        loginForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginForm.setVisible(true);
    }

    private static void setupLookAndFeel() {
        Properties props = new Properties();
        props.put("logoString", "g11");
        HiFiLookAndFeel.setCurrentTheme(props);
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            handleLookAndFeelException(e);
        }
    }

    private static void handleLookAndFeelException(Exception e) {
        // Handle the exception more robustly, e.g., display an error message or log the
        // error
        System.err.println("Error setting up look and feel: " + e.getMessage());
    }
}