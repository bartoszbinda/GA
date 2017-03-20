import java.io.FileNotFoundException;

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
        int numPop = 18;
        int numTournament = 3;
        int endNode = file.getEndNode();
        int firstNode = file.getFirstNode();
        GeneticAlgorithm ga = new GeneticAlgorithm(file.getGraph(), endNode, firstNode, numTournament, crossoverRate);
        ga.initializePopulation(numNodes, numPop);
        ga.printPopulationArray();
        ga.fitnessFunction(ga.getPopulationArray());
    }
}
