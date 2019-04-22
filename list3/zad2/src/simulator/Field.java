package simulator;

import data.Data;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private List<Data> dataList;

    Field() {
        dataList = new ArrayList<>();
    }

    void addData(Data data) {
        dataList.add(data);
    }

    void removeData(Data data) {
        dataList.remove(data);
    }

    void removeMultipleData(List<Data> data) {
        dataList.removeAll(data);
    }

    List<Data> getDataList() {
        return dataList;
    }

    int getSize() {
        return dataList.size();
    }

    Data getFirst() {
        return dataList.get(0);
    }

    boolean checkIfEqual() {
        boolean equal = true;
        for (int i = 1; i < getSize() && equal; i++) {
            if (!dataList.get(i - 1).getUser().equals(dataList.get(i).getUser())) {
                equal = false;
            }
        }
        return equal;
    }

    void checkCollision() {
        if (!checkIfEqual()) {
            for (Data data : dataList) {
                data.setUser("X");
            }
        }
    }

    void clear() {
        dataList.clear();
    }

    List<Data> getAll(Direction direction) {
        List<Data> result = new ArrayList<>();
        for (Data data : dataList) {
            if (data.getDirection() == direction) result.add(data);
        }
        return result;
    }

    public void addMultipleData(List<Data> toAdd) {
        dataList.addAll(toAdd);
    }
}




