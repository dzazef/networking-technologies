package simulator;

import data.Direction;
import data.Frame;
import data.JamFrame;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private List<Frame> frameList;

    Field() {
        frameList = new ArrayList<>();
    }

    void addData(Frame frame) {
        frameList.add(frame);
    }

    void removeData(Frame frame) {
        frameList.remove(frame);
    }

    void removeMultipleData(List<Frame> data) {
        frameList.removeAll(data);
    }

    List<Frame> getFrameList() {
        return frameList;
    }

    int getSize() {
        return frameList.size();
    }

    Frame getFirst() {
        return frameList.get(0);
    }

    boolean checkIfEqual() {
        boolean equal = true;
        for (int i = 1; i < getSize() && equal; i++) {
            if (!frameList.get(i - 1).getUser().equals(frameList.get(i).getUser())) {
                equal = false;
            }
        }
        return equal;
    }

    boolean isJammed() {
        boolean jammed = false;
        for (Frame frame : frameList) {
            if (frame instanceof JamFrame) {
                jammed = true;
            }
        }
        return jammed;
    }

    void clear() {
        frameList.clear();
    }

    List<Frame> getAll(Direction direction) {
        List<Frame> result = new ArrayList<>();
        for (Frame frame : frameList) {
            if (frame.getDirection() == direction) result.add(frame);
        }
        return result;
    }

    public void addMultipleData(List<Frame> toAdd) {
        frameList.addAll(toAdd);
    }
}




