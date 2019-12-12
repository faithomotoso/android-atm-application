package com.example.androidatmapplication.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidatmapplication.R;

public class OtherAmountDialogFragment extends DialogFragment {
    private OtherAmountListener callback;


    public interface OtherAmountListener {
        public void OnOtherAmountSubmit(Double amount);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        callback = (OtherAmountListener) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

        builder.setView(layoutInflater.inflate(R.layout.others_amount, null))
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final EditText amount = getDialog().findViewById(R.id.amount_edittext);
                        Log.v("Fragment", amount.toString());
                        callback.OnOtherAmountSubmit(Double.parseDouble(amount.getText().toString()));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
