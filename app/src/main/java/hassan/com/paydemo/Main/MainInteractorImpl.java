package hassan.com.paydemo.Main;

import android.content.Context;

import org.json.JSONException;

import hassan.com.paydemo.ApiUtils.ApiResponse;
import hassan.com.paydemo.ApiUtils.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Hassan on 5/18/2018.
 */

public class MainInteractorImpl implements MainInteractor {

    @Override
    public void getBalance(Context context, final OnMainInteractorGetBalanceFinishedListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        Call<String> call = retrofit.create(MainApi.class).getMyInfo();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        try {
                            String balance = apiResponse.getPayload().getJSONObject(0).getString("account_balance");
                            listener.onSuccess(balance);
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
}
