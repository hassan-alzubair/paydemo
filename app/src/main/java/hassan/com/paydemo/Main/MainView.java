package hassan.com.paydemo.Main;

/**
 * Created by Hassan on 5/12/2018.
 */

public interface MainView {
    void startScanner();
    void navigateToNotifications();
    void navigateToTransactions();
    void navigateToSettings();
    void navigateToReceive();
    void doLogout();

    void showProgress();
    void hideProgress();
    void setBalance(String balance);
    void showError(String err);
}
