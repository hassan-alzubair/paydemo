package hassan.com.paydemo.Notifications;

import java.util.List;

/**
 * Created by Hassan on 5/16/2018.
 */

public interface NotificationsView {
    void setNotificationsList(List<NotificationModel> list);
    void showError(String err);
    void showProgress();
    void hideProgress();
}
