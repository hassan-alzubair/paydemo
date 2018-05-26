package hassan.com.paydemo.Main;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.NotificationService.NotificationService;
import hassan.com.paydemo.Notifications.ActivityNotifications;
import hassan.com.paydemo.R;
import hassan.com.paydemo.ReceiveCash.ActivityReceiveCash;
import hassan.com.paydemo.Scanner.ScannerFragment;
import hassan.com.paydemo.Settings.SettingsActivity;
import hassan.com.paydemo.Transactions.TransactionsActivity;

public class ActivityMain extends BaseActivity implements View.OnClickListener, MainView {

    private Toolbar mToolbar;
    private View btnTransfeer;
    private MainPresenter presenter;
    private View navigateNotifications;
    private View navigateLogout;
    private View navigateToTransactions;
    private ProgressBar progressBar;
    private TextView txtBalance;
    private View currency;
    private View navigateSettings;
    private View btnReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Pay Demo");

        presenter = new MainPresenterImpl(this, this, new MainInteractorImpl());
        btnTransfeer = findViewById(R.id.btnTransfeer);
        btnTransfeer.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_balance);
        txtBalance = findViewById(R.id.txtBalance);
        currency = findViewById(R.id.currency);

        navigateNotifications = findViewById(R.id.navigate_notifications);
        navigateSettings = findViewById(R.id.navigate_settings);
        navigateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.navigateToSettings();
            }
        });
        navigateLogout = findViewById(R.id.navigate_logout);
        navigateToTransactions = findViewById(R.id.navigate_transactions);

        btnReceive = findViewById(R.id.btnReceive);
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.navigateToReceive();
            }
        });
        navigateToTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.navigateToTransactions();
            }
        });
        navigateLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.logout();
            }
        });
        navigateNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.navigateToNotificatoins();
            }
        });
        startService(new Intent(this, NotificationService.class));
    }

    @Override
    public void navigateToReceive() {
        startActivity(new Intent(this, ActivityReceiveCash.class));
    }

    @Override
    public void navigateToNotifications() {
        startActivity(new Intent(this, ActivityNotifications.class));
    }

    @Override
    public void navigateToSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMyBalance();
    }

    @Override
    public void navigateToTransactions() {
        startActivity(new Intent(this, TransactionsActivity.class));
    }

    @Override
    public void onClick(View view) {
        presenter.startScanner();
    }

    @Override
    public void startScanner() {
        ScannerFragment scannerFragment = new ScannerFragment();
        scannerFragment.setCancelable(true);
        scannerFragment.show(getFragmentManager(), "SCANNER");
    }

    @Override
    public void doLogout() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);

        if (preferences.contains("remember") && preferences.getBoolean("remember", false) == true) {
            builder.setMessage("هل تريد تسجيل الخروج من التطبيق ام الخروج فقط ؟");
            builder.setPositiveButton("خروج من التطبيق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNeutralButton("الغاء", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("تسجيل الخروج", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // stop notification service
                    stopService(new Intent(getBaseContext(), NotificationService.class));
                    // clear authorization token
                    preferences.edit().clear().commit();
                    finish();
                }
            });
        } else {
            builder.setMessage("هل انت متأكد ؟");
            builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // stop notification service
                    stopService(new Intent(getBaseContext(), NotificationService.class));
                    // clear authorization token
                    preferences.edit().clear().commit();
                    finish();
                }
            });
        }
        builder.show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setBalance(String balance) {
        txtBalance.setVisibility(View.VISIBLE);
        currency.setVisibility(View.VISIBLE);
        txtBalance.setText(balance);
    }

    @Override
    public void showError(String err) {
        new AlertDialog.Builder(this)
                .setMessage(err)
                .setNeutralButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
