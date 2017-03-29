import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;

import java.io.FileNotFoundException;
public class Main {
    protected String styleSheet =
            "node {" +
                    "	fill-color: black;" +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}";

    public static void main(String[] args) {
        Initializer file = null;
        try {
            file = new Initializer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int iterationNum = 1;
        int numNodes = file.getNumNodes();
        double mutationRate = file.getMutationRate();
        int limit = file.getLimit();
        int numPop = file.getNumPop();
        int numTournament = file.getTournamentNum();
        int endNode = file.getEndNode();
        int firstNode = file.getFirstNode();
        GeneticAlgorithm ga = new GeneticAlgorithm(file.getGraph(), endNode, firstNode,
                numTournament, mutationRate);
        ga.initializePopulation(numNodes, numPop);
        ga.printPopulationArray();
        ga.fitnessFunction(ga.getPopulationArray());
        while (iterationNum != limit) {
            System.out.print(iterationNum + " ");
            ga.newPopulation(ga.getPopulationArray());
            ga.fitnessFunction(ga.getPopulationArray());
            ga.printPopulationArray();
            ga.printGenResult();
            iterationNum++;
            System.out.println();
        }
        ga.fitnessFunction(ga.getPopulationArray());
        ga.printEndResult();
        SingleGraph graphic = file.getGraphVisualisation();
        int[] best = GeneticAlgorithm.convertToIntArray(ga.getBestIndividual());
        for (int i : best) {
            org.graphstream.graph.Node node = graphic.getNode(Integer.toString(i));
            node.setAttribute("ui.class", "marked");
            if (i == endNode) break;
        }
        Viewer view = graphic.display();


    }
}
