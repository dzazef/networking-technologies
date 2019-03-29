import org.jgraph.graph.DefaultEdge;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

import java.util.Scanner;
import java.util.Set;

class NetworkManager {
    private DefaultDirectedWeightedGraph<Integer, DefaultEdge> g = new DefaultDirectedWeightedGraph<>(DefaultEdge.class);
    private Integer[][] capacity = new Integer[10][10];
    private int packageSize;
    private Integer[][] flow = new Integer[10][10];

    NetworkManager() {
        for(int i=1; i<=10; i++) {
            g.addVertex(i);
        }
    }

    void addEdge(int v1, int v2) {
        g.addEdge(v1, v2);
    }

    void setPackageSize(int p) {
        this.packageSize = p;
    }

    public int getPackageSize() {
        return packageSize;
    }

    void setCapacity(int v1, int v2, Integer value) {
        capacity[v1-1][v2-1] = value;
    }

    Integer getCapacity(int v1, int v2) {
        return capacity[v1-1][v2-1];
    }

    void setFlow(int v1, int v2, int f) {
        flow[v1-1][v2-1] = f;
    }

    Integer getFlow(int v1, int v2) {
        return flow[v1-1][v2-1];
    }

    DefaultDirectedWeightedGraph<Integer, DefaultEdge> getGraph() {
        return this.g;
    }

    Set<DefaultEdge> getEdgeSet() {
        return this.g.edgeSet();
    }

    boolean checkIfConnected() {
        ConnectivityInspector<Integer, DefaultEdge> c = new ConnectivityInspector<>(g);
        return c.isConnected();
    }

    void clearConnections() {
        g.removeAllEdges(g.edgeSet());
    }
}

class NetworkBuilder {

    private NetworkManager n;
    private Scanner s;
    private int edges;

    NetworkBuilder() {
        this.n = new NetworkManager();
        this.s = new Scanner(System.in);
    }

    private void setNumberOfEdges() {
        System.out.print("How many edges you want to set (<20): ");
        this.edges = s.nextInt();
        if (edges>=20) {
            edges=19;
            System.out.println("Number of edges set to 19.");
        }
    }

    private void buildEdges() {
        //Setting edges
        System.out.println("Set edges: ");
        int v1, v2;
        for (int i=0; i<edges; i++) {
            System.out.println("Edge no "+(i+1));
            do {
                System.out.print("Vertex 1: ");
                v1 = s.nextInt();
                if (v1>10 || v1<=0) System.out.println("Incorrect value. Try again.");
            } while(v1>10 || v1<=0);
            do {
                System.out.print("Vertex 2: ");
                v2 = s.nextInt();
                if (v2>10 || v2<=0 || v1==v2) System.out.println("Incorrect value. Try again.");
            } while(v2>10 || v2<=0 || v1==v2);
            n.addEdge(v1, v2);
        }

    }

    private void setPackageSize() {
        int packageSize;
        System.out.print("Set package size(Bytes): ");
        do {
            packageSize = s.nextInt();
            if (packageSize<0) System.out.println("Incorrect value. Try again.");
        } while(packageSize<0);
        n.setPackageSize(packageSize);
    }

    private void setFlow() {
        int v1, v2, packages;
        System.out.println("Setting numbers of packages sent between users: ");
        for (DefaultEdge d : n.getEdgeSet()) {
            v1 = n.getGraph().getEdgeSource(d);
            v2 = n.getGraph().getEdgeTarget(d);
            System.out.print("From client "+v1+" to client "+v2+": ");
            do {
                packages = s.nextInt();
                if (packages<0) System.out.println("Incorrect value. Try again.");
            } while (packages<0);
            n.setFlow(v1, v2, packages);

            v1 = n.getGraph().getEdgeTarget(d);
            v2 = n.getGraph().getEdgeSource(d);
            System.out.print("From client "+v1+" to client "+v2+": ");
            do {
                packages = s.nextInt();
                if (packages<0) System.out.println("Incorrect value. Try again.");
            } while (packages<0);
            n.setFlow(v1, v2, packages);
        }
    }

    private void setCapacity() {
        int v1, v2, capacity;
        System.out.println("Setting max numbers of bytes sent between users: ");
        for (DefaultEdge d : n.getEdgeSet()) {
            v1 = n.getGraph().getEdgeSource(d);
            v2 = n.getGraph().getEdgeTarget(d);
            System.out.print("From client "+v1+" to client "+v2+": ");
            do {
                capacity = s.nextInt();
                if (capacity<0) System.out.println("Incorrect value. Try again.");
            } while (capacity<0);
            n.setCapacity(v1, v2, capacity);

            v1 = n.getGraph().getEdgeTarget(d);
            v2 = n.getGraph().getEdgeSource(d);
            System.out.print("From client "+v1+" to client "+v2+": ");
            do {
                capacity = s.nextInt();
                if (capacity<0) System.out.println("Incorrect value. Try again.");
            } while (capacity<0);
            n.setCapacity(v1, v2, capacity);
        }

    }

    private boolean checkIfConnected() {
        return n.checkIfConnected();
    }

    NetworkManager buildNetwork() {
        setNumberOfEdges();
        boolean connected; String answer;
        while(true) {
            buildEdges();
            connected = n.checkIfConnected();
            if (!connected) {
                System.out.print("Your graph is not connected. Do you want to rearrange the edges?(yes/no): ");
                answer = s.next();
                if (answer.equals("no") || answer.equals("n"))
                    break;
            } else {
                break;
            }
            n.clearConnections();
        }
        setPackageSize();
        setFlow();
        setCapacity();
        return this.n;
    }
}

public class Zad2 {
    public static void main(String[] args) {
        NetworkBuilder nb = new NetworkBuilder();
        nb.buildNetwork();
    }
}