package com.example.meli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mMontoIngresado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMontoIngresado = findViewById(R.id.montoEnviado_editText);

        Intent i = getIntent();
        String monto = i.getStringExtra(getString(R.string.monto_key_putExtra));
        if (monto != null) {
            mMontoIngresado.setText(monto);
        }
    }

    public void enviarMonto(View view) {
        mMontoIngresado = findViewById(R.id.montoEnviado_editText);
        Intent intent = new Intent(MainActivity.this, PaymentMethodActivity.class);
        intent.putExtra(getString(R.string.monto_key_putExtra), mMontoIngresado.getText().toString());
        startActivity(intent);
    }
}
