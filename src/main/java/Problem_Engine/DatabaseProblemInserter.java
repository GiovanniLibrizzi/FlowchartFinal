package Problem_Engine;

import Database.CodeProblems;
import Database.DB;
import java.time.OffsetDateTime;

import Database.FlowchartProblems;
import Handlers.SaveManager;
import Shapes.Flowchart;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class DatabaseProblemInserter {

    private static final DSLContext dsl = DSL.using(DB.configure());

    public static DSLContext getDsl(){
        return dsl;
    }

    public static void insertCodeProblem(String problemText, String solutionText) {
        // Define the table fields explicitly
        Field<String> problem = DSL.field("problem", SQLDataType.VARCHAR);
        Field<String> answer = DSL.field("answer", SQLDataType.VARCHAR);

        // Insert a record into the CodeProblems table
        dsl.insertInto(CodeProblems.CODE_PROBLEMS_TABLE)
            .set(problem, problemText)
            .set(answer, solutionText)
            .execute();
    }

    public static void insertFlowchartProblem(String problemText, String flowchartJson) {
        Field<String> problem = DSL.field("problem", SQLDataType.VARCHAR);
        Field<String> answer = DSL.field("answer", SQLDataType.VARCHAR);

        dsl.insertInto(FlowchartProblems.FLOWCHART_PROBLEMS_TABLE)
                .set(problem, problemText)
                .set(answer, flowchartJson)
                .execute();
    }

    public static CodeProblem retrieveCodeProblems(int desiredId) {
        // Retrieve all records from the CodeProblems table
        Result<Record> result = dsl.select()
            .from(CodeProblems.CODE_PROBLEMS_TABLE)
            .fetch();

        Record record = result.get(desiredId);
        Long id = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("id", Long.class));
        OffsetDateTime createdAt =
            record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("created_at", OffsetDateTime.class));
        String problem =
            record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("problem", String.class));
        String answer = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("answer", String.class));
        String hint = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("hint",String.class));
        String flowchart = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("flowchart", String.class));

        return new CodeProblem(id, createdAt, problem, answer,hint, flowchart);


    }

    public static FlowchartProblem retrieveFlowchartProblems(int desiredId) {
        Result<Record> result = dsl.select()
                .from(FlowchartProblems.FLOWCHART_PROBLEMS_TABLE)
                .fetch();

        Record record = result.get(desiredId);
        Long id = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("id", Long.class));
        OffsetDateTime createdAt =
                record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("created_at", OffsetDateTime.class));
        String problem =
                record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("problem", String.class));
        String answer = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("answer", String.class));

        return new FlowchartProblem(id, createdAt, problem, answer);
    }

    private static void displayAllCodeProblems() {
        // Retrieve all records from the CodeProblems table
        Result<Record> result = dsl.select()
            .from(CodeProblems.CODE_PROBLEMS_TABLE)
            .fetch();

        // Print the retrieved records
        for (org.jooq.Record record : result) {
            Long id = record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("id", Long.class));
            OffsetDateTime createdAt = record.get(
                CodeProblems.CODE_PROBLEMS_TABLE.field("created_at", OffsetDateTime.class));
            String problem =
                record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("problem", String.class));
            String answer =
                record.get(CodeProblems.CODE_PROBLEMS_TABLE.field("answer", String.class));


            System.out.println("ID: " + id);
            System.out.println("Created At: " + createdAt);
            System.out.println("Problem: " + problem);
            System.out.println("Answer: " + answer);
            System.out.println();


        }
    }

    public static int getResultSize(TableImpl<Record> table) {
        Result<Record> result = dsl.select()
            .from(table)
            .fetch();
        return result.size();
    }

    public static long getSmallestId() {
        Result<Record> result = dsl.select()
            .from(CodeProblems.CODE_PROBLEMS_TABLE)
            .fetch();

        return result.get(0).get(CodeProblems.CODE_PROBLEMS_TABLE.field("id", Long.class));
    }


    public static void main(String[] args) {
        DB.deleteFlowchartProblems();
        String f1 = "[{\"type\":\"Parallelogram\",\"x1\":136,\"y1\":257,\"label\":\"\\\"Hello world!\\\"\"}]~~~[]";
        insertFlowchartProblem("System.out.println(\"Hello world!\")", f1);

        insertFlowchartProblem("import java.util.Scanner;\npublic class Add {\n\tpublic static void main(String[] args) {\n\t\tScanner scan = new Scanner(System.in);\n\t\t\n\t\tSystem.out.println(\"Enter value of a: \");\n\t\tint a = scan.nextInt();\n\t\t\n\t\tSystem.out.println(\"Enter value of b: \");\n\t\tint b = scan.nextInt();\n\t\t\n\t\tint c = a + b;\n\t\tSystem.out.println(\"Sum is: \" + c);\n\t}\n}",
                "[{\"type\":\"Parallelogram\",\"x1\":148,\"y1\":129,\"label\":\"Read a, b\"},{\"type\":\"Parallelogram\",\"x1\":152,\"y1\":361,\"label\":\"Write c\"},{\"type\":\"RectangleStandard\",\"x1\":150,\"y1\":238,\"label\":\"c \\u003d a + b\"}]~~~[{\"label\":\"\",\"id1\":0,\"id2\":2},{\"label\":\"\",\"id1\":2,\"id2\":1}]");

        insertFlowchartProblem("import java.util.Scanner;\npublic class Square {\n\tpublic static void main(String[] args) {\n\t\tScanner scan = new Scanner(System.in);\n\t\t\n\t\tSystem.out.println(\"Enter length of a square: \");\n\t\tint length = scan.nextInt();\n\t\tint area = length * length;\n\t\tint perimeter = length * 4;\n\t\t\n\t\tSystem.out.println(\"Area: \" + area);\n\t\tSystem.out.println(\"Perimeter: \" + perimeter);\n\t}\n}",
                "[{\"type\":\"Parallelogram\",\"x1\":170,\"y1\":145,\"label\":\"Read length\"},{\"type\":\"RectangleStandard\",\"x1\":170,\"y1\":261,\"label\":\"area \\u003d length * length, perimeter \\u003d 4 * length\"},{\"type\":\"Parallelogram\",\"x1\":171,\"y1\":378,\"label\":\"write area, perimeter\"}]~~~[{\"label\":\"\",\"id1\":0,\"id2\":1},{\"label\":\"\",\"id1\":1,\"id2\":2}]");

        insertFlowchartProblem("// Maximum of 2 numbers\nimport java.util.Scanner;\npublic static void main(String[] args) {\n\tScanner scan = new Scanner(System.in);\n\t\n\tSystem.out.println(\"Enter value of a: \");\n\tint a = scan.nextInt();\n\t\n\tSystem.out.println(\"Enter value of b: \");\n\tint b = scan.nextInt();\n\t\n\tif (a > b) {\n\t\tSystem.out.println(\"a is larger\");\n\t} else {\n\t\tSystem.out.println(\"b is larger\");\n\t}\n}",
                "[{\"type\":\"Parallelogram\",\"x1\":161,\"y1\":123,\"label\":\"read a, b\"},{\"type\":\"Diamond\",\"x1\":161,\"y1\":222,\"label\":\"is a \\u003e b?\"},{\"type\":\"Parallelogram\",\"x1\":83,\"y1\":334,\"label\":\"b is larger\"},{\"type\":\"Parallelogram\",\"x1\":228,\"y1\":335,\"label\":\"a is larger\"}]~~~[{\"label\":\"\",\"id1\":0,\"id2\":1},{\"label\":\"yes\",\"id1\":1,\"id2\":3},{\"label\":\"no\",\"id1\":1,\"id2\":2}]");

        insertFlowchartProblem("import java.util.Scanner;\npublic static void main(String[] args) {\n\tint sum = 0;\n\tint n = 0;\n\t\n\twhile (n < 10) {\n\t\tsum += n;\n\t\tn++;\n\t}\n\t\n\tSystem.out.println(sum);\n}",
                "[{\"type\":\"RectangleStandard\",\"x1\":159,\"y1\":145,\"label\":\"sum \\u003d 0, n \\u003d 0\"},{\"type\":\"Diamond\",\"x1\":159,\"y1\":266,\"label\":\"n \\u003c 10?\"},{\"type\":\"RectangleStandard\",\"x1\":160,\"y1\":403,\"label\":\"sum +\\u003d n\"},{\"type\":\"RectangleStandard\",\"x1\":54,\"y1\":397,\"label\":\"n++\"},{\"type\":\"Parallelogram\",\"x1\":294,\"y1\":335,\"label\":\"print sum\"}]~~~[{\"label\":\"\",\"id1\":0,\"id2\":1},{\"label\":\"false\",\"id1\":1,\"id2\":2},{\"label\":\"\",\"id1\":2,\"id2\":3},{\"label\":\"\",\"id1\":3,\"id2\":1},{\"label\":\"true\",\"id1\":1,\"id2\":4}]");

        FlowchartProblem p1 = retrieveFlowchartProblems(0);
        System.out.println(p1.getAnswerJson());

    }

   /* public static void main(String[] args) {
        displayAllCodeProblems();
        System.out.println(retrieveCodeProblems(0));
    }
    /*
    public static void main(String[] args) {
        //Insert statements of the first Problems inserted in the database

     */
        /*
        insertCodeProblem(dsl,"public class ArraySum {" +
            "public static void main(String[] args) {" +
            "int[] numbers = {2, 4, 6, 8, 10};" +
            "    int sum = ......;" +
            "for (int i=0; i < numbers.length; i++) {" +
            "......;"+
            "}"+
            "" +
            "    System.out.println(Sum of the numbers is  + sum);" +
            "}" +
            "}","0:sum+=number");

        insertCodeProblem(dsl,"public class NumberSum {" +
            "public static void main(String[] args) {" +
            "int num1 = 25;" +
            "int num2 = 37;"+
            " int sum = ......;" +
            "" +
            "    System.out.println(Sum:  + sum);" +
            "}" +
            "}","num1+num2");





        insertCodeProblem(dsl,"public class ArraySum {" +
            "    public static int calculateSum(int[] arr) {" +
            "        int sum = 0;" +
            "        for (int i = 0; i < .......; i++) {" +
            "            sum += arr[i];" +
            "        }" +
            "        return .......;" +
            "    }" +
            "" +
            "    public static void main(String[] args) {" +
            "        int[] array = {1, 2, 3, 4, 5};" +
            "        int result = calculateSum(array);" +
            "        System.out.println(Sum of array elements:  + result);" +
            "    }" +
            "}","arr.length():sum" );
        insertCodeProblem(dsl,"public class RandomNumber {" +
            "    public static void main(String[] args) {" +
            "//Create a random number between 0 and 100 using Math.random" +
            "        int randomNumber = ......" +
            "        " +
            "        System.out.println(Random Number:  + randomNumber);" +
            "    }" +
            "}","(int) (Math.random() * 100)");
        insertCodeProblem(dsl,"public class ArraySum {" +
            "    public static void main(String[] args) {" +
            "        int[] numbers = {2, 4, 6, 8, 10};" +
            "" +
            "//Get the sum using the Arrays.stream() Api"+
            "        int sum = ......" +
            "        int average = ......" +
            "" +
            "        System.out.println(Sum:  + sum);" +
            "        System.out.println(Average:  + average);" +
            "    }" +
            "}","Arrays.stream(numbers).sum():sum/numbers.length");
        insertCodeProblem(dsl, "public class StringConcatenation {" +
            "    public static void main(String[] args) {" +
            "        String firstName = John;" +
            "        String lastName = Doe;" +
            "" +
            "        String fullName = ......" +
            "        String greeting = ......" +
            "" +
            "        System.out.println(Full Name: + fullName);" +
            "        System.out.println(Greeting:  + greeting);" +
            "    }" +
            "}","firstName + + lastName:Hello, + fullName + !");
        insertCodeProblem(dsl,"public class NumberComparison {" +
            "    public static void main(String[] args) {" +
            "        int num1 = 15;" +
            "        int num2 = 20;" +
            "" +
            "        boolean isNum1GreaterThanNum2 = ......" +
            "        boolean isNum1EqualToNum2 = ......" +
            "" +
            "        System.out.println(Is Num1 greater than Num2?  + isNum1GreaterThanNum2);" +
            "        System.out.println(Is Num1 equal to Num2?  + isNum1EqualToNum2);" +
            "    }" +
            "}","num1>num2:num1==num2");

         */
    /*
        displayAllCodeProblems();
    }
    */
}
