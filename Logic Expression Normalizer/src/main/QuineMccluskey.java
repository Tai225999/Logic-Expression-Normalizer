package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import algorithm.Minterm;
import algorithm.column.ColumnTable;
import algorithm.pitable.PITable;

public class QuineMccluskey {
    private int numOfBits;
    private int[] values;
    private boolean SOPexpression;
    private List<Minterm> finalPIs;

    public QuineMccluskey(int numOfBits, int[] values, boolean SOPexpression) {
        this.numOfBits = numOfBits;
        this.SOPexpression = SOPexpression;
        if (SOPexpression) {
            this.values = values;
        } else {
            this.values = findComplements(values, numOfBits);
        }
        this.finalPIs = new ArrayList<Minterm>();

    }

    public void performingAlgorithm() {
        ColumnTable columnTable = new ColumnTable(numOfBits, values);
        columnTable.merging();

        columnTable.printPrimeImplicants();
        Set<Integer> setValues = Set.of(toIntegers(values));
        PITable piTable = new PITable(columnTable.getPrimeImplicants(), setValues);
        finalPIs = piTable.findFinalPrimeImplicants();
    }

    public void printExpression() {
        System.out.println("Final implicants: ");
        if (SOPexpression) {
            System.out.println("Y = " + toExpression(finalPIs));
        } else {
            System.out.println("Y' = " + toExpression(finalPIs));
            System.out.println("Y = " + convertToComplementForm(toExpression(finalPIs)));
        }
    }
    public static void main(String[] args) {

        int[] values = new int[]{0, 2, 4, 5, 6, 8, 10, 13, 14};
        // int[] values = new int[]{0, 1, 2, 5, 6, 7};
        // int[] values = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // int[] values = new int[]{};
        // int[] values = new int[]{0};

        QuineMccluskey session = new QuineMccluskey(4, values, true);
        session.performingAlgorithm();
        session.printExpression();
    }

    public static Integer[] toIntegers (int[] values) {
        Integer[] intValues = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            intValues[i] = values[i]; // Autoboxing
        }
        return intValues;
    }

    public static String toExpression (List<Minterm> finalPIs) {
        if (finalPIs.isEmpty()) return "0";

        StringBuilder sb = new StringBuilder("");
        Iterator<Minterm> iterator = finalPIs.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().getBooleanRepresentation());
            if (iterator.hasNext()) {
                sb.append(" + ");
            }
        }
        return sb.toString();
    }

    public static String convertToComplementForm(String booleanFunction) {
        // Split the boolean function into individual terms
        String[] terms = booleanFunction.split("\\s*\\+\\s*");

        // List to store complemented terms
        List<String> dualTerms = new ArrayList<>();

        // Iterate over each term
        for (String term : terms) {
            //System.out.println(term);
            StringBuilder compTerm = new StringBuilder("(");

            // Iterate over each character in the term
            for (int i = 0; i < term.length() - 1; i++) {
                char currentChar = term.charAt(i);
                char nextChar = term.charAt(i+1);
                if (Character.isLetter(currentChar)) {
                    compTerm.append(currentChar);
                    if (nextChar == '\'') {
                        continue;
                    } else {
                        compTerm.append('\'').append("+");
                    }
                } else {
                    compTerm.append("+");
                }
            }

            //adding final terms
            char c = term.charAt(term.length() - 1);
            compTerm.append(c);
            if (Character.isLetter(c)) {
                compTerm.append('\'');
            }
            compTerm.append(")");

            // Add the complemented term to the list
            dualTerms.add(compTerm.toString());
        }

        // Combine complemented terms using logical AND
        StringBuilder complementForm = new StringBuilder();
        for (String term : dualTerms) {
            complementForm.append(term);
        }

        return complementForm.toString();
    }

    public static int[] findComplements(int[] nums, int numOfBits) {
        Set<Integer> set = new HashSet<>();
        
        // Add given numbers to set
        for (int num : nums) {
            set.add(num);
        }
        
        int maxNum = (1 << numOfBits) - 1; // Calculate the maximum number with given number of bits
        int[] complements = new int[maxNum + 1 - set.size()];
        int index = 0;
        
        // Iterate from 0 to maxNum and check if each number is present in the set,
        // if not, add it to complements array
        for (int i = 0; i <= maxNum; i++) {
            if (!set.contains(i)) {
                complements[index++] = i;
            }
        }
        
        return complements;
    }
}
