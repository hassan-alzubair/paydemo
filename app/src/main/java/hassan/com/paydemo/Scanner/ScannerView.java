package hassan.com.paydemo.Scanner;

/**
 * Created by Hassan on 5/12/2018.
 */

public interface ScannerView {

    void showProgress();

    void hideProgress();

    void navigateToPayment(String account_id, String account_name);

    void showError(String err);

    void toggleFlash();
}
