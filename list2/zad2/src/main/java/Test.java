import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

import java.util.Scanner;

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
        capacity[v1][v2] = value;
    }

    Integer getCapacity(int v1, int v2) {
        return capacity[v1][v2];
    }
}

public class Test {
    public static void main(String[] args) {
        NetworkManager n = new NetworkManager();
        Scanner s = new Scanner(System.in);

        //Setting number of edges
        System.out.print("How many edges you want to set (<20): ");
        int edges = s.nextInt();
        if (edges>=20) {
            edges=19;
            System.out.println("Number of edges set to 19.");
        }

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

        //Setting package size
        int packageSize;
        System.out.print("Set package size(Bytes): ");
        do {
            packageSize = s.nextInt();
            if (packageSize<0) System.out.println("Incorrect value. Try again.");
        } while(packageSize<0);
        n.setPackageSize(packageSize);
    }
}