package data;

public class UserFactory {
    private static int currentUser = 1;
    public static User getUser() {
        return new User(Integer.toString((currentUser++)));
    }
}
