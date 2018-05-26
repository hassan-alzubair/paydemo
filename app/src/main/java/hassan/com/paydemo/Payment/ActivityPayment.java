package hassan.com.paydemo.Payment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.PaymentConfirmation.ActivityPaymentConfirmation;
import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/15/2018.
 */

public class ActivityPayment extends BaseActivity implements PaymentView {

    private Toolbar mToolbar;
    private EditText txtName;
    private EditText txtAmount;
    private String receiver_account_id;
    private ProgressDialog progressDialog;
    private Button btnPay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment);

        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("إجراء عملية التحويل");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final PaymentPresenter presenter = new PaymentPresenterImpl(this, this, new PaymentInteractorImpl());

        txtName = findViewById(R.id.payment_txt_account_name);
        txtAmount = findViewById(R.id.payment_txt_amount);
        btnPay = findViewById(R.id.payment_btn_pay);

        txtName.setText(getIntent().getExtras().getString("receiver_account_name"));
        this.receiver_account_id = getIntent().getExtras().getString("receiver_account_id");

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtAmount.getText().toString().trim().length() == 0) {
                    txtAmount.setError("الحقل اجباري");
                    txtAmount.requestFocus();
                } else {
                    presenter.pay(receiver_account_id, txtAmount.getText().toString());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            NavUtils.navigateUpFromSameTask(this);
        else
            return super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("عملية التحويل جارية ...");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onPaymentCompleted(String ref_id) {
        Intent intent = new Intent(this, ActivityPaymentConfirmation.class);
        intent.putExtra("ref_id", ref_id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String err) {
        new AlertDialog.Builder(this)
                .setMessage(err)
                .setCancelable(false)
                .setNeutralButton("اغلاق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}