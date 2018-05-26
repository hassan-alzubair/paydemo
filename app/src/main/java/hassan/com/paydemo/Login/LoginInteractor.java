package hassan.com.paydemo.Login;

/**
 * Created by Hassan on 5/14/2018.
 */

public interface LoginInteractor {

    public interface OnLoginInteractorFinishedListener{
        void onLoginSuccess(String token,String id);
        void onError(String err);
    }

    public void login(String phone,String pin,OnLoginInteractorFinishedListener listener);
}
