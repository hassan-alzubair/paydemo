package hassan.com.paydemo.NotificationService;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hassan on 5/15/2018.
 */

public interface NotificationServiceApi {

    @GET("Notifications/GetLatest")
    Call<String> getLatestNotifications();
}
