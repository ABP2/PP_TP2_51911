package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMax, boolean requiereNotebook){
        super(id, titulo, cupoMax);
        this.requiereNotebook=requiereNotebook;
    }


    //--------------------------------//

    @Override
    public double calcularCostoMateriales(){
        return requiereNotebook?5000:2000;
    }

    //--------------------------------//

    @Override
    public String getTipo(){
        return "Taller";
    }

    @Override
    public String generarCertificado(Estudiante e){
        return ENTIDAD_EMISORA + " certifica que el/la alumno/a "+e.getNombre()+" legajo n°: "+e.getLegajo()+" asistió al taller "+this.getTitulo();
    }
}
