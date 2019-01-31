package info.palomatica.agenda;

public class Contacto
{
    public static final int CAT_AMIGOS = 0;
    public static final int CAT_FAMILIARES = 1;
    public static final int CAT_TRABAJO = 2;

    private int id;
    private String nombre;
    private String telefono;
    private int categoria;

    public Contacto()
    {

    }

    public int getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public int getCategoria()
    {
        return categoria;
    }

    public Contacto setId(int id)
    {
        this.id = id;
        return this;
    }

    public Contacto setNombre(String nombre)
    {
        this.nombre = nombre;
        return this;
    }

    public Contacto setTelefono(String telefono)
    {
        this.telefono = telefono;
        return this;
    }

    public Contacto setCategoria(int categoria)
    {
        this.categoria = categoria;
        return this;
    }
}
