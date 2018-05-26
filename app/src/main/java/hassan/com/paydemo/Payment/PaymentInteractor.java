package hassan.com.paydemo.Payment;

import android.content.Context;

/**
 * Created by Hassan on 5/15/2018.
 */

public interface PaymentInteractor {
    public interface OnPaymentFinishedListener {
        void onPaymentSuccess(String ref_id);

        void onPaymentError(String err);
    }

    public void pay(Context context, String toAccount, String amount, OnPaymentFinishedListener listener);
}
