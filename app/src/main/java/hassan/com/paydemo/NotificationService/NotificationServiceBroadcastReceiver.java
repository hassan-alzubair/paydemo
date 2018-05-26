package hassan.com.paydemo.NotificationService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import hassan.com.paydemo.Notifications.ActivityNotifications;
import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/15/2018.
 */

public class NotificationServiceBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private int number = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals("com.hassan.demopay.NOTIFICATION_RECEIVED"))
            sendNotification(intent.getExtras().getString("content"));
    }


    private void sendNotification(String content) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);


        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText(content);
        builder.setSubText("Pay Demo");
        builder.setTicker("اشعار تحويل مبلغ");
        builder.setNumber(++number);
        builder.setPriority(Notification.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= 21)
            builder.setVibrate(new long[0]);


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        Intent intent = new Intent(context, ActivityNotifications.class);
        taskStackBuilder.addParentStack(ActivityNotifications.class);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("اشعار تحويل مبلغ");
        bigTextStyle.bigText(content);

        builder.setStyle(bigTextStyle);

        Notification notification = builder.build();
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }
}
