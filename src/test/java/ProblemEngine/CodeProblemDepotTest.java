package ProblemEngine;

import static org.junit.jupiter.api.Assertions.*;

import Problem_Engine.CodeProblemDepot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodeProblemDepotTest {

    //ProblemDepot starts with the first Problem in the database
    @Test
    void getCurrentProblemTestId() {
        Assertions.assertEquals(1, CodeProblemDepot.getInstance().getCurrentProblem().getId());
    }

    //Check if all the Problems get picked in the right order
    @Test
    void getCurrentProblemCountUP(){
        for (int i =0; i < CodeProblemDepot.getInstance().getSizeOfCodeProblems(); i++){
            Assertions.assertEquals(i+1, CodeProblemDepot.getInstance().getCurrentProblem().getId());
            CodeProblemDepot.getInstance().getNextProblem();
        }
    }

    //Check if the nextProblem Method gives a different Problem
    @Test
    void testCurrentvsNextProblem(){
        assertNotEquals(CodeProblemDepot.getInstance().getCurrentProblem(),
            CodeProblemDepot.getInstance().getNextProblem());
    }
}