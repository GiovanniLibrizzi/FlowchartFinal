package Handlers;
import Shapes.Flowchart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ControlHandler - detects actions being performed
 * @author Jacob Balikov, Giovanni Librizzi, Nicholas Zarate, Jin Wu, Umair Pathan, Amogh Prajapat
 * @version FlowchartFinal v1.0
 */
public class MenuBarControl implements ActionListener {


    public MenuBarControl() {

    }

    /**
     * Checks actions that can be performed by the users and executes the appropriate action
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Repository repo = Repository.getInstance();
        //System.out.println(e.getActionCommand());
        // Handling all MenuOptions
        switch(e.getActionCommand()){
            case "Submit":
                PanelHandler.getInstance().switchWorkingPanel(PanelHandler.Panel.FlowChartProblem);
                System.out.println("CHANGE!");
                break;
            case "Clear":
                repo.clear();
                break;
            case "Save file":
                String saveFile = JOptionPane.showInputDialog("Enter File Name:");
                SaveManager.getSaveManager().save(saveFile);
                break;
            case "Load file":
                String loadFile = JOptionPane.showInputDialog("Enter File to Load:");
                if (loadFile != null) {
                    repo.clear();
                    repo.add(SaveManager.getSaveManager().load(loadFile));
                }
                break;
            case "Undo Shape":
                repo.undoShape();
                break;
            case "Undo Line":
                repo.undoLine();
                break;
            case "Call a Method":
                repo.setShapeSelection("RectangleToolMethod");
                break;
            case "Instruction":
                repo.setShapeSelection("RectangleStandard");
                break;
            case "Input or Output":
                repo.setShapeSelection("Parallelogram");
                break;
            case "Variable Declaration":
                repo.setShapeSelection("RectangleToolVariable");
                break;
            case "Condition":
                repo.setShapeSelection("Diamond");
                break;

            case "About":
                JOptionPane.showMessageDialog(null, "Authors of the project are: Jacob Balikov, Giovanni Librizzi, \nNicholas Zarate, Jin Wu, Umair Pathan, Amogh Prajapat");
                break;
        }
    }

}

