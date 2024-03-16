class Admin extends User {
    public Admin(String name, String ID, String password) {
        super(name, ID, password, true);
    }
}