package hassan.com.paydemo.Main;

import android.content.Context;

/**
 * Created by Hassan on 5/12/2018.
 */

public class MainPresenterImpl implements MainPresenter {


    private Context context;
    private MainView mainView;
    private MainInteractor mainInteractor;

    @Override
    public void logout() {
        mainView.doLogout();
    }

    public MainPresenterImpl(Context context, MainView mainView, MainInteractor mainInteractor) {
        this.context = context;
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void navigateToNotificatoins() {
        mainView.navigateToNotifications();
    }

    @Override
    public void startScanner() {
        mainView.startScanner();
    }

    @Override
    public void navigateToTransactions() {
        mainView.navigateToTransactions();
    }

    @Override
    public void navigateToSettings() {
        mainView.navigateToSettings();
    }


    @Override
    public void navigateToReceive() {
        mainView.navigateToReceive();
    }

    @Override
    public void getMyBalance() {
        mainView.showProgress();
        mainInteractor.getBalance(context, new MainInteractor.OnMainInteractorGetBalanceFinishedListener() {
            @Override
            public void onSuccess(String balance) {
                mainView.hideProgress();
                mainView.setBalance(balance);
            }

            @Override
            public void onError(String err) {
                mainView.hideProgress();
                mainView.showError(err);
            }
        });
    }
}