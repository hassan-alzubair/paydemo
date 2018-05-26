package hassan.com.paydemo.Notifications;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/16/2018.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    private final Context context;
    private final List<NotificationModel> list;

    public NotificationListAdapter(Context context, List<NotificationModel> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.row_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        holder.txtDate.setText(list.get(position).getCreated_at());
        holder.txtContent.setText(list.get(position).getNotfication_content());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView txtContent;
        TextView txtDate;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.row_notification_content);
            txtDate = itemView.findViewById(R.id.row_notification_date);
        }
    }
}
