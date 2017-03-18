import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class GeneticAlgorithm {
    private int numberOfIteration;
    private ArrayList<String[]> populationArray = new ArrayList<>();
    GeneticAlgorithm(){
         this.numberOfIteration=0;
    }
    ArrayList<String[]> getPopulationArray() {
        return populationArray;
    }
    public int getNumberOfIteration(){
        return numberOfIteration;
    }
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
        this.numberOfIteration++;
    }
    int[] convertToIntArray(String[] stringArray){
        int[] intArray = new int[stringArray.length];
        for(int i = 0; i < intArray.length; i++){
            intArray[i] = Integer.parseInt(stringArray[i],2);
        }
        return intArray;
    }
    int[] fitnessFunction(ArrayList<String[]> popArray, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph){
        int[] res = new int[popArray.size()];
        for(int i = 0; i < popArray.size(); i++)
        {
            String[] biniaryString = popArray.get(i);
            int[] intArray = convertToIntArray(biniaryString);
            for(int j = 0; j < intArray.length-1; j++){

            }
        }
        return res;
    }

    void printPopulationArray() {
        System.out.println("Number of iteration: " + this.numberOfIteration);
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
