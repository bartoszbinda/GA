import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {


    private int numberOfIteration;
    private ArrayList<String[]> populationArray = new ArrayList<>();
    private AdjacencyMatrixGraph graph;
    private int endNode;
    private int firstNode;

    GeneticAlgorithm(AdjacencyMatrixGraph graph, int endNode, int firstNode) {
        this.numberOfIteration = 0;
        this.graph = graph;
        this.endNode = endNode;
        this.firstNode = firstNode;

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

    int[] fitnessFunction(ArrayList<String[]> popArray) {
        int[] res = new int[popArray.size()];
        System.out.println("Result table:");
        int counter = 0;
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (String[] binaryString : popArray) {
            int[] intArray = convertToIntArray(binaryString);
            int fitness = 0;
            for (int j = 0; j < intArray.length - 1; j++) {
                int weight = graph.getWeightedEdge(intArray[j], intArray[j + 1]);
                if (intArray[0] != firstNode) {
                    fitness += 2000;
                }
                if (intArray[j] == endNode) {
                    break;
                } else if (weight > 0) {
                    fitness += weight;
                } else {
                    fitness += 1000;
                }
            }
            res[counter] = fitness;
            if (fitness <= min) {
                min = fitness;
                minIndex = counter;
            }

            counter++;
        }
        for (int i : res) System.out.print(i + "\n");
        System.out.println("Minimum fitness: " + min);
        System.out.println("Minimum index: " + minIndex);

        return res;

    }

    void printPopulationArray() {
        System.out.println("Number of iteration: " + this.numberOfIteration);
        int counter = 0;
        for (String[] arr : populationArray) {
            System.out.println("Chromosome no : " + counter);
            System.out.print(arr[0]);
            for (int j = 1; j < arr.length; j++) {
                System.out.print(" : " + arr[j]);
            }
            counter++;
            System.out.println();
        }
    }


}
