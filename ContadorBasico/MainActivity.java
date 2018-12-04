package info.palomatica.holamundo;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int cont = 0;
    private TextView tvNumero;
    private static final String KEY_CONT = "key_contador";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ciclovida","onCreate");

        tvNumero = findViewById(R.id.tvNumero); //Asi recuperamos el TextView del numero

        /*Un objeto de la clase Bundle es similar a una coleccion de tipo Map*/

        if(savedInstanceState != null)
        {
            cont = savedInstanceState.getInt(KEY_CONT);
            tvNumero.setText(cont + "");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ciclovida","onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ciclovida","onPause" + cont);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ciclovida","onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ciclovida","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ciclovida","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ciclovida","onRestart");
    }

    public void onClickIncrementar(View view) {
        cont++;
        tvNumero.setText(cont + "");//Convertimos el int cont a Charsequence o a String concatenandole unas comillas
    }

    public void onClickDecrementar(View view) {

        if(cont > 0)
        {
            cont--;
            tvNumero.setText(cont + "");
        }
        else
        {//Para importar Alt + Enter
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);//Añadir permiso vibrate

            //Salta un toast con un mensaje para avisar de que se ha llegado al cero
            Toast.makeText(this, R.string.yaCero, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Este metodo nos permite guardar los datos de la aplicacion en el Bundle
     * para que al volver a abrir la app, girarlo, etc, tengamos los mismos.
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*Un objeto de la clase Bundle es similar a una coleccion de tipo Map*/
        //Usamos el metodo putInt() porque queremos guardar nuestro contador que es un int
        outState.putInt(KEY_CONT, cont);
    }
}
