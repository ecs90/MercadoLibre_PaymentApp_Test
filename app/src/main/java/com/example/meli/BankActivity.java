package com.example.meli;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankActivity extends AppCompatActivity {
    //Key for accessing retrofit methods
    private static final String PUBLIC_KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";

    private TextView mMontoIngresado;
    private TextView mPago;
    private ImageView mImagenPago;
    private Button mEditAmount;
    private Button mEditPaymentMethod;
    private String mMonto;
    private RecyclerView mRecycler;
    private Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        mMontoIngresado = findViewById(R.id.monto_mainActivity);
        mPago = findViewById(R.id.pay_plus_method);
        mImagenPago = findViewById(R.id.image_from_payment);
        mEditAmount = findViewById(R.id.editarMonto_button);
        mEditPaymentMethod = findViewById(R.id.editarMedio_button);
        mSend = findViewById(R.id.recycler_button);

        mEditAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(BankActivity.this, MainActivity.class);
                intento.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                startActivity(intento);
            }
        });

        mEditPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankActivity.this, PaymentMethodActivity.class);
                intent.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        mMonto = intent.getStringExtra(getString(R.string.monto_key_putExtra));
        mMontoIngresado.setText(mMonto + "$");

        final String imagen = intent.getStringExtra(getString(R.string.image_key_putExtra));
        Picasso.with(this)
                .load(imagen)
                .into(mImagenPago);

        final String tipo = intent.getStringExtra(getString(R.string.cardType_key_putExtra));
        final String nombre = intent.getStringExtra(getString(R.string.card_key_putExtra));
        String nombreYtipo = nombre;
        final String id = intent.getStringExtra(getString(R.string.id_payment_key_putExtra));

        if (!tipo.equals("debit_card")) {
            nombreYtipo = nombre + " (" + tipo + ")";
        }
        mPago.setText(nombreYtipo);


        mRecycler = (RecyclerView) findViewById(R.id.payment_recyclerview);

        retrofitResponseHandler(mRecycler, intent.getStringExtra(getString(R.string.id_payment_key_putExtra)));

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankRecyclerAdapter mAdapter = (BankRecyclerAdapter) mRecycler.getAdapter();
                BankModel toIntent = mAdapter.getSelected();

                Intent intent = new Intent(BankActivity.this, InstallmentsActivity.class);
                intent.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                intent.putExtra(getString(R.string.id_payment_key_putExtra), id);
                intent.putExtra(getString(R.string.cardType_key_putExtra), tipo);
                intent.putExtra(getString(R.string.card_key_putExtra), nombre);
                intent.putExtra(getString(R.string.image_key_putExtra), imagen);
                intent.putExtra(getString(R.string.id_bank_key), toIntent.getId());
                intent.putExtra(getString(R.string.imagen_bank_key), toIntent.getImageURL());
                intent.putExtra(getString(R.string.name_bank_key), toIntent.getName());
                startActivity(intent);
            }
        });

    }

    public void retrofitResponseHandler(final RecyclerView recycler, String payment_id) {
        /*Create handle for the RetrofitInstance interface*/
        RetrofitInterface service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterface.class);
        Call<List<BankModel>> call =
                service.getBanksByPaymentMethod(PUBLIC_KEY, payment_id);
        call.enqueue(new Callback<List<BankModel>>() {
            @Override
            public void onResponse(Call<List<BankModel>> call, Response<List<BankModel>> response) {

                recycler.setLayoutManager(new LinearLayoutManager(BankActivity.this));
                recycler.setAdapter(new BankRecyclerAdapter(BankActivity.this, response.body()));
                if (response.body().isEmpty()) {
                    Intent intento = getIntent();

                    Intent intent = new Intent(BankActivity.this, InstallmentsActivity.class);
                    intent.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                    intent.putExtra(getString(R.string.id_payment_key_putExtra),
                            intento.getStringExtra(getString(R.string.id_payment_key_putExtra)));
                    intent.putExtra(getString(R.string.cardType_key_putExtra),
                            intento.getStringExtra(getString(R.string.cardType_key_putExtra)));
                    intent.putExtra(getString(R.string.card_key_putExtra),
                            intento.getStringExtra(getString(R.string.card_key_putExtra)));
                    intent.putExtra(getString(R.string.image_key_putExtra),
                            intento.getStringExtra(getString(R.string.image_key_putExtra)));
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<BankModel>> call, Throwable t) {
                Toast.makeText(BankActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                SystemClock.sleep(1000);
                Intent intentBack = new Intent(BankActivity.this, PaymentMethodActivity.class);
                intentBack.putExtra(getString(R.string.monto_key_putExtra), mMontoIngresado.getText().toString());
                startActivity(intentBack);
            }
        });
    }

}
