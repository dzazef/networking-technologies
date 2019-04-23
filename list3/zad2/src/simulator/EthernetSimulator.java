package simulator;

import data.Data;
import data.User;
import exceptions.IncorrectUserIndexException;
import parameters.Parameters;
import ticker.Ticker;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EthernetSimulator extends Thread{

    private Field[] ethernet;
    private Map<Integer, User> userMap;
    private boolean running = true;
    private final Ticker ticker = new Ticker(Parameters.DELAY_MILLISECONDS);

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

    @Override
    public void run() {
        ticker.start();
        while (running) {
            moveLeft();
            moveRight();
//                insertNewData();
            checkCollision();
            printEthernet();
            try {
                synchronized (ticker) {
                    ticker.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
}
//    @Deprecated
//    private void insertNewData() {
//        for (Map.Entry mapEntry : userMap.entrySet()) {
//            User user = (User) mapEntry.getValue();
//            if (user.dataAvailable()) {
//                int index = (int) mapEntry.getKey();
//                ethernet[index].addData(new Data(user.getID(), Direction.LEFT));
//                ethernet[index].addData(new Data(user.getID(), Direction.RIGHT));
//            }
//        }
//    }