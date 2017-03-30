import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.FileNotFoundException;

public class DijkstraAlgorithm {
    public DijkstraAlgorithm() {
        Initializer file = null;
        try {
            file = new Initializer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long startTime = System.currentTimeMillis();
        int firstNode = file.getFirstNode();
        int endNode = file.getEndNode();
        SingleGraph graphic = file.getGraphVisualisation();
        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, "result", "length");
        dijkstra.init(graphic);
        dijkstra.setSource(graphic.getNode(Integer.toString(firstNode)));
        dijkstra.compute();
        System.out.println(dijkstra.getPathLength(graphic.getNode(Integer.toString(endNode))));
        dijkstra.clear();
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");

    }

    public static void main(String[] args) {
        DijkstraAlgorithm da = new DijkstraAlgorithm();

    }
}
