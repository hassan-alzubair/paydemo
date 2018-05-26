package hassan.com.paydemo.Settings;

/**
 * Created by Hassan on 5/20/2018.
 */

public interface SettingsPresenter {
    void getNotifingMethods();

    void updateNotifingMethods(String[] methds);

    void updatePin(String oldPin,String pin);
}
