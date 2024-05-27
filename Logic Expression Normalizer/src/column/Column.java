package column;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Column {

    /*
    To do list: 
        - solving empty consecutive rows problem
        - return number of columns

    */
    private List<List<Minterm>> table;
    private List<Minterm> primeImplicants;

    public Column() {
        this.table = new ArrayList<>();
        this.primeImplicants = new ArrayList<>();
    }

    public void addMinterm(Minterm minterm) {
        int index = minterm.getNumOnes();
        while (table.size() <= index) {
            table.add(new ArrayList<>());
        }
        table.get(index).add(minterm);
    }

    public boolean combineMinterms() {
        List<List<Minterm>> newTable = new ArrayList<>();
        boolean altered = false;
        Set<Minterm> currentImplicants = new HashSet<>(); // Track implicants being considered in this iteration
        Set<Minterm> mergableImplicants = new HashSet<>(); // Track implicants that can be merged
        
        //initialize current Implicants
        for (int a = 0; a < table.size(); a++){
            List<Minterm> aRow = table.get(a);
            for (int b = 0; b < aRow.size(); b++){
                Minterm cur = aRow.get(b);
                currentImplicants.add(cur);
            }
        }
        
        for (int i = 0; i < table.size() - 1; i++) {
            List<Minterm> currentRow = table.get(i);
            List<Minterm> nextRow = table.get(i + 1);
            List<Minterm> newRow = new ArrayList<>();
            Set<String> binaryRepresentations = new HashSet<>(); // store unique binary representations
            //iterate through each pair
            for (int j = 0; j < currentRow.size(); j++) {
                Minterm currentMinterm = currentRow.get(j);
                for (int k = 0; k < nextRow.size(); k++) {
                    Minterm nextMinterm = nextRow.get(k);
                    Minterm combined = combineMinterms(currentMinterm, nextMinterm);

                    if (combined != null) {
                        String binaryRepresentation = combined.getBinaryRepresentation();
                        mergableImplicants.add(currentMinterm);
                        mergableImplicants.add(nextMinterm);

                        //check if the combined minterm already exists or not
                        if (!binaryRepresentations.contains(binaryRepresentation)) {
                            newRow.add(combined);
                            altered = true;
                            binaryRepresentations.add(binaryRepresentation);
                        }
                    }
                }
            }
            if (!newRow.isEmpty()) {
                newTable.add(newRow);
            }
        }
        
        // Add the implicants that could not be merged
        for (Minterm implicant : currentImplicants) {
            if (!mergableImplicants.contains(implicant)) {
                primeImplicants.add(implicant);
            }
        }
        
        table = newTable;
        return altered;
    }

    private Minterm combineMinterms(Minterm minterm1, Minterm minterm2) {
        String binary1 = minterm1.getBinaryRepresentation();
        String binary2 = minterm2.getBinaryRepresentation();

        // Check if only one bit differs between the two minterms
        int differingBitIndex = -1;
        for (int i = 0; i < binary1.length(); i++) {
            if (binary1.charAt(i) != binary2.charAt(i)) {
                if (differingBitIndex != -1) return null; // More than one bit differs, cannot combine
                differingBitIndex = i;
            }
        }

        // Combine minterms if only one bit differs
        StringBuilder combinedBinary = new StringBuilder(binary1);
        combinedBinary.setCharAt(differingBitIndex, '-'); // Represent don't care as '-'

        Set<Integer> combinedValues = new HashSet<>(minterm1.getValues());
        combinedValues.addAll(minterm2.getValues());

        return new Minterm(combinedValues, combinedBinary.toString());
    }

    public static class Minterm {
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
    }

    private static void printMintermTable(Column mintermTable) {
        for (int i = 0; i < mintermTable.table.size(); i++) {
            List<Minterm> row = mintermTable.table.get(i);
            System.out.println("Row " + i + ": ");
            for (Minterm minterm : row) {
                // Retrieve values from the minterm
                Set<Integer> values = minterm.getValues();
                // Print each value in the set
                System.out.print("\tValue(s): ");
                for (Integer value : values) {
                    System.out.print(value + ", ");
                }
                System.out.println("Binary: " + minterm.getBinaryRepresentation());
            }
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        Column mintermTable = new Column();
        int numOfBits = 4;

        Set<Integer> numbers = new HashSet<>();
        for (int i : new int[]{0, 2, 4, 5, 6, 8, 10, 13, 14}) {
            numbers.add(i);
        }

        for (int number : numbers) {
            mintermTable.addMinterm(new Minterm(number, numOfBits));
        }

        boolean altered;
        do {
            printMintermTable(mintermTable);
            altered = mintermTable.combineMinterms();
        } while (altered);

        // Print prime implicants
        System.out.println("Prime Implicants:");
        for (Column.Minterm primeImplicant : mintermTable.primeImplicants) {
            System.out.println("Binary: " + primeImplicant.getBooleanRepresentation()
                + " " + primeImplicant.getBinaryRepresentation());
        }
    }
}