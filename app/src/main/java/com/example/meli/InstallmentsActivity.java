package com.example.meli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstallmentsActivity extends AppCompatActivity {
    //Key for accessing retrofit methods
    private static final String PUBLIC_KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";

    private TextView mMontoIngresado;
    private TextView mPago;
    private ImageView mImagenPago;
    private Button mEditAmount;
    private Button mEditPaymentMethod;
    private Button mSend;
    private String mMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installments);

        mMontoIngresado = findViewById(R.id.monto_mainActivity);
        mPago = findViewById(R.id.pay_plus_method);
        mImagenPago = findViewById(R.id.image_from_payment);
        mEditAmount = findViewById(R.id.editarMonto_button);
        mEditPaymentMethod = findViewById(R.id.editarMedio_button);
        mSend = findViewById(R.id.recycler_button);

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

        mEditAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(InstallmentsActivity.this, MainActivity.class);
                intento.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                startActivity(intento);
            }
        });

        mEditPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstallmentsActivity.this, PaymentMethodActivity.class);
                intent.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                startActivity(intent);
            }
        });
    }

    public void editarBanco(View view) {
        Intent intentBack = new Intent(InstallmentsActivity.this, BankActivity.class);
        intentBack.putExtra(getString(R.string.monto_key_putExtra), mMonto);
        intentBack.putExtra(getString(R.string.id_payment_key_putExtra), id);
        intentBack.putExtra(getString(R.string.cardType_key_putExtra), tipo);
        intentBack.putExtra(getString(R.string.card_key_putExtra), nombre);
        intentBack.putExtra(getString(R.string.image_key_putExtra), imagen);
        intentBack.putExtra(getString(R.string.id_tipo_key), toIntent.getId());
        intentBack.putExtra(getString(R.string.imagen_tipo_key), toIntent.getImageURL());
        intentBack.putExtra(getString(R.string.name_tipo_key), toIntent.getName());
        startActivity(intentBack);
    }
}
