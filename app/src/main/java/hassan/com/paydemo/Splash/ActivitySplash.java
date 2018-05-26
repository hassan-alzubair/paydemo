package hassan.com.paydemo.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.Login.ActivityLogin;
import hassan.com.paydemo.Main.ActivityMain;
import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/12/2018.
 */

public class ActivitySplash extends BaseActivity implements Runnable {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_splash);
        new Handler().postDelayed(this, 300);
    }

    @Override
    public void run() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("remember", false) == true) {
            startActivity(new Intent(this, ActivityMain.class));
            finish();
        } else if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("logged", false) == true) {
            startActivity(new Intent(this, ActivityMain.class));
            finish();
        } else {
            startActivity(new Intent(this, ActivityLogin.class));
            finish();
        }
    }
}
