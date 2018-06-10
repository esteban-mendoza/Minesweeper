package mx.unam.fciencias.buscaminas;

/**
 * Clase que crea los registros de la base de datos de Estadísticas.
 * 
 * @author Jorge Esteban Mendoza Ortiz (418002863)
 */
public class Registro {

    private String fecha;
    private String resultado;
    private double duracion;

    public Registro(String fecha, String resultado, double duracion) {
        this.fecha = fecha;
        this.resultado = resultado;
        this.duracion = duracion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getResultado() {
        return resultado;
    }

    public double getDuracion() {
        return duracion;
    }
}
