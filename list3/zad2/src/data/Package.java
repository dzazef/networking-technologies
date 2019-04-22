package data;

import simulator.Direction;

public class Package {
    private String user;
    private int size;

    Package(String user, int size) {
        this.size = size;
        this.user = user;
    }

    boolean dataAvailable() {
        if (size>0) {
            size--;
            return true;
        } else {
            return false;
        }
    }

}
