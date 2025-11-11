import java.util.*;

public class prac1 {
    public static void main(String[] args) {
        // Sample input (assembly program)
        String[] code = {
            "- START 100",
            "L1 MOVER AREG =3",
            "- MOVEM BREG X",
            "- SUB AREG =1",
            "- LTORG",
            "- MOVEM AREG Y",
            "- BC any L1",
            "- ADD CREG,4",
            "X DC 6",
            "Y DS 2",
            "- END"
        };

        int lc = 0; // Location Counter
        Map<String, Integer> symtab = new LinkedHashMap<>();
        Map<String, Integer> littab = new LinkedHashMap<>();
        List<String> ic = new ArrayList<>();

        for (String line : code) {
            String[] parts = line.trim().split("\\s+", 3);
            String label = parts[0];
            String opcode = (parts.length > 1) ? parts[1] : "";
            String operand = (parts.length > 2) ? parts[2] : "";

            // store literals (like =3, =1)
            if (operand.contains("=")) {
                for (String w : operand.replace(",", " ").split(" ")) {
                    if (w.startsWith("=") && !littab.containsKey(w))
                        littab.put(w, null);
                }
            }

            // handle directives and instructions
            if (opcode.equals("START")) {
                lc = Integer.parseInt(operand);
                ic.add(lc + " (AD,01) (C," + operand + ")");
            } else if (opcode.equals("LTORG") || opcode.equals("END")) {
                for (String lit : littab.keySet()) {
                    if (littab.get(lit) == null) {
                        littab.put(lit, lc);
                        ic.add(lc + " " + lit);
                        lc++;
                    }
                }
                ic.add(lc + " (AD," + opcode + ")");
            } else if (opcode.equals("DC")) {
                symtab.put(label, lc);
                ic.add(lc + " (DL,01) (C," + operand + ")");
                lc++;
            } else if (opcode.equals("DS")) {
                symtab.put(label, lc);
                ic.add(lc + " (DL,02) (C," + operand + ")");
                lc += Integer.parseInt(operand);
            } else if (!opcode.equals("")) {
                if (!label.equals("-")) symtab.put(label, lc);
                ic.add(lc + " (IS," + opcode + ") " + operand);
                lc++;
            }
        }

        // output
        System.out.println("---- INTERMEDIATE CODE ----");
        ic.forEach(System.out::println);

        System.out.println("\n---- SYMBOL TABLE ----");
        symtab.forEach((k,v) -> System.out.println(k + "\t" + v));

        System.out.println("\n---- LITERAL TABLE ----");
        littab.forEach((k,v) -> System.out.println(k + "\t" + v));

        System.out.println("\n=== Code Execution Successful ===");
    }
}
