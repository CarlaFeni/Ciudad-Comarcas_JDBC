package model;


import java.util.List;

public class Comarca {
    private int id_comarca;
    private String nombre;
    private String provincia;
    private List<Ciudad> ciudad;

    public Comarca(int id_comarca, String nombre, String provincia, List<Ciudad> ciudad) {
        this.id_comarca = id_comarca;
        this.nombre = nombre;
        this.provincia = provincia;
        this.ciudad = ciudad;
    }

    public int getId_comarca() {
        return id_comarca;
    }

    public void setId_comarca(int id_comarca) {
        this.id_comarca = id_comarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    public List<Ciudad> getCiudad() {
        return ciudad;
    }

    public void setCiudad(List<Ciudad> ciudad) {
        this.ciudad = ciudad;
    }

    public void addCiudad (Ciudad c){
        ciudad.add(c);
    }
    private List<Ciudad> getCiudades (){
        return ciudad;
    }
}
