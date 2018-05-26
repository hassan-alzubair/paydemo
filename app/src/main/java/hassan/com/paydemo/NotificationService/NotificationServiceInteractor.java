package hassan.com.paydemo.NotificationService;

import android.content.Context;

/**
 * Created by Hassan on 5/15/2018.
 */

public interface NotificationServiceInteractor {
    public interface OnNotificationRecivedListener{
        void notificationReceived(String content);
    }

    public void receiveNotifications(Context context, OnNotificationRecivedListener listener);
}
