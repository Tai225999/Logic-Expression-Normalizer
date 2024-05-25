package algorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Minterm {
    private Set<Integer> values;
    private String binaryRepresentation;
    private String booleanRepresentation;

    // Constructor for single-value minterm
    public Minterm(int value, int mintermLength) {
        this.values = new HashSet<>();
        this.values.add(value);
        String binary = Integer.toBinaryString(value);
        
        this.binaryRepresentation = String.format("%" + mintermLength + "s", binary).replace(' ', '0');
        this.booleanRepresentation = convertToLiteral(binaryRepresentation);
    }

    // Constructor for multi-value minterm
    public Minterm(Set<Integer> values, String binaryRepresentation) {
        this.values = values;
        this.binaryRepresentation = binaryRepresentation;
        this.booleanRepresentation = convertToLiteral(binaryRepresentation);
    }

    public Set<Integer> getValues() {
        return values;
    }

    public String getBinaryRepresentation() {
        return binaryRepresentation;
    }

    public String getBooleanRepresentation() {
        return booleanRepresentation;
    }

    public int getNumOnes() {
        int count = 0;
        for (int i = 0; i < binaryRepresentation.length(); i++) {
            if (binaryRepresentation.charAt(i) == '1') {
                count++;
            }
        }
        return count;
    }

    public String convertToLiteral(String binaryRepresentation) {
        char[] variables = {'A', 'B', 'C', 'D'}; // Assuming maximum 4 variables

        boolean allDontCare = true;
        for (int i = 0; i < binaryRepresentation.length(); i++) {
            if (binaryRepresentation.charAt(i) != '-') {
                allDontCare = false;
                break;
            }
        }
        
        if (allDontCare) return "1";
    
        StringBuilder booleanLiteral = new StringBuilder();
        for (int i = 0; i < binaryRepresentation.length(); i++) {
            if (binaryRepresentation.charAt(i) == '-') {
                continue;
            } else if (binaryRepresentation.charAt(i) == '0') {
                booleanLiteral.append(variables[i]).append("'");
            } else if (binaryRepresentation.charAt(i) == '1') {
                booleanLiteral.append(variables[i]);
            }
        }
        
        return booleanLiteral.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        Iterator<Integer> iterator = values.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
