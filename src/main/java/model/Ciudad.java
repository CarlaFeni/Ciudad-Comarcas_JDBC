package model;

public class Ciudad {

    private int id_ciudad;
    private String nombre;
    private int cp;
    private int habitantes;
    private float extension;
    private int id_comarca;

    public Ciudad(int id_ciudad, String nombre, int cp, int habitantes, float extension, int id_comarca) {
        this.id_ciudad = id_ciudad;
        this.nombre = nombre;
        this.cp = cp;
        this.habitantes = habitantes;
        this.extension = extension;
        this.id_comarca = id_comarca;
    }

    public int getId_comarca() {
        return id_comarca;
    }

    public void setId_comarca(int id_comarca) {
        this.id_comarca = id_comarca;
    }

    public int getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(int id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(int habitantes) {
        this.habitantes = habitantes;
    }

    public float getExtension() {
        return extension;
    }

    public void setExtension(float extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "nombre='" + nombre + '\'' +
                ", cp=" + cp +
                ", habitantes=" + habitantes +
                ", extension=" + extension +
                '}';
    }

}
