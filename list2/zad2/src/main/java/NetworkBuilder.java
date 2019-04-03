import org.jgraph.graph.DefaultEdge;

import java.util.Scanner;

class NetworkBuilder {

    private Network n;
    private Scanner s;
    private int edges = 0;
    private int vertexes;

    NetworkBuilder() {
        this.s = new Scanner(System.in);
    }

    private void setSize() {
        System.out.print("Set number of users: ");
        this.vertexes = s.nextInt();
    }

    private void addEdge(int num) {
        int v1, v2;
        System.out.println("Edge no "+num);
        do {
            System.out.print("Vertex 1: ");
            v1 = s.nextInt();
            if (v1>vertexes || v1<=0) System.out.println("Incorrect value. Try again.");
        } while(v1>vertexes || v1<=0);
        do {
            System.out.print("Vertex 2: ");
            v2 = s.nextInt();
            if (v2>vertexes || v2<=0 || v1==v2) System.out.println("Incorrect value. Try again.");
        } while(v2>vertexes || v2<=0 || v1==v2);
        n.addEdge(v1, v2);
    }

    private void setEdges() {
        //Setting edges
        System.out.println("Set connections between users: ");
        System.out.print("Do you want to begin with basic ring topology?(yes/no): ");
        String answer = s.next();
        if (answer.equals("no") || answer.equals("n")) {
            System.out.print("How many edges you want to set: ");
            this.edges = s.nextInt();
            for (int i=0; i<edges; i++) {
                addEdge(i+1);
            }
        }
        else {
            for (int i=1; i<vertexes; i++) {
                n.addEdge(i, i+1);
            }
            n.addEdge(1, vertexes);
            edges += vertexes;
            System.out.println("Do you want to add additional edges?(yes/no): ");
            answer = s.next();
            if (answer.equals("yes") || answer.equals("y")) {
                System.out.print("How many edges you want to add: ");
                int addedges = s.nextInt();
                edges += addedges;
                for (int i=vertexes; i<edges; i++) {
                    addEdge(i+1);
                }
            }
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
        System.out.print("Do you want to set same number of packages for all users?(yes/no): ");
        String answer = s.next();
        if (answer.equals("no") || answer.equals("n")) {
            for (int v1=1;v1<=vertexes;v1++) {
                for (int v2=1;v2<=vertexes;v2++) {
                    if(v1!=v2) {
                        System.out.print("From user " + v1 + " to user " + v2 + ": ");
                        value = s.nextInt();
                        n.setFlow(v1, v2, value);
                    }
                }
            }
        } else {
            System.out.print("Set number of packages: ");
            value = s.nextInt();
            for (int v1=1;v1<=vertexes;v1++) {
                for (int v2=1;v2<=vertexes;v2++) {
                    if(v1!=v2) n.setFlow(v1, v2, value);
                }
            }
        }
    }

    private void setCapacity() {
        int v1, v2, capacity;
        System.out.println("Setting capacity(BYTES): ");
        System.out.print("Do you want to set same capacity for all edges?(yes/no): ");
        String answer = s.next();
        if (answer.equals("no") || answer.equals("n")) {
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
        } else {
            System.out.print("Capacity: ");
            double c = s.nextDouble();
            for (DefaultEdge d : n.getEdgeSet()) {
                 n.setCapacity(d, c);
            }
        }
    }

    private void setTMax() {
        System.out.print("Set maximum delay: ");
        n.setTmax(s.nextDouble());
    }

    private void setP() {
        System.out.print("Set probability of not being damaged for edges: ");
        n.setP(s.nextDouble());
    }

    Network buildTestNetwork() {
        this.n = buildNetwork();
        setP();
        setTMax();
        return n;
    }

    Network buildNetwork() {
        System.out.print("Do you want to use default network?(yes/no): ");
        String answer;
        answer = s.next();
        if (answer.equals("yes") || answer.equals("y")) {
            return buildDefaultNetwork();
        }
        setSize();
        this.n = new Network((vertexes));
        boolean connected;
        while(true) {
            setEdges();
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
        return this.n;
    }

    private Network buildDefaultNetwork() {
        this.n = new Network(10);
        for (int i=1; i<10; i++) {
            n.addEdge(i, i+1);
        }
        n.addEdge(1, 10);
        n.addEdge(1, 6);
        n.addEdge(2, 7);
        n.addEdge(3, 8);
        n.addEdge(4, 9);
        n.addEdge(5, 10);
        for (DefaultEdge edge : n.getEdgeSet()) {
            n.setCapacity(edge, 200000);
        }
        for (int i=1; i<=10; i++) {
            for (int j=1; j<=10; j++) {
                n.setFlow(i, j, 1000);
            }
        }
        n.setFlow(1, 2, 5999);
        n.setFlow(2, 3, 5999);
        n.setFlow(3, 4, 6999);
        n.setFlow(4, 5, 7999);
        n.setFlow(5, 6, 7999);
        n.setPackageSize(10);
        return n;
    }
}
