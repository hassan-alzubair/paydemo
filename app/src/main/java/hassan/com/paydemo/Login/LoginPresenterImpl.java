package hassan.com.paydemo.Login;

/**
 * Created by Hassan on 5/14/2018.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginInteractorFinishedListener {


    private final LoginView loginView;
    private final LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor interactor) {
        this.loginView = loginView;
        this.interactor = interactor;
    }

    @Override
    public void showProgress() {
        loginView.showProgress();
    }

    @Override
    public void hideProgress() {
        loginView.hideProgress();
    }

    @Override
    public void onError(String err) {
        loginView.releaseLoginButton();
        loginView.hideProgress();
        loginView.onError(err);
    }

    @Override
    public void navigateToHome() {
        loginView.hideProgress();
        loginView.navigateToHome();
    }

    @Override
    public void login(String phone, String pin) {
        loginView.lockLoginButton();
        loginView.showProgress();
        interactor.login(phone, pin, this);
    }

    @Override
    public void onLoginSuccess(String token,String id) {
        loginView.hideProgress();
        loginView.releaseLoginButton();
        loginView.saveTokenAndID(token,id);
        loginView.navigateToHome();
    }


    @Override
    public void navigateToNewAccount() {
        loginView.navigateToNewAccount();
    }

    @Override
    public void setLockLoginButton() {
        loginView.lockLoginButton();
    }

    @Override
    public void setReleaseLoginButtno() {
        loginView.releaseLoginButton();
    }
}
