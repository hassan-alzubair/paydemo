package hassan.com.paydemo.Scanner;

import android.content.Context;
import android.util.Log;

/**
 * Created by Hassan on 5/12/2018.
 */

public class ScannerPresenterImpl implements ScannerPresenter {

    private Context context;
    private final ScannerView scannerView;
    private ScannerInteractor interactor;

    public ScannerPresenterImpl(Context context, ScannerView scannerView, ScannerInteractor interactor) {
        this.context = context;
        this.scannerView = scannerView;
        this.interactor = interactor;
    }

    @Override
    public void checkForAccount(String account_id) {
        scannerView.showProgress();
        interactor.checkForAccount(context, account_id, new ScannerInteractor.OnAccountCheckFinishedListener() {
            @Override
            public void onChecked(String account_id, String account_name) {
                scannerView.hideProgress();
                scannerView.navigateToPayment(account_id, account_name);
            }

            @Override
            public void onError(String error) {
                Log.e("onError: ", error);
                scannerView.hideProgress();
                scannerView.showError(error);
            }
        });
    }

    @Override
    public void toggleFlash() {
        scannerView.toggleFlash();
    }
}
