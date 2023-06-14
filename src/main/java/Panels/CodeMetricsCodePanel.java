package Panels;

import Handlers.CodeMetricHandler;
import Problem_Engine.CodeProblem;
import Problem_Engine.CodeProblemDepot;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CodeMetricsCodePanel extends JPanel {
    private JTextArea currentCode;
    public CodeMetricsCodePanel(){
        setBackground(PanelConstants.CUSTOM_WHITE);
        BoxLayout fl = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(fl);

        JLabel codeTitle = new JLabel("Code:");
        codeTitle.setFont(new Font("Dialog",Font.BOLD, 16));
        codeTitle.setForeground(PanelConstants.CUSTOM_GREY);;
        add(codeTitle);

        //Get CodeProblem from Database
        CodeMetricHandler codeMetricHandler = CodeMetricHandler.getInstance();
        CodeMetricHandler.addObserver(this);
        String currentProblem = codeMetricHandler.getCurrProblem();
        //Add to TextArea for display
        currentCode = new JTextArea(currentProblem);
        currentCode.setEditable(false);
        currentCode.setFont(CodePanel.fontCode);

        add(currentCode);
    }

    public void update() {
        //Get CodeProblem from Database
        CodeMetricHandler codeMetricHandler = CodeMetricHandler.getInstance();
        String currentProblem = codeMetricHandler.getCurrProblem();
        //Add to TextArea for display
        currentCode.setText(currentProblem);
    }
}
