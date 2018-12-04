package info.palomatica.desaparecerbotones;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etNumero;
    private TextView tvTiempo;
    public long tiempoInicial;
    public static final String KEY_TEXTO = "key_texto";
    public static final int REQUEST_CODE_ACTIVITY_SEGUNDO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumero = findViewById(R.id.etNumero);
        tvTiempo = findViewById(R.id.tvTiempo);

        tvTiempo.setText(getResources().getString(R.string.hasTardado, 1.5f));
    }

    public void onclickGenerarBt(View view)
    {
        Intent it = new Intent(this, MainActivity2.class);
        it.putExtra(KEY_TEXTO, etNumero.getText().toString());
        tiempoInicial = System.currentTimeMillis();
        startActivityForResult(it, REQUEST_CODE_ACTIVITY_SEGUNDO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent it)
    {
        super.onActivityResult(requestCode, resultCode, it);

        if(requestCode == REQUEST_CODE_ACTIVITY_SEGUNDO && resultCode == RESULT_OK)
        {
            long tiempo = Long.valueOf(it.getStringExtra(MainActivity2.KEY2)).longValue() - tiempoInicial;




            tvTiempo.setText(getResources().getString(R.string.hasTardado, tiempo));
        }
    }
}
