package hassan.com.paydemo.Transactions;

import android.content.Context;

import java.util.List;

/**
 * Created by Hassan on 5/17/2018.
 */

public interface TransactionInteractor {
    interface OnTransactionInteractorFinishedListener {
        void onSuccess(List<TransactionModel> models);

        void onError(String err);
    }

    void getTransactionsList(Context context, OnTransactionInteractorFinishedListener listener);
}
