import java.util.ArrayList;

class Student extends User {
    private ArrayList<Game> borrowedGames;

    public Student(String name, String ID, String password) {
        super(name, ID, password, false);
        this.borrowedGames = new ArrayList<>();
    }

    public ArrayList<Game> getBorrowedGames() {
        return borrowedGames;
    }

    public void setBorrowedGames(ArrayList<Game> borrowedGames) {
        this.borrowedGames = borrowedGames;
    }

    public void borrowGame(Game game) {
        borrowedGames.add(game);
    }

    public void returnGame(Game game) {
        borrowedGames.remove(game);
    }

    public boolean hasBorrowed(Game game) {
        for (Game borrowedGame : borrowedGames) {
            if (borrowedGame.getSerialNumber().equals(game.getSerialNumber())) {
                return true;
            }
        }
        return false;
    }
}