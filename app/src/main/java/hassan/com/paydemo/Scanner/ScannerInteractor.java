package hassan.com.paydemo.Scanner;

import android.content.Context;

/**
 * Created by Hassan on 5/15/2018.
 */

public interface ScannerInteractor {

    public interface OnAccountCheckFinishedListener {
        void onChecked(String account_id, String account_name);
        void onError(String error);
    }

    void checkForAccount(Context context, String account_id, OnAccountCheckFinishedListener listener);
}
