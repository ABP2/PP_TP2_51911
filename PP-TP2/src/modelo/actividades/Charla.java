package modelo.actividades;

public class Charla extends Actividad {
    private String disertante;

    public Charla(int id, String titulo, int cupoMax, String disertante){
        super(id,titulo,cupoMax);
        this.disertante=disertante;
    }

    //--------------------------------//

    @Override
    public double calcularCostoMateriales(){
        return 0;
    }

    //--------------------------------//

    @Override
    public String getTipo(){
        return "Charla";
    }
}

