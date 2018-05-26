package hassan.com.paydemo.Login;

/**
 * Created by Hassan on 5/14/2018.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void onError(String err);

    void navigateToHome();

    void navigateToNewAccount();

    void saveTokenAndID(String token,String id);

    void lockLoginButton();

    void releaseLoginButton();
}
