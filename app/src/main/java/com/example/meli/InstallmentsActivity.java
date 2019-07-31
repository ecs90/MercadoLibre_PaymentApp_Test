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
    private PaymentMethodModel mCard;
    private BankModel mBank;
    private TextView mBankTV;
    private ImageView mBankImage;
    private RecyclerView mInstallmentRecycler;

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
        mBankTV = findViewById(R.id.bank_textview);
        mBankImage = findViewById(R.id.bank_imageview);
        mInstallmentRecycler = findViewById(R.id.installments_recyclerview);

        //Bring back things on previous Actvt and set on Amount View
        Intent intent = getIntent();
        mMonto = intent.getStringExtra(getString(R.string.monto_key_putExtra));
        mMontoIngresado.setText(mMonto + "$");
        mEditAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(InstallmentsActivity.this, MainActivity.class);
                intento.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                startActivity(intento);
            }
        });

        //set PaymentMethod View
        String tipo = intent.getStringExtra(getString(R.string.cardType_key_putExtra));
        String nombre = intent.getStringExtra(getString(R.string.card_key_putExtra));
        String nombreYtipo = nombre;
        String id = intent.getStringExtra(getString(R.string.id_payment_key_putExtra));
        String imagenTarjeta = intent.getStringExtra(getString(R.string.image_key_putExtra));
        Picasso.with(this)
                .load(imagenTarjeta)
                .into(mImagenPago);
        mCard = new PaymentMethodModel(id, nombre, tipo, imagenTarjeta);
        if (!tipo.equals("debit_card")) {
            nombreYtipo = nombre + " (" + tipo + ")";
        }
        mPago.setText(nombreYtipo);
        mEditPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstallmentsActivity.this, PaymentMethodActivity.class);
                intent.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                startActivity(intent);
            }
        });

        //set selected Bank View
        String bankId = intent.getStringExtra(getString(R.string.id_bank_key));
        String bankName = intent.getStringExtra(getString(R.string.name_bank_key));
        String bankImage = intent.getStringExtra(getString(R.string.imagen_bank_key));
        Picasso.with(this)
                .load(bankImage)
                .into(mBankImage);
        mBank = new BankModel(bankId, bankName, bankImage);
        mBankTV.setText(bankName);


    }

    public void editarBanco(View view) {
        Intent intentBack = new Intent(InstallmentsActivity.this, BankActivity.class);
        intentBack.putExtra(getString(R.string.monto_key_putExtra), mMonto);
        intentBack.putExtra(getString(R.string.id_payment_key_putExtra), mCard.getId());
        intentBack.putExtra(getString(R.string.cardType_key_putExtra), mCard.getType_id());
        intentBack.putExtra(getString(R.string.card_key_putExtra), mCard.getName());
        intentBack.putExtra(getString(R.string.image_key_putExtra), mCard.getImageURL());
        startActivity(intentBack);
    }

    public void retrofitResponseHandler(final RecyclerView recycler, String payment_id) {
        /*Create handle for the RetrofitInstance interface*/
        RetrofitInterface service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterface.class);
        Call<List<InstallmentsModel>> call =
                service.getInstallments(PUBLIC_KEY, mMonto, mCard.getType_id());
        call.enqueue(new Callback<List<InstallmentsModel>>() {
            @Override
            public void onResponse(Call<List<InstallmentsModel>> call, Response<List<InstallmentsModel>> response) {

                recycler.setLayoutManager(new LinearLayoutManager(InstallmentsActivity.this));
                recycler.setAdapter(new InstallmentsRecyclerAdapter(InstallmentsActivity.this,
                        response.body()));
            }

            @Override
            public void onFailure(Call<List<InstallmentsModel>> call, Throwable t) {
                Toast.makeText(InstallmentsActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                SystemClock.sleep(1000);
                Intent intentBack = new Intent(InstallmentsActivity.this, BankActivity.class);
                intentBack.putExtra(getString(R.string.monto_key_putExtra), mMontoIngresado.getText().toString());
                startActivity(intentBack);
            }
        });
    }
}
