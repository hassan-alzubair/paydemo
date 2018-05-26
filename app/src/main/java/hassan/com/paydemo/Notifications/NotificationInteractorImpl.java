package hassan.com.paydemo.Notifications;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hassan.com.paydemo.ApiUtils.ApiResponse;
import hassan.com.paydemo.ApiUtils.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Hassan on 5/16/2018.
 */

public class NotificationInteractorImpl implements NotificationInteractor {

    @Override
    public void getNotifications(Context context, final OnNotificationFetchFinishedListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        Call<String> call = retrofit.create(NotificationApi.class).getNotifications();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        List<NotificationModel> notificationModels = new ArrayList<>();
                        for (int i = 0; i < apiResponse.getPayload().length(); i++) {
                            try {
                                JSONObject jsonObject = apiResponse.getPayload().getJSONObject(i);
                                NotificationModel model = new NotificationModel();
                                model.setId(jsonObject.getString("id"));
                                model.setNotfication_content(jsonObject.getString("notfication_content"));
                                model.setCreated_at(jsonObject.getString("created_at"));
                                notificationModels.add(model);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onError(e.getMessage());
                            }
                        }
                        listener.onSuccess(notificationModels);
                    } else {
                        listener.onError(apiResponse.getError());
                    }
                } else {
                    listener.onError("empty response");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
