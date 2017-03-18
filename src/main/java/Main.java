public class Main {

    public static void main(String[] args) {
        Initializer file = new Initializer("data.txt");
        int numNodes = file.getNumNodes();
        int numPop = 18;
        int endNode = file.getEndNode();
        GeneticAlgorithm ga = new GeneticAlgorithm(file.getGraph(), endNode);
        ga.initializePopulation(numNodes, numPop);
        ga.printPopulationArray();
        ga.fitnessFunction(ga.getPopulationArray());
    }
}
