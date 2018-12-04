package info.palomatica.colores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private LinearLayout llColores, llAux;
    private TextView tv, tvAux;
    private boolean iguales = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llColores = findViewById(R.id.llColores);
    }

    public void onClickColor(View view)
    {
        tv = ((TextView) view);

        if (tv.getTag().equals("rojo"))
        {
            tv.setBackgroundColor(getResources().getColor(R.color.Azul));
            tv.setText(getResources().getString(R.string.tvAzul));
            tv.setTag("azul");
        }
        else if (tv.getTag().equals("verde"))
        {
            tv.setBackgroundColor(getResources().getColor(R.color.Rojo));
            tv.setText(getResources().getString(R.string.tvRojo));
            tv.setTag("rojo");
        }
        else
        {
            tv.setBackgroundColor(getResources().getColor(R.color.Verde));
            tv.setText(getResources().getString(R.string.tvVerde));
            tv.setTag("verde");
        }

        iguales = true;

        for (int i = 0; i < llColores.getChildCount() && iguales; i++)
        {
            llAux = (LinearLayout) llColores.getChildAt(i); //Cada hijo del LinearLayout principal es a su vez un LinearLayout

            for (int j = 0; j < llAux.getChildCount() && iguales; j++) //Recorremos cada LinearLayout hijo para comparar los colores de los TextView
            {
                tvAux = (TextView) llAux.getChildAt(j); //Cada hijo es un TextView

                if(!tv.getTag().equals(tvAux.getTag())) //Si todavía hay al menos un color diferente, el juego continúa
                    iguales = false;
            }
        }

        if(iguales)  //Si todos los colores son iguales
            Toast.makeText(this, "Fin del juego", Toast.LENGTH_SHORT).show();
    }
}