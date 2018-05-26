package hassan.com.paydemo.Transactions;

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
 * Created by Hassan on 5/17/2018.
 */

public class TransactionInteractorImpl implements TransactionInteractor {

    @Override
    public void getTransactionsList(Context context, final OnTransactionInteractorFinishedListener listener) {
        Retrofit retrofit = RetrofitApi.getRetrofitWithHeaders(context);
        Call<String> call = retrofit.create(TransactionApi.class).getTransactionsList();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = new ApiResponse(response.body());
                    if (apiResponse.getStatus() == true) {
                        List<TransactionModel> transactionModels = new ArrayList<>();
                        for (int i = 0; i < apiResponse.getPayload().length(); i++) {
                            try {
                                JSONObject jsonObject = apiResponse.getPayload().getJSONObject(i);
                                TransactionModel model = new TransactionModel();
                                model.setAccountFrom(jsonObject.getString("account_from_name"));
                                model.setAccountTo(jsonObject.getString("account_from_to"));
                                model.setAmount(jsonObject.getString("amount"));
                                model.setCreatedAt(jsonObject.getString("created_at"));
                                model.setId(jsonObject.getString("id"));
                                model.setTransactionRefId(jsonObject.getString("transaction_ref_id"));
                                if (jsonObject.getBoolean("from_this_account") == true)
                                    model.setType(TransactionType.OUTGOING);
                                else
                                    model.setType(TransactionType.INCOMMING);
                                transactionModels.add(model);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listener.onSuccess(transactionModels);
                    } else {
                        listener.onError(apiResponse.getError());
                    }
                } else {
                    listener.onError("empty response");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
