package hassan.com.paydemo.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Hassan on 5/14/2018.
 */

public interface RegisterationApi {

    @FormUrlEncoded
    @POST("Account/Create")
    Call<String> createAccount(
            @Field("account_name") String accountName,
            @Field("account_email") String accountEmail,
            @Field("account_phone") String accountPhone,
            @Field("account_pin") String accountPin);

}
