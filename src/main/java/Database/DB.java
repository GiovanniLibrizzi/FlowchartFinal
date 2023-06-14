package Database;
import com.password4j.Hash;
import com.password4j.Password;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.conf.Settings;
import org.jooq.impl.SQLDataType;

public class DB {
    public static Configuration configure() {
        // Create a ConnectionProvider to handle the database connection
        ConnectionProvider connectionProvider = new JDBCConnectionProvider();

        // Create a jOOQ Configuration object
        Configuration configuration = new DefaultConfiguration();

        // Configure the connection and dialect
        configuration.set(connectionProvider)
                .set(SQLDialect.POSTGRES);

        // Optionally, configure additional settings
        configuration.set(new Settings().withRenderFormatted(true));

        return configuration;
    }

    public static Boolean createAccount(String usernameText, String passwordText, boolean student) {
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        // Define the table fields explicitly
        Field<String> username = DSL.field("username", SQLDataType.VARCHAR);
        Field<String> password_mixed = DSL.field("password_mixed", SQLDataType.VARCHAR);
        Field<String> role = DSL.field("role", SQLDataType.VARCHAR);
        String roleText;
        if(student){
            roleText = "Student";
        }else{
            roleText = "Teacher";
        }
        Field<Integer> current_problem_flowchart = DSL.field("current_problem_flowchart", SQLDataType.INTEGER);
        Field<Integer> current_problem_code = DSL.field("current_problem_code", SQLDataType.INTEGER);

        Hash hash = Password.hash(passwordText).withBcrypt();
        String passHash = hash.getResult();

        try {
            // Insert a record into the CodeProblems table
            dsl.insertInto(Users.USERS_TABLE)
                    .set(username, usernameText)
                    .set(password_mixed, passHash)
                    .set(role, roleText)
                    .set(current_problem_flowchart, 1)
                    .set(current_problem_code, 1)
                    .execute();
        } catch (Exception e) {
            // Handle unique constraint violation (duplicate username)
            System.out.println("Username is already taken. Please choose a different username. \n" + e);
            return false;
        }
        return true;
    }

