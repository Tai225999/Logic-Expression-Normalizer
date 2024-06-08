package algorithm.column;

import algorithm.Minterm;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Column {
    private int numOfBits;
    private List<List<Minterm>> table;
    private List<Minterm> tempPrimeImplicants;

    public Column(int numOfBits) {
        this.numOfBits = numOfBits;
        this.table = new ArrayList();
        this.tempPrimeImplicants = new ArrayList();
    }

    public Column(int numOfBits, List<List<Minterm>> table) {
        this.numOfBits = numOfBits;
        this.table = table;
        this.tempPrimeImplicants = new ArrayList();
    }

    public int getMax() {
        int max = 0;
        Iterator var2 = this.table.iterator();

        while(var2.hasNext()) {
            List<Minterm> l = (List)var2.next();
            if (l.size() > max) {
                max = l.size();
            }
        }

        return max;
    }

    public boolean isEmpty() {
        if (this.table.isEmpty()) {
            return true;
        } else {
            Iterator var1 = this.table.iterator();

            List innerList;
            do {
                if (!var1.hasNext()) {
                    return true;
                }

                innerList = (List)var1.next();
            } while(innerList.isEmpty());

            return false;
        }
    }

    public List<List<Minterm>> getTable() {
        return this.table;
    }

    public int getTableYSize() {
        return this.table.isEmpty() ? 0 : ((List)this.table.get(0)).size();
    }

    public List<Minterm> getTempPrimeImplicants() {
        return this.tempPrimeImplicants;
    }

    public void addMinterm(Minterm minterm, int numOfColumns) {
        int index = minterm.getNumOnes();
        int expectedRows = this.numOfBits - numOfColumns + 1;

        while(this.table.size() < expectedRows) {
            this.table.add(new ArrayList());
        }

        ((List)this.table.get(index)).add(minterm);
    }

    public List<List<Minterm>> combineMinterms() {
        List<List<Minterm>> newTable = new ArrayList();
        Set<Minterm> currentImplicants = new HashSet();
        Set<Minterm> mergableImplicants = new HashSet();

        int i;
        List currentRow;
        for(i = 0; i < this.table.size(); ++i) {
            currentRow = (List)this.table.get(i);

            for(int b = 0; b < currentRow.size(); ++b) {
                Minterm cur = (Minterm)currentRow.get(b);
                currentImplicants.add(cur);
            }
        }

        for(i = 0; i < this.table.size() - 1; ++i) {
            currentRow = (List)this.table.get(i);
            List<Minterm> nextRow = (List)this.table.get(i + 1);
            List<Minterm> newRow = new ArrayList();
            Set<String> binaryRepresentations = new HashSet();

            for(int j = 0; j < currentRow.size(); ++j) {
                Minterm currentMinterm = (Minterm)currentRow.get(j);

                for(int k = 0; k < nextRow.size(); ++k) {
                    Minterm nextMinterm = (Minterm)nextRow.get(k);
                    Minterm combined = this.combineMinterms(currentMinterm, nextMinterm);
                    if (combined != null) {
                        String binaryRepresentation = combined.getBinaryRepresentation();
                        mergableImplicants.add(currentMinterm);
                        mergableImplicants.add(nextMinterm);
                        if (!binaryRepresentations.contains(binaryRepresentation)) {
                            newRow.add(combined);
                            binaryRepresentations.add(binaryRepresentation);
                        }
                    }
                }
            }

            newTable.add(newRow);
        }

        Iterator var15 = currentImplicants.iterator();

        while(var15.hasNext()) {
            Minterm implicant = (Minterm)var15.next();
            if (!mergableImplicants.contains(implicant)) {
                this.tempPrimeImplicants.add(implicant);
            }
        }

        return newTable;
    }

    private Minterm combineMinterms(Minterm minterm1, Minterm minterm2) {
        String binary1 = minterm1.getBinaryRepresentation();
        String binary2 = minterm2.getBinaryRepresentation();
        int differingBitIndex = -1;

        for(int i = 0; i < binary1.length(); ++i) {
            if (binary1.charAt(i) != binary2.charAt(i)) {
                if (differingBitIndex != -1) {
                    return null;
                }

                differingBitIndex = i;
            }
        }

        StringBuilder combinedBinary = new StringBuilder(binary1);
        combinedBinary.setCharAt(differingBitIndex, '-');
        Set<Integer> combinedValues = new HashSet(minterm1.getValues());
        combinedValues.addAll(minterm2.getValues());
        return new Minterm(combinedValues, combinedBinary.toString());
    }

    public void printMintermTable(List<Minterm> primeImplicants) {
        for(int i = 0; i < this.table.size(); ++i) {
            List<Minterm> row = (List)this.table.get(i);
            System.out.println("Row " + i + ": ");

            for(Iterator var4 = row.iterator(); var4.hasNext(); System.out.println("")) {
                Minterm minterm = (Minterm)var4.next();
                Set<Integer> values = minterm.getValues();
                System.out.print("\tValue(s): ");
                Iterator var7 = values.iterator();

                while(var7.hasNext()) {
                    Integer value = (Integer)var7.next();
                    System.out.print("" + value + ", ");
                }

                System.out.print("Binary: " + minterm.getBinaryRepresentation());
                if (!primeImplicants.contains(minterm)) {
                    System.out.print("\t Checked");
                }
            }
        }

        System.out.println("");
    }
}
