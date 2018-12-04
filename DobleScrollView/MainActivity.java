package info.palomatica.examen1ev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
{

    private LinearLayout llIzquierda;
    private LinearLayout llDerecha;

    private int colorIzq;
    private int colorDer;

    private final String KEY_NUM_RECTANGULOS_IZQ = "num_rec_izq";

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_NUM_RECTANGULOS_IZQ, llIzquierda.getChildCount());
        outState.putInt(KEY_NUM_RECTANGULOS_DER, llDerecha.getChildCount());
    }

    private final String KEY_NUM_RECTANGULOS_DER = "num_rec_der";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llIzquierda = findViewById(R.id.llIzquierda);
        llDerecha = findViewById(R.id.llDerecha);

        colorIzq =  R.color.color1;
        colorDer = R.color.color2;

        //Si no es null es que venimos de un giro
        if(savedInstanceState != null)
        {
            int numIzquierda = savedInstanceState.getInt(KEY_NUM_RECTANGULOS_IZQ);
            int numDerecha = savedInstanceState.getInt(KEY_NUM_RECTANGULOS_DER);

            for (int i = 0; i < numIzquierda; i++)
            {
                Button btRectangulo = new Button(this);
                anadirRectangulo(llIzquierda, btRectangulo, colorIzq);
                colorIzq = alternarColor(colorIzq);
            }

            for (int i = 0; i < numDerecha; i++)
            {
                Button btRectangulo = new Button(this);
                anadirRectangulo(llDerecha, btRectangulo, colorDer);
                colorDer = alternarColor(colorDer);
            }
        }
    }

    public void onClickAnadir(View view)
    {
        Button btRectangulo = new Button(this);

        //Preguntamos si es un boton de la izquierda o de la derecha, accediendo a su identificador
        if(view.getId() == R.id.btAnadirIzq){
            colorIzq = alternarColor(colorIzq);
            anadirRectangulo(llIzquierda,btRectangulo,colorIzq);
        }
        else{
            colorDer = alternarColor(colorDer);
            anadirRectangulo(llDerecha,btRectangulo,colorDer);
        }

    }

    private void anadirRectangulo(LinearLayout linearLayout, Button button, int colorResource)
    {
        button.setBackgroundResource(colorResource);
        linearLayout.addView(button);

    }

    private int alternarColor(int color){
        if(color == R.color.color1){
            color = R.color.color2;
        }
        else{
            color = R.color.color1;
        }
        return color;
    }

    public void onClickEliminar(View view)
    {
        //Si hay algun boton en el LinearLayout, podemos eliminar algo
        if((view.getId() == R.id.btEliminarIzq || view.getId() == R.id.llIzquierda) && llIzquierda.getChildCount() > 0){
            llIzquierda.removeViewAt(llIzquierda.getChildCount() - 1);
            colorIzq = alternarColor(colorIzq);
        }
        else if(view.getId() == R.id.btEliminarDer && llDerecha.getChildCount() > 0){
            llDerecha.removeViewAt(llDerecha.getChildCount() - 1);
            colorDer = alternarColor(colorDer);
        }
    }
}
