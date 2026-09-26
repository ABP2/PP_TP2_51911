import excepciones.CuposExcedidosException;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;
import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;
import modelo.certificacion.Certificable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void sopln(String texto) {
        System.out.println(texto);
    }

    public static void sop(String texto) {
        System.out.print(texto);
    }

    private static final Scanner si = new Scanner(System.in);

    //--------------------------------//

    static List<EventoUniversitario> listaEventos = new ArrayList<>();
    static List<Sala> listaSalas = new ArrayList<>();
    static List<Estudiante> listaAlumnos = new ArrayList<>();

    //--------------------------------//

    static boolean validacion() {
        sopln("1.Sí - 2.No");
        int opcion = si.hasNextInt() ? si.nextInt() : 0;
        si.nextLine();
        return opcion == 1;
    }

    static EventoUniversitario buscadorEventos() {
        while (true) {
            sop("Ingrese la ID del evento: ");
            String id = si.nextLine();
            for (EventoUniversitario e : listaEventos) {
                if (e.getId().equals(id)) {
                    return e;
                }
            }
            sopln("Evento no encontrado - Intente nuevamente");
        }
    }

    static Sala buscadorSala() {
        while (true) {
            sop("Ingrese la ID de la sala: ");
            String id = si.nextLine();
            for (Sala s : listaSalas) {
                if (s.getId().equals(id)) {
                    return s;
                }
            }
            sopln("Sala no encontrada - Intente nuevamente");
        }
    }

    static Estudiante buscadorAlumno() {
        while (true) {
            sop("Ingrese el legajo del alumno/a: ");
            String legajo = si.nextLine();
            for (Estudiante a : listaAlumnos) {
                if (a.getLegajo().equals(legajo)) {
                    return a;
                }
            }
            sopln("Alumno no encontrado - Intente nuevamente");
        }
    }

    //--------------------------------//

    static void crearSala() {
        String id;
        String nombre;

        sopln("Ingrese los siguientes datos");

        sop("Nombre de la sala: ");
        nombre = si.nextLine();

        sop("ID de la sala: ");
        id = si.nextLine();

        Sala salon = new Sala(id, nombre);
        listaSalas.add(salon);
    }

    static void registrarAlumno() {
        sopln("Ingrese los siguientes datos");

        sop("Nombre del alumno/a: ");
        String nombre = si.nextLine();

        sop("Legajo del alumno/a: ");
        String legajo = si.nextLine();

        Estudiante alumno = new Estudiante(legajo, nombre);
        listaAlumnos.add(alumno);
    }

    static void crearEvento() {
        String id;
        String titulo;
        double costoBase;
        boolean gratuito;

        sopln("Ingrese los siguientes datos");
        sop("Titulo del evento: ");
        titulo = si.nextLine();

        sop("ID del evento: ");
        id = si.nextLine();

        sopln("¿Es gratuito?: ");
        sopln("1.Sí - 2.No");
        int opcion = si.hasNextInt() ? si.nextInt() : 0;
        si.nextLine();
        gratuito = opcion == 1;
        costoBase = 0;
        if (!gratuito) {
            while (costoBase == 0) {
                sop("Costo base del evento: ");
                costoBase = si.hasNextDouble() ? si.nextDouble() : 0;
                si.nextLine();
            }
        }

        EventoUniversitario evento = new EventoUniversitario(id, titulo, costoBase, gratuito);
        listaEventos.add(evento);

        sopln("¿Desea asignarle una sala al evento?");
        if (validacion()) {
            sopln("Seleccione una sala para realizar el evento");
            evento.asignarSala(buscadorSala());
        }
    }

    static void crearActividad() {
        int id = 0;
        sopln("Seleccione el evento donde se realizara la actividad");
        EventoUniversitario evento = buscadorEventos();
        sopln("Complete los siguientes datos");

        sop("ingrese la ID de la actividad (Número): ");

        while (id <= 0) {
            id = si.hasNextInt() ? si.nextInt() : 0;
            si.nextLine();
            if (id <= 0) {
                sopln("ID inválido");
            }
        }

        sop("ingrese el título: ");
        String titulo = si.nextLine();

        sop("ingrese la cantidad de cupos máximos (mínimo "+Actividad.getCupoMin()+"): ");
        int cupos=0;
        while (cupos<Actividad.getCupoMin()) {
            cupos = si.nextInt();
            si.nextLine();
        }

        sopln("Indique el tipo de Actividad");
        sopln("1.Charla - 2.Taller - 3.Curso");
        boolean val = true;
        while (val) {
            int opcion = si.hasNextInt() ? si.nextInt() : 0;
            si.nextLine();
            switch (opcion) {
                case 1:
                    sop("Ingrese el nombre del disertante: ");
                    String disertante = si.nextLine();
                    evento.crearActividad(id, titulo, cupos, disertante);
                    val = false;
                    break;

                case 2:
                    boolean notebook = false;
                    sopln("¿Requiere notebook?");
                    sopln("1.Sí - 2.No");
                    opcion = si.nextInt();
                    if (opcion == 1) {
                        notebook = true;
                    }
                    evento.crearActividad(id, titulo, cupos, notebook);
                    val = false;
                    break;
                case 3:
                    int nivel;
                    do {
                        sop("Ingrese el nivel del curso: ");
                        nivel =si.hasNextInt()?si.nextInt():0;
                        evento.crearActividad(id, titulo, cupos, nivel);
                    }while(nivel!=0);
                    break;
                default:
                    sopln("Opción inválida");
                    break;
            }
        }

        sopln("¿Desea inscribir a un alumno a la actividad");
        while (validacion()) {
            inscribirAlumno(evento, id);
            sopln("¿Desea inscribir otro alumno?");
        }
    }

    static void inscribirAlumno(EventoUniversitario evento, int id) {
        try {
            for (Actividad a : evento.getListaActividades()) {
                if (a.getId() == id) {
                    a.inscribir(buscadorAlumno());
                    break;
                }
            }

        } catch (CuposExcedidosException e) {
            sopln(e.getMessage());
        }
    }

    static void main(String[] arg) {
        boolean menu=true;
        while (menu) {
            sopln("----MENÚ----");
            sopln("1.Cargar sala");
            sopln("2.Registrar alumno");
            sopln("3.Crear Evento");
            sopln("4.Crear Actividad");
            sopln("5.Inscribir alumno a actividad");
            sopln("6.Copiar Evento");
            sopln("7.Mostar Datos");
            sopln("8.Cargar evento");
            sopln("9.Guardar evento");
            sopln("10.Emitir certificados");
            sopln("11.Calcular costo total de actividades según su tipo");
            sopln("12.Finalizar el programa");
            sopln("------------");
            sop("Seleccione una opcion: ");
            int opcion;
            opcion=si.hasNextInt()?si.nextInt():0;
            si.nextLine();
            switch (opcion) {
                case 1:
                    sopln("----Crear sala----");
                    do {
                        crearSala();
                        sopln("¿Desea crear otra sala?");
                    } while (validacion());
                    break;
                case 2:
                    sopln("----Registrar alumnos----");
                    do {
                        registrarAlumno();
                        sopln("¿Desea registrar otro alumno?");
                    } while (validacion());
                    break;
                case 3:
                    sopln("----Crear eventos----");
                    do {
                            crearEvento();
                            sopln("¿Desea crear otro evento?");
                    } while (validacion());
                    break;
                case 4:
                    sopln("----Crear Actividades----");
                    do {
                        crearActividad();
                        sopln("¿Desea crear otra actividad?");
                    } while (validacion());
                    break;
                case 5:
                    sopln("----Inscribir alumno a actividad----");
                    do {
                        sop("Ingrese la ID de la actividad: ");
                        int id=si.hasNextInt()? si.nextInt() : 0;
                        inscribirAlumno(buscadorEventos(),id);
                        sopln("¿Desea inscribir otro alumno?");
                    }while(validacion());
                    break;
                case 6:
                    sopln("----Crear Copias----");
                    do {
                        if (listaEventos.isEmpty()){
                            System.out.println("Debe crear o cargar un evento antes de copiarlo");
                            break;
                        }
                        EventoUniversitario copia = new EventoUniversitario(buscadorEventos());
                        listaEventos.add(copia);
                        sopln("¿Desea hacer otra copia?");
                    } while (validacion());
                    break;
                case 7:
                    sopln("----Información de eventos----");
                    sopln("Cantidad de eventos creados: " + EventoUniversitario.getCantEventos());
                    for (EventoUniversitario evento : listaEventos) {
                        evento.mostrarDatosEvento();
                    }
                    sopln("-------------------------");
                    break;
                case 8:
                    do {
                        try {
                            sopln("Ingrese la id del evento a cargar");
                            String id = si.nextLine();
                            listaEventos.add(EventoUniversitario.recuperarEvento(id));
                        } catch (FileNotFoundException e) {
                            sopln("Error al cargar el evento: " + e.getMessage());
                        } catch (ClassNotFoundException | IOException e) {
                            sopln("Error: " + e.getMessage());
                        }finally {
                            sopln("¿Desea cargar otro evento?");
                        }
                    }while(validacion());
                    break;
                case 9:
                    do {
                        if(listaEventos.isEmpty()){
                            sopln("Debe crear un evento antes de guardarlo");
                            break;
                        }
                        try{
                            EventoUniversitario e = buscadorEventos();
                            e.persistirEvento();
                            listaEventos.remove(e);
                        }catch(FileNotFoundException e){
                            sopln("Error al guardar el evento: " + e.getMessage());
                        }catch(IOException e){
                            sopln("Error: " + e.getMessage());
                        }
                        sopln("¿Desea guardar otro evento?");
                    }while (validacion());
                    break;
                case 10:
                    sopln("Ingrese el evento donde se realizaron las actividades");
                    EventoUniversitario e=buscadorEventos();
                    for (Actividad a:e.getListaActividades()){
                        if (a instanceof Certificable){
                            for (Inscripcion i : a.getListaInscripciones()) {
                                sopln(((Certificable) a).generarCertificado(i.getAlumno()));
                            }
                        }
                    }
                    break;
                case 11:
                    EventoUniversitario evt = buscadorEventos();
                    sopln("Seleccione el tipo de actividades");
                    sopln("1.Charla 2.Taller 3.Curso");
                    opcion=si.hasNextInt()? si.nextInt():0;
                    switch (opcion){
                        case 1:
                            List<Charla> charlas = evt.filtrarActividadesPorTipo(Charla.class);
                            sopln("Cantidad de charlas: "+charlas.size());
                            sopln("Costo total de materiales: "+evt.calcularCostoMateriales(charlas));
                            break;
                        case 2:
                            List<Taller> talleres = evt.filtrarActividadesPorTipo(Taller.class);
                            sopln("Cantidad de talleres: "+talleres.size());
                            sopln("Costo total de materiales: "+evt.calcularCostoMateriales(talleres));
                            break;
                        case 3:
                            List<Curso> cursos = evt.filtrarActividadesPorTipo(Curso.class);
                            sopln("Cantidad de cursos: "+cursos.size());
                            sopln("Costo total de materiales: "+evt.calcularCostoMateriales(cursos));
                            break;
                        default:
                            break;
                    }
                    break;
                case 12:
                    menu=false;
                    break;
                default:
                    sopln("Opción inválida");
                    break;
            }
        }
    }
}