package hassan.com.paydemo.Register;

/**
 * Created by Hassan on 5/14/2018.
 */

public interface RegisterView {
    void navigateToLogin();

    void showRegisterSuccessfull();

    void showProgress();

    void hideProgress();

    void lockSubmitButton();

    void releaseSubmitButton();

    void showRegisterError(String error);
}
