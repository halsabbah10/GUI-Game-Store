import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameStoreGUI extends JFrame {

    private JList<String> gameList;
    private JLabel messageLabel;

    private User currentUser;
    private ArrayList<Game> games;

    public GameStoreGUI() {
        initComponents();
        setResizable(false);
        loadGameList();
    }

    public GameStoreGUI(User user) {
        currentUser = user;
        initComponents();
        setResizable(false);
        loadGameList();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel();
        gameList = new JList<>();
        JButton borrowButton = new JButton();
        JButton returnButton = new JButton();
        JButton manageUsersButton = new JButton();
        JButton addGameButton = new JButton();
        JButton removeGameButton = new JButton();
        messageLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setText("Game Store");

        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        borrowButton.setText("Borrow");
        borrowButton.addActionListener(this::borrowButtonActionPerformed);

        returnButton.setText("Return");
        returnButton.addActionListener(this::returnButtonActionPerformed);

        manageUsersButton.setText("Manage Users");
        manageUsersButton.addActionListener(this::manageUsersButtonActionPerformed);

        addGameButton.setText("Add Game");
        addGameButton.addActionListener(this::addGameButtonActionPerformed);

        removeGameButton.setText("Remove Game");
        removeGameButton.addActionListener(this::removeGameButtonActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(titleLabel)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(gameList, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(borrowButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(returnButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(manageUsersButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(addGameButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(removeGameButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(gameList, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(borrowButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(returnButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(manageUsersButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(addGameButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(removeGameButton)))
                                .addGap(18, 18, 18)
                                .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void loadGameList() {
        try {
            Scanner input = new Scanner(new FileReader("GamesInfo.txt"));
            games = new ArrayList<>();
            DefaultListModel<String> model = new DefaultListModel<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] gameInfo = line.split(",");
                Game game = new Game(gameInfo[0], gameInfo[1], Integer.parseInt(gameInfo[2]), gameInfo[3].split(":"), Double.parseDouble(gameInfo[4]), Integer.parseInt(gameInfo[5]), Integer.parseInt(gameInfo[6]));
                games.add(game);
                model.addElement(game.getTitle());
            }
            gameList.setModel(model);
            input.close();
        } catch (FileNotFoundException ex) {
            messageLabel.setText("Error: GamesInfo.txt not found.");
            ex.printStackTrace();
        }
    }

    private void borrowButtonActionPerformed(ActionEvent evt) {
        int selectedIndex = gameList.getSelectedIndex();
        if (selectedIndex != -1) {
            Game selectedGame = games.get(selectedIndex);
            if (selectedGame.getAvailabilityCount() > 0) {
                if (currentUser instanceof Student) {
                    if (((Student) currentUser).getBorrowedGames().size() >= 3) {
                        messageLabel.setText("You have already borrowed 3 games. Return some before borrowing more.");
                        return;
                    }
                    if (((Student) currentUser).hasBorrowed(selectedGame)) {
                        messageLabel.setText("You have already borrowed a copy of this game.");
                        return;
                    }
                }
                selectedGame.borrowGame();
                // Update borrowedInfo.txt
                try (PrintWriter writer = new PrintWriter(new FileWriter("borrowedInfo.txt", true))) {
                    writer.println(selectedGame.getSerialNumber() + "," + currentUser.getID());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                messageLabel.setText("You borrowed: " + selectedGame.getTitle());
            } else {
                messageLabel.setText("No copies available to borrow.");
            }
        } else {
            messageLabel.setText("Please select a game to borrow.");
        }
    }

    private void returnButtonActionPerformed(ActionEvent evt) {
        int selectedIndex = gameList.getSelectedIndex();
        if (selectedIndex != -1) {
            Game selectedGame = games.get(selectedIndex);
            if (selectedGame.getBorrowedCount() > 0) {
                selectedGame.returnGame();
                messageLabel.setText("You returned: " + selectedGame.getTitle());
            } else {
                messageLabel.setText("You have no copies of this game to return.");
            }
        } else {
            messageLabel.setText("Please select a game to return.");
        }
    }

    private void manageUsersButtonActionPerformed(ActionEvent evt) {
        if (currentUser instanceof Admin) {
            // Open the Manage Users window
            new ManageUsersGUI().setVisible(true);
            this.dispose();
        } else {
            messageLabel.setText("Only administrators can manage users.");
        }
    }

    private void addGameButtonActionPerformed(ActionEvent evt) {
        if (currentUser instanceof Admin) {
            // Implement the functionality to add a new game
            String title = JOptionPane.showInputDialog(this, "Enter game title:");
            String genre = JOptionPane.showInputDialog(this, "Enter game genre:");
            int availabilityCount = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter availability count:"));
            String[] platforms = JOptionPane.showInputDialog(this, "Enter game platforms (separated by comma):").split(",");
            double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter game price:"));
            int totalCopies = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter total copies:"));

            Game newGame = new Game(title, genre, availabilityCount, platforms, price, totalCopies, 0);
            addGame(newGame);
            messageLabel.setText("Game added successfully.");
        } else {
            messageLabel.setText("Only administrators can add games.");
        }
    }

    private void removeGameButtonActionPerformed(ActionEvent evt) {
        if (currentUser instanceof Admin) {
            int selectedIndex = gameList.getSelectedIndex();
            if (selectedIndex != -1) {
                removeGame(selectedIndex);
                messageLabel.setText("Game removed successfully.");
            } else {
                messageLabel.setText("Please select a game to remove.");
            }
        } else {
            messageLabel.setText("Only administrators can remove games.");
        }
    }

    private void addGame(Game newGame) {
        games.add(newGame);
        DefaultListModel<String> model = (DefaultListModel<String>) gameList.getModel();
        model.addElement(newGame.getTitle());
    }

    private void removeGame(int index) {
        games.remove(index);
        DefaultListModel<String> model = (DefaultListModel<String>) gameList.getModel();
        model.remove(index);
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);

            loginGUI.addLoginListener(new LoginListener() {
                @Override
                public void onLoginSuccess(User user) {
                    // Once login is successful, open the GameStoreGUI window
                    new GameStoreGUI(user).setVisible(true);
                }

                @Override
                public void onLoginFailure() {
                    // Handle login failure if needed
                }
            });
        });
    }
}
