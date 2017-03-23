import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Initializer file = null;
        try {
            file = new Initializer("data.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int numNodes = file.getNumNodes();
        double crossoverRate = file.getCrossoverRate();
        double mutationRate = file.getMutationRate();
        int numPop = 16;
        int iterationNum = 1;
        int numTournament = 5;
        int endNode = file.getEndNode();
        int firstNode = file.getFirstNode();
        GeneticAlgorithm ga = new GeneticAlgorithm(file.getGraph(), endNode, firstNode,
                numTournament, crossoverRate, mutationRate);
        ga.initializePopulation(numNodes, numPop);
        ga.printPopulationArray();
        ga.fitnessFunction(ga.getPopulationArray());
        while (iterationNum != 600) {
            System.out.println("Number of iteration: " + iterationNum);
            System.out.println();
            ArrayList<String[]> popArray = ga.getPopulationArray();
            ga.newPopulation(popArray);
            ga.printPopulationArray();
            iterationNum++;
        }
        ga.fitnessFunction(ga.getPopulationArray());
        ga.printResult();
    }
}
