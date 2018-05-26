package hassan.com.paydemo.ReceiveCash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;

import hassan.com.paydemo.Base.BaseActivity;
import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/21/2018.
 */

public class ActivityReceiveCash extends BaseActivity {

    private ImageView img;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_receive);

        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        getSupportActionBar().setTitle("امسح و ادفع");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img = findViewById(R.id.img_qr);

        Bitmap bmp = encodeID();
        img.setImageBitmap(bmp);

    }


    private Bitmap encodeID() {
        String id = PreferenceManager.getDefaultSharedPreferences(this).getString("account_id", "0");
        int width = 500;
        int height = 500;
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try {
            matrix = writer.encode(id, BarcodeFormat.QR_CODE, width, height);

        } catch (Exception ex) {

        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }

        return bitmap;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            NavUtils.navigateUpFromSameTask(this);
        else if (item.getItemId() == R.id.menu_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            Bitmap bitmap = encodeID();
            try {
                File file = new File(this.getExternalCacheDir(), "qr.png");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                intent.setType("image/png");
                startActivity(Intent.createChooser(intent, "مشاركة ال QR Code عبر"));
            } catch (Exception ex) {

            }
        } else
            return super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
