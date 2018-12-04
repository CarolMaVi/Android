package info.palomatica.desaparecerbotones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity2 extends AppCompatActivity
{

    private LinearLayout llBotones;
    private String tiempoFinal;
    public static final String KEY2 = "key_2";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        llBotones = findViewById(R.id.llBotones);
        String numBt = getIntent().getStringExtra(MainActivity.KEY_TEXTO);

        Button[] botones = new Button[Integer.parseInt(numBt)];

        for (int i = 0; i < botones.length; i++)
        {
            botones[i] = new Button(MainActivity2.this);
            botones[i].setText(getResources().getString(R.string.boton) + " " + (i + 1));

            botones[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    llBotones.removeView(v);

                    if(llBotones.getChildCount() == 0)
                    {
                        Intent it = new Intent();
                        tiempoFinal = String.valueOf(System.currentTimeMillis());

                        it.putExtra(KEY2, tiempoFinal);

                        setResult(RESULT_OK, it);
                        finish();
                    }
                }
            });

            llBotones.addView(botones[i]);
        }
    }
}
