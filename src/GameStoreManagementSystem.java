import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameStoreManagementSystem {
    private final ArrayList<Game> games;
    private final ArrayList<User> users;
    private static final Logger logger = Logger.getLogger(GameStoreManagementSystem.class.getName());

    public GameStoreManagementSystem() {
        this.games = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    // Methods for game management
    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public Game findGameBySerialNumber(String serialNumber) {
        for (Game game : games) {
            if (game.getSerialNumber().equals(serialNumber)) {
                return game;
            }
        }
        return null; // Game not found
    }

    public ArrayList<Game> searchGames(String keyword) {
        ArrayList<Game> matchedGames = new ArrayList<>();
        for (Game game : games) {
            if (game.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    Arrays.stream(game.getPlatforms()).anyMatch(platform -> platform.toLowerCase().contains(keyword.toLowerCase()))) {
                matchedGames.add(game);
            }
        }
        return matchedGames;
    }

    // Methods for user management
    public void addUser(User user) {
        users.add(user);
    }

    public User findUserByID(String ID) {
        for (User user : users) {
            if (user.getID().equals(ID)) {
                return user;
            }
        }
        return null; // User not found
    }

    // File operations
    public void saveGamesToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Game game : games) {
                writer.println(game.getSerialNumber() + "," + game.getTitle() + "," + game.getPublicationYear() +
                        "," + String.join(":", game.getPlatforms()) + "," + game.getPrice() + "," +
                        game.getAvailabilityCount() + "," + game.getBorrowedCount());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "We're sorry, something went wrong while processing your request. Please try again later. If the problem persists, please contact support and provide the following error details: " + e.getMessage(), e);
        }
    }

    public void loadGamesFromFile(String filename) {
        games.clear(); // Clear existing games
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Game game = new Game(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3].split(":"), Double.parseDouble(parts[4]),
                        Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
                games.add(game);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "We're sorry, something went wrong while processing your request. Please try again later. If the problem persists, please contact support and provide the following error details: " + e.getMessage(), e);
        }
    }

    public void borrowGame(Game game, String userId) {
        try {
            Scanner scanner = new Scanner(new File("gamesInfo.txt"));
            ArrayList<String> lines = new ArrayList<>();
            boolean gameExists = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(game.getSerialNumber())) {
                    gameExists = true;
                    int availableCopies = Integer.parseInt(parts[5]);
                    int borrowedCopies = Integer.parseInt(parts[6]);
                    if (availableCopies > 0 && borrowedCopies < 3) {
                        // Update available and borrowed counts
                        availableCopies--;
                        borrowedCopies++;
                        parts[5] = String.valueOf(availableCopies);
                        parts[6] = String.valueOf(borrowedCopies);
                        line = String.join(",", parts);
                        lines.add(line);
                        // Add borrowing record to borrowedInfo.txt
                        BufferedWriter writer = new BufferedWriter(new FileWriter("borrowedInfo.txt", true));
                        writer.write(userId + "," + game.getSerialNumber() + "\n");
                        writer.close();
                        System.out.println("Game borrowed successfully.");
                    } else {
                        System.out.println("Cannot borrow the game. Either all copies are borrowed or user has borrowed maximum games.");
                    }
                }
                lines.add(line);
            }
            scanner.close();
            if (!gameExists) {
                System.out.println("Game not found.");
            } else {
                // Write updated game information back to gamesInfo.txt
                BufferedWriter writer = new BufferedWriter(new FileWriter("gamesInfo.txt"));
                for (String updatedLine : lines) {
                    writer.write(updatedLine + "\n");
                }
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());
        }
    }

    public void returnGame(Game game) {
        if (game != null && game.getBorrowedCount() > 0) {
            // Logic to return the game
            game.setAvailabilityCount(game.getAvailabilityCount() + 1);
            game.setBorrowedCount(game.getBorrowedCount() - 1);
            System.out.println("Game '" + game.getTitle() + "' returned successfully.");
        } else {
            assert game != null;
            System.out.println("Game '" + game.getTitle() + "' cannot be returned.");
        }
    }

    // Other methods for game management, borrowing, returning, etc. can be added as needed
}
