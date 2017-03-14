import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultDirectedGraph;

public class FileReader {

    private int numNodes;
    private double mutationRate;
    private int curLine;
    private StringTokenizer st;
    private int arrayCount;
    private double crossoverRate;
    private String fileName;
    private Node[] nodes;
    private DefaultDirectedWeightedGraph <String, DefaultEdge> g = new DefaultDirectedWeightedGraph<>(DefaultEdge.class);
    public FileReader(String fileName) {
        numNodes = 0;
        mutationRate = 0;
        Node[] nodes = new Node[0];
        curLine = 1;
        arrayCount = 0;
        this.fileName=fileName;
        crossoverRate = 0.1;
        read();
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

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public int getCurLine() {
        return curLine;
    }

    public void setCurLine(int curLine) {
        this.curLine = curLine;
    }

    public StringTokenizer getSt() {
        return st;
    }

    public void setSt(StringTokenizer st) {
        this.st = st;
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
        try {
            BufferedReader in = new BufferedReader(new java.io.FileReader("./" + fileName));
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    String[] parseArray = line.split(" ");
                    g.addVertex(parseArray[0]);
                    g.addVertex(parseArray[2]);
                    g.addEdge(parseArray[0],parseArray[2]);
                    DefaultEdge edge = g.getEdge(parseArray[0], parseArray[1]);
                    g.setEdgeWeight(edge, Integer.parseInt(parseArray[1]));
                    curLine++;
                    System.out.println("Edge weight: " + edge);

                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!!");
            System.exit(0);

        }
        this.numNodes = curLine;
    }


    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }
}