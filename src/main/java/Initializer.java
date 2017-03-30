import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Initializer {

    protected String styleSheet =
            "node {" +
                    "	fill-color: black;" +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}";
    int numVertices;
    AdjacencyMatrixGraph graph;
    private int numNodes;
    private double mutationRate;
    private int arrayCount;
    private String fileName;

    private int endNode;
    private int firstNode;
    private ArrayList<Integer> amountOfNodes;
    private int numPop;
    private int tournamentNum;
    private int limit;
    private SingleGraph graphVisualisation = new SingleGraph("Genetic Algorithm");

    public Initializer() throws FileNotFoundException {
        numNodes = 0;
        limit = 0;
        numPop = 0;
        tournamentNum = 0;
        mutationRate = 0;
        arrayCount = 0;
        this.fileName = fileName;
        amountOfNodes = new ArrayList<>();
        fileName = "data.txt";
        try {
            read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean validate(double mutationRate) {
        return mutationRate >= 0 && mutationRate <= 1;
    }


    public void read() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter mutation rate: ");
        mutationRate = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the first node:");
        setFirstNode(sc.nextInt());
        sc.nextLine();
        System.out.println("Enter the end node");
        setEndNode(sc.nextInt());
        System.out.println("Please enter the number of population:");
        setNumPop(sc.nextInt());
        sc.nextLine();
        System.out.println("Please enter the maximum number of iterations: ");
        setLimit(sc.nextInt());
        sc.nextLine();
        System.out.println("Please enter the number of chromosomes in tournament:");
        setTournamentNum(sc.nextInt());
        sc.nextLine();
        sc.close();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        try {
            while (reader.readLine() != null) numVertices++;
            reader.close();
        } catch (Exception e) {

        }
        // numVertices * 2, *10 is for testing
        graph = new AdjacencyMatrixGraph(numVertices * 2, Graph.GraphType.DIRECTED);
        sc.close();
        if (validate(mutationRate)) {
            try {
                BufferedReader in = new BufferedReader(new java.io.FileReader("./" + fileName));
                String line;
                try {
                    while ((line = in.readLine()) != null) {
                        String[] parseArray = line.split(" ");
                        int nodeOne = Integer.parseInt(parseArray[0]);
                        int nodeTwo = Integer.parseInt(parseArray[2]);
                        int weight = Integer.parseInt(parseArray[1]);
                        graph.addEdge(nodeOne, nodeTwo, weight);
                        amountOfNodes.add(nodeOne);
                        amountOfNodes.add(nodeTwo);
                        graphVisualisation.setStrict(false);
                        graphVisualisation.setAutoCreate(true);
                        try {
                            Edge edge = graphVisualisation.addEdge(parseArray[0] + parseArray[2], parseArray[0], parseArray[2]);
                            edge.addAttribute("length", Integer.parseInt(parseArray[1]));
                            edge.setAttribute("ui.label", edge.getId());
                            edge.addAttribute("label", "" + (int) edge.getNumber("length"));
                        } catch (Exception e) {
                            System.out.println("Node is already created");
                        }
                    }
                    graphVisualisation.addAttribute("ui.stylesheet", styleSheet);
                    for (Node node : graphVisualisation) {
                        node.addAttribute("ui.label", node.getId());
                        node.addAttribute("ui.stylesheet", styleSheet);
                    }

                    this.numNodes = Collections.max(amountOfNodes);
                    in.close();


                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found!!");
                System.exit(0);

            }
        } else {
            System.out.println("Wrong format of inputs! CrossOver and mutation rate must be >= 0 and <= 1!");
            System.exit(0);
        }

    }

    public int getNumNodes() {
        return numNodes;
    }


    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getArrayCount() {
        return arrayCount;
    }

    public void setArrayCount(int arrayCount) {
        this.arrayCount = arrayCount;
    }

    public AdjacencyMatrixGraph getGraph() {
        return graph;
    }

    public void setGraph(AdjacencyMatrixGraph graph) {
        this.graph = graph;
    }

    public int getEndNode() {
        return this.endNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    public int getFirstNode() {
        return this.firstNode;
    }

    public void setFirstNode(int firstNode) {
        this.firstNode = firstNode;
    }

    public int getNumPop() {
        return numPop;
    }

    public void setNumPop(int numPop) {
        this.numPop = numPop;
    }

    public int getTournamentNum() {
        return tournamentNum;
    }

    public void setTournamentNum(int tournamentNum) {
        this.tournamentNum = tournamentNum;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setFilename(String fileName) {
        this.fileName = fileName;
    }

    public SingleGraph getGraphVisualisation() {
        return graphVisualisation;
    }

    public void setGraphVisualisation(SingleGraph graphVisualisation) {
        this.graphVisualisation = graphVisualisation;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Integer> getAmountOfNodes() {
        return amountOfNodes;
    }

    public void setAmountOfNodes(ArrayList<Integer> amountOfNodes) {
        this.amountOfNodes = amountOfNodes;
    }


}