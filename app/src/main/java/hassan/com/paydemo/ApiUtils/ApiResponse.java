package hassan.com.paydemo.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hassan on 5/14/2018.
 */

public class ApiResponse {

    private boolean status;
    private JSONArray payload;
    private String error;


    public ApiResponse(String json) {
        if (json == null) {
            status = false;
            error = "empty response";
        } else {
            try {
                JSONObject jsonObject = new JSONObject(json);
                this.status = jsonObject.getBoolean("status");
                this.error = jsonObject.getString("error");
                this.payload = jsonObject.getJSONArray("payload");
            } catch (JSONException e) {
                e.printStackTrace();
                status = false;
                error = e.getMessage();
                payload = null;
            }
        }
    }

    public boolean getStatus() {
        return status;
    }

    public JSONArray getPayload() {
        return payload;
    }

    public String getError() {
        return error;
    }
}
