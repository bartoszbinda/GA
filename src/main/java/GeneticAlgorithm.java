import java.math.BigDecimal;
import java.math.BigInteger;
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
    private String[] bestIndividual;
    private int bestIndividualFitness;
    private int bestIndividualGenerationNumber;
    private ArrayList<Integer> allFitness;
    private ArrayList<Integer> genFitness;

    GeneticAlgorithm(AdjacencyMatrixGraph graph, int endNode, int firstNode, int numTournament, double crossoverRate, double mutationRate) {
        this.numberOfIteration = 0;
        this.graph = graph;
        this.endNode = endNode;
        this.firstNode = firstNode;
        this.numTournament = numTournament;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.bestIndividual = new String[0];
        this.bestIndividualFitness = Integer.MAX_VALUE;
        this.bestIndividualGenerationNumber = 0;
        this.allFitness = new ArrayList<>();
        this.genFitness = new ArrayList<>();
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
        setNumberOfIteration(this.numTournament++);
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
                    char[] binCharArray = chromosome[j].toCharArray();
                    for (int z = 0; z < binCharArray.length; z++) {
                        if (binCharArray[z] == '0') {
                            binCharArray[z] = '1';
                        } else {
                            binCharArray[z] = '0';
                        }
                        chromosome[j] = String.valueOf(binCharArray);
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
        setNumberOfIteration(this.numTournament++);
        this.genFitness.clear();
    }

    void setGenFitness(ArrayList<Integer> GenFitness) {
        this.genFitness = GenFitness;
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
                    fitness += 100;
                }
                if (intArray[0] == endNode) {
                    fitness += 20;
                } else if (intArray[j] == intArray[j + 1]) {
                    fitness += 50;
                }

                if (intArray[j] == endNode) {
                    fitness += weight;
                    break;
                } else if (weight > 0) {
                    fitness += weight;
                } else {
                    fitness += 20;
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
                setBestIndividualFitness(fitness);
                setBestIndividual(binaryString);
                setBestIndividualGenerationNumber(getNumberOfIteration());
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
        return this.numberOfIteration;
    }

    public void setNumberOfIteration(int numberOfIteration) {
        this.numberOfIteration = numberOfIteration;
    }

    private int[] convertToIntArray(String[] stringArray) {
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i], 2);
        }
        return intArray;
    }

    public void printGenResult() {
        System.out.println();
        System.out.println("In iteration number:");
        System.out.println(this.bestIndividualGenerationNumber);
        System.out.println("Best fitness:");
        System.out.println(this.bestIndividualFitness);
        BigInteger sum = sum(genFitness);
        BigDecimal mean = new BigDecimal(sum.divide(BigInteger.valueOf(genFitness.size())));
        System.out.println("Arithmetic mean: " + mean);
    }

    public void printEndResult() {
        System.out.println();
        System.out.println("BEST SOLUTION");
        System.out.println("best individual:");
        System.out.print(this.bestIndividual[0] + " : ");
        for (int i = 1; i < bestIndividual.length - 1; i++) {
            System.out.print(this.bestIndividual[i] + " : ");
        }
        System.out.println(bestIndividual[bestIndividual.length - 1]);
        System.out.println();
        System.out.println("Best fitness:");
        System.out.println(this.bestIndividualFitness);
        System.out.println("In iteration number:");
        System.out.println(this.bestIndividualGenerationNumber);
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

    public void setBestIndividualGenerationNumber(int genNumber) {
        this.bestIndividualGenerationNumber = genNumber;
    }

    public BigInteger sum(ArrayList<Integer> list) {
        BigInteger sum = new BigInteger(String.valueOf(0));

        for (int i : list)
            sum = sum.add(BigInteger.valueOf(i));
        return sum;
    }
}
