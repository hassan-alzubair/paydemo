package hassan.com.paydemo.Base;

import android.app.Application;

import hassan.com.paydemo.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Hassan on 5/12/2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .setDefaultFontPath("fonts/JF-Flat-regular.ttf")
                .build());
    }
}
