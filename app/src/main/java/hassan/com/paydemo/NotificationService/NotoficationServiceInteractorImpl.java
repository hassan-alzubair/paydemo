package hassan.com.paydemo.NotificationService;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hassan.com.paydemo.ApiUtils.ApiResponse;
import hassan.com.paydemo.ApiUtils.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Hassan on 5/15/2018.
 */

public class NotoficationServiceInteractorImpl implements NotificationServiceInteractor {
    @Override
    public void receiveNotifications(Context context, final OnNotificationRecivedListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        Call<String> call = retrofit.create(NotificationServiceApi.class).getLatestNotifications();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        JSONArray jsonArray = apiResponse.getPayload();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String content = object.getString("notfication_content");
                                listener.notificationReceived(content);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    Log.e("onResponse: ", "empty response");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t.getMessage() != null)
                    Log.e("onFailure: ", t.getMessage());
            }
        });
    }
}
