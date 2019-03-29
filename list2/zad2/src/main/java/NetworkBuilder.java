import org.jgraph.graph.DefaultEdge;

import java.util.Scanner;

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

    NetworkManager buildDefaultNetwork() {
        NetworkManager nm = new NetworkManager();
        nm.addEdge(1, 10);
        nm.addEdge(5, 10);
        nm.addEdge(2, 7);
        for (int i=1; i<10; i++) {
            nm.addEdge(i, i+1);
            nm.setCapacity(i, i+1, 20);
        }
        nm.setFlow(1, 7, 10);
        nm.setFlow(1, 6, 10);
        nm.setPackageSize(255);
        return nm;
    }
}
