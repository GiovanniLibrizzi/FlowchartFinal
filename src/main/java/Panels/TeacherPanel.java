package Panels;
import Database.DB;
import Handlers.PanelHandler;
import Handlers.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class TeacherPanel extends WorkingPanel {
    public RoundedButton problemButton;
    public RoundedButton dataButton;
    public RoundedButton logOutButton;

    public TeacherPanel() {
        setBackground(PanelConstants.CUSTOM_BLACK);
        setLayout(new GridLayout(4, 0));

        Color color = PanelConstants.CUSTOM_GREY;
        JLabel productDescriptionLabel = new JLabel("Teacher View Menu" );
        productDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productDescriptionLabel.setFont(new Font("Consolas", Font.BOLD, 36));
        productDescriptionLabel.setForeground(color);
        JLabel welcomeLabel = new JLabel("Welcome, " + Repository.getInstance().getCurrentUser() + "!" );
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 36));
        welcomeLabel.setForeground(color);

        problemButton = new RoundedButton("Create Custom Problems",200, PanelConstants.CUSTOM_WHITE, PanelConstants.CUSTOM_BLACK);
        dataButton = new RoundedButton("View Student Data",200, PanelConstants.CUSTOM_WHITE, PanelConstants.CUSTOM_BLACK);
        logOutButton = new RoundedButton("Log Out",200, PanelConstants.CUSTOM_WHITE, PanelConstants.CUSTOM_BLACK);

        problemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.TeacherProblem);
            }
        });
        dataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.TeacherData);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.Login);
            }
        });

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.setBackground(PanelConstants.CUSTOM_BLACK);
        top.add(productDescriptionLabel);
        top.add(welcomeLabel);

        add(top);
        add(problemButton);
        add(dataButton);
        add(logOutButton);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
