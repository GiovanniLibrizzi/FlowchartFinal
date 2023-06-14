package Panels;
import Database.DB;
import Handlers.PanelHandler;
import Handlers.Repository;
import org.jooq.Record5;
import org.jooq.Record9;
import org.jooq.Result;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class ViewProgressPanel extends WorkingPanel{
    public ViewProgressPanel(){
        // Initializing all JPanels
        int i = 3;
        int j = 3;
        JPanel[][] panelHolder = new JPanel[i][j];
        setLayout(new GridLayout(i,j));
        for(int m = 0; m < i; m++) {
            for(int n = 0; n < j; n++) {
                JPanel tempPanel = new JPanel();
                tempPanel.setBackground(PanelConstants.CUSTOM_WHITE);
                panelHolder[m][n] = tempPanel;
                add(panelHolder[m][n]);
            }
        }

        // Setting up borders for JPanels
//        Border blackBorder = BorderFactory.createLineBorder(Color.black);
//        panelHolder[1][1].setBorder(blackBorder);
//        setBorder(blackBorder);

        // Setting up sub panels

        // panelHolder[0][0]
        Color color = PanelConstants.CUSTOM_GREY;
        JLabel productNameLabel = new JLabel("<html><u>BasicCodeGenius</u></html>");
        productNameLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        productNameLabel.setForeground(color);
        JLabel productDescriptionLabel = new JLabel("Your Friendly Coding Tutor");
        productDescriptionLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        productDescriptionLabel.setForeground(color);
        panelHolder[0][0].add(productNameLabel);
        panelHolder[0][0].add(productDescriptionLabel);

        // panelHolder[0][1]
        panelHolder[0][1].setLayout(new GridLayout(2, 2));
        panelHolder[0][1].add(new JLabel());
        panelHolder[0][1].add(new JLabel());
        JLabel duckTutor = new JLabel();
        ImageIcon imageIcon = new javax.swing.ImageIcon("resources/ducky.png");
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        imageIcon = new ImageIcon(image);
        duckTutor.setIcon(imageIcon);
        panelHolder[0][1].add(duckTutor);
        JLabel selectOptionLabel = new JLabel("<html>You're doing great,<br/>" + Repository.getInstance().getCurrentUser() + "!</html>");
        selectOptionLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        selectOptionLabel.setForeground(color);
        panelHolder[0][1].add(selectOptionLabel);

        // panelHolder[1][1]
        panelHolder[1][1].setLayout(new GridLayout(6, 1));
        Result<Record5<Long, String, String, Integer, Integer>> user = DB.getUserByUsername(Repository.getInstance().getCurrentUser());
        Result<Record5<Long, String, String, String, String>> flowProbs = DB.getFlowchartProblems();
        Result<Record9<Long, String, String, String, String, Integer, Integer, Integer, Integer>> codeProbs = DB.getCodeProblems();
        int current_flowchart_problem = user.get(0).value4();
        if(current_flowchart_problem > 0)
            current_flowchart_problem--;
        int current_code_problem = user.get(0).value5();
        if(current_code_problem > 0)
            current_code_problem--;
        double flowProg = (((double)current_flowchart_problem) / flowProbs.size())*100;
        double codeProg = (((double)current_code_problem) / codeProbs.size())*100;
        flowProg =  (double) Math.round(flowProg * 100) / 100;
        codeProg =  (double) Math.round(codeProg * 100) / 100;
        JLabel flowProgLabel = new JLabel("You're " + flowProg + "% done with flowchart problems!");
        flowProgLabel.setFont(new Font("Dialog", Font.ITALIC, 13));
        flowProgLabel.setForeground(color);
        JProgressBar flowProgBar = new JProgressBar(0, 100);
        flowProgBar.setValue((int)flowProg);
        JLabel codeProgLabel = new JLabel("You're " + codeProg + "% done with code problems!");
        codeProgLabel.setFont(new Font("Dialog", Font.ITALIC, 13));
        codeProgLabel.setForeground(color);
        JProgressBar codeProgBar = new JProgressBar(0, 100);
        codeProgBar.setValue((int)codeProg);
        JLabel codeMetricProgLabel = new JLabel("You're " + codeProg + "% done with code metric problems!");
        codeMetricProgLabel.setFont(new Font("Dialog", Font.ITALIC, 13));
        codeMetricProgLabel.setForeground(color);
        JProgressBar codeMetricProgBar = new JProgressBar(0, 100);
        codeMetricProgBar.setValue((int)codeProg);
        panelHolder[1][1].add(flowProgLabel);
        panelHolder[1][1].add(flowProgBar);
        panelHolder[1][1].add(codeProgLabel);
        panelHolder[1][1].add(codeProgBar);
        panelHolder[1][1].add(codeMetricProgLabel);
        panelHolder[1][1].add(codeMetricProgBar);

        // panelHolder[2][2]
        JPanel[] holderFor22 = new JPanel[4];
        for(i = 0; i<4; i++){
            holderFor22[i] = new JPanel();
            holderFor22[i].setBackground(PanelConstants.CUSTOM_WHITE);
        }
        panelHolder[2][2].setLayout(new GridLayout(4, 1));
        JButton mainMenuButton = new RoundedButton("Main Menu", 25);
        JButton logoutButton = new RoundedButton("Logout", 25);
        holderFor22[2].add(mainMenuButton);
        holderFor22[3].add(logoutButton);
        for(JPanel jPanel : holderFor22){
            panelHolder[2][2].add(jPanel);
        }

        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.MainMenu);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.Login);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
