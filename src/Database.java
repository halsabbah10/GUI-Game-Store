import java.util.ArrayList;

public class Database {
    private static User currentUser;
    private static final ArrayList<Game> everyGame = new ArrayList<>();

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public Database(ArrayList<User> users) {
        for (User user : users) {
            if (user.isAdmin()) {
                currentUser = user;
                break; // Assign the first admin user as current user
            }
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void addGame(Game game) {
        everyGame.add(game);
    }

    public static ArrayList<Game> getAllGames() {
        return everyGame;
    }
}
