package ProblemEngine;

import Problem_Engine.MetricsCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MetricsCalculatorTest {

    //Test with null
    @Test
    void calculateMetricWithNull(){
        int[] expectedResult = {0,0,0,0};
        Assertions.assertArrayEquals(expectedResult,MetricsCalculator.calculateMetrics(null));
    }

    //Test with EmptyString
    @Test
    void calculateMetricsTestWithEmtpyString() {
        int[] expectedResult = {1,1,0,1};
        Assertions.assertArrayEquals(expectedResult, MetricsCalculator.calculateMetrics(""));
    }

    @Test
    void calculateMetricsTestWithNormalCode() {
        int[] expectedResult = {9,6,3,2};
        String code = "public void test(){\n" +
            "System.out.println(\"this is a test\");\n" +
            "if ( 1<2){\n" +
            "int x = 0;\n" +
            "}\n" +
            "else{\n" +
            "int y = 4;\n" +
            "}\n" +
            "}\n";
        Assertions.assertArrayEquals(expectedResult, MetricsCalculator.calculateMetrics(code));
    }
}