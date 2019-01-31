package info.palomatica.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class CrearContactoActivity extends AppCompatActivity
{

    private EditText etNombre;
    private EditText etTelefono;
    private int categoria = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_contacto);

        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);

    }

    public void onClickAceptar(View view)
    {
        ContactosDB contactosDB = ContactosDB.getInstance(this);

        Contacto contacto = new Contacto();
        contacto.setNombre(etNombre.getText().toString()).
                setTelefono(etTelefono.getText().toString()).
                setCategoria(categoria);

        contactosDB.insertarContacto(contacto);
        setResult(RESULT_OK);
        finish();
    }

    public void onClickCategoria(View view)
    {
        if(view.getTag().equals("0")) // Amigos
        {
            view.setBackgroundResource(R.drawable.cat_familia);
            view.setTag("1");
            categoria = Contacto.CAT_FAMILIARES;
        }
        else if(view.getTag().equals("1"))
        {
            view.setBackgroundResource(R.drawable.cat_trabajo);
            view.setTag("2");
            categoria = Contacto.CAT_TRABAJO;
        }
        else if(view.getTag().equals("2"))
        {
            view.setBackgroundResource(R.drawable.cat_amigos);
            view.setTag("0");
            categoria = Contacto.CAT_AMIGOS;
        }
    }
}
