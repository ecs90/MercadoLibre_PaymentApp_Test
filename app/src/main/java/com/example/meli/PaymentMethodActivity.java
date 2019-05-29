package com.example.meli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;

public class PaymentMethodActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    //Key for accessing retrofit methods
    private static final String PUBLIC_KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";
    //Payment type the exercise wants to use
    private static final String PAYMENT_TYPE_ID_PREDETERMINADO = "credit_card";

    private TextView mMontoIngresado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentMethod);
        mMontoIngresado = findViewById(R.id.monto_mainActivity);

        Intent intent = getIntent();
        String monto = intent.getStringExtra(getString(R.string.monto_key_putExtra));
        mMontoIngresado.setText(monto);

        /*Create handle for the RetrofitInstance interface*/
        RetrofitInterface service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterface.class);
        Call<ArrayList<PaymentMethodModel>> call =
                service.getSpecificPaymentMethods(PUBLIC_KEY, PAYMENT_TYPE_ID_PREDETERMINADO);

        Spinner spinner = findViewById(R.id.tarjeta_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
    }

    public void editarMonto(View view) {
        Intent intentBack = new Intent(PaymentMethodActivity.this, MainActivity.class);
        intentBack.putExtra(getString(R.string.monto_key_putExtra), mMontoIngresado.getText().toString());
        startActivity(intentBack);
    }

    public void enviarTarjeta(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
