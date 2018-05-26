package hassan.com.paydemo.Main;

import android.content.Context;

/**
 * Created by Hassan on 5/18/2018.
 */

public interface MainInteractor {
    interface OnMainInteractorGetBalanceFinishedListener {
        void onSuccess(String balance);

        void onError(String err);
    }

    void getBalance(Context context, OnMainInteractorGetBalanceFinishedListener listener);
}
