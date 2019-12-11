package com.example.androidatmapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidatmapplication.models.ATM;
import com.example.androidatmapplication.models.Customer;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Customer> customers = new ArrayList<>();
    static Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating sim customers
        customers.add(Customer.init("Angela Franklin", generateAmount(), "1893829301", "9903"));
        customers.add(Customer.init("Ayo Emmanuel", generateAmount(), "3872819203", "3285"));

        Button insertButton = findViewById(R.id.insert_card_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment fragment = new dialogFragment();
                fragment.show(getSupportFragmentManager(), "cardnumber");
            }
        });
    }

    static double generateAmount(){
        Random random = new Random();
        return Math.random() * random.nextInt(100000);
    }


   public static class dialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.select_card)
                    .setItems(getResources().getStringArray(R.array.cardnumbers), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String selectedNum = getResources().getStringArray(R.array.cardnumbers)[i];
                            Log.v("MainActivity", "Selected number " + selectedNum);
                            for (int a = 0; a < customers.size(); a++){
                                switch (selectedNum){
                                    case "1893829301":
                                        customer = Customer.init("Angela Franklin", generateAmount(), "1893829301", "9903");
                                        break;

                                    case "3872819203":
                                        customer = Customer.init("Ayo Emmanuel", generateAmount(), "3872819203", "3285");
                                }
                            }
                            if (customer != null){
                                Log.v("MainActivity", "Customer name: " + customer.getName());
                                passwordDialogFragment passwordFragment = new passwordDialogFragment();
                                passwordFragment.show(getActivity().getSupportFragmentManager(), "password");
                            }
                        }
                    });
            return builder.create();
        }

    public static class passwordDialogFragment extends DialogFragment{

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
                            ATM atm = ATM.init();
                            atm.setPin(password.getText().toString());
                            final Intent transactionsIntent = new Intent(getActivity().getApplicationContext(), Transactions.class);
                            startActivity(transactionsIntent);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

            return builder.create();
        }
    }
    }
}



