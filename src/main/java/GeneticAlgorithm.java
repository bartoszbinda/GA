import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


public class GeneticAlgorithm {
    Set<String> vertexSet;
    private Set<DefaultWeightedEdge> edgeSet;
    private int numberOfIteration;
    private ArrayList<String[]> populationArray = new ArrayList<>();
    ConnectivityInspector connect;
    SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph;

    GeneticAlgorithm(Set<String> vertexSet, Set<DefaultWeightedEdge> edgeSet,     SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
        this.numberOfIteration = 0;
        this.edgeSet = edgeSet;
        this.vertexSet = vertexSet;
        this.graph = graph;
        this.connect = new ConnectivityInspector(graph);
    }

    ArrayList<String[]> getPopulationArray() {
        return populationArray;
    }

    public int getNumberOfIteration() {
        return numberOfIteration;
    }

    void initializePopulation(int numNodes, int amountOfPopulations) {
        int count = 0;
        while (count != amountOfPopulations) {
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

    private int[] convertToIntArray(String[] stringArray) {
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i], 2);
        }
        return intArray;
    }

    int[] fitnessFunction(ArrayList<String[]> popArray, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
        int[] res = new int[popArray.size()];
        for (String[] binaryString : popArray) {
            int[] intArray = convertToIntArray(binaryString);
            for (int j = 0; j < intArray.length - 1; j++) {
                if(connect.pathExists(Integer.toString(intArray[j]), Integer.toString(intArray[j+1]))) {
                    System.out.println("Path exists");
                }
                else {
                    System.out.println("Path not exists");
                }

            }
        }
        return res;
    }

    void printPopulationArray() {
        System.out.println("Number of iteration: " + this.numberOfIteration);
        for (String[] arr : populationArray) {
            System.out.print(arr[0]);
            for (int j = 1; j < arr.length; j++) {
                System.out.print(" : " + arr[j]);
            }
            System.out.println();
        }
    }


}
