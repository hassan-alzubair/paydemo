package hassan.com.paydemo.Settings;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;

import hassan.com.paydemo.ApiUtils.ApiResponse;
import hassan.com.paydemo.ApiUtils.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Hassan on 5/20/2018.
 */

public class SettingsInteractorImpl implements SettingsInteractor {

    @Override
    public void getNotifyingMethods(final Context context, final OnGetNotifingMethodsListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        Call<String> call = retrofit.create(SettingsApi.class).getNotifingMethods();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        try {
                            String methods = apiResponse.getPayload().getJSONObject(0).getString("notifications_methods");
                            String[] notifying_methods = methods.split(",");
                            listener.onSuccess(notifying_methods);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    @Override
    public void updateNotifingMethods(String[] methods, Context context, final OnUpdateNotifingMethodsListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        String arr = TextUtils.join(",", methods);
        Call<String> call = retrofit.create(SettingsApi.class).updateNotifingMethods(arr);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        listener.onSuccess();
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

    @Override
    public void updatePin(String oldPin, String pin, Context context, final OnPinUpdatedListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        Call<String> call = retrofit.create(SettingsApi.class).updatePin(oldPin, pin);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        listener.onSuccess();
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
