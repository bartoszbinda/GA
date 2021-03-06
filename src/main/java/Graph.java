import java.util.List;

public interface Graph {
    void addEdge(int v1, int v2, int weight);

    int getWeightedEdge(int v1, int v2);

    List<Integer> getAdjacentVertices(int v);

    int getNumVertices();

    int getIndegree(int v);

    enum GraphType {
        DIRECTED,
        UNDIRECTED
    }
}
