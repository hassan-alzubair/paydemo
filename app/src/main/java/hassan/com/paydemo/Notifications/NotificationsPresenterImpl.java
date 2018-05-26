package hassan.com.paydemo.Notifications;

import android.content.Context;

import java.util.List;

/**
 * Created by Hassan on 5/16/2018.
 */

public class NotificationsPresenterImpl implements NotificationPresenter {

    private Context context;
    private final NotificationsView notificationsView;
    private final NotificationInteractor interactor;

    public NotificationsPresenterImpl(Context context, NotificationsView notificationsView, NotificationInteractor interactor) {
        this.context = context;
        this.notificationsView = notificationsView;
        this.interactor = interactor;
    }

    @Override
    public void getNotifications() {
        notificationsView.showProgress();
        interactor.getNotifications(context, new NotificationInteractor.OnNotificationFetchFinishedListener() {
            @Override
            public void onSuccess(List<NotificationModel> notificationModels) {
                notificationsView.hideProgress();
                notificationsView.setNotificationsList(notificationModels);
            }

            @Override
            public void onError(String err) {
                notificationsView.hideProgress();
                notificationsView.showError(err);
            }
        });
    }
}
