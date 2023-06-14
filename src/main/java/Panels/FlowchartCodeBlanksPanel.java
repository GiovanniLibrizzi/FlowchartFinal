package Panels;

import Problem_Engine.CodeProblem;
import Problem_Engine.CodeProblemDepot;
import Problem_Engine.FlowchartProblem;

import javax.swing.*;
import java.awt.*;

public class FlowchartCodeBlanksPanel extends JPanel {

    JTextArea givenCode;
    CodeProblemDepot codeProblemDepot;
    public FlowchartCodeBlanksPanel() {
        setBackground(PanelConstants.CUSTOM_WHITE);
        BoxLayout fl = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(fl);

        JLabel codeTitle = new JLabel("Code:");
        codeTitle.setFont(new Font("Dialog", Font.BOLD, 16));
        codeTitle.setForeground(PanelConstants.CUSTOM_GREY);;
        add(codeTitle);

        //Get CodeProblem from Database
        codeProblemDepot = CodeProblemDepot.getInstance();
        FlowchartProblem currentProblem = codeProblemDepot.getCurrentFlowchartProblem();
        //Add to TextArea for display
        givenCode = new JTextArea(currentProblem.getProblem());
        givenCode.setEditable(false);
        givenCode.setFont(CodePanel.fontCode);
        givenCode.setTabSize(2);


        add(givenCode);
    }

    public void updateProblem() {
        givenCode.setText("<html>" + codeProblemDepot.getCurrentFlowchartProblem().getProblem() + "</html>");
    }

    public void updateProblem(int v) {
        codeProblemDepot.setCounterFlowchart(v);
        givenCode.setText(codeProblemDepot.getCurrentFlowchartProblem().getProblem());
    }

    public void updateProblem(FlowchartProblem problem) {
        givenCode.setText(problem.getProblem());
    }

}