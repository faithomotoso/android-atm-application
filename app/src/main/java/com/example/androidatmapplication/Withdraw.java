package com.example.androidatmapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.icu.util.Currency;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidatmapplication.fragments.OtherAmountDialogFragment;
import com.example.androidatmapplication.models.ATM;
import com.example.androidatmapplication.models.Bank;
import com.example.androidatmapplication.models.Customer;
import com.example.androidatmapplication.models.Transaction;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Withdraw extends AppCompatActivity implements OtherAmountDialogFragment.OtherAmountListener {
    Customer customer = Customer.getInstance();
    ATM atm = ATM.getInstance();
    Bank bank = Bank.getInstance();
    ProgressDialog progressDialog;
    TransactionDialogFragment transactionDialogFragment = new TransactionDialogFragment();
    static boolean transactionCancelled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        progressDialog = new ProgressDialog(this);

    }

    public void buttonClick(View view) {
        int id = view.getId();
        transactionCancelled = false;


        if (id == R.id.button_500) {
            atm.withdrawAmount = 500;
        } else if (id == R.id.button_1000) {
            atm.withdrawAmount = 1000;
        } else if (id == R.id.button_5000) {
            atm.withdrawAmount = 5000;
        } else if (id == R.id.button_10000) {
            atm.withdrawAmount = 10000;
        } else if (id == R.id.button_20000) {
            atm.withdrawAmount = 20000;
        } else if (id == R.id.button_others){
            OtherAmountDialogFragment otherAmountDialogFragment = new OtherAmountDialogFragment();
            otherAmountDialogFragment.show(getSupportFragmentManager(), "others");
        }


        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        if (atm.withdrawAmount != 0){
            performTransaction();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ATM.passwordTries--;
            performTransaction();
        } else {
            transactionDialogFragment.dismiss();
        }
    }

    public void performTransaction() {
        transactionDialogFragment.show(getSupportFragmentManager(), "transaction dialog");


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!transactionCancelled) {
                    if (bank.checkPin()) {
                        // correct password
                        String message = atm.performTransaction(atm.withdrawAmount);
                        Toast.makeText(getApplicationContext(), message + ".", Toast.LENGTH_LONG).show();

                        if (message.toLowerCase().contains("successful")) {
                            String datePattern = "dd MMMM yyyy";
                            SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
                            String date = dateFormat.format(new Date());

                            String timePattern = "hh:mm a";
                            SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern);
                            String time = timeFormat.format(new Date());

                            String location = "Wuse Zone 4";
                            String transactionType = "Withdraw";
                            String account = "Savings";

                            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                            formatter.setCurrency(Currency.getInstance("NGN"));

                            String amount = formatter.format(atm.withdrawAmount);
                            String availableBalance = formatter.format(customer.getAccountBalance());

                            Transaction transaction = new Transaction(customer, date, time, location,
                                    transactionType, account, amount, availableBalance);
                            Intent receiptIntent = new Intent(Withdraw.this, Receipt.class);
                            receiptIntent.putExtra("transaction", (Serializable) transaction);
                            startActivity(receiptIntent);
                            finish();

                        } else {
                            final Handler handler = new Handler();
                            atm.withdrawAmount = 0;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    transactionDialogFragment.dismiss();
                                }
                            }, 1500);
                        }
                    } else {
                        atm.withdrawAmount = 0;
                        final Toast invalidPinToast = Toast.makeText(getApplicationContext(), getString(R.string.incorrect_pin), Toast.LENGTH_LONG);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                invalidPinToast.show();
                                transactionDialogFragment.dismiss();
                                Intent pfragment = new Intent(Withdraw.this, PasswordFragment.class);
                                if (ATM.passwordTries > 0) {
                                    startActivityForResult(pfragment, 0);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect Pin. Card would be retained. Contact your bank for assistance.", Toast.LENGTH_LONG).show();
                                    Intent loginIntent = new Intent(Withdraw.this, MainActivity.class);
                                    Customer.clearInstance();
                                    startActivity(loginIntent);
                                    finish();
                                }
                            }
                        }, 500);

                    }
                }
            }
        }, 2500);


    }

    @Override
    public void OnOtherAmountSubmit(Double amount) {
        atm.withdrawAmount = amount;
        if (atm.canWithdrawOtherAmount()){
            performTransaction();
        } else {
            Toast.makeText(getApplicationContext(), R.string.multiples_error, Toast.LENGTH_LONG).show();
            atm.withdrawAmount = 0;
        }
    }

    public static class TransactionDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

            builder.setView(layoutInflater.inflate(R.layout.transaction_dialog, null))
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            transactionCancelled = true;
                        }
                    });
            return builder.create();
        }
    }

}
