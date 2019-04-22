package data;

import simulator.Direction;

public class Data {

    private String user;
    private Direction direction;

    public Data(String user, Direction direction) {
        this.user = user;
        this.direction = direction;
    }






    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
