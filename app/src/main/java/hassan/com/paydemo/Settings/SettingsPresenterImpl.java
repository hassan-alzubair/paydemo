package hassan.com.paydemo.Settings;

import android.content.Context;

/**
 * Created by Hassan on 5/20/2018.
 */

public class SettingsPresenterImpl implements SettingsPresenter {


    private final Context context;
    private final SettingsView view;
    private final SettingsInteractor interactor;

    public SettingsPresenterImpl(Context context, SettingsView view, SettingsInteractor interactor) {
        this.context = context;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void getNotifingMethods() {
        view.showProgress();
        interactor.getNotifyingMethods(context, new SettingsInteractor.OnGetNotifingMethodsListener() {
            @Override
            public void onSuccess(String[] methods) {
                view.hideProgress();
                view.setNotifingMethods(methods);
            }

            @Override
            public void onError(String err) {
                view.hideProgress();
                view.showError(err);
            }
        });
    }

    @Override
    public void updateNotifingMethods(String[] methds) {
        view.showProgress();
        interactor.updateNotifingMethods(methds, context, new SettingsInteractor.OnUpdateNotifingMethodsListener() {
            @Override
            public void onSuccess() {
                view.hideProgress();
            }

            @Override
            public void onError(String err) {
                view.hideProgress();
                view.showError(err);
            }
        });
    }

    @Override
    public void updatePin(String oldPin, String pin) {
        view.showProgress();
        interactor.updatePin(oldPin, pin, context, new SettingsInteractor.OnPinUpdatedListener() {
            @Override
            public void onSuccess() {
                view.hideProgress();
            }

            @Override
            public void onError(String err) {
                view.hideProgress();
                view.showError(err);
            }
        });
    }
}