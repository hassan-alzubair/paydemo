package hassan.com.paydemo.Transactions;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hassan on 5/17/2018.
 */

public interface TransactionApi {

    @GET("Transaction/GetAll")
    Call<String> getTransactionsList();
}
