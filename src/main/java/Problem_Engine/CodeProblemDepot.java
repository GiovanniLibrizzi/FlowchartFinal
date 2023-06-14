package Problem_Engine;

import Database.CodeProblems;
import Database.FlowchartProblems;

import java.util.ArrayList;

public class CodeProblemDepot {
    private static CodeProblemDepot codeProblemDepot;
    private ArrayList<CodeProblem> codeProblems = new ArrayList<>();

    private ArrayList<FlowchartProblem> flowchartProblems = new ArrayList<>();

    private int counter = 0;

    private int counterFlowchart = 0;

    private CodeProblemDepot() {
        // Private constructor to prevent instantiation from outside the class
        for (int i = 0; i < DatabaseProblemInserter.getResultSize(CodeProblems.CODE_PROBLEMS_TABLE);
             i++) {
            codeProblems.add(DatabaseProblemInserter.retrieveCodeProblems(i));
        }

        for (int i = 0; i < DatabaseProblemInserter.getResultSize(FlowchartProblems.FLOWCHART_PROBLEMS_TABLE);
            i++) {
            flowchartProblems.add(DatabaseProblemInserter.retrieveFlowchartProblems(i));
        }
    }

    public static CodeProblemDepot getInstance() {
        if (codeProblemDepot == null) {
            synchronized (CodeProblemDepot.class) {
                if (codeProblemDepot == null) {
                    codeProblemDepot = new CodeProblemDepot();
                }
            }
        }
        return codeProblemDepot;
    }

    // Other methods and variables of the class
    public CodeProblem getCurrentProblem() {
        if (counter >= codeProblems.size()) {
            counter = 0;
        }
        return codeProblems.get(counter);
    }

    public FlowchartProblem getCurrentFlowchartProblem() {
        if (counterFlowchart >= flowchartProblems.size()) {
            counterFlowchart = 0;
        }
        return flowchartProblems.get(counterFlowchart);
    }


    public CodeProblem getNextProblem() {
        counter++;
        return getCurrentProblem();
    }

    public FlowchartProblem getNextFlowchartProblem() {
        counterFlowchart++;
        return getCurrentFlowchartProblem();
    }

    public int getCounterFlowchart() {
        return counterFlowchart;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounterFlowchart(int v) {
        counterFlowchart = v;
    }

    public int getSizeOfCodeProblems(){
        return codeProblems.size();
    }

}
