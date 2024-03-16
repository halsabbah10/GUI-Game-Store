public class User {
    private String name;
    private String ID;
    private String password;
    private boolean isAdmin;

    public User(String name, String ID, String password, boolean isAdmin) {
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}