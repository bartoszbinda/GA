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
        int numPop = 18;
        int endNode = file.getEndNode();
        int firstNode = file.getFirstNode();
        GeneticAlgorithm ga = new GeneticAlgorithm(file.getGraph(), endNode, firstNode);
        ga.initializePopulation(numNodes, numPop);
        ga.printPopulationArray();
        ga.fitnessFunction(ga.getPopulationArray());
    }
}
