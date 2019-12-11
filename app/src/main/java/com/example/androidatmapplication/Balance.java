package com.example.androidatmapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.icu.util.Currency;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidatmapplication.models.ATM;
import com.example.androidatmapplication.models.Bank;
import com.example.androidatmapplication.models.Customer;

public class Balance extends AppCompatActivity {
    ATM atm = ATM.getInstance();
    Bank bank = Bank.getInstance();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            ATM.passwordTries--;
            showBalance();
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        showBalance();
    }

    void showBalance(){
        if (bank.checkPin()){
            Customer customer = Customer.getInstance();

            TextView balanceTextView = findViewById(R.id.balance_amount_textview);

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setCurrency(Currency.getInstance("NGN"));

            balanceTextView.setText(formatter.format(customer.getAccountBalance()));
        } else {
            final Toast invalidPinToast = Toast.makeText(getApplicationContext(), getString(R.string.incorrect_pin), Toast.LENGTH_LONG);

            invalidPinToast.show();
            Intent pfragment = new Intent(Balance.this, PasswordFragment.class);
            if (ATM.passwordTries > 0){
                startActivityForResult(pfragment, 0);
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect Pin. Card would be retained. Contact your bank for assistance.", Toast.LENGTH_LONG).show();
                Intent loginIntent = new Intent(Balance.this, MainActivity.class);
                Customer.clearInstance();
                startActivity(loginIntent);
                finish();
            }
        }
    }
}
