package hassan.com.paydemo.Login;

import android.util.Log;

import org.json.JSONException;

import hassan.com.paydemo.ApiUtils.ApiResponse;
import hassan.com.paydemo.ApiUtils.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginInteractorImpl implements LoginInteractor {

    private String TAG = "LoginInteractorImpl";

    @Override
    public void login(String phone, String pin, final OnLoginInteractorFinishedListener listener) {
        Log.e(TAG, "login: " + "phone => " + phone + " pin => " + pin);
        Retrofit retrofit = RetrofitApi.getRetrofit();
        Call<String> call = retrofit.create(LoginApi.class).login(phone, pin);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: " + response.body());
                if (response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        try {
                            String token = apiResponse.getPayload().getJSONObject(0).getString("token");
                            String id = apiResponse.getPayload().getJSONObject(0).getString("account_id");
                            if (token == null) {
                                listener.onError("null token");
                            } else {
                                listener.onLoginSuccess(token, id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(e.getMessage());
                        }
                    } else {
                        listener.onError(apiResponse.getError());
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
