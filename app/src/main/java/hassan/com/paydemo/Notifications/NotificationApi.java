package hassan.com.paydemo.Notifications;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hassan on 5/16/2018.
 */

public interface NotificationApi {

    @GET("Notifications/GetAll")
    Call<String> getNotifications();
}
