//package data;
//
//import data.Direction;
//import simulator.EthernetSimulator;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Deprecated
//public class User extends Thread {
//    private String ID;
//    private List<FramePackage> framePackageList;
//    private int index;
//    private EthernetSimulator ethernet;
//
//    User(String id) {
//        this.ID = id;
//        framePackageList = new ArrayList<>();
//    }
//
//    public String getID() {
//        return ID;
//    }
//    void setID(String ID) {
//        this.ID = ID;
//    }
//
//    Frame getNewData(Direction direction) {
//        return new Frame(getID(), direction);
//    }
//
//    @Override
//    public void run() {
//
//    }
//
//    @Deprecated
//    public void createPackage(int size) {
//        framePackageList.add(new FramePackage(ID, size));
//    }
//
//    @Deprecated
//    public boolean dataAvailable() {
//        if (framePackageList.size() > 0) {
//            boolean dataAvailable = framePackageList.get(0).dataAvailable();
//            if (!dataAvailable) {
//                framePackageList.remove(0);
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }
//}
