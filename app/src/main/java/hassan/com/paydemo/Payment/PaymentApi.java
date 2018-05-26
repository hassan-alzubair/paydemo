package hassan.com.paydemo.Payment;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Hassan on 5/15/2018.
 */

public interface PaymentApi {

    @FormUrlEncoded
    @POST("Transaction/Create")
    Call<String> pay(@Field("account_to") String toAccount,
                     @Field("amount") String amount);
}
