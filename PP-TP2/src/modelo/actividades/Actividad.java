package modelo.actividades;
import excepciones.CuposExcedidosException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    private int id;
    private String titulo;
    private int cupoMax;
    private static int cupoMin=1;

    //--------------------------------//

    private List<Inscripcion> listaInscripciones = new ArrayList<>();

    //--------------------------------//

    public Actividad(int id, String titulo, int cupoMax){
        this.id=id;
        this.titulo=titulo;
        this.cupoMax=cupoMax;
    }

    public void inscribir (Estudiante estudiante)
        throws CuposExcedidosException {
        if(listaInscripciones.size()>=cupoMax){
            throw new CuposExcedidosException("No hay cupos disponibles");
        }
        Inscripcion insc = new Inscripcion(LocalDate.now(),"Vigente",estudiante);
        listaInscripciones.add(insc);
    }

    public void mostrarInscripciones(){
        System.out.println("- "+id+"-"+titulo);
        System.out.println("Inscriptos: ");
        for (Inscripcion i:listaInscripciones){
            System.out.println("   + "+i.getFecha()+" -- "+i.getAlumno().getNombre());
        }
        System.out.println("     -----     -----     ");
    };

    public abstract double calcularCostoMateriales();

    //--------------------------------//


    public static int getCupoMin() {
        return cupoMin;
    }

    public int getId() {
        return id;
    }

    public abstract String getTipo();

    public String getTitulo(){
        return titulo;
    }

    public List<Inscripcion> getListaInscripciones() {
        return listaInscripciones;
    }
}

