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
        int numPop = 18;
        int iterationNum = 1;
        int numTournament = 3;
        int endNode = file.getEndNode();
        int firstNode = file.getFirstNode();
        GeneticAlgorithm ga = new GeneticAlgorithm(file.getGraph(), endNode, firstNode,
                numTournament, crossoverRate, mutationRate);
        ga.initializePopulation(numNodes, numPop);
        ArrayList<String[]> popArray = ga.getPopulationArray();
        ga.printPopulationArray();
        ga.fitnessFunction(ga.getPopulationArray());
        while (iterationNum != 30) {
            System.out.println("Number of iteration: " + iterationNum);
            ga.newPopulation(popArray);
            ga.printPopulationArray();
            iterationNum++;
            ga.fitnessFunction(ga.getPopulationArray());
        }
        ga.fitnessFunction(ga.getPopulationArray());
    }
}
