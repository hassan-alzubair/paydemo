package hassan.com.paydemo.Register;

/**
 * Created by Hassan on 5/14/2018.
 */

public class RegisterPresenterImpl implements RegisterPresenter, RegisterInteractor.OnRegisterCompletedListener {


    private RegisterView registerView;
    private final RegisterInteractor interactor;

    public RegisterPresenterImpl(RegisterView registerView, RegisterInteractor interactor) {
        this.registerView = registerView;
        this.interactor = interactor;
    }


    @Override
    public void showProgress() {
        registerView.showProgress();
    }

    @Override
    public void hideProgress() {
        registerView.hideProgress();
    }

    @Override
    public void showRegisterationSuccessfull() {
        registerView.showRegisterSuccessfull();
    }

    @Override
    public void showRegisterationError(String error) {
        registerView.showRegisterError(error);
    }

    @Override
    public void setNavigateToLogin() {
        registerView.navigateToLogin();
    }

    @Override
    public void onDestroy() {
        if (registerView != null)
            registerView = null;
    }

    @Override
    public void register(String accountName,
                         String accountEmail,
                         String accountPhone,
                         String accountPin) {
        registerView.showProgress();
        registerView.lockSubmitButton();
        interactor.register(accountName, accountEmail, accountPhone, accountPin, this);
    }

    @Override
    public void onRegisterCompleted() {
        registerView.hideProgress();
        registerView.releaseSubmitButton();
        registerView.showRegisterSuccessfull();
    }

    @Override
    public void onError(String err) {
        registerView.hideProgress();
        registerView.releaseSubmitButton();
        registerView.showRegisterError(err);
    }
}