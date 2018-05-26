package hassan.com.paydemo.Settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hassan.com.paydemo.R;

/**
 * Created by Hassan on 5/21/2018.
 */

public class ChangePinFragment extends DialogFragment {

    private OnUpdatePinListener listener;

    interface OnUpdatePinListener {
        void setPin(String oldPin, String newPin);
    }

    public void setOnUpdatePinListener(OnUpdatePinListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_change_pin, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("تغيير رمز التعريف الشخصي");
        final EditText txtOldPin = view.findViewById(R.id.txtOldPin);
        final EditText txtNewPin = view.findViewById(R.id.txtNewPin);
        final EditText txtNewPinAgain = view.findViewById(R.id.txtNewPinAgain);
        builder.setView(view);
        builder.setPositiveButton("حفظ ", null);
        builder.setNegativeButton("الغاء", null);
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnSave = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtOldPin.getText().toString().length() == 0) {
                            txtOldPin.setError("حقل اجباري");
                        } else if (txtNewPin.getText().toString().length() == 0) {
                            txtNewPin.setError("حقل اجباري");
                        } else if (txtNewPinAgain.getText().toString().length() == 0) {
                            txtNewPinAgain.setError("حقل اجباري");
                        } else {
                            if (txtNewPin.getText().toString().equals(txtNewPinAgain.getText().toString())) {
                                listener.setPin(txtOldPin.getText().toString(), txtNewPin.getText().toString());
                            } else {
                                txtNewPin.setError("الحقلان غير متطابقان");
                                txtNewPinAgain.setError("الحقلان غير متطابقان");
                            }
                        }
                    }
                });
            }
        });
        return dialog;
    }
}
