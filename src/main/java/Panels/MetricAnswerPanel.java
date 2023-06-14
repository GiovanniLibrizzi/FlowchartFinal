package Panels;

import javax.swing.*;
import java.awt.*;

public class MetricAnswerPanel extends JPanel {
    JTextField locField;
    JTextField elocField;
    JTextField llocField;
    JTextField ccField;

    public MetricAnswerPanel(){
        setBackground(PanelConstants.CUSTOM_WHITE);
        GridLayout metricLayout = new GridLayout(4, 1);
        this.setLayout(metricLayout);
        // loc
        JLabel locLabel = new JLabel("Loc: ");
        locField = new JTextField(10);
        JPanel locPanel = new JPanel();
        locPanel.add(locLabel);
        locPanel.add(locField);
        this.add(locPanel);
        // eloc
        JLabel elocLabel = new JLabel("eLoc: ");
        elocField = new JTextField(10);
        JPanel elocPanel = new JPanel();
        elocPanel.add(elocLabel);
        elocPanel.add(elocField);
        this.add(elocPanel);
        // lloc
        JLabel llocLabel = new JLabel("lLoc: ");
        llocField = new JTextField(10);
        JPanel llocPanel = new JPanel();
        llocPanel.add(llocLabel);
        llocPanel.add(llocField);
        this.add(llocPanel);
        // cc
        JLabel ccLabel = new JLabel("cc: ");
        ccField = new JTextField(10);
        JPanel ccPanel = new JPanel();
        ccPanel.add(ccLabel);
        ccPanel.add(ccField);
        this.add(ccPanel);
    }
    public int[] getMetrics(){
        int[] metrics = new int[4];
        metrics[0] = Integer.MAX_VALUE;
        try{
            metrics[0] = Integer.parseInt(locField.getText());
            metrics[1] = Integer.parseInt(elocField.getText());
            metrics[2] = Integer.parseInt(llocField.getText());
            metrics[3] = Integer.parseInt(ccField.getText());
            return metrics;
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Please input numbers only!");
        }
        return metrics;
    }

}
