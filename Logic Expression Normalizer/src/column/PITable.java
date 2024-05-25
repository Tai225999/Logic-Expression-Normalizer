package column;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PITable {
    // private int numOfBits = 4;
    public static void main(String[] args) {
        Set<Integer> minterm = new HashSet<>(Set.of(0, 2, 4, 5, 6, 8, 10, 13, 14));
        // Set<Integer> minterm = new HashSet<>(Set.of(0, 1, 2, 5, 6, 7));
        List<Set<Integer>> primeImplicants = new ArrayList<>();
        
        /*
        primeImplicants.add(new HashSet<>(Set.of(0, 1)));
        primeImplicants.add(new HashSet<>(Set.of(0, 2)));
        primeImplicants.add(new HashSet<>(Set.of(1, 5)));
        primeImplicants.add(new HashSet<>(Set.of(2, 6)));
        primeImplicants.add(new HashSet<>(Set.of(5, 7)));
        primeImplicants.add(new HashSet<>(Set.of(6, 7)));
        */
        
        primeImplicants.add(new HashSet<>(Set.of(0, 2, 4, 6)));
        primeImplicants.add(new HashSet<>(Set.of(0, 2, 8, 10)));
        primeImplicants.add(new HashSet<>(Set.of(2, 6, 10, 14)));
        primeImplicants.add(new HashSet<>(Set.of(4, 5)));
        primeImplicants.add(new HashSet<>(Set.of(5, 13)));

        Set<Integer> essentialIndices = findEssentialPrimeImplicants(primeImplicants, minterm);

        List<Set<Integer>> essentialPrimeImplicants = new ArrayList<>();
        List<Set<Integer>> finalImplicants = new ArrayList<>();

        if (essentialIndices.isEmpty()) {
            System.out.println("Currently no essential prime implicants found.");
        } else {
            System.out.println("Essential Prime Implicants:");
            for (Integer index : essentialIndices) {
                essentialPrimeImplicants.add(primeImplicants.get(index));
            }
        }

        for (Set<Integer> essentialImplicant : essentialPrimeImplicants){
            for (int minElement : essentialImplicant){
                System.out.printf("%d ", minElement);
            }
            System.out.println("");
        }

        Set<Integer> finalIndices = findMinimumPrimeImplicants(primeImplicants, minterm, essentialIndices);

        // use the indices to print the prime implicants
        if (finalIndices.isEmpty()) {
            System.out.println("Cannot find prime implicants!!");
        } else {
            System.out.println("Final Implicants:");
            for (Integer index : finalIndices) {
                finalImplicants.add(primeImplicants.get(index));
            }
        }

        for (Set<Integer> finalImplicant : finalImplicants){
            for (int minElement : finalImplicant){
                System.out.printf("%d ", minElement);
            }
            System.out.println("");
        }
        
    }    

    private static Set<Integer> findEssentialPrimeImplicants(List<Set<Integer>> primeImplicants, Set<Integer> minterms) {
        Set<Integer> essentialPrimeImplicants = new HashSet<>();

        boolean[][] table = new boolean[primeImplicants.size()][minterms.size()];

        int i = 0;
        int j = 0;

        for (Set<Integer> implicant : primeImplicants) {
            j = 0;
            for (int minterm: minterms){
                if (implicant.contains(minterm)) {
                    table[i][j] = true;
                }
                j++;
                System.out.println(j);
            }
            i++;
        }

        // Print the table
        for (i = 0; i < primeImplicants.size(); i++){
            for (j = 0; j < minterms.size(); j++){
                System.out.printf("%d ", (table[i][j]) ? 1 : 0);
            }
            System.out.println("");
        }
        
        for (j = 0; j < minterms.size(); j++) {
            int count = 0;
            int position = -1;
            for (i = 0; i < primeImplicants.size(); i++) {
                if (table[i][j]) {
                    count++;
                    position = i;
                    if (count > 1) break;
                }
            }
            if (count == 1) {
                essentialPrimeImplicants.add(position); // Add index of essential implicant
            }
        }

        return essentialPrimeImplicants;
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

    private static Set<Integer> findAdditionalPrimeImplicants(List<Set<Integer>> primeImplicants, Set<Integer> minterms, Set<Integer> essentialPrimeImplicants) {
        Set<Integer> additionalPrimeImplicants = new HashSet<>();
    
        // Identify the minterms not covered by essential prime implicants
        Set<Integer> uncoveredMinterms = new HashSet<>(minterms);
        for (Integer implicantIndex : essentialPrimeImplicants) {
            Set<Integer> implicant = primeImplicants.get(implicantIndex);
            uncoveredMinterms.removeAll(implicant);
        }
    
        // Find additional prime implicants to cover the uncovered minterms
        while (!uncoveredMinterms.isEmpty()) {
            int maxCoveredMinterms = 0;
            Integer selectedImplicant = null;
    
            // Iterate through prime implicants to find the one covering the maximum number of uncovered minterms
            for (int i = 0; i < primeImplicants.size(); i++) {
                if (additionalPrimeImplicants.contains(i)) {
                    continue; // Skip implicants already selected
                }
    
                Set<Integer> implicant = primeImplicants.get(i);
                int coveredMinterms = 0;
                for (Integer minterm : uncoveredMinterms) {
                    if (implicant.contains(minterm)) {
                        coveredMinterms++;
                    }
                }
    
                if (coveredMinterms > maxCoveredMinterms) {
                    maxCoveredMinterms = coveredMinterms;
                    selectedImplicant = i;
                }
            }
    
            if (selectedImplicant != null) {
                additionalPrimeImplicants.add(selectedImplicant);
                Set<Integer> implicant = primeImplicants.get(selectedImplicant);
                uncoveredMinterms.removeAll(implicant);
            } else {
                // If no implicant can cover more minterms, exit the loop
                break;
            }
        }
    
        return additionalPrimeImplicants;
    }
    
    // Combine essential and additional prime implicants
    private static Set<Integer> findMinimumPrimeImplicants(List<Set<Integer>> primeImplicants, Set<Integer> minterms, Set<Integer> essentialPrimeImplicants) {
        Set<Integer> additionalPrimeImplicants = findAdditionalPrimeImplicants(primeImplicants, minterms, essentialPrimeImplicants);
        Set<Integer> minimumPrimeImplicants = new HashSet<>(essentialPrimeImplicants);
        minimumPrimeImplicants.addAll(additionalPrimeImplicants);
        return minimumPrimeImplicants;
    }
}
