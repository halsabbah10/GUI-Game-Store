import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ManageUsersGUI extends JFrame {

    public ManageUsersGUI() {
        initComponents();
        setTitle("Manage Users");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        JButton addUserButton = new JButton("Add User");
        JButton removeUserButton = new JButton("Remove User");

        setLayout(new GridLayout(2, 1));

        addUserButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(ManageUsersGUI.this, "Enter username:");
            if (username != null && !username.isEmpty()) {
                addUser(username);
            }
        });

        removeUserButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(ManageUsersGUI.this, "Enter username to remove:");
            if (username != null && !username.isEmpty()) {
                removeUser(username);
            }
        });

        add(addUserButton);
        add(removeUserButton);
    }

    private void addUser(String username) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("UserInfo.txt", true))) {
            // Append user information to the file
            writer.println(username + ",password,false");
            writer.flush();
            JOptionPane.showMessageDialog(ManageUsersGUI.this, "User added successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ManageUsersGUI.this, "Error adding user.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void removeUser(String username) {
        // Load existing user information
        StringBuilder updatedUsers = new StringBuilder();
        try (Scanner scanner = new Scanner(new FileReader("UserInfo.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] userInfo = line.split(",");
                if (!userInfo[0].equals(username)) {
                    updatedUsers.append(line).append("\n");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ManageUsersGUI.this, "Error removing user.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }

        // Rewrite the user file without the removed user
        try (PrintWriter writer = new PrintWriter(new FileWriter("UserInfo.txt"))) {
            writer.print(updatedUsers);
            JOptionPane.showMessageDialog(ManageUsersGUI.this, "User removed successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ManageUsersGUI.this, "Error removing user.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageUsersGUI().setVisible(true));
    }
}
