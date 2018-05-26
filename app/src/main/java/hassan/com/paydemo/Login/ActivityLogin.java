package hassan.com.paydemo.Login;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.Main.ActivityMain;
import hassan.com.paydemo.R;
import hassan.com.paydemo.Register.ActivityRegister;

/**
 * Created by Hassan on 5/13/2018.
 */

public class ActivityLogin extends BaseActivity implements LoginView, View.OnClickListener {

    private Toolbar mToolbar;
    private EditText txtEmail;
    private EditText txtPin;
    private Button btnLogin;
    private ProgressBar progressBar;
    private LoginPresenter presenter;
    private TextView txtNewAccount;
    private CheckBox cbRemember;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("تسجيل الدخول");

        presenter = new LoginPresenterImpl(this, new LoginInteractorImpl());

        txtEmail = findViewById(R.id.txt_login_email);
        txtPin = findViewById(R.id.txt_login_pin);
        cbRemember = findViewById(R.id.login_remember);
        txtNewAccount = findViewById(R.id.txtNewAccount);
        txtNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.navigateToNewAccount();
            }
        });
        btnLogin = findViewById(R.id.txt_login_submit);
        btnLogin.setOnClickListener(this);
        progressBar = findViewById(R.id.login_progress);
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
    public void onError(String err) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void navigateToHome() {
        if (cbRemember.isChecked()) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("remember", true).commit();
        }
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("logged", true).commit();
        startActivity(new Intent(this, ActivityMain.class));
        finish();
    }

    @Override
    public void saveTokenAndID(String token, String id) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("token", token).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("account_id", id).commit();
    }

    @Override
    public void navigateToNewAccount() {
        startActivity(new Intent(this, ActivityRegister.class));
    }

    @Override
    public void lockLoginButton() {
        btnLogin.setEnabled(false);
    }

    @Override
    public void releaseLoginButton() {
        btnLogin.setEnabled(true);
    }

    @Override
    public void onClick(View view) {

        boolean cool = true;
        if (txtEmail.getText().toString().trim().length() == 0) {
            cool = false;
            txtEmail.setError("الحقل اجباري");
        }

        if (txtPin.getText().toString().trim().length() == 0) {
            cool = false;
            txtPin.setError("الحقل اجباري");
        }

        if (cool == true) {
            presenter.login(txtEmail.getText().toString(), txtPin.getText().toString());
        }


    }
}