package hassan.com.paydemo.Scanner;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Hassan on 5/15/2018.
 */

public interface AccountApi {

    @FormUrlEncoded
    @POST("Account/GetInfo")
    Call<String> getAccountInfo(@Field("account_id") String accountId);

}
