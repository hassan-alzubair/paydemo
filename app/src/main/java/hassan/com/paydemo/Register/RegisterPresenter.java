package hassan.com.paydemo.Register;

/**
 * Created by Hassan on 5/14/2018.
 */

public interface RegisterPresenter {
    void showProgress();

    void hideProgress();

    void showRegisterationSuccessfull();

    void showRegisterationError(String error);

    void setNavigateToLogin();

    void onDestroy();

    void register(String accountName,
                  String accountEmail,
                  String accountPhone,
                  String accountPin);
}
