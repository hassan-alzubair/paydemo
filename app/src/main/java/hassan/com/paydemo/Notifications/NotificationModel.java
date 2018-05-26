package hassan.com.paydemo.Notifications;

/**
 * Created by Hassan on 5/16/2018.
 */

public class NotificationModel {
    private String id;
    private String notfication_content;
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotfication_content() {
        return notfication_content;
    }

    public void setNotfication_content(String notfication_content) {
        this.notfication_content = notfication_content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
