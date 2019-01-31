package info.palomatica.agenda;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{


    private final int REQUEST_CODE_CREAR_CONTACTO = 0;
    private LinearLayout llContactos;

    public final static String KEY_ID_CONTACTO = "key_contacto";

    private ContactosDB contactosDB;

    // Concesión de permisos
    private final static int REQUEST_CODE_PERMISO_LLAMAR = 0;
    private final static int REQUEST_CODE_PERMISO_IMPORTAR_CONTACTOS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llContactos = findViewById(R.id.llContactos);

        contactosDB = ContactosDB.getInstance(this);

        dibujarContactos();

    }

    private void dibujarContactos()
    {
        llContactos.removeAllViews();

        LayoutInflater layoutInflater = getLayoutInflater();

        RelativeLayout rlContacto;

        for(final Contacto contacto: contactosDB.getContactos())
        {
            rlContacto = (RelativeLayout) layoutInflater.inflate(R.layout.item_contacto, null);
            TextView tvNombre = rlContacto.findViewById(R.id.tvNombre);
            TextView tvTelefono = rlContacto.findViewById(R.id.tvTelefono);
            ImageView ivIcono = rlContacto.findViewById(R.id.ivIcono);

            tvNombre.setText(contacto.getNombre());
            tvTelefono.setText(contacto.getTelefono());

            switch (contacto.getCategoria())
            {
                case Contacto.CAT_AMIGOS:
                    ivIcono.setBackgroundResource(R.drawable.cat_amigos);
                    break;
                case Contacto.CAT_FAMILIARES:
                    ivIcono.setBackgroundResource(R.drawable.cat_familia);
                    break;
                case Contacto.CAT_TRABAJO:
                    ivIcono.setBackgroundResource(R.drawable.cat_trabajo);
                    break;
            }

            rlContacto.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(final View v)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle(R.string.dialogoBorrarContacto)
                            .setMessage(R.string.dialogoConfirmar)
                            .setPositiveButton(R.string.dialogoSi, new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    contactosDB.eliminarContacto(contacto.getId());
                                    llContactos.removeView(v);
                                    dialog.dismiss();
                                }
                            });
                    builder.setNegativeButton(R.string.dialogoNo, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return true;
                }
            });

            rlContacto.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String[] opciones = { getString(R.string.dialogoLlamar), getString(R.string.dialogoEnviarSMS)};



                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.dialogoOpciones);

                    builder.setItems(opciones, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(which == 0) // Llamar
                            {

                                if(!hayPermiso(Manifest.permission.CALL_PHONE))
                                {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_PERMISO_LLAMAR);
                                }
                                else
                                {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + contacto.getTelefono()));
                                    startActivity(intent);
                                }

                            }
                            else if(which == 1) // SMS
                            {
                                Intent intent = new Intent(MainActivity.this, EnviarSMSActivity.class);
                                intent.putExtra(KEY_ID_CONTACTO, contacto.getId());
                                startActivity(intent);
                            }

                            dialog.dismiss();
                        }
                    });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });


            View view = new View(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(10,15));


            llContactos.addView(rlContacto);
            llContactos.addView(view);
        }
    }


    public void onClickNuevoContacto(View view)
    {


        Intent intent = new Intent(this, CrearContactoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CREAR_CONTACTO);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_CREAR_CONTACTO)
        {
            dibujarContactos();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ppal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.mImportar)
        {
            new ImportarContactosTask().execute();
        }
        return super.onOptionsItemSelected(item);
    }

    private void importarContactos()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            // Si no hay permisos, se piden
            if(!hayPermiso(Manifest.permission.READ_CONTACTS))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_PERMISO_IMPORTAR_CONTACTOS);
                return;
            }
        }


        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String nombre = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);
                    if (pCur.moveToNext())
                    {
                        String telefono = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Contacto contacto = new Contacto();
                        contacto.setNombre(nombre);
                        contacto.setTelefono(telefono);
                        contacto.setCategoria(Contacto.CAT_AMIGOS);

                        contactosDB.insertarContacto(contacto);
                    }
                    pCur.close();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        if(requestCode == REQUEST_CODE_PERMISO_IMPORTAR_CONTACTOS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            // Permisos concedidos
            new ImportarContactosTask().execute();
        }
        else if(requestCode == REQUEST_CODE_PERMISO_LLAMAR && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            // Permiso de llamada concedido
        }
    }


    private boolean hayPermiso(String permiso)
    {
        int result = ContextCompat.checkSelfPermission(this, permiso);
        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private class ImportarContactosTask extends AsyncTask<Void, Void, Void>
    {
        private ProgressBar pbImportando;

        @Override
        protected void onPreExecute()
        {
            pbImportando = findViewById(R.id.pbImportando);
            pbImportando.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            importarContactos();
            try
            {
                Thread.sleep(5000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {

            dibujarContactos();
            pbImportando.setVisibility(View.INVISIBLE);

        }
    }
}
