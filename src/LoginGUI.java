import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class LoginGUI extends JFrame {

    private GameStoreGUI gameStoreGUI;
    private JTextField Idinput;
    private JPasswordField passwordinput;
    private JLabel message;

    public LoginGUI() {
        initComponents();
        setResizable(false);
    }

    public LoginGUI(GameStoreGUI gameStoreGUI) {
        this.gameStoreGUI = gameStoreGUI; // Initialize the reference to GameStoreGUI
        initComponents();
        setResizable(false);
    }

    private void setCurrentUser(User user) {
        gameStoreGUI.setCurrentUser(user);
    }

    private void initComponents() {
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        Idinput = new JTextField();
        passwordinput = new JPasswordField();
        JLabel jLabel3 = new JLabel();
        JButton login = new JButton();
        message = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("User Id:");

        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel2.setText("Password:");

        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        jLabel3.setText("Log in:");

        login.setBackground(new Color(204, 204, 255));
        login.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        login.setText("Log in");
        login.addActionListener(this::loginActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(login)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup()
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2))
                                                .addGap(36, 36, 36)
                                                .addGroup(layout.createParallelGroup()
                                                        .addComponent(Idinput, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(passwordinput, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(56, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(message, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(Idinput, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(passwordinput, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                        .addGap(26, 26, 26)
                        .addComponent(login)
                        .addGap(18, 18, 18)
                        .addComponent(message, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE)
        );
        pack();
    }

    private void loginActionPerformed(ActionEvent evt) {
        String username = Idinput.getText();
        String password = new String(passwordinput.getPassword());

        try {
            Scanner input = new Scanner(new FileReader("UserInfo.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] userInfo = line.split(",");
                if (userInfo[1].equals(username) && userInfo[2].equals(password)) {
                    User user;
                    if (Boolean.parseBoolean(userInfo[3])) {
                        user = new Admin(userInfo[0], userInfo[1], userInfo[2]);
                    } else {
                        user = new Student(userInfo[0], userInfo[1], userInfo[2]);
                    }

                    // Open GameStoreGUI with the authenticated user
                    GameStoreGUI gameStoreGUI = new GameStoreGUI(user);
                    gameStoreGUI.setVisible(true);
                    dispose(); // Close the LoginGUI window
                    return;
                }
            }
            message.setText("Invalid username or password!");
        } catch (FileNotFoundException ex) {
            System.err.println("Error: UserInfo.txt not found.");
            ex.printStackTrace();
        }
    }



    private LoginListener loginListener;

    public void addLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    // Method to notify listeners about successful login
    private void notifyLoginSuccess(User user) {
        if (loginListener != null) {
            loginListener.onLoginSuccess(user);
        }
    }

    // Method to notify listeners about failed login
    private void notifyLoginFailure() {
        if (loginListener != null) {
            loginListener.onLoginFailure();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
