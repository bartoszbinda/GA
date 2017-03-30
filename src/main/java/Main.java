import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    protected String styleSheet =
            "node {" +
                    "	fill-color: black;" +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}";
    private String filename = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter the name of file:");
        String filename = sc.nextLine();
        System.out.println("Filename: " + filename);
        filename = filename.replace("\\w+", "");
        Initializer file = null;
        try {
            file = new Initializer(filename);
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
//        for(int i : best){
//            Node node = graphic.getNode(Integer.toString(i));
//            node.setAttribute("ui.class", "marked");
//            if(i == endNode) break;
//        }
        Viewer view = graphic.display();

    }

    public String getFilename() {
        return filename;
    }
}
