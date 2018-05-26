package hassan.com.paydemo.Transactions;

import java.util.List;

/**
 * Created by Hassan on 5/17/2018.
 */

public interface TransactionView {
    void showProgress();
    void hideProgress();
    void showError(String err);
    void setList(List<TransactionModel> transactionModels);
}
