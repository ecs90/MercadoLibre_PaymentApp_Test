package com.example.meli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    //Key for accessing retrofit methods
    private static final String PUBLIC_KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";

    private TextView mMontoIngresado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        mMontoIngresado = findViewById(R.id.monto_mainActivity);

        Intent intent = getIntent();
        String monto = intent.getStringExtra(getString(R.string.monto_key_putExtra));
        mMontoIngresado.setText(monto);

        final Spinner spinner = findViewById(R.id.tarjeta_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(PaymentMethodActivity.this);
        }

        /*Create handle for the RetrofitInstance interface*/
        RetrofitInterface service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterface.class);
        Call<List<PaymentMethodModel>> call =
                service.getSpecificPaymentMethods(PUBLIC_KEY);
        call.enqueue(new Callback<List<PaymentMethodModel>>() {
            @Override
            public void onResponse(Call<List<PaymentMethodModel>> call,
                                   Response<List<PaymentMethodModel>> response) {

                List<PaymentMethodModel> lista = response.body();

                List<PaymentMethodModel> reemplazo = new ArrayList<PaymentMethodModel>();
                PaymentMethodModel helper;
                String creditCard;
                for (int i = 0; i < lista.size(); i++) {
                    helper = lista.get(i);
                    creditCard = helper.getType_id();
                    if (creditCard.equals("credit_card")) {
                        reemplazo.add(helper);
                    }
                }
                ArrayAdapter adapter = new ArrayAdapter<>(PaymentMethodActivity.this,
                        R.layout.item_imagetext, lista);
                /*adapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);
                if (spinner != null) {
                    spinner.setAdapter(adapter);
                }*/
            }

            @Override
            public void onFailure(Call<List<PaymentMethodModel>> call, Throwable t) {

            }
        });

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
