package Algorithm.column;
import Algorithm.Minterm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Column {
    private int numOfBits;
    private List<List<Minterm>> table;
    private List<Minterm> tempPrimeImplicants;

    public Column(int numOfBits) {
        this.numOfBits = numOfBits;
        this.table = new ArrayList<>();
        this.tempPrimeImplicants = new ArrayList<>();
    }

    public Column(int numOfBits, List<List<Minterm>> table) {
        this.numOfBits = numOfBits;
        this.table = table;
        this.tempPrimeImplicants = new ArrayList<>();
    }

    public boolean isEmpty(){
        // Check if the outer list (table) is empty
        if (table.isEmpty()) return true;
        
        // Check if all inner lists are empty
        for (List<?> innerList : table) {
            if (!innerList.isEmpty()) {
                return false; // At least one inner list is not empty, so the table is not completely empty
            }
        }
        
        // If reached here, all inner lists are empty
        return true;
    }

    public List<List<Minterm>> getTable() {
        return table;
    }

    public int getTableYSize(){
        if (table.isEmpty()) return 0;
        return table.get(0).size();
    }

    public List<Minterm> getTempPrimeImplicants() {
        return tempPrimeImplicants;
    }

    public void addMinterm(Minterm minterm, int numOfColumns) {
        int index = minterm.getNumOnes();

        // Ensure each column has the correct number of rows
        int expectedRows = numOfBits - numOfColumns + 1 ;
        while (table.size() < expectedRows) {
            table.add(new ArrayList<>());
        }
        
        // Add the minterm to the correct row in the column
        table.get(index).add(minterm);
    }

    public List<List<Minterm>> combineMinterms() {
        List<List<Minterm>> newTable = new ArrayList<>();
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
                            binaryRepresentations.add(binaryRepresentation);
                        }
                    }
                }
            }
            //if (!newRow.isEmpty()) {
                newTable.add(newRow);
            //}
        }

        // Add the implicants that could not be merged
        for (Minterm implicant : currentImplicants) {
            if (!mergableImplicants.contains(implicant)) {
                tempPrimeImplicants.add(implicant);
            }
        }
        
        return newTable;
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

    public void printMintermTable(List<Minterm> primeImplicants) {
        for (int i = 0; i < table.size(); i++) {
            List<Minterm> row = table.get(i);
            System.out.println("Row " + i + ": ");
            for (Minterm minterm : row) {

                // Retrieve values from the minterm
                Set<Integer> values = minterm.getValues();

                // Print each value in the set
                System.out.print("\tValue(s): ");
                for (Integer value : values) {
                    System.out.print(value + ", ");
                }
                System.out.print("Binary: " + minterm.getBinaryRepresentation());

                // Checkmark part
                if (!primeImplicants.contains(minterm)) {

                    // Modify as needed
                    System.out.print("\t Checked");
                }
                System.out.println("");
            }
        }
        System.out.println("");
    }
}
