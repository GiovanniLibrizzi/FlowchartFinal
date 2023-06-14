package Handlers;
import Panels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelHandler extends JFrame {

    //private JPanel panelContainer;
    //private CardLayout cardLayout;
    private WorkingPanel mainPanel;
    private Repository mainRepo;
    private ControlHandler mainController;

    public enum Panel {
        Workspace,
        Login,
        MainMenu,
        FlowChartProblem,
        CodeProblem,
        CreateAccount,
        CodeMetricProblem,
        TeacherView,
        TeacherData,
        TeacherProblem,
        ViewProgress
    }
    private static PanelHandler panelHandler;

    /**
     * Singleton - gets the static panelHandler instance
     * @return panelHandler
     */
    public static PanelHandler getInstance(){
        if (panelHandler == null)
            panelHandler = new PanelHandler();
        return panelHandler;
    }

    public PanelHandler() {
        this.mainPanel = new Workspace();
        this.mainRepo = Repository.getInstance();

        setUpMenuBar();

        // Main starting panel
        switchWorkingPanel(Panel.Login);

        //switchWorkingPanel(Panel.TeacherView);
        //switchWorkingPanel(Panel.CodeProblem);
    }


    // Setup for JMenu Options/ActionListener for Workspace
    public void setUpMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu ("File");
        JMenuItem clear = new JMenuItem ("Clear");
        JMenuItem save = new JMenuItem ("Save file");
        JMenuItem load = new JMenuItem ("Load file");
        JMenuItem undoShape = new JMenuItem("Undo Shape");
        JMenuItem undoLine = new JMenuItem("Undo Line");

        JMenu menuHelp = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");


        JMenu shapes = new JMenu("Shapes");
        JMenuItem method = new JMenuItem("Call a Method");
        JMenuItem instruction = new JMenuItem("Instruction");
        JMenuItem io = new JMenuItem("Input or Output");
        JMenuItem var = new JMenuItem("Variable Declaration");
        JMenuItem condition = new JMenuItem("Condition");

        menuBar.add(menu);
        menuBar.add(menuHelp);
        menuBar.add(shapes);

        menu.add(clear);
        menu.add(save);
        menu.add(load);
        menu.add(undoShape);
        menu.add(undoLine);

        menuHelp.add(about);

        shapes.add(method);
        shapes.add(instruction);
        shapes.add(io);
        shapes.add(var);
        shapes.add(condition);

        setJMenuBar(menuBar);

        ActionListener menuController = new MenuBarControl();

        about.addActionListener(menuController);
        clear.addActionListener(menuController);
        save.addActionListener(menuController);
        load.addActionListener(menuController);
        undoShape.addActionListener(menuController);
        undoLine.addActionListener(menuController);
        method.addActionListener(menuController);
        instruction.addActionListener(menuController);
        io.addActionListener(menuController);
        var.addActionListener(menuController);
        condition.addActionListener(menuController);
    }

    public void switchWorkingPanel(Panel panelType) {//, WorkingPanel newPanel, ControlHandler newController){
        getContentPane().removeAll();
        Repository.getInstance().clear();

        WorkingPanel newPanel = null;
        ControlHandler newController = null;
        // based on the type of Panel added, it will set up JMenu/ActionListners needed

        mainRepo.deleteObserver(mainPanel);

        switch(panelType){
            case Workspace:
                //setUpWorkspace();
                newPanel = new Workspace();
                newController = getController();
                break;
            case Login:
                newPanel = new LoginPanel();
                newController = getController(); // change this to a login controller later
                break;
            case MainMenu:
                newPanel = new MainMenuPanel();
                break;
            case FlowChartProblem:
                System.out.println("inside switchWorkingPanel");
                newPanel = new FlowChartProblemPage();
                //cardLayout.next(panelContainer);
                //cardLayout.
                //cardLayout.show(panelContainer, "flowchartPanel");
                newController = getController();    // change this to flowchart controller
                break;
            case CodeProblem:
                newPanel = new CodeProblemPage();
                break;
            case CreateAccount:
                newPanel = new CreateAccountPanel();
                break;
            case CodeMetricProblem:
                newPanel = new CodeMetricsProblemPage();
                break;
            case TeacherView:
                newPanel = new TeacherPanel();
                break;
            case TeacherData:
                newPanel = new TeacherDataPanel();
                break;
            case TeacherProblem:
                newPanel = new TeacherProblemPanel();
                break;
            case ViewProgress:
                newPanel = new ViewProgressPanel();
                break;
        }

        mainPanel = newPanel;
        getContentPane().add(mainPanel);
        revalidate();
        repaint();


        if(newController != null){
            mainController = newController;
        }
    }

    public ControlHandler getController() {
        return mainController;
    }

}
