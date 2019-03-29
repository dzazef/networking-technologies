import java.util.ArrayList;
import java.util.List;

class UndirectedEdge<T> {
    T vertex1;
    T vertex2;

    UndirectedEdge(T v1, T v2) {
        this.vertex1 = v1;
        this.vertex2 = v2;
    }

    public T getVertex1() {
        return vertex1;
    }

    public void setVertex1(T vertex1) {
        this.vertex1 = vertex1;
    }

    public T getVertex2() {
        return vertex2;
    }

    public void setVertex2(T vertex2) {
        this.vertex2 = vertex2;
    }
}

class WeightedUndirectedEdge<T> extends UndirectedEdge<T> {

    private double weight;

    WeightedUndirectedEdge(T v1, T v2, double w) {
        super(v1, v2);
        this.weight=w;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return weight+":"+"["+vertex1+", "+vertex2+"]";
    }
}

class UndirectedGraph<T> {
    List<T> vertexList;
    List<UndirectedEdge<T>> edgeList;

    UndirectedGraph() {
        this.vertexList = new ArrayList<T>();
        this.edgeList = new ArrayList<UndirectedEdge<T>>();
    }

    public void addVertex(T v) {
        vertexList.add(v);
    }

    public boolean removeVertex(T v) {
        return vertexList.remove(v);
    }
}

@SuppressWarnings("unused")
class UndirectedWeightedGraph<T> extends UndirectedGraph<T> {

    UndirectedWeightedGraph() {
        super();
    }

    public void addEdge(WeightedUndirectedEdge<T> e) {
        edgeList.add(e);
    }

    public void addEdge(T v1, T v2, double w) {
        edgeList.add(new WeightedUndirectedEdge<T>(v1, v2, w));
    }

    public boolean removeEdge(WeightedUndirectedEdge<T> e) {
        return edgeList.remove(e);
    }

    public boolean removeEdge(T v1, T v2) {
        for (UndirectedEdge<T> edge : edgeList) {
            if (edge.getVertex1()==v1 && edge.getVertex2()==v2) {
                edgeList.remove(edge);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "(Vertex: " + vertexList.toString() + ", Edge: "+edgeList.toString()+")";
    }
}
public class Task1 {


    public static void main(String[] args) {
        UndirectedWeightedGraph<Integer> graph = new UndirectedWeightedGraph<Integer>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2, 0.95);
        graph.addEdge(2, 3, 0.95);

        System.out.println(graph.toString());
    }
}
