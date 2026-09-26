package modelo;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;

import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private String id;
    private final String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantEventos=0;

    //--------------------------------//

    private Sala salon=null;
    private List<Actividad> listaActividades = new ArrayList<>();

    //--------------------------------//

    public EventoUniversitario(String id,String titulo, double costoBase, boolean gratuito){
        this.id=id;
        this.titulo=titulo;
        this.costoBase=costoBase;
        this.gratuito=gratuito;
        cantEventos++;
    }

    public EventoUniversitario(EventoUniversitario copia){
        this.id=copia.id+"-COPIA";
        this.titulo=copia.titulo;
        this.costoBase=copia.costoBase;
        this.gratuito=copia.gratuito;
        cantEventos++;
    }

    public double calcularCostoEstimado(){
        double costoTotal=0;
        for (Actividad a:listaActividades){
            costoTotal=costoTotal+costoBase+a.calcularCostoMateriales();
        }
        return costoTotal*1.21;
    }

    public void asignarSala(Sala salon){
        this.salon=salon;
    }

    public void crearActividad(int id, String titulo, int cupo, String disertante){
        Actividad act=new Charla(id,titulo,cupo,disertante);
        listaActividades.add(act);
    }
    public void crearActividad(int id, String titulo, int cupo, boolean notebook){
        Actividad act=new Taller(id,titulo,cupo,notebook);
        listaActividades.add(act);
    }
    public void crearActividad(int id, String titulo, int cupo, int nivel){
        Actividad act=new Curso(id,titulo,cupo,nivel);
        listaActividades.add(act);
    }

    public void mostrarDatosEvento(){
        System.out.println("-------------------------");
        System.out.println("Título: " + titulo);
        System.out.println("ID: " + id);
        if (gratuito){
            System.out.println("Gratuito");
        }else{
            System.out.println("Costo base: " + costoBase);
            System.out.println("Costo total: " + calcularCostoEstimado());
        }
        if (salon==null){
            System.out.println("SALA SIN ASIGNAR");
        }else{
            System.out.println("Sala: "+salon.getId()+" "+salon.getNombre());
        }
        System.out.println("      -ACTIVIDADES-");
        for (Actividad a:listaActividades){
            System.out.println(a.getTipo());
            a.mostrarInscripciones();
        }
    }

    public boolean persistirEvento() throws IOException {
        String nombreArchivo = "persistencias/evento_" + this.id + ".dat";
        FileOutputStream archivo = new FileOutputStream(nombreArchivo);
        ObjectOutputStream salida = new ObjectOutputStream(archivo);
        salida.writeObject(this);
        System.out.println(nombreArchivo+" guardado con éxito");
        salida.close();
        return true;
    }

    public static EventoUniversitario recuperarEvento(String id) throws IOException,ClassNotFoundException{
        FileInputStream archivo = new FileInputStream("persistencias/evento_" + id + ".dat");
        ObjectInputStream entrada = new ObjectInputStream(archivo);
        EventoUniversitario evento = (EventoUniversitario) entrada.readObject();
        entrada.close();
        return evento;
    }

    public <T extends Actividad> List<T> filtrarActividadesPorTipo (Class<T> tipo){
        List<T> listaActFiltradas = new ArrayList<>();
        for (Actividad act : listaActividades){
            if (tipo.isInstance(act)){
                listaActFiltradas.add(tipo.cast(act));
            }
        }
        return listaActFiltradas;
    }

    public double calcularCostoMateriales (List<? extends Actividad> act){
        double costoMateriales=0;
        for (Actividad a:act){
            costoMateriales+=a.calcularCostoMateriales();
        }
        return costoMateriales;
    }



    //--------------------------------//

    public static int getCantEventos() {
        return cantEventos;
    }
    public String getId(){
        return id;
    }
    public List<Actividad> getListaActividades() {
        return listaActividades;
    }
}
