import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Initializer {

    private int numNodes;
    private double mutationRate;
    private int curLine;
    private int arrayCount;
    private double crossoverRate;
    private String fileName;
    private Object[] vertexSet;
    private Object[] edgeSet;
    SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph =
            new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>
                    (DefaultWeightedEdge.class);
    public Initializer(String fileName) {
        numNodes = 0;
        mutationRate = 0;
        curLine = 1;
        arrayCount = 0;
        this.fileName=fileName;
        crossoverRate = 0.1;
        read();
    }
    private boolean validate(double crossoverRate, double mutationRate){
        if(crossoverRate>=0 && crossoverRate<=1.0 && mutationRate >= 0 && mutationRate<=1){
            return true;
        }
        return false;
    }

    public int getNumNodes() {
        return numNodes;
    }
    public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> getGraph() {
        return graph;
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


    public void read() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter mutation rate: ");
        mutationRate = sc.nextDouble();
        sc.nextLine();
        System.out.println("Please enter the crossover rate: ");
        crossoverRate = sc.nextDouble();
        sc.nextLine();
        sc.close();
        if( validate(crossoverRate,mutationRate)) {
            try {
                BufferedReader in = new BufferedReader(new java.io.FileReader("./" + fileName));
                String line;
                try {
                    while ((line = in.readLine()) != null) {
                        String[] parseArray = line.split(" ");

                        graph.addVertex(parseArray[0]);
                        graph.addVertex(parseArray[2]);
                        DefaultWeightedEdge edge = graph.addEdge(parseArray[0], parseArray[2]);
                        graph.setEdgeWeight(edge, Integer.parseInt(parseArray[1]));
                        curLine++;


                    }
                    this.numNodes = curLine;
                    this.vertexSet = graph.vertexSet().toArray();
                    this.edgeSet = graph.edgeSet().toArray();
                    in.close();

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found!!");
                System.exit(0);

            }
        }
        else {
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
}