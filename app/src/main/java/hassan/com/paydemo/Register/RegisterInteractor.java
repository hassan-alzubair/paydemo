package hassan.com.paydemo.Register;

/**
 * Created by Hassan on 5/14/2018.
 */

public interface RegisterInteractor {

    public interface OnRegisterCompletedListener {
        void onRegisterCompleted();
        void onError(String err);
    }

    public void register(String accountName,
                         String accountEmail,
                         String accountPhone,
                         String accountPin,
                         OnRegisterCompletedListener listener);

}
