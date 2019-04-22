package simulator;

import data.Data;
import data.User;
import exceptions.IncorrectUserIndexException;
import parameters.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EthernetSimulator {

    private Field[] ethernet;
    private Map<Integer, User> userMap;
    private boolean running = true;

    public EthernetSimulator(int size) {
        ethernet = new Field[size];
        userMap = new HashMap<>();
        for (int i=0; i<ethernet.length; i++) {
            ethernet[i] = new Field();
        }
    }

    public void addUser(int index, User user) throws IncorrectUserIndexException {
        if (index<0 || index>=ethernet.length) throw new IncorrectUserIndexException("Incorrect index "+index+" for length "+ethernet.length);
        userMap.put(index, user);
    }

    private void insertNewData() {
        for (Map.Entry mapEntry : userMap.entrySet()) {
            User user = (User) mapEntry.getValue();
            if (user.dataAvailable()) {
                int index = (int) mapEntry.getKey();
                ethernet[index].addData(new Data(user.getID(), Direction.LEFT));
                ethernet[index].addData(new Data(user.getID(), Direction.RIGHT));
            }
        }
    }

    private void printEthernet() {
        for (Field field : ethernet) {
            switch (field.getSize()) {
                case 0: {
                    System.out.print("0");
                    break;
                }
                default:  {
                    System.out.print(field.getFirst().getUser());
                    break;
                }
            }
        }
        System.out.println();
    }

    private void moveLeft() {
        ethernet[0].clear();
        for (int i = 1; i<ethernet.length; i++) {
            List<Data> toMove = ethernet[i].getAll(Direction.LEFT);
            ethernet[i-1].addMultipleData(toMove);
            ethernet[i].removeMultipleData(toMove);
        }
    }

    private void moveRight() {
        ethernet[ethernet.length-1].clear();
        for (int i = ethernet.length-2; i>=0; i--) {
            List<Data> toMove = ethernet[i].getAll(Direction.RIGHT);
            ethernet[i+1].addMultipleData(toMove);
            ethernet[i].removeMultipleData(toMove);
        }
    }

    private void checkCollision() {
        for (int i = 0; i<ethernet.length; i++) {
            if (ethernet[i].getSize() > 1) {
                ethernet[i].checkCollision();
            }
        }
    }

    public void createEthernet() {
        new Thread(() -> {
            while(running) {
                moveLeft();
                moveRight();
                insertNewData();
                checkCollision();
                printEthernet();
                try { Thread.sleep(Parameters.DELAY_MILLISECONDS); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();
    }
}


//    private void moveLeft() {
//        //MOVE LEFT PACKAGES HEADING LEFT
//        ethernet[0] = null;
//        for (int i=1; i<ethernet.length; i++) {
//            if (ethernet[i]!=null) {
//                if (ethernet[i].getDirection() == Direction.LEFT) {
//                    ethernet[i-1] = ethernet[i];
//                    ethernet[i] = null;
//                }
//            }
//        }
//    }
//
//    private void moveRight() {
//        //MOVE RIGHT PACKAGES HEADING RIGHT
//        ethernet[ethernet.length-1] = null;
//        for (int i=ethernet.length-2; i>=0; i--) {
//            if (ethernet[i]!=null) {
//                if (ethernet[i].getDirection() == Direction.RIGHT) {
//                    ethernet[i+1] = ethernet[i];
//                    ethernet[i] = null;
//                }
//            }
//        }
//    }
//
//    private void moveNewData() {
//        //MOVE NEW PACKAGES BOTH LEFT AND RIGHT
//        for (int i=1; i<ethernet.length; i++) {
//            if (ethernet[i]!=null) {
//                if (ethernet[i].getDirection() == Direction.BOTH) {
//                    ethernet[i-1] = new Data(ethernet[i].getUser(), Direction.LEFT);
//                    ethernet[i+1] = new Data(ethernet[i].getUser(), Direction.RIGHT);
//                    ethernet[i] = null;
//                }
//            }
//        }
//    }
//
//    @Deprecated
//    private void moveData() {
//        for (int i = 1; i<ethernet.length-1; i++) {
//            List<Data> dataList = ethernet[i].getDataList();
//            List<Data> toDelete = new ArrayList<>();
//            for (int k = 0; k < dataList.size(); k++) {
//                Data data = dataList.get(k);
//                if (data.getDirection() == Direction.LEFT) {
//                    System.err.println("MOVE_LEFT");
////                        if (i != 0)
//                    ethernet[i - 1].addData(data);
//                    toDelete.add(data);
//                } else if (data.getDirection() == Direction.RIGHT) {
//                    System.err.println("MOVE_RIGHT");
////                        if (i != ethernet.length-1)
//                    ethernet[i+1].addData(data);
//                    toDelete.add(data);
//                }
//            }
//            dataList.removeAll(toDelete);
//        }
//    }