package info.palomatica.calculadora;

import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private TextView tvCalculadora;
    private  Button btOperacion;
    private double valor1, valor2, resultado;
    private boolean sumar, restar, multiplicar, dividir, nuevaOperacion;
    private ColorStateList colorTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCalculadora = findViewById(R.id.tvCalculadora);

        nuevaOperacion = true;  //Sirve para limpiar el TextView si hay una operación nueva

        //Booleanos para determinar el tipo de operación
        sumar = false;
        restar = false;
        multiplicar = false;
        dividir = false;

        //Dos operandos
        valor1 = 0;
        valor2 = 0;

        colorTV = tvCalculadora.getTextColors(); //Variable con el color original del TextView (Para restablecerlo si hay errores)
    }

    public void onClickPonerNumero(View view)
    {
        //TODO Diferenciar entre numeros enteros y decimales

        if(nuevaOperacion)
        {
            tvCalculadora.setTextColor(colorTV); //Restablecemos el color por defecto (Por si la ultima operación ha dado un resultado erroneo)
            tvCalculadora.setText(((Button)view).getText().toString()); //Escribe el numero pulsado

            nuevaOperacion = false; //Para la siguiente instrucción
        }
        else tvCalculadora.setText(tvCalculadora.getText() + ((Button)view).getText().toString()); //Escribe los numeros existentes y el pulsado
    }

    public void onClickPonerDecimal(View view)
    {
        if(tvCalculadora.length() != 0) //  Impide poner un decimal sin que existan numeros
        {
            tvCalculadora.setTextColor(colorTV); //Restablecemos el color por defecto (Al borrar el TextView queda un 0, asi que puede ponerse una coma detras)
            tvCalculadora.setText(tvCalculadora.getText() + ((Button)view).getText().toString()); //Escribe los numeros existentes y el punto

            nuevaOperacion = false; //Para la siguiente instrucción
        }
    }

    public void onClickResta(View view) //Caso especial: Poner numero negativo/Operacion de resta
    {
        if(nuevaOperacion)
            onClickPonerNumero(view);
        else onClickOperacion(view);
    }

    public void onClickOperacion(View view)
    {
        btOperacion = (Button) view;

        if(tvCalculadora.length() != 0) //Impide realizar la operación sin que existan numeros
        {
            valor1 = Double.parseDouble(tvCalculadora.getText().toString()); //El contenido actual del TextView se guarda para usarlo como primer operando

            tvCalculadora.setText(""); //Limpia el TextView para el siguiente operando

            if(btOperacion.getText().equals("+"))  //Activamos la operacion segun el caso
                sumar = true;
            else if(btOperacion.getText().equals("-"))
                restar = true;
            else if(btOperacion.getText().equals("*"))
                multiplicar = true;
            else if(btOperacion.getText().equals("/"))
                dividir = true;
        }
    }

    //TODO Despues de una operacion, si seguimos pulsando "=" se realiza otra vez la operacion

    public void onClickIgual(View view)
    {
        valor2 = Double.parseDouble(tvCalculadora.getText().toString()); //El contenido actual del TextView se guarda para usarlo como segundo operando

        if(sumar) //Comprueba cual de las operaciones se ha activado y muestra el resultado
        {
            resultado = valor1 + valor2;

            tvCalculadora.setText(resultado + "");
            sumar = false; //Los booleanos de las operaciones vuelven a ponerse en false para la siguiente operacion
        }
        else if(restar)
        {
            resultado = valor1 - valor2;

            tvCalculadora.setText(resultado + "");
            restar = false;
        }
        else if(multiplicar)
        {
            resultado = valor1 * valor2;

            tvCalculadora.setText(resultado + "");
            multiplicar = false;
        }
        else if(dividir)
        {
            resultado = valor1 / valor2;

            if(Double.isNaN(resultado)) //Si el resultado es erroneo
            {
                tvCalculadora.setTextColor(getResources().getColor(R.color.colorError));
                tvCalculadora.setText("ERROR");
            }
            else tvCalculadora.setText(resultado + "");

            dividir = false;
        }

        nuevaOperacion = true; //Al terminar una operación prepara otra nueva
    }

    public void onClickBorrar(View view) //Borra solo el ultimo caracter
    {
        if(tvCalculadora.getText().length() != 0) //Impide realizar la operación sin que existan numeros
            tvCalculadora.setText(tvCalculadora.getText().subSequence(0, tvCalculadora.getText().length() - 1));
        else Toast.makeText(this, "No hay números", Toast.LENGTH_SHORT).show(); //Muestra un toast alegando que el TextView esta vacio
    }

    public void onClickBorrarTodo(View view)
    {
        tvCalculadora.setText("0");  //Reseteamos el TextView, las variables, el color y empezamos una operacion nueva
        valor1 = 0;
        valor2 = 0;

        tvCalculadora.setTextColor(colorTV);

        nuevaOperacion = true;
    }
}