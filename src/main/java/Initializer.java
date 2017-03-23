import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Initializer {

    int numVertices;
    AdjacencyMatrixGraph graph;
    private int numNodes;
    private double mutationRate;
    private int arrayCount;
    private double crossoverRate;
    private String fileName;
    private int endNode;
    private int firstNode;
    private ArrayList<Integer> amountOfNodes;
    private int numPop;
    private int tournamentNum;
    private int limit;
    private String filename;


    public Initializer() throws FileNotFoundException {
        numNodes = 0;
        limit = 0;
        numPop = 0;
        tournamentNum = 0;
        mutationRate = 0;
        arrayCount = 0;
        this.fileName = fileName;
        crossoverRate = 0.1;
        amountOfNodes = new ArrayList<>();
        fileName = "data.txt";
        try {
            read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean validate(double crossoverRate, double mutationRate) {
        return crossoverRate >= 0 && crossoverRate <= 1.0 && mutationRate >= 0 && mutationRate <= 1;
    }


    public void read() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter the name of file:");
        setFilename(sc.nextLine().replace("\\w+", ""));
        System.out.println("Please enter mutation rate: ");
        mutationRate = sc.nextDouble();
        sc.nextLine();
        System.out.println("Please enter the crossover rate: ");
        crossoverRate = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the first node:");
        int firstNode = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the end node");
        endNode = sc.nextInt();
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

        graph = new AdjacencyMatrixGraph(numVertices * 2, Graph.GraphType.DIRECTED);
        sc.close();
        if (validate(crossoverRate, mutationRate)) {
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

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
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

    public int getEndNode() {
        return this.endNode;
    }

    public int getFirstNode() {
        return this.firstNode;
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

    public void setFilename(String filename) {
        this.filename = filename;
    }
}