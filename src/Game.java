import java.util.Arrays;

public class Game {
    private String serialNumber;
    private String title;
    private int publicationYear;
    private String[] platforms;
    private double price;
    private int availabilityCount;
    private int borrowedCount;
    private static int totalGamesCount;

    public Game(String serialNumber, String title, int publicationYear, String[] platforms, double price, int availabilityCount, int borrowedCount) {
        this.serialNumber = serialNumber;
        this.title = title;
        this.publicationYear = publicationYear;
        this.platforms = platforms;
        this.price = price;
        this.availabilityCount = availabilityCount;
        this.borrowedCount = borrowedCount;
        totalGamesCount++;
    }

    // Getters and setters
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String[] platforms) {
        this.platforms = platforms;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailabilityCount() {
        return availabilityCount;
    }

    public void setAvailabilityCount(int availabilityCount) {
        this.availabilityCount = availabilityCount;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    public static int getTotalGamesCount() {
        return totalGamesCount;
    }

    // Borrow a game
    public boolean borrowGame() {
        if (availabilityCount > 0) {
            availabilityCount--;
            borrowedCount++;
            return true; // Successfully borrowed the game
        } else {
            System.out.println("This game is currently not available for borrowing.");
            return false; // Game is not available for borrowing
        }
    }

    // Return a game
    public void returnGame() {
        if (borrowedCount > 0) {
            availabilityCount++;
            borrowedCount--;
            System.out.println("The game has been successfully returned.");
        } else {
            System.out.println("No copies of this game have been borrowed.");
        }
    }

    // Get available copies of the game
    public int getAvailablityCount() {
        return availabilityCount;
    }

    @Override
    public String toString() {
        return "Game{" +
                "serialNumber='" + serialNumber + '\'' +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", platforms=" + Arrays.toString(platforms) +
                ", price=" + price +
                ", availabilityCount=" + availabilityCount +
                ", borrowedCount=" + borrowedCount +
                '}';
    }
}
