import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameStoreGUITest extends JFrame {

    private JList<Game> gameList;
    private JTextArea gameDetailsTextArea;
    private JLabel messageLabel;

    private User currentUser;
    private ArrayList<Game> games;

    public GameStoreGUITest() {
        initComponents();
        setResizable(false);
        loadGameList();
    }

    public GameStoreGUITest(User user) {
        currentUser = user;
        initComponents();
        setResizable(false);
        loadGameList();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel();
        gameList = new JList<>();
        gameDetailsTextArea = new JTextArea();
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
        gameList.addListSelectionListener(e -> displaySelectedGameDetails());

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
                                                .addComponent(gameDetailsTextArea, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(borrowButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(returnButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(manageUsersButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(addGameButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(removeGameButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(gameList, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(gameDetailsTextArea, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(borrowButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(returnButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(manageUsersButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addGameButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeGameButton)
                                .addGap(18, 18, 18)
                                .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void displaySelectedGameDetails() {
        Game selectedGame = gameList.getSelectedValue();
        if (selectedGame != null) {
            gameDetailsTextArea.setText(selectedGame.toString());
        }
    }

    private void borrowButtonActionPerformed(ActionEvent evt) {
        Game selectedGame = gameList.getSelectedValue();
        if (selectedGame != null) {
            // Implement borrowing logic here
            messageLabel.setText("Borrowed: " + selectedGame.getTitle());
        } else {
            messageLabel.setText("No game selected!");
        }
    }

    private void returnButtonActionPerformed(ActionEvent evt) {
        Game selectedGame = gameList.getSelectedValue();
        if (selectedGame != null) {
            // Implement returning logic here
            messageLabel.setText("Returned: " + selectedGame.getTitle());
        } else {
            messageLabel.setText("No game selected!");
        }
    }

    private void manageUsersButtonActionPerformed(ActionEvent evt) {
        // Implement managing users logic here
        messageLabel.setText("Managing Users...");
    }

    private void addGameButtonActionPerformed(ActionEvent evt) {
        // Implement adding game logic here
        messageLabel.setText("Adding Game...");
    }

    private void removeGameButtonActionPerformed(ActionEvent evt) {
        // Implement removing game logic here
        messageLabel.setText("Removing Game...");
    }

    private void loadGameList() {
        // Load game list from file or database
        // For demonstration, using a temporary list
        games = new ArrayList<>();
        games.add(new Game("123", "Game 1", 2022, new String[]{"PC", "PS5"}, 59.99, 10, 2));
        games.add(new Game("456", "Game 2", 2023, new String[]{"Xbox Series X", "PS5"}, 49.99, 5, 1));
        games.add(new Game("789", "Game 3", 2021, new String[]{"Nintendo Switch"}, 39.99, 15, 3));
        gameList.setListData(games.toArray(new Game[0]));
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new GameStoreGUI().setVisible(true));
    }
}
