import java.util.*;

public class prac2 {
    static Map<String, Integer> SYMTAB = new HashMap<>();
    static Map<String, Integer> LITTAB = new HashMap<>();

    public static void main(String[] args) {
        initializeTables();

        String[] intermediateCode = {
            "(AD,01)(C,200)", "(IS,04)(1)(L,1)", "(IS,05)(1)(S,1)",
            "(IS,04)(1)(S,1)", "(IS,04)(3)(S,3)", "(IS,01)(3)(L,2)",
            "(DL,01)(C,5)", "(DL,01)(C,1)", "(IS,02)(1)(L,3)",
            "(IS,07)(1)(S,5)", "(IS,00)", "(IS,03)(3)(S,3)",
            "(DL,02)(C,1)", "(DL,02)(C,1)", "(AD,02)"
        };

        generateMachineCode(intermediateCode);

        System.out.println("\n---- SYMBOL TABLE ----");
        for (var e : SYMTAB.entrySet())
            System.out.println(e.getKey() + "\t" + e.getValue());

        System.out.println("\n---- LITERAL TABLE ----");
        for (var e : LITTAB.entrySet())
            System.out.println("=" + e.getKey() + "\t" + e.getValue());
    }

    static void initializeTables() {
        SYMTAB.put("A", 211);
        SYMTAB.put("LOOP", 202);
        SYMTAB.put("B", 212);
        SYMTAB.put("NEXT", 208);
        SYMTAB.put("BACK", 202);
        SYMTAB.put("LAST", 210);

        LITTAB.put("1", 207);
        LITTAB.put("2", 213);
        LITTAB.put("3", 214); // added missing literal
        LITTAB.put("5", 206);
    }

    static void generateMachineCode(String[] ic) {
        int lc = 0;
        String[] symbols = {"", "A", "LOOP", "B", "NEXT", "BACK", "LAST"};

        System.out.println("LC\tMachine Code");
        System.out.println("------------------------");

        for (String instruction : ic) {
            String clean = instruction.replace("(", "").replace(")", "");
            String[] parts = clean.split(",");

            String type = parts[0];
            String code = parts[1];

            switch (type) {
                case "AD":
                    if (code.equals("01")) {
                        // safely check before using parts[3]
                        if (parts.length > 3)
                            lc = Integer.parseInt(parts[3]);
                        System.out.println("START " + lc);
                    } else if (code.equals("02")) {
                        System.out.println("END");
                    }
                    break;

                case "IS":
                    if (parts.length >= 7) {
                        String reg = parts[2];
                        String refType = parts[4];
                        String refNum = parts[5];

                        Integer address = null;
                        if (refType.equals("L")) {
                            address = LITTAB.get(refNum);
                        } else {
                            int idx = Integer.parseInt(refNum);
                            if (idx < symbols.length)
                                address = SYMTAB.get(symbols[idx]);
                        }

                        if (address == null)
                            System.out.println(lc + "\t⚠️ Missing entry for " + refType + refNum);
                        else
                            System.out.println(lc + "\t" + code + " " + reg + " " + address);
                        lc++;

                    } else if (parts.length == 2) {
                        System.out.println(lc + "\t" + code);
                        lc++;
                    }
                    break;

                case "DL":
                    // Defensive check to prevent crash
                    if (parts.length > 3) {
                        String value = parts[3];
                        String output = code.equals("01") ? "DS " + value : "DC " + value;
                        System.out.println(lc + "\t" + output);
                    } else {
                        System.out.println(lc + "\t(DL," + code + ")");
                    }
                    lc++;
                    break;
            }
        }
    }
}
