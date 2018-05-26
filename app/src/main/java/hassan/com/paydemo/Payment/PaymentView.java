package hassan.com.paydemo.Payment;

/**
 * Created by Hassan on 5/15/2018.
 */

public interface PaymentView {
    void showProgress();

    void hideProgress();

    void onPaymentCompleted(String ref_id);

    void onError(String err);
}
