package algorithm.column;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import algorithm.Minterm;

public class ColumnTable {
    private int numOfBits;
    private List<Column> columnList;
    private List<Minterm> primeImplicants;
    
    public ColumnTable(int numOfBits, int[] minterms){
        this.numOfBits = numOfBits;
        this.columnList = new ArrayList<Column>();

        //initiate first column
        Column firstColumn = new Column(numOfBits);
        for (int number: minterms) {
            firstColumn.addMinterm(new Minterm(number, numOfBits), columnList.size());
        }

        this.primeImplicants = new ArrayList<Minterm>();
        columnList.add(firstColumn);

        this.merging();
    }
    
    public int getNumOfColumns(){
        return columnList.size();
    }

    public int getMaxLines(){
        int maxSum = 0, sum = 0;
        for(int i = 0; i < columnList.size(); i++){
            sum = 0;
            for(int j = 0; j < columnList.get(i).getTable().size(); j++){
                sum += columnList.get(i).getTable().get(j).size();
            }
            if(sum >= maxSum) maxSum = sum;
        }
        return maxSum;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public List<Minterm> getPrimeImplicants() {
        return primeImplicants;
    }

    public void merging(){
        while (true) {
            Column currColumn = columnList.get(columnList.size() - 1);
            Column newColumn = new Column(numOfBits, currColumn.combineMinterms());

            List<Minterm> newColumnTempPrimeImplicants = currColumn.getTempPrimeImplicants();
            currColumn.printMintermTable(newColumnTempPrimeImplicants);

            if (newColumnTempPrimeImplicants != null) {
                primeImplicants.addAll(newColumnTempPrimeImplicants);
            }
            if (newColumn.isEmpty()) break;

            columnList.add(newColumn);

        }
        sortMinterms(primeImplicants);
    }

    public void printPrimeImplicants() {
        for (Minterm pi : primeImplicants) {
            System.out.println(pi.getBooleanRepresentation());
        }
    }

    // Sort the prime implicants
    public static void sortMinterms(List<Minterm> list) {
        Collections.sort(list, new Comparator<Minterm>() {
            @Override
            public int compare(Minterm obj1, Minterm obj2) {
                Set<Integer> set1 = obj1.getValues();
                Set<Integer> set2 = obj2.getValues();

                Iterator<Integer> iterator1 = set1.iterator();
                Iterator<Integer> iterator2 = set2.iterator();

                while (iterator1.hasNext() && iterator2.hasNext()) {
                    int elem1 = iterator1.next();
                    int elem2 = iterator2.next();
                    if (elem1 != elem2) {
                        return Integer.compare(elem1, elem2);
                    }
                }
                // If one set is a subset of the other, the shorter set comes first
                return Integer.compare(set1.size(), set2.size());
            }
        });
    }

    public List<Minterm> getPrimeImplicantsFromEachColumn() {
        System.out.println("Start");
        List<Minterm> primeImplicants = new ArrayList<Minterm>();
        for (Column column : columnList) {
            for(List<Minterm> mintermList : column.getTable()) {
                for (Minterm minterm : mintermList) {
                    if(column.getTempPrimeImplicants().contains(minterm))
                        primeImplicants.add(minterm);
                }
            }
        }
        System.out.println("End");

        return primeImplicants;
    }
}