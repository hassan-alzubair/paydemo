package hassan.com.paydemo.NotificationService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Hassan on 5/15/2018.
 */

public class NotificationService extends IntentService implements Runnable {


    private final NotificationServiceInteractor notificationService;
    private boolean stopped = false;
    private Thread thread;

    public NotificationService() {
        super("NotificationService");
        notificationService = new NotoficationServiceInteractorImpl();
    }

    @Override
    public void run() {
        while (!stopped) {

            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("run: ", "new request ====================================");
            notificationService.receiveNotifications(this, new NotificationServiceInteractor.OnNotificationRecivedListener() {
                @Override
                public void notificationReceived(String content) {
                    Intent intent = new Intent();
                    intent.setAction("com.hassan.demopay.NOTIFICATION_RECEIVED");
                    intent.putExtra("content", content);
                    sendBroadcast(intent);
                }
            });
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onDestroy() {
        this.stopped = true;
        super.onDestroy();
        Log.e("onDestroy: ", "=================================== Notification Service Stopped ===========================");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        thread = new Thread(this);
        thread.start();
        return START_STICKY;
    }
}
