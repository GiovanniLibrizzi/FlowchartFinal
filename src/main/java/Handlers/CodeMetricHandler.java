package Handlers;

import Database.CodeProblems;
import Database.DB;
import Panels.CodeMetricsCodePanel;
import Problem_Engine.DatabaseProblemInserter;
import org.jooq.Record;
import org.jooq.Record9;
import org.jooq.Result;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;

public class CodeMetricHandler{
    private static CodeMetricHandler codeMetricHandler = null;
    private static CodeMetricsCodePanel codePanel;
    private static int loc;
    private static int eloc;
    private static int lloc;
    private static int cc;
    private int problemNum;

    private ArrayList<String> codeProblems = new ArrayList<>();
    private CodeMetricHandler(){
        this.problemNum = 0;
        for(int i = 0; i < DatabaseProblemInserter.getResultSize(CodeProblems.CODE_PROBLEMS_TABLE); i++){
            codeProblems.add(DatabaseProblemInserter.retrieveCodeProblems(i).getProblem());
        }
    }

    public static CodeMetricHandler getInstance(){
        if(codeMetricHandler == null){
            codeMetricHandler = new CodeMetricHandler();
        }
        return codeMetricHandler;
    }

    public String getCurrProblem(){
        try{
            String codeProblem = codeProblems.get(problemNum);
            System.out.println(codeProblem);
            return codeProblem;
        } catch(Error e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "No more questions! Good Job!");
        }
        return "could not load problem from database";
    }

    public static void submitUserAnswers(int givenloc, int giveneloc, int givenlloc, int givencc){
        loc = givenloc;
        eloc = giveneloc;
        lloc = givenlloc;
        cc = givencc;
    }

    public void submit(){
        try{
            Result<Record> result = DatabaseProblemInserter.getDsl().select()
                    .from(CodeProblems.CODE_PROBLEMS_TABLE)
                    .fetch();
            Record record = result.get(problemNum);

            int correctloc = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("loc", Integer.class));
            int correcteloc = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("eloc", Integer.class));
            int correctlloc = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("lloc", Integer.class));
            int correctcc = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("cc", Integer.class));

            if(loc == correctloc && eloc == correcteloc && lloc == correctlloc && cc == correctcc){
                JOptionPane.showMessageDialog(null, "Correct Answer!");
                this.problemNum += 1;
                codePanel.update();
            }
            else{
                JOptionPane.showMessageDialog(null, "Wrong Answer!");
            }
        } catch(Error e){
            System.out.println(e.getMessage());
        }
    }

    public static void addObserver(CodeMetricsCodePanel givenCodePanel){
        codePanel = givenCodePanel;
    }


}
