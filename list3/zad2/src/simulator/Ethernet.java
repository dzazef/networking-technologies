package simulator;

import data.Direction;
import data.Frame;
import data.JamFrame;
import ticker.ThreadChain;

import java.util.List;

public class Ethernet extends ThreadChain {
    private Field[] ethernet;

    public Ethernet(Thread ticker, int size) {
        super(ticker);
        ethernet = new Field[size];
        for (int i=0; i<ethernet.length; i++) {
            ethernet[i] = new Field();
        }
    }

    private void moveLeft() {
        ethernet[0].clear();
        for (int i = 1; i<ethernet.length; i++) {
            List<Frame> toMove = ethernet[i].getAll(Direction.LEFT);
            ethernet[i-1].addMultipleData(toMove);
            ethernet[i].removeMultipleData(toMove);
        }
    }


    private void moveRight() {
        ethernet[ethernet.length-1].clear();
        for (int i = ethernet.length-2; i>=0; i--) {
            List<Frame> toMove = ethernet[i].getAll(Direction.RIGHT);
            ethernet[i+1].addMultipleData(toMove);
            ethernet[i].removeMultipleData(toMove);
        }
    }


    private void printEthernet() {
        for (Field field : ethernet) {
            if (field.getSize() == 0) {
                System.out.print("0");
            } else if (field.getSize() == 1) {
                System.out.print(field.getFirst().getUser());
            } else {
                if (field.isJammed()) {
                    System.out.print("X");
                } else {
                    if (field.checkIfEqual()) {
                        System.out.print("✚");
                    } else {
                        System.out.print("█");
                    }
                }
            }
        }
        System.out.println();
    }

    @Override
    public void task() {
        printEthernet();
        moveLeft();
        moveRight();
    }

    //UTILS
    public Field getField(int idx) {
        return ethernet[idx];
    }

    public int getFieldSize(int idx) {
        return ethernet[idx].getSize();
    }

    public int getSize() {
        return ethernet.length;
    }

    public void insertNewFrames(int idx, String username) {
        ethernet[idx].addData(new Frame(username, Direction.LEFT));
        ethernet[idx].addData(new Frame(username, Direction.RIGHT));
    }

    public void insertNewJamFrames(int idx, String username) {
        ethernet[idx].addData(new JamFrame(username, Direction.LEFT));
        ethernet[idx].addData(new JamFrame(username, Direction.RIGHT));
    }
}
