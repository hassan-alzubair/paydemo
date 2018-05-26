package hassan.com.paydemo.Login;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Hassan on 5/14/2018.
 */

public interface LoginApi {

    @FormUrlEncoded
    @POST("Account/Auth")
    Call<String> login(@Field("account_phone") String email,
                       @Field("account_pin") String pin);
}
