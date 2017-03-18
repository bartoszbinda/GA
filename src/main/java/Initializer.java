import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Initializer {

    int numVertices;
    AdjacencyMatrixGraph graph;
    private int numNodes;
    private double mutationRate;
    private int curLine;
    private int arrayCount;
    private double crossoverRate;
    private String fileName;
    private int endNode;
    public Initializer(String fileName) {
        numNodes = 0;
        mutationRate = 0;
        curLine = 1;
        arrayCount = 0;
        this.fileName = fileName;
        crossoverRate = 0.1;
        read();
    }

    private boolean validate(double crossoverRate, double mutationRate) {
        return crossoverRate >= 0 && crossoverRate <= 1.0 && mutationRate >= 0 && mutationRate <= 1;
    }


    public void read() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter mutation rate: ");
        mutationRate = sc.nextDouble();
        sc.nextLine();
        System.out.println("Please enter the crossover rate: ");
        crossoverRate = sc.nextDouble();
        sc.nextLine();
        System.out.println("Please enter the number of vertices: ");
        numVertices = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the end node");
        endNode = sc.nextInt();
        sc.nextLine();
        graph = new AdjacencyMatrixGraph(numVertices, Graph.GraphType.DIRECTED);
        sc.close();
        if (validate(crossoverRate, mutationRate)) {
            try {
                BufferedReader in = new BufferedReader(new java.io.FileReader("./" + fileName));
                String line;
                try {
                    while ((line = in.readLine()) != null) {
                        String[] parseArray = line.split(" ");
                        graph.addEdge(Integer.parseInt(parseArray[0]), Integer.parseInt(parseArray[2]), Integer.parseInt(parseArray[1]));
                        curLine++;


                    }
                    this.numNodes = curLine;
//                    this.vertexSet = graph.vertexSet();
//                    this.edgeSet = graph.edgeSet();
                    in.close();
                    ///TODO: iterowanie sie i dostanie kazdego vertixa
//                        for(DefaultWeightedEdge edge : edgeSet){
//                            System.out.println(edge.toString());
//                        }
//                        for(String vertix : vertexSet){
//                            System.out.println(vertix);
//                        }

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

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

//    public Set<Integer> getVertexSet() {
//        return vertexSet;
//    }
//
//    public Set<DefaultWeightedEdge> getEdgeSet() {
//        return edgeSet;
//    }

    public int getNumNodes() {
        return numNodes;
    }

//    public SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
//        return graph;
//    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getCurLine() {
        return curLine;
    }

    public void setCurLine(int curLine) {
        this.curLine = curLine;
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

    public int getEndNode() {
        return this.endNode;
    }
}