package data;

import simulator.Direction;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String ID;
    private List<Package> packageList;

    User(String id) {
        //TODO: User as thread?
        this.ID = id;
        packageList = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }
    void setID(String ID) {
        this.ID = ID;
    }

    Data getNewData(Direction direction) {
        return new Data(getID(), direction);
    }

    public void createPackage(int size) {
        packageList.add(new Package(ID, size));
    }

    public boolean dataAvailable() {
        if (packageList.size() > 0) {
            boolean dataAvailable = packageList.get(0).dataAvailable();
            if (!dataAvailable) {
                packageList.remove(0);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
