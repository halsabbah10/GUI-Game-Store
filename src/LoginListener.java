public interface LoginListener {
    void onLoginSuccess(User user);
    void onLoginFailure();
}