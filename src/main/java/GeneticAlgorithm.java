import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {


    Random r = new Random();
    private int numberOfIteration;
    private ArrayList<String[]> populationArray = new ArrayList<>();
    private AdjacencyMatrixGraph graph;
    private int endNode;
    private int firstNode;
    private int numTournament;
    private double crossoverRate;
    private double mutationRate;

    GeneticAlgorithm(AdjacencyMatrixGraph graph, int endNode, int firstNode, int numTournament, double crossoverRate, double mutationRate) {
        this.numberOfIteration = 0;
        this.graph = graph;
        this.endNode = endNode;
        this.firstNode = firstNode;
        this.numTournament = numTournament;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
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


    public String[] tournament(ArrayList<String[]> population, int[] fitness) {
        ArrayList<String[]> tournamentPopulation = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < this.numTournament; i++) {
            int random = r.nextInt(population.size());
            tournamentPopulation.add(population.get(random));
        }
        int[] fitnessIndices = fitnessFunction(tournamentPopulation);
        String[] tournamentIndividual = new String[tournamentPopulation.size()];
        for (int i = 0; i < this.numTournament; i++) {
            if (fitnessIndices[i] < min) {
                min = fitnessIndices[i];
                tournamentIndividual = tournamentPopulation.get(i);
            }
        }
        return tournamentIndividual;
    }

    public ArrayList<String[]> crossover(ArrayList<String[]> population, double crossoverRate) {
        for (int i = 0; i < population.size() - 1; i++) {
            String[] firstChromosome = population.get(i);
            String[] secondChromosome = population.get(i + 1);
            for (int j = 0; j < secondChromosome.length; j++) {
                if (r.nextDouble() < crossoverRate) {
                    String temp = firstChromosome[j];
                    firstChromosome[j] = secondChromosome[j];
                    secondChromosome[j] = temp;
                }
            }
        }
        return population;
    }

    public ArrayList<String[]> mutation(ArrayList<String[]> population, double mutationRate) {
        for (int i = 0; i < population.size() - 1; i++) {
            String[] chromosome = population.get(i);
            for (int j = 0; j < chromosome.length; j++) {
                if (r.nextDouble() < mutationRate) {
                    if (chromosome[j].equals("0")) {
                        chromosome[j] = "1";
                    } else {
                        chromosome[j] = "0";
                    }
                }
            }
        }
        return population;
    }

    void newPopulation(ArrayList<String[]> population) {
        ArrayList<String[]> crossoverPopulation = crossover(population, crossoverRate);
        ArrayList<String[]> newPopulation = mutation(crossoverPopulation, mutationRate);
        String[] tournamentChromosome = tournament(population, fitnessFunction(population));
        newPopulation.set(newPopulation.size() - 1, tournamentChromosome);
        this.populationArray = newPopulation;
    }
    int[] fitnessFunction(ArrayList<String[]> popArray) {
        int[] res = new int[popArray.size()];
        int counter = 0;
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (String[] binaryString : popArray) {
            int[] intArray = convertToIntArray(binaryString);
            int fitness = 0;
            for (int j = 0; j < intArray.length - 1; j++) {
                int weight = graph.getWeightedEdge(intArray[j], intArray[j + 1]);
                if (intArray[0] != firstNode) {
                    fitness += 99999;
                }
                if (intArray[0] == endNode) {
                    fitness += 999999;
                }
                if (intArray[j] == endNode) {
                    fitness += weight;
                    break;
                } else if (weight > 0) {
                    fitness += weight;
                } else {
                    fitness += 4000;
                }
            }
            res[counter] = fitness;
            if (fitness <= min) {
                min = fitness;
                minIndex = counter;
            }

            counter++;
        }
        System.out.println("Minimum fitness: " + min);
        System.out.println("Minimum index: " + minIndex);

        return res;

    }

    void printPopulationArray() {
        System.out.println("Number of iteration: " + this.numberOfIteration);
        int counter = 0;
        int[] res = fitnessFunction(populationArray);
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

    ArrayList<String[]> getPopulationArray() {
        return populationArray;
    }

    public int getNumberOfIteration() {
        return numberOfIteration;
    }

    private int[] convertToIntArray(String[] stringArray) {
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i], 2);
        }
        return intArray;
    }


}
