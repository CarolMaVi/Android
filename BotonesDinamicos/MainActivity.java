package info.palomatica.botonesdinamicos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
{
    private final String KEY_BOTONES = "key_botones";
    private final String KEY_CONT = "key_cont";
    private int cont = 0;
    private LinearLayout llBotones;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        llBotones = findViewById(R.id.llBotones);

        if (bundle != null) //Si venimos de girar el movil
        {
            int[] numeros = bundle.getIntArray(KEY_BOTONES);

            Button bt;

            for (int i = 0; i < numeros.length; i++)
            {
                bt = crearBoton(numeros[i]);
                llBotones.addView(bt);
            }

            cont = bundle.getInt(KEY_CONT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);

        int[] numeros = new int[llBotones.getChildCount()];

        for (int i = 0; i < llBotones.getChildCount(); i++)
        {
            Button bt = (Button) llBotones.getChildAt(i);
            String strNum = bt.getText() + "";

            numeros[i] = Integer.parseInt(strNum);
        }

        bundle.putInt(KEY_CONT, cont);
        bundle.putIntArray(KEY_BOTONES, numeros);
    }

    public void onClickAñadir(View view)
    {
        Button bt = crearBoton(cont);
        cont++;

        llBotones.addView(bt);
    }

    @NonNull
    private Button crearBoton(int num)
    {
        Button bt = new Button(this);
        bt.setText(num + "");

        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Button bt = (Button) v;
                String strNum = bt.getText().toString();
                int num = Integer.parseInt(strNum);

                num++;

                bt.setText(num + "");
            }
        });

        bt.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                llBotones.removeView(v);
                return true;
            }
        });

        return bt;
    }

    public void onClickBorrarTodo(View view)
    {
        llBotones.removeAllViews();

        cont = 0;
    }

    public void onClickReset(View view)
    {
        for (int i = 0; i < llBotones.getChildCount(); i++)
        {
            ((Button) llBotones.getChildAt(i)).setText("0");
        }

        cont = 0;
    }
}
