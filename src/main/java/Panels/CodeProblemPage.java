package Panels;
import Database.DB;
import Handlers.*;
import Problem_Engine.CodeProblem;
import Problem_Engine.CodeProblemDepot;
import Problem_Engine.FlowchartProblem;
import Shapes.ConnectingLine;
import Shapes.Flowchart;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.List;

/**
 * This Panels.FlowChartProblemPage class represents the JPanel with all elements of a coding problem page on it
 * @author Jacob Balikov, Giovanni Librizzi, Jin Wu, Amogh Prajapat, Stefan Lutsch
 */
public class CodeProblemPage extends WorkingPanel {
    WorkingPanel diagramPanel;
    HintControl hintControl;
    ChatGPTControl chatGPTControl;
    public CodeProblemPage(){
        // Initializing all JPanels
        TutorPanel tutorPanel = new TutorPanel();
        ChatGPTResponsePanel chatPanel = new ChatGPTResponsePanel();
        JPanel typePanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel midPanel = new JPanel();
        FlowchartCodeBlanksPanel codePanel = new FlowchartCodeBlanksPanel();
        codePanel.updateProblem(DB.getUserFlowchartProgress(Repository.getInstance().getCurrentUser()));
        JPanel submitPanel = new CodeEntryPanel();
        diagramPanel = new Workspace();

        Repository.getInstance().addObserver(diagramPanel);
        DiagramControl c = new DiagramControl();
        diagramPanel.addMouseListener(c);
        diagramPanel.addMouseMotionListener(c);
        //PanelHandler.getInstance().InitWorkspace(diagramPanel);

        // Setting up borders for JPanels
        Border blackBorder = BorderFactory.createLineBorder(PanelConstants.CUSTOM_BLACK);
        tutorPanel.setBorder(blackBorder);
        chatPanel.setBorder(blackBorder);
        typePanel.setBorder(blackBorder);
        leftPanel.setBorder(blackBorder);
        midPanel.setBorder(blackBorder);
        codePanel.setBorder(blackBorder);
        submitPanel.setBorder(blackBorder);
        diagramPanel.setBorder(blackBorder);

        // Setting up gridlayouts for all 3 sections (and tutor section)
        GridLayout leftLayout = new GridLayout(2, 1);
        GridLayout midLayout = new GridLayout(2, 1);
        GridLayout mainLayout = new GridLayout(1, 3);
        GridLayout tutorLayout = new GridLayout(2, 1);

        // Applying gridlayouts to JPanels
        this.setLayout(mainLayout);
        leftPanel.setLayout(leftLayout);
        midPanel.setLayout(midLayout);
        tutorPanel.setLayout(tutorLayout);

        // Setting up sub panels

        // Code Panel
        JLabel codeIn = new JLabel("  Code Problem Here");
        //codePanel.add(codeIn);

        // Submit Panel
        JLabel codeOut = new JLabel("  Code Problem Submit Here");
        //submitPanel.add(codeOut);

        // Diagram Panel
        JLabel flowchartOut = new JLabel("  Flowchart Area Here ");
        //diagramPanel.add(flowchartOut);

        // Adding sub panels to main panels
        leftPanel.add(tutorPanel);
        leftPanel.add(chatPanel);

        //midPanel.add(codePanel);
        //midPanel.add(submitPanel);

        // Adding main 3 panels to Page
        this.add(leftPanel);
        this.add(diagramPanel);
        this.add(codePanel);



        JButton next = new RoundedButton("Next", 25);
        next.setVisible(false);
        codePanel.add(next);

        //this.add(midPanel);
        JButton submit = new RoundedButton("Submit",25);
        codePanel.add(submit);


        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flowchart fl = Repository.getInstance().getFlowchart();
                Flowchart flExpected = CodeProblemDepot.getInstance().getCurrentFlowchartProblem().getAnswer();


                String hint = fl.generateHint(flExpected);

                if (hint != null) {
                    tutorPanel.setText("<html>" + hint + "</html>");
                } else {
                    tutorPanel.setText("Correct! Great job!");
                    next.setVisible(true);
                    DB.updateUserFlowchartProgress(Repository.getInstance().getCurrentUser(), CodeProblemDepot.getInstance().getCounterFlowchart()+2);

                }

            }
        });

        JButton back = new RoundedButton("Back",25);
        codePanel.add(back);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e.getActionCommand());

                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.MainMenu);
                //switchPanel();

            }
        });


        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next.setVisible(false);
                tutorPanel.setText("Click for a motivation!");
                Repository.getInstance().clear();
                FlowchartProblem problem = CodeProblemDepot.getInstance().getNextFlowchartProblem();
                codePanel.updateProblem(problem);
            }
        });

        // Control Handler for Tutor Panel
        this.hintControl = new HintControl(tutorPanel);
        tutorPanel.setControlHandler(this.hintControl);

        // Control Handler for ChatGPT
        this.chatGPTControl = new ChatGPTControl(chatPanel);
        chatPanel.setControlHandler(this.chatGPTControl);

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Point getDiagramLocation() {
        return diagramPanel.getLocation();
    }



}

