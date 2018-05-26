package hassan.com.paydemo.Settings;

/**
 * Created by Hassan on 5/20/2018.
 */

public interface SettingsView {
    void setNotifingMethods(String[] methods);
    void showProgress();
    void hideProgress();
    void showError(String err);
}
