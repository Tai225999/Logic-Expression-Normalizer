package Algorithm.pitable;

import Algorithm.Minterm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PITable {
    private List<Minterm> primeImplicants;
    private Set<Integer> minterms;
    private boolean[][] table;
    private Set<Integer> finalImplicantsIndices;

    public PITable(List<Minterm> primeImplicants, Set<Integer> minterms) {
        this.primeImplicants = primeImplicants;
        this.minterms = minterms;
        this.table = new boolean[primeImplicants.size()][minterms.size()];
        this.finalImplicantsIndices = this.findFinalPrimeImplicantsIndex();
    }

    public List<Minterm> findFinalPrimeImplicants() {
        List<Minterm> finalImplicants = new ArrayList<>();
        for (Integer index : finalImplicantsIndices) {
            finalImplicants.add(primeImplicants.get(index));
        }
        return finalImplicants;
    }

    // 2-step process finding prime implicants
    public Set<Integer> findFinalPrimeImplicantsIndex() {
        Set<Integer> essentialPrimeImplicants = findEssentialPrimeImplicants();
        Set<Integer> additionalPrimeImplicants = findAdditionalPrimeImplicants(essentialPrimeImplicants);
        Set<Integer> minimumPrimeImplicants = new HashSet<>(essentialPrimeImplicants);
        minimumPrimeImplicants.addAll(additionalPrimeImplicants);
        return minimumPrimeImplicants;
    }

    // Find essential pis
    public Set<Integer> findEssentialPrimeImplicants() {
        Set<Integer> essentialPrimeImplicants = new HashSet<>();

        fillTable();

        for (int j = 0; j < minterms.size(); j++) {
            int count = 0;
            int position = -1;
            for (int i = 0; i < primeImplicants.size(); i++) {
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
        
        // Try to print essential prime implicants
        if (essentialPrimeImplicants.isEmpty()) {
            System.out.println("Currently no essential prime implicants found.");
        } else {
            System.out.println("Essential Prime Implicants:");
            for (Integer index : essentialPrimeImplicants) {
                System.out.println(primeImplicants.get(index).getBinaryRepresentation());
            }
        }

        return essentialPrimeImplicants;
    }

    private void fillTable() {
        for (int i = 0; i < primeImplicants.size(); i++) {
            for (int j = 0; j < minterms.size(); j++) {
                Set<Integer> implicant = primeImplicants.get(i).getValues();
                int minterm = (int) minterms.toArray()[j];
                if (implicant.contains(minterm)) {
                    table[i][j] = true;
                }
            }
        }
    }

    // Find non-essential pis
    private Set<Integer> findAdditionalPrimeImplicants(Set<Integer> essentialPrimeImplicants) {
        Set<Integer> additionalPrimeImplicants = new HashSet<>();

        // Identify the minterms not covered by essential prime implicants
        Set<Integer> uncoveredMinterms = new HashSet<>(minterms);
        for (Integer implicantIndex : essentialPrimeImplicants) {
            Set<Integer> implicant = primeImplicants.get(implicantIndex).getValues();
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

                Set<Integer> implicant = primeImplicants.get(i).getValues();
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
                Set<Integer> implicant = primeImplicants.get(selectedImplicant).getValues();
                uncoveredMinterms.removeAll(implicant);
            } else {
                // If no implicant can cover more minterms, exit the loop
                break;
            }
        }

        return additionalPrimeImplicants;
    }
}