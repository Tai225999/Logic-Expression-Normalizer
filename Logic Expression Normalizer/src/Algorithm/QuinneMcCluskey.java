package Algorithm;

import Algorithm.column.ColumnTable;
import Algorithm.pitable.PITable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class QuinneMcCluskey {
    private int numOfBits;
    private int[] values;
    private boolean SOPexpression;
    private List<Minterm> finalPIs;

    public QuinneMcCluskey(int numOfBits, int[] values, boolean SOPexpression) {
        this.numOfBits = numOfBits;
        this.SOPexpression = SOPexpression;
        if (SOPexpression) {
            this.values = values;
        } else {
            this.values = findComplements(values, numOfBits);
        }

        this.finalPIs = new ArrayList();
    }

    public ColumnTable performingAlgorithm() {
        ColumnTable columnTable = new ColumnTable(this.numOfBits, this.values);
        columnTable.merging();
        columnTable.printPrimeImplicants();
        Set<Integer> setValues = Set.of(toIntegers(this.values));
        PITable piTable = new PITable(columnTable.getPrimeImplicants(), setValues);
        this.finalPIs = piTable.findFinalPrimeImplicants();
        return columnTable;
    }

    public void printExpression() {
        System.out.println("Final implicants: ");
        if (this.SOPexpression) {
            System.out.println("Y = " + toExpression(this.finalPIs));
        } else {
            System.out.println("Y' = " + toExpression(this.finalPIs));
            System.out.println("Y = " + convertToComplementForm(toExpression(this.finalPIs)));
        }

    }

    public static void main(String[] args) {
        int[] values = new int[]{0, 2, 4, 5, 6, 8, 10, 13, 14};
        QuinneMcCluskey session = new QuinneMcCluskey(4, values, true);
        session.performingAlgorithm();
        session.printExpression();
    }

    public static Integer[] toIntegers(int[] values) {
        Integer[] intValues = new Integer[values.length];

        for(int i = 0; i < values.length; ++i) {
            intValues[i] = values[i];
        }

        return intValues;
    }

    public static String toExpression(List<Minterm> finalPIs) {
        if (finalPIs.isEmpty()) {
            return "0";
        } else {
            StringBuilder sb = new StringBuilder("");
            Iterator<Minterm> iterator = finalPIs.iterator();

            while(iterator.hasNext()) {
                sb.append(((Minterm)iterator.next()).getBooleanRepresentation());
                if (iterator.hasNext()) {
                    sb.append(" + ");
                }
            }

            return sb.toString();
        }
    }

    public static String convertToComplementForm(String booleanFunction) {
        String[] terms = booleanFunction.split("\\s*\\+\\s*");
        List<String> dualTerms = new ArrayList();
        String[] var3 = terms;
        int var4 = terms.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String term = var3[var5];
            StringBuilder compTerm = new StringBuilder("(");

            for(int i = 0; i < term.length() - 1; ++i) {
                char currentChar = term.charAt(i);
                char nextChar = term.charAt(i + 1);
                if (Character.isLetter(currentChar)) {
                    compTerm.append(currentChar);
                    if (nextChar != '\'') {
                        compTerm.append('\'').append("+");
                    }
                } else {
                    compTerm.append("+");
                }
            }

            char c = term.charAt(term.length() - 1);
            compTerm.append(c);
            if (Character.isLetter(c)) {
                compTerm.append('\'');
            }

            compTerm.append(")");
            dualTerms.add(compTerm.toString());
        }

        StringBuilder complementForm = new StringBuilder();
        Iterator var12 = dualTerms.iterator();

        while(var12.hasNext()) {
            String term = (String)var12.next();
            complementForm.append(term);
        }

        return complementForm.toString();
    }

    public int getNumOfBits() {
        return this.numOfBits;
    }

    public int[] getValues() {
        return this.values;
    }

    public boolean isSOPexpression() {
        return this.SOPexpression;
    }

    public List<Minterm> getFinalPIs() {
        return this.finalPIs;
    }

    public static int[] findComplements(int[] nums, int numOfBits) {
        Set<Integer> set = new HashSet();
        int[] var3 = nums;
        int var4 = nums.length;

        int index;
        int i;
        for(index = 0; index < var4; ++index) {
            i = var3[index];
            set.add(i);
        }

        int maxNum = (1 << numOfBits) - 1;
        int[] complements = new int[maxNum + 1 - set.size()];
        index = 0;

        for(i = 0; i <= maxNum; ++i) {
            if (!set.contains(i)) {
                complements[index++] = i;
            }
        }

        return complements;
    }
}
