package hassan.com.paydemo.Register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.Login.ActivityLogin;
import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/13/2018.
 */

public class ActivityRegister extends BaseActivity implements RegisterView, View.OnClickListener {

    private Toolbar mToolbar;
    private RegisterPresenter presenter;
    private ProgressBar mProgress;
    private Button btnRegister;
    private EditText txtAccountName;
    private EditText txtAccountPin;
    private EditText txtAccountEmail;
    private EditText txtAccountPhone;
    private EditText txtAccountRePin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        mToolbar = findViewById(R.id.app_bar);
        mProgress = findViewById(R.id.register_progress);
        btnRegister = findViewById(R.id.register_submit);
        btnRegister.setOnClickListener(this);


        txtAccountName = findViewById(R.id.register_account_name);
        txtAccountPin = findViewById(R.id.register_pin);
        txtAccountEmail = findViewById(R.id.register_account_email);
        txtAccountPhone = findViewById(R.id.register_account_phone);
        txtAccountRePin = findViewById(R.id.register_re_pin);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("تسجيل حساب جديد");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new RegisterPresenterImpl(this, new RegisterInteractorImpl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, ActivityLogin.class));
        finish();
    }

    @Override
    public void showRegisterSuccessfull() {
        new AlertDialog.Builder(this)
                .setMessage("تم التسجيل بنجاح , الرجاء تسجيل الدخول")
                .setNeutralButton("تسجيل دخول", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.setNavigateToLogin();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRegisterError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        boolean cool = true;
        if (txtAccountName.getText().toString().trim().length() == 0) {
            cool = false;
            txtAccountName.setError("الحقل اجباري");
        }
        if (txtAccountEmail.getText().toString().trim().length() == 0) {
            cool = false;
            txtAccountEmail.setError("الحقل اجباري");
        }
        if (txtAccountPhone.getText().toString().trim().length() == 0) {
            cool = false;
            txtAccountPhone.setError("الحقل اجباري");
        }
        if (txtAccountPin.getText().toString().trim().length() == 0) {
            cool = false;
            txtAccountPin.setError("الحقل اجباري");
        }
        if (txtAccountRePin.getText().toString().trim().length() == 0) {
            cool = false;
            txtAccountRePin.setError("الحقل اجباري");
        }

        if (!txtAccountPin.getText().toString().equals(txtAccountRePin.getText().toString())) {
            cool = false;
            txtAccountRePin.setError("الحقلين غير متطابقين");
            txtAccountPin.setError("الحقلين غير متطابقين");
        }
        if (cool == true) {


            presenter.register(txtAccountName.getText().toString(),
                    txtAccountEmail.getText().toString(),
                    txtAccountPhone.getText().toString(),
                    txtAccountPin.getText().toString());
        }
    }


    @Override
    public void lockSubmitButton() {
        btnRegister.setEnabled(false);
    }

    @Override
    public void releaseSubmitButton() {
        btnRegister.setEnabled(true);
    }
}
