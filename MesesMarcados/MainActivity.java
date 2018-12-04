package info.palomatica.meses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static final String KEY_MESES = "key_meses";
    private LinearLayout llMeses;
    private TextView tvHasMarcado;
    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMeses = findViewById(R.id.llMeses);
        tvHasMarcado = findViewById(R.id.tvHasMarcado);

        String[] meses = getResources().getStringArray(R.array.meses);
        CheckBox cbMes;

        for (String mes : meses)
        {
            cbMes = new CheckBox(getApplicationContext());
            cbMes.setText(mes);

            cbMes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                        cont++;
                    else cont--;

                    tvHasMarcado.setText(getResources().getQuantityString(R.plurals.seHanMarcado, cont, cont));
                }
            });

            llMeses.addView(cbMes);
        }

        if (savedInstanceState != null)
        {
            boolean[] mesesMarcados = savedInstanceState.getBooleanArray(KEY_MESES);

            for (int i = 0; i < llMeses.getChildCount(); i++)
            {
                ((CheckBox) llMeses.getChildAt(i)).setChecked(mesesMarcados[i]);
            }
        }
    }

    public void onClickDesmarcar(View view)
    {
        for (int i = 0; i < llMeses.getChildCount(); i++)
        {
            View v = llMeses.getChildAt(i);

            if (v instanceof CheckBox)
            {
                CheckBox cb = (CheckBox) v;
                cb.setChecked(false);
            }
        }

        cont = 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        boolean[] meses = new boolean[llMeses.getChildCount()];

        for (int i = 0; i < llMeses.getChildCount(); i++)
        {
            meses[i] = ((CheckBox) llMeses.getChildAt(i)).isChecked();
        }

        outState.putBooleanArray(KEY_MESES, meses);
    }
}