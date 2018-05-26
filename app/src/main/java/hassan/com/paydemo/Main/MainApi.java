package hassan.com.paydemo.Main;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hassan on 5/18/2018.
 */

public interface MainApi {

    @GET("Account/GetMyInfo")
    Call<String> getMyInfo();
}
