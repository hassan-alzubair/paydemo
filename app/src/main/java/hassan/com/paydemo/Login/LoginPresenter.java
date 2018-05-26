package hassan.com.paydemo.Login;

/**
 * Created by Hassan on 5/14/2018.
 */

public interface LoginPresenter {
    void showProgress();

    void hideProgress();

    void onError(String err);

    void navigateToHome();

    void navigateToNewAccount();

    void login(String phone, String pin);

    void setLockLoginButton();

    void setReleaseLoginButtno();
}
