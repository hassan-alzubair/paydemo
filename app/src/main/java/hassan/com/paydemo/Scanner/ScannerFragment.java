package hassan.com.paydemo.Scanner;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import hassan.com.paydemo.Payment.ActivityPayment;
import hassan.com.paydemo.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Hassan on 5/12/2018.
 */

public class ScannerFragment extends DialogFragment implements ZXingScannerView.ResultHandler, View.OnClickListener, ScannerView {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_QR = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION = 2;
    private ZXingScannerView scannerView;
    private boolean flash_on = false;
    private ScannerPresenter scannerPresenter;
    private ProgressDialog dialog;
    private FloatingActionButton fabImport;
    private FloatingActionButton fabToggleFlash;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerPresenter = new ScannerPresenterImpl(getActivity(), this, new ScannerInteractorImpl());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.layout_scanner, container, false);
        scannerView = view.findViewById(R.id.qr_scanner);
        fabToggleFlash = view.findViewById(R.id.fab_toggle_flash);
        fabToggleFlash.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getActivity(), "استخدام الفلاش", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        fabImport = view.findViewById(R.id.fab_import);

        fabImport.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getActivity(), "اختيار صورة ال QR Code", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        fabImport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (isCameraPremissionGranted()) {
                        pickQrCode();
                    } else {
                        requestForExternalStoragePermission();
                    }
                } else {
                    pickQrCode();
                }
            }
        });
        fabToggleFlash.setOnClickListener(this);
        return view;
    }

    private boolean isExternalStoragePermissionGranted() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForExternalStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_QR && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            String[] filepathcolumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filepathcolumn, null, null, null);
            cursor.moveToFirst();
            int columnindex = cursor.getColumnIndex(filepathcolumn[0]);
            String picturepath = cursor.getString(columnindex);
            cursor.close();
            try {
                Bitmap bmp = BitmapFactory.decodeFile(picturepath);
                bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);
                int[] intArr = new int[bmp.getWidth() * bmp.getHeight()];
                bmp.getPixels(intArr, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
                LuminanceSource source = new RGBLuminanceSource(bmp.getWidth(), bmp.getHeight(), intArr);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new MultiFormatReader();
                try {
                    Result result = reader.decode(bitmap);
                    this.handleResult(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isCameraPremissionGranted())
                scannerView.startCamera();
            else
                requestForCameraPremission();
        } else {
            scannerView.startCamera();
        }
    }

    private boolean isCameraPremissionGranted() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    private void requestForCameraPremission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void showError(String err) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(err);
            builder.setCancelable(false);
            builder.setNeutralButton("اغلاق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        scannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.CAMERA)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scannerView.startCamera();
                }
            }
        } else if (requestCode == EXTERNAL_STORAGE_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickQrCode();
                }
            }
        }
    }

    private void pickQrCode() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "قم بتحديد الQR Code"), PICK_QR);
    }

    @Override
    public void onClick(View view) {
        scannerPresenter.toggleFlash();
    }

    @Override
    public void handleResult(Result result) {
        String qr = result.getText().toString();
        if (qr != null)
            scannerPresenter.checkForAccount(qr);
        scannerView.resumeCameraPreview(this);
    }


    @Override
    public void toggleFlash() {
        this.flash_on = !this.flash_on;
        scannerView.setFlash(this.flash_on);
        Drawable icon = this.flash_on == true ? getResources().getDrawable(R.drawable.ic_flash_off_black_24dp) : getResources().getDrawable(R.drawable.ic_flash_on_black_24dp);
        fabToggleFlash.setImageDrawable(icon);
    }


    @Override
    public void showProgress() {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("الرجاء الانتظار");
        dialog.show();
    }

    @Override
    public void hideProgress() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void navigateToPayment(String account_id, String account_name) {
        Intent intent = new Intent(getActivity(), ActivityPayment.class);
        intent.putExtra("receiver_account_id", account_id);
        intent.putExtra("receiver_account_name", account_name);
        startActivity(intent);
        dismiss();
    }


}
