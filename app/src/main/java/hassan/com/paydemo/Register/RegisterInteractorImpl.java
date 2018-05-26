package hassan.com.paydemo.Register;

import hassan.com.paydemo.ApiUtils.ApiResponse;
import hassan.com.paydemo.ApiUtils.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Hassan on 5/14/2018.
 */

public class RegisterInteractorImpl implements RegisterInteractor {

    @Override
    public void register(String accountName,
                         String accountEmail,
                         String accountPhone,
                         String accountPin, final OnRegisterCompletedListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofit();
        final Call<String> request = retrofit.create(RegisterationApi.class).createAccount(accountName, accountEmail, accountPhone, accountPin);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ApiResponse apiResponse = new ApiResponse(response.body());
                        if (apiResponse.getStatus() == true) {
                            listener.onRegisterCompleted();
                        } else {
                            listener.onError(apiResponse.getError());
                        }
                    }
                } else {
                    listener.onError("unexpected error");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}