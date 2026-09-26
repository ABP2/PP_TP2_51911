package modelo;

import java.io.Serializable;

public class Sala implements Serializable{
    private String id;
    private String nombre;

    public Sala(String id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    //--------------------------------//

    public String getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
}
