package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

public class Curso extends Actividad implements Certificable {
    private int nivel;

    public Curso(int id, String titulo, int cupoMax, int nivel){
        super(id, titulo, cupoMax);
        this.nivel=nivel;
    }


    //--------------------------------//

    @Override
    public double calcularCostoMateriales(){
        return 1000;
    }

    //--------------------------------//

    @Override
    public String getTipo(){
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante e){
        return ENTIDAD_EMISORA + " certifica que el/la alumno/a "+e.getNombre()+" legajo n°: "+e.getLegajo()+" asistió al curso "+this.getTitulo();
    }
}


