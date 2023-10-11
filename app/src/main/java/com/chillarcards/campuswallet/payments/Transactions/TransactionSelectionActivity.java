package com.chillarcards.campuswallet.payments.Transactions;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityOnlineTransactionStatusBinding;
import com.chillarcards.campuswallet.databinding.ActivityTransactionSelectionBinding;

import com.chillarcards.campuswallet.Payment.History.PaymentHistoryActivity;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.payments.cardtransaction.CardTransactionActivity;

public class TransactionSelectionActivity extends CustomConnectionBuddyActivity {

    ActivityTransactionSelectionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_transaction_selection);
        binding = ActivityTransactionSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.HeaderTV.setText(getResources().getString(R.string.transaction));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.PamentTransactionsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Payment_Transactions_Menu_Selected),new Bundle());
                Intent i6 =new Intent(TransactionSelectionActivity.this,PaymentHistoryActivity.class);
                i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i6.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(i6);
            }
        });
        binding.WalletTransactionsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Wallet_Transactions_Menu_Selected),new Bundle());
                Intent i6 =new Intent(TransactionSelectionActivity.this,CardTransactionActivity.class);
                i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i6.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(i6);
            }
        });
        binding.onlinePaymentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Online_Transactions_Menu_Selected),new Bundle());
                Intent i6 =new Intent(TransactionSelectionActivity.this,OnlineTransactionStatusActivity.class);
                i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i6.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(i6);
            }
        });
    }
}
