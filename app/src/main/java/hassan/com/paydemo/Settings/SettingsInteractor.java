package hassan.com.paydemo.Settings;

import android.content.Context;

/**
 * Created by Hassan on 5/20/2018.
 */

public interface SettingsInteractor {
    interface OnGetNotifingMethodsListener{
        void onSuccess(String[] methods);
        void onError(String err);
    }

    interface OnUpdateNotifingMethodsListener{
        void onSuccess();
        void onError(String err);
    }

    interface OnPinUpdatedListener{
        void onSuccess();
        void onError(String err);
    }


    void getNotifyingMethods(Context context,OnGetNotifingMethodsListener listener);
    void updateNotifingMethods(String[] methods,Context context,OnUpdateNotifingMethodsListener listener);
    void updatePin(String oldPin,String pin,Context context,OnPinUpdatedListener listener);
}
