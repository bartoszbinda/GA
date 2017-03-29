import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {
    private final int penalty;
    Random r = new Random();
    private int numberOfIteration;
    private ArrayList<String[]> populationArray = new ArrayList<>();
    private AdjacencyMatrixGraph graph;
    private int endNode;
    private int firstNode;
    private int numTournament;
    private double mutationRate;
    private String[] bestIndividual;
    private int bestIndividualFitness;
    private int bestIndividualGenerationNumber;
    private ArrayList<Integer> allFitness;
    private ArrayList<Integer> genFitness;

    GeneticAlgorithm(AdjacencyMatrixGraph graph, int endNode, int firstNode, int numTournament, double mutationRate) {
        this.numberOfIteration = 0;
        this.graph = graph;
        this.endNode = endNode;
        this.penalty = 30;
        this.firstNode = firstNode;
        this.numTournament = numTournament;
        this.mutationRate = mutationRate;
        this.bestIndividual = new String[0];
        this.bestIndividualFitness = Integer.MAX_VALUE;
        this.bestIndividualGenerationNumber = 0;
        this.allFitness = new ArrayList<>();
        this.genFitness = new ArrayList<>();
    }

    static public int[] convertToIntArray(String[] stringArray) {
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i], 2);
        }
        return intArray;
    }

    int getNumTournament() {
        return this.numTournament;
    }

    public void setNumTournament(int numTournament) {
        this.numTournament = numTournament;
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
        int numTournament = getNumTournament();
        setNumberOfIteration(numTournament);
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

    public String[] crossover(String[] parent1, String[] parent2) {
        int splitID = ThreadLocalRandom.current().nextInt(0, parent1.length);
        String[] child = new String[parent1.length];
        System.arraycopy(parent1, 0, child, 0, splitID);
        System.arraycopy(parent2, splitID, child, splitID, parent2.length - splitID);
        return child;
    }

    public String[] mutation(String[] child) {
        for (int j = 0; j < child.length; j++) {

            char[] binCharArray = child[j].toCharArray();
            for (int z = 0; z < binCharArray.length; z++) {
                if (r.nextDouble() < getMutationRate()) {
                    if (binCharArray[z] == '0') {
                        binCharArray[z] = '1';
                    } else {
                        binCharArray[z] = '0';
                    }
                    child[j] = String.valueOf(binCharArray);

                }
            }
        }
        return child;
    }

    void newPopulation(ArrayList<String[]> population) {
        int[] fitness = fitnessFunction(population);
        int counter = 0;
        ArrayList<String[]> newPopulation = new ArrayList<>();

        while (counter != population.size()) {
            String[] parent1 = tournament(population, fitness);
            String[] parent2 = tournament(population, fitness);
            String[] child = crossover(parent1, parent2);
            child = mutation(child);
            newPopulation.add(child);
            counter++;
        }
        setPopulationArray(newPopulation);
        int numTournament = getNumTournament();
        setNumberOfIteration(numTournament++);
        getGenFitness().clear();
    }

    int[] fitnessFunction(ArrayList<String[]> popArray) {
        int[] res = new int[popArray.size()];
        int counter = 0;
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (String[] binaryString : popArray) {
            int[] intArray = convertToIntArray(binaryString);
            int fitness = 0;
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int j = 0; j < intArray.length - 1; j++) {
                int weight = getGraph().getWeightedEdge(intArray[j], intArray[j + 1]);
                fitness += weight;
                if (map.get(intArray[j]) != null) {
                    map.put(intArray[j], map.get(intArray[j]) + 1);
                } else {
                    map.put(intArray[j], 1);
                }
                if (map.get(intArray[j]) > 2) {
                    fitness += 3 * penalty;
                }
                if (intArray[0] != getFirstNode()) {
                    fitness += 10 * penalty;
                }
                if (intArray[j] == getEndNode()) {
                    break;
                }
                if (intArray[j] == intArray[j + 1]) {
                    fitness += 3 * penalty;
                }
                if (weight == 0) {
                    fitness += 10 * penalty;
                }

                if (intArray[0] == getEndNode()) {
                    fitness += 10 * penalty;
                }

            }
            res[counter] = fitness;
            allFitness.add(fitness);
            genFitness.add(fitness);
            if (fitness <= min) {
                min = fitness;
                minIndex = counter;
            }
            int minFitness = getBestIndividualFitness();
            if (fitness < minFitness) {
                this.setBestIndividualFitness(fitness);
                this.setBestIndividual(binaryString);
                this.setBestIndividualGenerationNumber(getNumberOfIteration());
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

    public void printGenResult() {
        System.out.println();
        System.out.println("In iteration number:");
        System.out.println(getBestIndividualGenerationNumber());
        System.out.println("Best fitness:");
        System.out.println(getBestIndividualFitness());
        BigInteger sum = sum(genFitness);
        BigDecimal mean = new BigDecimal(sum.divide(BigInteger.valueOf(genFitness.size())));
        System.out.println("Average mean: " + mean);
    }

    public void printEndResult() {
        System.out.println();
        System.out.println("BEST SOLUTION");
        System.out.println("best individual:");
        String[] bestIndividualOfAllTime = this.getBestIndividual();
        System.out.print(bestIndividualOfAllTime[0] + " : ");
        for (int i = 1; i < bestIndividualOfAllTime.length - 1; i++) {
            System.out.print(bestIndividualOfAllTime[i] + " : ");
        }
        System.out.println(bestIndividualOfAllTime[bestIndividualOfAllTime.length - 1]);
        System.out.println();
        System.out.println("Best fitness:");
        System.out.println(getBestIndividualFitness());
        System.out.println("In generation number:");
        System.out.println(getBestIndividualGenerationNumber());
        BigInteger sum = sum(allFitness);
        BigDecimal mean = new BigDecimal(sum.divide(BigInteger.valueOf(allFitness.size())));
        System.out.println("Arithmetic mean: " + mean);
    }

    public int getBestIndividualFitness() {
        return bestIndividualFitness;
    }

    public void setBestIndividualFitness(int bestIndividualFitness) {
        this.bestIndividualFitness = bestIndividualFitness;
    }

    public String[] getBestIndividual() {
        return bestIndividual;
    }

    public void setBestIndividual(String[] bestIndividual) {
        this.bestIndividual = bestIndividual;
    }

    public BigInteger sum(ArrayList<Integer> list) {
        BigInteger sum = new BigInteger(String.valueOf(0));

        for (int i : list)
            sum = sum.add(BigInteger.valueOf(i));
        return sum;
    }

    public AdjacencyMatrixGraph getGraph() {
        return graph;
    }

    public void setGraph(AdjacencyMatrixGraph graph) {
        this.graph = graph;
    }

    int getEndNode() {
        return this.endNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    int getFirstNode() {
        return this.firstNode;
    }

    public void setFirstNode(int firstNode) {
        this.firstNode = firstNode;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public Random getR() {
        return r;
    }

    public void setR(Random r) {
        this.r = r;
    }

    public int getBestIndividualGenerationNumber() {
        return bestIndividualGenerationNumber;
    }

    public void setBestIndividualGenerationNumber(int genNumber) {
        this.bestIndividualGenerationNumber = genNumber;
    }

    public ArrayList<Integer> getAllFitness() {
        return allFitness;
    }

    public void setAllFitness(ArrayList<Integer> allFitness) {
        this.allFitness = allFitness;
    }

    public ArrayList<Integer> getGenFitness() {
        return genFitness;
    }

    void setGenFitness(ArrayList<Integer> GenFitness) {
        this.genFitness = GenFitness;
    }

    ArrayList<String[]> getPopulationArray() {
        return populationArray;
    }

    public void setPopulationArray(ArrayList<String[]> populationArray) {
        this.populationArray = populationArray;
    }

    public int getNumberOfIteration() {
        return this.numberOfIteration;
    }

    public void setNumberOfIteration(int numberOfIteration) {
        this.numberOfIteration = numberOfIteration;
    }


}
