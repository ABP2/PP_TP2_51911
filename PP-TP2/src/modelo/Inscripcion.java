package modelo;
import modelo.actividades.Actividad;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    private LocalDate fecha;
    private String estado;

    //--------------------------------//

    private Estudiante alumno;

    public Inscripcion(LocalDate fecha, String estado, Estudiante alumno){
        this.fecha=fecha;
        this.estado=estado;
        this.alumno=alumno;
    }

    //--------------------------------//

    public LocalDate getFecha() {
        return fecha;
    }

    public Estudiante getAlumno() {
        return alumno;
    }

    public String getEstado() {
        return estado;
    }

}