    public static Boolean login(String usernameText, String passwordText) {
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<String> username = DSL.field("username", SQLDataType.VARCHAR);
        Field<String> password_mixed = DSL.field("password_mixed", SQLDataType.VARCHAR);

        try {
            // Query the users table for the given username and password
            Result<Record1<String>> result = dsl.select(password_mixed)
                    .from(Users.USERS_TABLE)
                    .where(username.eq(usernameText))
                    .fetch();

            String hashFromDB = result.get(0).value1();
            boolean verified = Password.check(passwordText, hashFromDB).withBcrypt();

            if (verified) {
                System.out.println("Login successful.");
            } else {
                System.out.println("Invalid credentials. Please try again.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while authenticating the user.");
            return false;
        }
        return true;
    }


    // to use this: Result<Record5<Long, String, String, Integer, Integer>> users = DB.getUsers();
    // returns all users with their id, username, role, and current_problem
    public static Result<Record5<Long, String, String, Integer, Integer>> getUsers(){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> username = DSL.field("username", SQLDataType.VARCHAR);
        Field<String> role = DSL.field("role", SQLDataType.VARCHAR);
        Field<Integer> current_problem_flowchart = DSL.field("current_problem_flowchart", SQLDataType.INTEGER);
        Field<Integer> current_problem_code = DSL.field("current_problem_code", SQLDataType.INTEGER);

        try {
            // Query the users table
            Result<Record5<Long, String, String, Integer, Integer>> result =
                    dsl.select(id, username, role, current_problem_flowchart, current_problem_code)
                    .from(Users.USERS_TABLE)
                    .fetch();

            if (result.isNotEmpty()) {
                return result;
            } else {
                System.out.println("No users.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    // to use this: Result<Record5<Long, String, String, String, String>> flowProbs = DB.getFlowchartProblems();
    // returns all FlowchartProblems with their id, problem, answer, hint, and code
    public static Result<Record5<Long, String, String, String, String>> getFlowchartProblems(){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> problem = DSL.field("problem", SQLDataType.VARCHAR);
        Field<String> answer = DSL.field("answer", SQLDataType.VARCHAR);
        Field<String> hint = DSL.field("hint", SQLDataType.VARCHAR);
        Field<String> code = DSL.field("code", SQLDataType.VARCHAR);

        try {
            // Query the users table
            Result<Record5<Long, String, String, String, String>> result =
                    dsl.select(id, problem, answer, hint, code)
                            .from(FlowchartProblems.FLOWCHART_PROBLEMS_TABLE)
                            .fetch();

            if (result.isNotEmpty()) {
                return result;
            } else {
                System.out.println("No flowchart problems.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    // to use this:
    // Result<Record9<Long, String, String, String, String, Integer, Integer, Integer, Integer>> codeProbs = DB.getCodeProblems();
    // returns all CodeProblems with their id, problem, answer, hint, flowchart, loc, eloc, lloc, and cc
    public static Result<Record9<Long, String, String, String, String, Integer, Integer, Integer, Integer>> getCodeProblems(){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> problem = DSL.field("problem", SQLDataType.VARCHAR);
        Field<String> answer = DSL.field("answer", SQLDataType.VARCHAR);
        Field<String> hint = DSL.field("hint", SQLDataType.VARCHAR);
        Field<String> flowchart = DSL.field("flowchart", SQLDataType.VARCHAR);
        Field<Integer> loc = DSL.field("loc", SQLDataType.INTEGER);
        Field<Integer> eloc = DSL.field("eloc", SQLDataType.INTEGER);
        Field<Integer> lloc = DSL.field("lloc", SQLDataType.INTEGER);
        Field<Integer> cc = DSL.field("cc", SQLDataType.INTEGER);

        try {
            // Query the users table
            Result<Record9<Long, String, String, String, String, Integer, Integer, Integer, Integer>> result =
                    dsl.select(id, problem, answer, hint, flowchart, loc, eloc, lloc, cc)
                            .from(CodeProblems.CODE_PROBLEMS_TABLE)
                            .fetch();

            if (result.isNotEmpty()) {
                return result;
            } else {
                System.out.println("No code problems.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    // to use this: Result<Record5<Long, String, String, Integer, Integer>> user = DB.getUserByID(idNum);
    // returns a single user that has the given id with their id, username, role, and current_problem
    public static Result<Record5<Long, String, String, Integer, Integer>> getUserByID(long idNum){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> username = DSL.field("username", SQLDataType.VARCHAR);
        Field<String> role = DSL.field("role", SQLDataType.VARCHAR);
        Field<Integer> current_problem_flowchart = DSL.field("current_problem_flowchart", SQLDataType.INTEGER);
        Field<Integer> current_problem_code = DSL.field("current_problem_code", SQLDataType.INTEGER);

        try {
            // Query the users table
            Result<Record5<Long, String, String, Integer, Integer>> result =
                    dsl.select(id, username, role, current_problem_flowchart, current_problem_code)
                            .from(Users.USERS_TABLE)
                            .where(id.eq(idNum))
                            .fetch();

            if (result.isNotEmpty()) {
                return result;
            } else {
                System.out.println("No users with given id.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    // to use this: Result<Record5<Long, String, String, Integer, Integer>> user = DB.getUserByUsername(username);
    // returns a single user that has the given username with their id, username, role, and current_problem
    public static Result<Record5<Long, String, String, Integer, Integer>> getUserByUsername(String usernameText){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> username = DSL.field("username", SQLDataType.VARCHAR);
        Field<String> role = DSL.field("role", SQLDataType.VARCHAR);
        Field<Integer> current_problem_flowchart = DSL.field("current_problem_flowchart", SQLDataType.INTEGER);
        Field<Integer> current_problem_code = DSL.field("current_problem_code", SQLDataType.INTEGER);

        try {
            // Query the users table
            Result<Record5<Long, String, String, Integer, Integer>> result =
                    dsl.select(id, username, role, current_problem_flowchart, current_problem_code)
                            .from(Users.USERS_TABLE)
                            .where(username.eq(usernameText))
                            .fetch();

            if (result.isNotEmpty()) {
                return result;
            } else {
                System.out.println("No users with given username.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    // to use this: Result<Record5<Long, String, String, String, String>> flowProb = DB.getFlowchartProblemByID(idNum);
    // returns a single FlowchartProblem with the given id with their id, problem, answer, hint, and code
    public static Result<Record5<Long, String, String, String, String>> getFlowchartProblemByID(long idNum){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> problem = DSL.field("problem", SQLDataType.VARCHAR);
        Field<String> answer = DSL.field("answer", SQLDataType.VARCHAR);
        Field<String> hint = DSL.field("hint", SQLDataType.VARCHAR);
        Field<String> code = DSL.field("code", SQLDataType.VARCHAR);

        try {
            // Query the users table
            Result<Record5<Long, String, String, String, String>> result =
                    dsl.select(id, problem, answer, hint, code)
                            .from(FlowchartProblems.FLOWCHART_PROBLEMS_TABLE)
                            .where(id.eq(idNum))
                            .fetch();

            if (result.isNotEmpty()) {
                return result;
            } else {
                System.out.println("No flowchart problems with given id.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    // to use this:
    // Result<Record9<Long, String, String, String, String, Integer, Integer, Integer, Integer>> codeProb = DB.getCodeProblemByID(idNum);
    // returns a single CodeProblem with the given id with their id, problem, answer, hint, flowchart, loc, eloc, lloc, and cc
    public static Result<Record9<Long, String, String, String, String, Integer, Integer, Integer, Integer>> getCodeProblemByID(long idNum){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> problem = DSL.field("problem", SQLDataType.VARCHAR);
        Field<String> answer = DSL.field("answer", SQLDataType.VARCHAR);
        Field<String> hint = DSL.field("hint", SQLDataType.VARCHAR);
        Field<String> flowchart = DSL.field("flowchart", SQLDataType.VARCHAR);
        Field<Integer> loc = DSL.field("loc", SQLDataType.INTEGER);
        Field<Integer> eloc = DSL.field("eloc", SQLDataType.INTEGER);
        Field<Integer> lloc = DSL.field("lloc", SQLDataType.INTEGER);
        Field<Integer> cc = DSL.field("cc", SQLDataType.INTEGER);

        try {
            // Query the users table
            Result<Record9<Long, String, String, String, String, Integer, Integer, Integer, Integer>> result =
                    dsl.select(id, problem, answer, hint, flowchart, loc, eloc, lloc, cc)
                            .from(CodeProblems.CODE_PROBLEMS_TABLE)
                            .where(id.eq(idNum))
                            .fetch();

            if (result.isNotEmpty()) {
                return result;
            } else {
                System.out.println("No code problems with given id.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    public static boolean deleteUsers(){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        try {
            // Query the users table
            dsl.truncate(Users.USERS_TABLE).restartIdentity().execute();
            System.out.println("Successful, all rows deleted and identity reset");
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred when resetting the users table.");
            return false;
        }
    }

    public static boolean deleteFlowchartProblems(){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        try {
            // Query the users table
            dsl.truncate(FlowchartProblems.FLOWCHART_PROBLEMS_TABLE).restartIdentity().execute();
            System.out.println("Successful, all rows deleted and identity reset");
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred when resetting the users table.");
            return false;
        }
    }

    public static boolean deleteCodeProblems(){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        try {
            // Query the users table
            dsl.truncate(CodeProblems.CODE_PROBLEMS_TABLE).restartIdentity().execute();
            System.out.println("Successful, all rows deleted and identity reset");
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred when resetting the users table.");
            return false;
        }
    }

    public static boolean deleteUserByID(long idNum){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);

        try {
            // Query the users table
            dsl.deleteFrom(Users.USERS_TABLE).where(id.eq(idNum)).execute();
            System.out.println("Successful, user " + idNum + " deleted");
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred when deleting user.");
            return false;
        }
    }

    public static boolean deleteFlowchartProblemByID(long idNum){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);

        try {
            // Query the users table
            dsl.deleteFrom(FlowchartProblems.FLOWCHART_PROBLEMS_TABLE).where(id.eq(idNum)).execute();
            System.out.println("Successful, flowchart problem " + idNum + " deleted");
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred when deleting flowchart problem.");
            return false;
        }
    }

    public static boolean deleteCodeProblemByID(long idNum){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);

        try {
            // Query the users table
            dsl.deleteFrom(CodeProblems.CODE_PROBLEMS_TABLE).where(id.eq(idNum)).execute();
            System.out.println("Successful, code problem " + idNum + " deleted");
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred when deleting code problem.");
            return false;
        }
    }

    // Inserts a flowchart problem with the given info, RETURNS ITS ID
    public static long insertFlowchartProblem(String probText, String ansText, String hintText, String codeText){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        // Define the table fields explicitly
        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> problem = DSL.field("problem", SQLDataType.VARCHAR);
        Field<String> answer = DSL.field("answer", SQLDataType.VARCHAR);
        Field<String> hint = DSL.field("hint", SQLDataType.VARCHAR);
        Field<String> code = DSL.field("code", SQLDataType.VARCHAR);

        // Insert a record into the CodeProblems table
        try {
            return dsl.insertInto(FlowchartProblems.FLOWCHART_PROBLEMS_TABLE, problem, answer, hint, code)
                    .values(probText, ansText, hintText, codeText)
                    .returningResult(id)
                    .fetch().get(0).value1();
        } catch (Exception e) {
            System.out.println("An error occurred when inserting flowchart problem.");
            return -1;
        }
    }

    // Inserts a code problem with the given info, RETURNS ITS ID
    public static long insertCodeProblem(String probText, String ansText, String hintText, String flowText,
                                         int locCount, int elocCount, int llocCount, int ccCount){
        // Obtain the jOOQ DSLContext using your configured DB class
        DSLContext dsl = DSL.using(DB.configure());

        // Define the table fields explicitly
        Field<Long> id = DSL.field("id", SQLDataType.BIGINT);
        Field<String> problem = DSL.field("problem", SQLDataType.VARCHAR);
        Field<String> answer = DSL.field("answer", SQLDataType.VARCHAR);
        Field<String> hint = DSL.field("hint", SQLDataType.VARCHAR);
        Field<String> flowchart = DSL.field("flowchart", SQLDataType.VARCHAR);
        Field<Integer> loc = DSL.field("loc", SQLDataType.INTEGER);
        Field<Integer> eloc = DSL.field("eloc", SQLDataType.INTEGER);
        Field<Integer> lloc = DSL.field("lloc", SQLDataType.INTEGER);
        Field<Integer> cc = DSL.field("cc", SQLDataType.INTEGER);

        // Insert a record into the CodeProblems table
        try {
            return dsl.insertInto(CodeProblems.CODE_PROBLEMS_TABLE, problem, answer, hint, flowchart, loc, eloc, lloc, cc)
                    .values(probText, ansText, hintText, flowText, locCount, elocCount, llocCount, ccCount)
                    .returningResult(id)
                    .fetch().get(0).value1();
        } catch (Exception e) {
            System.out.println("An error occurred when inserting code problem.");
            return -1;
        }
    }

    public static boolean updateUserFlowchartProgress(String username, int progress) {

        DSLContext dsl = DSL.using(DB.configure());

        Field<Integer> flProb = DSL.field("current_problem_flowchart", SQLDataType.INTEGER);
        Field<String> usrname = DSL.field("username", SQLDataType.VARCHAR);
        try {
            dsl.update(Users.USERS_TABLE)
                    .set(flProb, progress)
                    .where(usrname.eq(username))
                    .execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getUserFlowchartProgress(String username) {

        DSLContext dsl = DSL.using(DB.configure());

        Field<Integer> current_problem_flowchart = DSL.field("current_problem_flowchart", SQLDataType.INTEGER);
        Field<String> usrname = DSL.field("username", SQLDataType.VARCHAR);
        try {
            Result<Record1<Integer>> result =
                    dsl.select(current_problem_flowchart)
                    .from(Users.USERS_TABLE)
                    .where(usrname.eq(username))
                    .fetch();
            return result.get(0).get(Users.USERS_TABLE.field("current_problem_flowchart", Integer.class));
        } catch (Exception e) {
            return -1;
        }
    }

}

