package hassan.com.paydemo.Scanner;

import android.content.Context;

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

public class ScannerInteractorImpl implements ScannerInteractor {

    @Override
    public void checkForAccount(Context context, String account_id, final OnAccountCheckFinishedListener listener) {

        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        Call<String> call = retrofit.create(AccountApi.class).getAccountInfo(account_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        ApiResponse apiResponse = new ApiResponse(response.body());
                        if (apiResponse.getStatus() == false) {
                            listener.onError(apiResponse.getError());
                        } else {
                            JSONObject jsonObject = apiResponse.getPayload().getJSONObject(0);
                            String accountName = jsonObject.getString("account_name");
                            String accountId = jsonObject.getString("id");
                            listener.onChecked(accountId, accountName);
                        }
                    } catch (Exception ex) {
                        listener.onError(ex.getMessage());
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
