package mx.unam.fciencias.buscaminas;

import javafx.application.Application;

/**
 * La clase Main instancia la aplicación Buscaminas y 
 * establece la referencia necesaria para que la clase Casilla
 * pueda llevar a cabo los métodos para concluir el juego.
 * 
 * @author Jorge Esteban Mendoza Ortiz (418002863)
 */

public class Main {
    public static void main(String[] args) {
        BuscaminasApp buscaminasApp = new BuscaminasApp();
        Casilla.setAplicacion(buscaminasApp);

        Application.launch(BuscaminasApp.class, args);
    }
}
