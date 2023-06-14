package Panels;
import Database.DB;
import Handlers.PanelHandler;
import Handlers.Repository;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Observable;

public class TeacherDataPanel extends WorkingPanel{

    private JTextArea outputArea;
    private RoundedButton queryButton;
    private RoundedButton returnButton;
    private JCheckBox userNameCheck;
    private boolean userChecked;
    private JCheckBox problemCheck;
    private boolean problemChecked;
    private RoundedTextField userNameField;
    private RoundedTextField problemField;
    private WorkingPanel rightPanel;
    public TeacherDataPanel() {
        setBackground(PanelConstants.CUSTOM_WHITE);
        setLayout(new GridLayout(0,2));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
//        outputArea.setContentType("text/html");
        outputArea.setBackground(getBackground());
        outputArea.setBorder(null);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI(PanelConstants.CUSTOM_BLACK, PanelConstants.CUSTOM_WHITE));

        JPanel leftPanel = new JPanel(new GridLayout(2,0));
        JPanel leftTop = new JPanel((new GridLayout(2,1)));
        JPanel leftTopTop = new JPanel((new GridLayout(1, 2)));
        leftTop.add(leftTopTop);
        JPanel leftTopBottom = new JPanel(new BorderLayout());
        leftTop.add(leftTopBottom);
        JPanel leftBottom = new JPanel(new BorderLayout());

        ImageIcon imageIcon = new javax.swing.ImageIcon("resources/ducky.png");
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        imageIcon = new ImageIcon(image);
        JLabel duck = new JLabel();
        duck.setIcon(imageIcon);

        JLabel productNameLabel = new JLabel("Data View");
        productNameLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        productNameLabel.setForeground(PanelConstants.CUSTOM_GREY);
        JLabel welcomeLabel = new JLabel("Welcome, " + Repository.getInstance().getCurrentUser() + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        welcomeLabel.setForeground(PanelConstants.CUSTOM_GREY);

        leftTopTop.add(duck);
        leftTopTop.add(productNameLabel);
        leftTopBottom.add(welcomeLabel, BorderLayout.CENTER);

        // panel for checkList
        JPanel checkListPanel = new JPanel(new GridLayout(2,2));
        userNameCheck = new JCheckBox("Query By UserName");
        problemCheck = new JCheckBox("Query By Problem Number");
        userNameField = new RoundedTextField(100);
        problemField = new RoundedTextField(100);
        checkListPanel.add(userNameCheck);
        checkListPanel.add(userNameField);
        checkListPanel.add(problemCheck);
        checkListPanel.add(problemField);

        userNameCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChecked = userNameCheck.isSelected();
            }
        });
        problemCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                problemChecked = problemCheck.isSelected();
            }
        });

        // panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0,2));
        queryButton = new RoundedButton("Submit",25, PanelConstants.CUSTOM_GREY, PanelConstants.CUSTOM_WHITE);
        returnButton = new RoundedButton("Return",25, PanelConstants.CUSTOM_GREY, PanelConstants.CUSTOM_WHITE);

        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Result<Record5<Long, String, String, Integer, Integer>> users = DB.getUsers();
                String userText = userNameField.getText();
                String probText = problemField.getText();
                Integer probNum = 0;
                if(problemChecked) {
                    try {
                        probNum = Integer.valueOf(probText);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(TeacherDataPanel.this, "Problem number must be a number: " + probText);
                        return;
                    }
                }
                if(userChecked && problemChecked){
                    System.out.println("Querying by both userName and problemChecked");
                    System.out.println("userName: " + userText + ", problem: " + probText);
                    String formattedStudents = "";
                    for(Record5<Long, String, String, Integer, Integer> user : users){
                        if(user.value3().equals("Student") &&
                                user.value2().toUpperCase().contains(userText.toUpperCase()) &&
                                user.value4().equals(probNum)){
                            formattedStudents += "ID: " + user.value1() + ", Username: "
                                    + user.value2() + ", Current Flowchart Problem: " + user.value4()
                                    + ", Current Code Problem: " + user.value5()
                                    + ", Current Code Metric Problem: " +  user.value5() + "\n\n";
                        }
                    }
                    outputArea.setText(formattedStudents);
                } else {
                    if(userChecked){
                        System.out.println("Querying by userName");
                        System.out.println("userName: " + userText);
                        String formattedStudents = "";
                        for(Record5<Long, String, String, Integer, Integer> user : users){
                            if(user.value3().equals("Student") && user.value2().toUpperCase().contains(userText.toUpperCase())){
                                formattedStudents += "ID: " + user.value1() + ", Username: "
                                        + user.value2() + ", Current Flowchart Problem: " + user.value4()
                                        + ", Current Code Problem: " + user.value5()
                                        + ", Current Code Metric Problem: " +  user.value5() + "\n\n";
                            }
                        }
                        outputArea.setText(formattedStudents);
                    }else if(problemChecked){
                        System.out.println("Querying by problem");
                        System.out.println("problem: " + probText);
                        String formattedStudents = "";
                        for(Record5<Long, String, String, Integer, Integer> user : users){
                            if(user.value3().equals("Student") && user.value4().equals(probNum)){
                                formattedStudents += "ID: " + user.value1() + ", Username: "
                                        + user.value2() + ", Current Flowchart Problem: " + user.value4()
                                        + ", Current Code Problem: " + user.value5()
                                        + ", Current Code Metric Problem: " +  user.value5() + "\n\n";
                            }
                        }
                        outputArea.setText(formattedStudents);
                    }else{
                        System.out.println("Querying");
                        String formattedStudents = "";
                        for(Record5<Long, String, String, Integer, Integer> user : users){
                            if(user.value3().equals("Student")){
                                formattedStudents += "ID: " + user.value1() + ", Username: "
                                        + user.value2() + ", Current Flowchart Problem: " + user.value4()
                                        + ", Current Code Problem: " + user.value5()
                                        + ", Current Code Metric Problem: " +  user.value5() + "\n\n";
                            }
                        }
                        outputArea.setText(formattedStudents);
                    }
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.TeacherView);
            }
        });

        buttonPanel.add(returnButton);
        buttonPanel.add(queryButton);
        leftBottom.add(buttonPanel, BorderLayout.SOUTH);
        leftBottom.add(checkListPanel,BorderLayout.CENTER);

        leftPanel.add(leftTop);
        leftPanel.add(leftBottom);

        add(leftPanel);
        add(scrollPane);

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
