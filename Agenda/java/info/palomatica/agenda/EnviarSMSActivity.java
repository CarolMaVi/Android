package info.palomatica.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

public class EnviarSMSActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_sms);

        int id = getIntent().getIntExtra(MainActivity.KEY_ID_CONTACTO, -1);
        ContactosDB contactosDB = ContactosDB.getInstance(this);
        Contacto contacto = contactosDB.getContacto(id);



        // onClick
        //String mensaje = etMensaje.getText().toString();
        //SmsManager sms = SmsManager.getDefault();
        //sms.sendTextMessage(contacto.getTelefono(), null, mensaje, null, null);


    }
}
