package hassan.com.paydemo.Notifications;

import android.content.Context;

import java.util.List;

/**
 * Created by Hassan on 5/16/2018.
 */

public interface NotificationInteractor {
    interface OnNotificationFetchFinishedListener {
        void onSuccess(List<NotificationModel> notificationModels);

        void onError(String err);
    }

    void getNotifications(Context context, OnNotificationFetchFinishedListener listener);
}
