package data;

public class Frame {

    private String user;
    private Direction direction;

    public Frame(String user, Direction direction) {
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
