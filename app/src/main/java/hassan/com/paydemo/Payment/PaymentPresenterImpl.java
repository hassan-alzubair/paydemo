package hassan.com.paydemo.Payment;

import android.content.Context;

/**
 * Created by Hassan on 5/15/2018.
 */

public class PaymentPresenterImpl implements PaymentPresenter {

    private Context context;
    private final PaymentView paymentView;
    private final PaymentInteractor interactor;

    public PaymentPresenterImpl(Context context, PaymentView paymentView, PaymentInteractor interactor) {
        this.context = context;
        this.paymentView = paymentView;
        this.interactor = interactor;
    }

    @Override
    public void pay(String toAccount, String amount) {
        paymentView.showProgress();
        interactor.pay(context, toAccount, amount, new PaymentInteractor.OnPaymentFinishedListener() {
            @Override
            public void onPaymentSuccess(String ref_id) {
                paymentView.hideProgress();
                paymentView.onPaymentCompleted(ref_id);
            }

            @Override
            public void onPaymentError(String err) {
                paymentView.hideProgress();
                paymentView.onError(err);
            }
        });
    }
}
