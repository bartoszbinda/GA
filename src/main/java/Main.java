import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Plese enter the name of file containg the graph: (without .txt)");
//        Scanner sc = new Scanner(System.in);
//        String fileName = sc.nextLine().replace("\\w+", "");
//        sc.close();
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
//        ga.fitnessFunction(ga.getPopulationArray());
        while (iterationNum != 60) {
            System.out.println("Number of iteration: " + iterationNum);
            System.out.println();
            ArrayList<String[]> popArray = ga.getPopulationArray();
            ga.newPopulation(popArray);
            ga.fitnessFunction(ga.getPopulationArray());
            ga.printGenResult();
            iterationNum++;
        }
        ga.fitnessFunction(ga.getPopulationArray());
        ga.printEndResult();
    }
}
