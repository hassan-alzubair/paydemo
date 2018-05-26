package hassan.com.paydemo.PaymentConfirmation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.Main.ActivityMain;
import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/15/2018.
 */

public class ActivityPaymentConfirmation extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment_confirmation);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("تمت عملية التحويل");
        Button btn = findViewById(R.id.payment_done);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ActivityMain.class));
        finish();
    }
}
