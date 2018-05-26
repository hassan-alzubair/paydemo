package hassan.com.paydemo.Settings;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Hassan on 5/20/2018.
 */

public interface SettingsApi {

    @GET("Account/GetMyInfo")
    Call<String> getNotifingMethods();

    @FormUrlEncoded
    @POST("Account/Update")
    Call<String> updateNotifingMethods(@Field("notifications_methods") String methods);

    @FormUrlEncoded
    @POST("Account/Update")
    Call<String> updatePin(@Field("old") String oldPin ,@Field("account_pin") String pin);


}
