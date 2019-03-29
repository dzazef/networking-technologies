import org.jgraph.graph.DefaultEdge;

import java.util.Scanner;

class NetworkBuilder {

    private Network n;
    private Scanner s;
    private int edges;

    NetworkBuilder() {
        this.n = new Network(10);
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
        int value;
        System.out.println("Setting numbers of packages sent between users: ");
        for (int v1=1;v1<=10;v1++) {
            for (int v2=1;v2<=10;v2++) {
                System.out.print("From user "+v1+" to user "+v2+": ");
                value = s.nextInt();
                n.setFlow(v1, v2, value);
            }
        }
    }

    private void setCapacity() {
        int v1, v2, capacity;
        System.out.println("Setting capacity: ");
        for (DefaultEdge d : n.getEdgeSet()) {
            v1 = n.getGraph().getEdgeSource(d);
            v2 = n.getGraph().getEdgeTarget(d);
            System.out.print("On edge "+v1+" - "+v2+": ");
            do {
                capacity = s.nextInt();
                if (capacity<0) System.out.println("Incorrect value. Try again.");
            } while (capacity<0);
            n.setCapacity(v1, v2, capacity);
        }

    }

    Network buildNetwork() {
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
        setCapacity();
        setFlow();
        n.countCompleteFlow();
        n.countAverageDelay();
        return this.n;
    }

    Network buildDefaultNetwork() {
        n.addEdge(1, 10);
        n.setCapacity(1, 10, 20000);
        for (int i=1; i<10; i++) {
            n.addEdge(i, i+1);
            n.setCapacity(i, i+1, 20000);
        }
        n.setFlow(1, 10, 1000);
        n.setFlow(1, 2, 1000);
        n.setPackageSize(20);
        n.countCompleteFlow();
        n.countAverageDelay();
        return n;
    }
}
