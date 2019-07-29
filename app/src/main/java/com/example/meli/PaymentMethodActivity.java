package com.example.meli;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private String mFiltroRetrofit;
    private RecyclerView mRecycler;
    private Button mEditAmount;
    private String mMonto;
    private Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        mMontoIngresado = findViewById(R.id.monto_mainActivity);
        mEditAmount = findViewById(R.id.editarMonto_button);
        mSend = findViewById(R.id.recycler_button);

        mEditAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(PaymentMethodActivity.this, MainActivity.class);
                intentBack.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                startActivity(intentBack);
            }
        });

        Intent intent = getIntent();
        mMonto = intent.getStringExtra(getString(R.string.monto_key_putExtra));
        mMontoIngresado.setText(mMonto + "$");

        mFiltroRetrofit = "Ninguno";

        //Filter spinner code
        Spinner spinner = findViewById(R.id.tarjeta_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.paymenttype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        //RecyclerView code
        mRecycler = (RecyclerView) findViewById(R.id.payment_recyclerview);

        retrofitResponseHandler(mRecycler);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardRecyclerAdapter mAdapter = (CardRecyclerAdapter) mRecycler.getAdapter();
                PaymentMethodModel toIntent = mAdapter.getSelected();

                Intent intent = new Intent(PaymentMethodActivity.this, BankActivity.class);
                intent.putExtra(getString(R.string.monto_key_putExtra), mMonto);
                intent.putExtra(getString(R.string.id_payment_key_putExtra), toIntent.getId());
                intent.putExtra(getString(R.string.cardType_key_putExtra), toIntent.getType_id());
                intent.putExtra(getString(R.string.card_key_putExtra), toIntent.getName());
                intent.putExtra(getString(R.string.image_key_putExtra), toIntent.getImageURL());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerLabel = parent.getItemAtPosition(position).toString();

        if (spinnerLabel == parent.getItemAtPosition(0).toString()) {
            mFiltroRetrofit = spinnerLabel;
        } else if (spinnerLabel == parent.getItemAtPosition(1).toString()) {
            mFiltroRetrofit = "debit_card";
        } else if (spinnerLabel == parent.getItemAtPosition(2).toString()) {
            mFiltroRetrofit = "credit_card";
        } else if (spinnerLabel == parent.getItemAtPosition(3).toString()) {
            mFiltroRetrofit = "ticket";
        }

        retrofitResponseHandler(mRecycler);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public List<PaymentMethodModel> retrofitPaymentMethodHelper(Response<List<PaymentMethodModel>> response) {
        List<PaymentMethodModel> lista = response.body();
        List<PaymentMethodModel> reemplazo = new ArrayList<PaymentMethodModel>();

        PaymentMethodModel helper;
        String type = "Ninguno";
        if (!mFiltroRetrofit.equals(type)) {
            for (int i = 0; i < lista.size(); i++) {
                helper = lista.get(i);
                type = helper.getType_id();
                if (type.equals(mFiltroRetrofit)) {
                    reemplazo.add(helper);
                }
            }
        } else {
            reemplazo.addAll(0, lista);
        }

        return reemplazo;
    }

    public void retrofitResponseHandler(final RecyclerView recycler) {
        /*Create handle for the RetrofitInstance interface*/
        RetrofitInterface service = RetrofitClientInstance.getRetrofitInstance()
                .create(RetrofitInterface.class);
        Call<List<PaymentMethodModel>> call =
                service.getSpecificPaymentMethods(PUBLIC_KEY);
        call.enqueue(new Callback<List<PaymentMethodModel>>() {
            @Override
            public void onResponse(Call<List<PaymentMethodModel>> call,
                                   Response<List<PaymentMethodModel>> response) {
                List<PaymentMethodModel> handler = retrofitPaymentMethodHelper(response);

                recycler.setLayoutManager(new LinearLayoutManager(PaymentMethodActivity.this));
                recycler.setAdapter(new CardRecyclerAdapter(PaymentMethodActivity.this, handler));
            }

            @Override
            public void onFailure(Call<List<PaymentMethodModel>> call, Throwable t) {
                Toast.makeText(PaymentMethodActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                SystemClock.sleep(1000);
                Intent intentBack = new Intent(PaymentMethodActivity.this, MainActivity.class);
                intentBack.putExtra(getString(R.string.monto_key_putExtra), mMontoIngresado.getText().toString());
                startActivity(intentBack);
            }
        });
    }
}
