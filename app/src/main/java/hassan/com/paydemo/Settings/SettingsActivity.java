package hassan.com.paydemo.Settings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/20/2018.
 */

public class SettingsActivity extends BaseActivity implements SettingsView {

    private View btnNotifyMethods;
    private SettingsPresenter presenter;
    private ProgressDialog dialog;
    private View btnChangeBin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);

        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        getSupportActionBar().setTitle("الإعدادات");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new SettingsPresenterImpl(this, this, new SettingsInteractorImpl());

        btnNotifyMethods = findViewById(R.id.settings_notify_methods);
        btnChangeBin = findViewById(R.id.settings_change_pin);

        btnChangeBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ChangePinFragment fragment = new ChangePinFragment();
                fragment.setOnUpdatePinListener(new ChangePinFragment.OnUpdatePinListener() {
                    @Override
                    public void setPin(String oldPin, String newPin) {
                        fragment.dismiss();
                        presenter.updatePin(oldPin, newPin);
                    }
                });
                fragment.show(getFragmentManager(), "NEW_PIN");
            }
        });

        btnNotifyMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getNotifingMethods();
            }
        });
    }

    @Override
    public void setNotifingMethods(String[] methods) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final boolean[] checked = new boolean[2];

        if (methods.length == 0) {
            checked[0] = false;
            checked[1] = false;
        } else {
            if (Arrays.asList(methods).contains("email")) {
                checked[0] = true;
            } else {
                checked[0] = false;
            }
            if (Arrays.asList(methods).contains("app")) {
                checked[1] = true;
            } else {
                checked[1] = false;
            }
        }

        builder.setMultiChoiceItems(new String[]{"بالبريد الالكتروني", "بالتطبيق"}, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                checked[i] = b;
            }
        });


        builder.setTitle("تحديد طرق تلقى الإشعارات");
        builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<String> items = new ArrayList<>();
                if (checked[0] == true)
                    items.add("email");
                if (checked[1] == true)
                    items.add("app");
                String[] arr = new String[items.size()];
                items.toArray(arr);
                presenter.updateNotifingMethods(arr);
            }
        });
        builder.setNeutralButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void showProgress() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("جاري التحميل ...");
        dialog.show();
    }

    @Override
    public void hideProgress() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void showError(String err) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(err);
        builder.setNeutralButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
