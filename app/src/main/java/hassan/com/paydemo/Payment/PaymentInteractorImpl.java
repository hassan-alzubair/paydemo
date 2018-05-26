package hassan.com.paydemo.Payment;

import android.content.Context;

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

public class PaymentInteractorImpl implements PaymentInteractor {
    @Override
    public void pay(Context context, String toAccount, String amount, final OnPaymentFinishedListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        Call<String> call = retrofit.create(PaymentApi.class).pay(toAccount, amount);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        try {
                            JSONObject jsonObject = apiResponse.getPayload().getJSONObject(0);
                            String ref_id = jsonObject.getString("transaction_ref_id");
                            listener.onPaymentSuccess(ref_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onPaymentError(e.getMessage());
                        }
                    } else {
                        listener.onPaymentError(apiResponse.getError());
                    }
                } else {
                    listener.onPaymentError("empty response");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
