package MainWindow;

import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class MainWindow  {

    private JFrame mainWindow;
    private JPanel loginPanel;
    private JButton button;
    private GridBagLayout gb;
    private int windowWidth = 1000;
    private int windowHeight = 500;
    private BufferedImage image;
    private JLabel imageLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
    private SQLShenanigans sqlController;

    public MainWindow(){
        init();
    }

    public void init() {
        mainWindow = new JFrame("Main Window");
        // Setup panels
        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.black);

        setupImage();
        setupFields();
        setupButtons();

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // mainWindow.setLocationByPlatform(true);
        mainWindow.setPreferredSize(new Dimension(windowWidth,windowHeight));
        mainWindow.setResizable(true);
        mainWindow.setVisible(false);
        // Change this to remove the JFrame border
        mainWindow.setUndecorated(false);
        mainWindow.pack();
        mainWindow.setVisible(true);
        mainWindow.add(loginPanel);

        sqlController = new SQLShenanigans();

    }

    void setupImage() {
        imageLabel = new JLabel();
        imageLabel.setBounds(new Rectangle(7,167,312,114));

        // Insert image
        try {
            image = ImageIO.read(new File("payload/logo.png"));

        } catch (IOException ex) {
            System.out.println(ex);
        }

        Image dImage = image.getScaledInstance(312, 114, Image.SCALE_SMOOTH);

        imageLabel.setIcon(new ImageIcon(dImage));

        loginPanel.add(imageLabel);
    }

    void setupFields() {
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');

        // Constraints for the fields
        usernameLabel.setBounds(new Rectangle(377,71,149,35));
        usernameField.setBounds(new Rectangle(380,115,400,40));
        passwordLabel.setBounds(new Rectangle(377,209,149,35));
        passwordField.setBounds(new Rectangle(380,253,400,40));

        // Customise Fields
        usernameLabel.setFont(new Font("Avenir", Font.BOLD, 25));
        usernameLabel.setForeground(new Color(0, 112, 226, 207));
        passwordLabel.setFont(new Font("Avenir", Font.BOLD, 25));
        passwordLabel.setForeground(new Color(0,112,226,207));

        // Add Fields
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
    }

    void setupButtons() {
        loginButton = new JButton("Login");
        resetButton = new JButton("Forgot Password");

        // Constraints for the buttons
        loginButton.setBounds(new Rectangle(420,330,301,53));
        resetButton.setBounds(new Rectangle(420,410,301,53));

        // Customise Buttons
        loginButton.setBackground(new Color(74,144,226, 172));
        resetButton.setBackground(new Color(245,166,35, 210));


        // Add Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Login button clicked");
                String temp1, temp2;
                temp1 = usernameField.getText();
                temp2 = String.valueOf(passwordField.getPassword());
                try {
                    if (sqlController.readFromTable(temp1, temp2) == true) {
                        System.out.println("User verified");
                    } else {
                        System.out.println("Wrong credentials");
                    }
                } catch (SQLException se){
                    se.printStackTrace();
                } catch (ClassNotFoundException c) {
                    c.printStackTrace();
                }
            }
        });

        // Add Buttons
        loginPanel.add(loginButton);    
        loginPanel.add(resetButton);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
