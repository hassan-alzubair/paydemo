package hassan.com.paydemo.Transactions;

import android.content.Context;

import java.util.List;

/**
 * Created by Hassan on 5/17/2018.
 */

public class TransactionsPresenterImpl implements TransactionPresenter {

    private final Context context;
    private final TransactionView view;
    private final TransactionInteractor interactor;

    public TransactionsPresenterImpl(Context context, TransactionView view, TransactionInteractor interactor) {
        this.context = context;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void getTransactionList() {
        view.showProgress();
        interactor.getTransactionsList(context, new TransactionInteractor.OnTransactionInteractorFinishedListener() {
            @Override
            public void onSuccess(List<TransactionModel> models) {
                view.hideProgress();
                view.setList(models);
            }

            @Override
            public void onError(String err) {
                view.hideProgress();
                view.showError(err);
            }
        });
    }
}
