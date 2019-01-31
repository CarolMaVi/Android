package info.palomatica.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ContactosDB extends SQLiteOpenHelper
{

    private SQLiteDatabase db;

    private static ContactosDB contactosDB = null;


    public static ContactosDB getInstance(Context context)
    {
        if(contactosDB == null)
        {
            contactosDB = new ContactosDB(context);
        }

        return contactosDB;
    }


    private ContactosDB(Context context)
    {
        super(context, "contactos.db", null, 1);
        db = getWritableDatabase();
    }

    public ContactosDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE contactos (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre TEXT," +
                "telefono TEXT," +
                "categoria INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public boolean insertarContacto(Contacto contacto)
    {
        //Se puede comprobar si ya existe un contacto o no

        ContentValues contentValues = new ContentValues();

        contentValues.put("nombre", contacto.getNombre());
        contentValues.put("telefono", contacto.getTelefono());
        contentValues.put("categoria", contacto.getCategoria());

        db.insert("contactos", null, contentValues);

        /*  Otra forma

        db.execSQL("INSERT INTO contactos (nombre, telefono, categoria) values " +
                "('" + contacto.getNombre() + "','" + contacto.getTelefono() + "'," +
                + contacto.getCategoria() + ");");
        */
        return true;
    }

    public ArrayList<Contacto> getContactos()
    {
        Cursor cursor = db.rawQuery("SELECT _id, nombre, telefono, categoria FROM contactos order by nombre;", null);
        ArrayList<Contacto> alContactos = new ArrayList<>(cursor.getCount());

        Contacto contacto;

        if(cursor.moveToFirst())
        {
            do
            {
                contacto = new Contacto();

                contacto.setId(cursor.getInt(0))
                        .setNombre(cursor.getString(1))
                        .setTelefono(cursor.getString(2))
                        .setCategoria(cursor.getInt(3));

                alContactos.add(contacto);
            }
            while (cursor.moveToNext());
        }

        return alContactos;
    }

    public void eliminarContacto(int id)
    {
        db.execSQL("delete from contactos where _id = " + id);
    }

    public Contacto getContacto(int id)
    {
        Cursor cursor = db.rawQuery("SELECT _id, nombre, telefono, categoria FROM contactos where _id = " + id, null);
        cursor.moveToFirst();
        Contacto contacto = new Contacto();
        contacto.setId(cursor.getInt(0))
                .setNombre(cursor.getString(1))
                .setTelefono(cursor.getString(2))
                .setCategoria(cursor.getInt(3));
        return contacto;
    }
}
