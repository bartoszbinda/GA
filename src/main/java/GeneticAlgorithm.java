import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class GeneticAlgorithm {
    private ArrayList<String[]> populationArray = new ArrayList<>();
    void initializePopulation(int numNodes, int amountOfPopulations){
        int count = 0;
        while(count != amountOfPopulations) {
            String[] population = new String[numNodes * numNodes];
            for (int i = 0; i < numNodes * numNodes; i++) {
                int nodeID = ThreadLocalRandom.current().nextInt(1, numNodes + 1);
                population[i] = Integer.toBinaryString(nodeID);
            }
            populationArray.add(population);
            count++;
        }
    }
    void printPopulationArray() {
        for(int i = 0; i < populationArray.size(); i++){
            String[] arr = populationArray.get(i);
            System.out.print(arr[0]);
            for(int j = 1; j < arr.length; j++){
                System.out.print(" : " + arr[j]);
            }
            System.out.println();
        }
    }
}
