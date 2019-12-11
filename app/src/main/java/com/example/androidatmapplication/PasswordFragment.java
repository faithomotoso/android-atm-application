package com.example.androidatmapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.androidatmapplication.models.ATM;

public class PasswordFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_fragment);
        passwordDialogFragment passwordDialogFragment = new passwordDialogFragment();
        passwordDialogFragment.show(getSupportFragmentManager(), "password");
    }

    public static class passwordDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

            builder.setView(layoutInflater.inflate(R.layout.password_dialog, null))
                    .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final EditText password = getDialog().findViewById(R.id.password_edittext);
                            ATM atm = ATM.getInstance();
                            atm.setPin(password.getText().toString());
                            getActivity().setResult(RESULT_OK);
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().setResult(RESULT_CANCELED);
                            dialogInterface.cancel();
                        }
                    });

            return builder.create();
        }

        @Override
        public void onStop() {
            super.onStop();
        }
    }
}
