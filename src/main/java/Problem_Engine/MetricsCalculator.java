package Problem_Engine;

public class MetricsCalculator {

    public static int[] calculateMetrics(String code){
        if (code != null){
            String[] lines = code.trim().split("\n");
            int loc =lines.length;
            int eLOC = 0;
            for (String line : lines) {
                if (!line.equals("{") && !line.equals("}") && !line.equals("{\r") && !line.equals("}\r") ) {
                    eLOC++;
                }
            }
            int lLOC = 0;
            for (String line : lines) {
                if (line.contains(";")) {
                    lLOC++;
                }
            }
            int cc = 1;
            for (String line : lines) {
                if (line.contains("if") || line.contains("while") || line.contains("for")) {
                    cc++;
                }
            }
            return new int[] {loc,eLOC,lLOC,cc};
        }else{
            return new int[] {0,0,0,0};
        }

    }

}
