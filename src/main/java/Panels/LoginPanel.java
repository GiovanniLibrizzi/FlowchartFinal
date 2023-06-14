package Panels;
import Database.DB;
import Handlers.PanelHandler;
import Handlers.Repository;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Result;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.*;

/**
 * Login Panel class which creates the panel for the user login
 *
 * @author Stefan Lutsch
 */
public class LoginPanel extends WorkingPanel {
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton createAccountButton;

    public LoginPanel() {
        setBackground(PanelConstants.CUSTOM_WHITE);
        setLayout(new GridBagLayout()); // sets the layout of the panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // adds some padding around the elements

        // creates the labels, text fields, and button
        Font font = new Font("Dialog", Font.BOLD, 16);
        Color color = PanelConstants.CUSTOM_GREY;

        JLabel productNameLabel = new JLabel("BasicCodeGenius");
        productNameLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        productNameLabel.setForeground(color);

        ImageIcon imageIcon = new javax.swing.ImageIcon("resources/ducky.png");
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        imageIcon = new ImageIcon(image);
        JLabel duck = new JLabel();
        duck.setIcon(imageIcon);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        usernameLabel.setForeground(color);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        passwordLabel.setForeground(color);

        usernameField = new RoundedTextField(20);
        passwordField = new RoundedPasswordField(20);
        submitButton = new RoundedButton("Submit",25);
        createAccountButton = new RoundedButton("New? Create An Account",25);

        // adds the components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(duck, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(productNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(submitButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(createAccountButton, gbc);

        // adds an ActionListener to the submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(DB.login(usernameField.getText(), String.valueOf(passwordField.getPassword()))){
                    Repository.getInstance().setCurrentUser(usernameField.getText());
                    Result<Record5<Long, String, String, Integer, Integer>> user = DB.getUserByUsername(usernameField.getText());
                    String role = user.get(0).value3();
                    if(role.equals("Student")) {
                        PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.MainMenu);
                    }else{
                        PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.TeacherView);
                    }
                    JOptionPane.showMessageDialog(LoginPanel.this, "Success");
                }else{
                    JOptionPane.showMessageDialog(LoginPanel.this, "Invalid Credentials");
                    passwordField.setText("");
                }
            }
        });
        // adds an ActionListener to the createAccount button
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.CreateAccount);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }


}

