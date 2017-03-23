import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Initializer file = null;
        try {
            file = new Initializer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int iterationNum = 1;
        int numNodes = file.getNumNodes();
        double crossoverRate = file.getCrossoverRate();
        double mutationRate = file.getMutationRate();
        int limit = file.getLimit();
        int numPop = file.getNumPop();
        int numTournament = file.getTournamentNum();
        int endNode = file.getEndNode();
        int firstNode = file.getFirstNode();
        GeneticAlgorithm ga = new GeneticAlgorithm(file.getGraph(), endNode, firstNode,
                numTournament, crossoverRate, mutationRate);
        ga.initializePopulation(numNodes, numPop);
        ga.printPopulationArray();
        ga.fitnessFunction(ga.getPopulationArray());
        while (iterationNum != limit) {
            System.out.print(iterationNum + " ");
            ArrayList<String[]> popArray = ga.getPopulationArray();
            ga.newPopulation(popArray);
            ga.fitnessFunction(ga.getPopulationArray());
            ga.printPopulationArray();
            ga.printGenResult();
            iterationNum++;
            System.out.println();
        }
        ga.fitnessFunction(ga.getPopulationArray());
        ga.printEndResult();
    }
}
