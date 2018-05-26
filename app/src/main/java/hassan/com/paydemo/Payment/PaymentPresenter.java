package hassan.com.paydemo.Payment;

import android.content.Context;

/**
 * Created by Hassan on 5/15/2018.
 */

public interface PaymentPresenter {
    void pay(String toAccount, String amount);
}
