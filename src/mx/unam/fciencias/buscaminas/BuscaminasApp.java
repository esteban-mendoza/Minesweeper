package mx.unam.fciencias.buscaminas;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BuscaminasApp es la clase que determina la lógica de la GUI:
 * Crea varias pantallas (escenas) del juego que siguen la siguiente
 * lógica:
 * 
 * 1. Principal 
 * 1.1 Jugar > Dificultades
 * 1.2 Estadísticas
 *
 * 2. Dificultades 
 * 2.1 Principiante
 * 2.2 Intermedio 
 * 2.3 Experto
 *
 * Si el jugador gana o pierde Buscaminas.terminar(victoria : boolean) se llama.
 * Dicho método genera un Registro que se guarda en Estadisticas.registros.
 * 
 * 3. pantallaFinal 
 * 3.1 Muestra registro de victoria o derrota de la última partida
 * 3.2 Jugar > Dificultades
 * 3.3 Estadísticas
 * 
 * 4. Estadísticas
 * La pantalla estadísticas muestra una tabla con los registros actuales de 
 * juegos: fecha y hora del juego, resultado del juego y duración en segundos
 * de la partida.
 * 
 * @author Jorge Esteban Mendoza Ortiz (418002863)
 */
public class BuscaminasApp extends Application {

    private static Button jugar,estadisticasBoton;

    private static Stage ventana;

    private static long inicioJuego;

    private Estadisticas estadisticas;

    private final int ANCHO = 250;
    private final int ALTO = 250;

    @Override
    public void start(Stage primaryStage) {
        ventana = primaryStage;
        ventana.setTitle("Buscaminas");

        // Dificultades: elementos
        Label seleccionar = new Label();
        seleccionar.setText("Seleccione la dificultad:");

        Button principianteBoton = new Button("Principiante");
        principianteBoton.setOnAction(event -> {
            // Comienza a medir tiempo
            inicioJuego = System.nanoTime();

            Buscaminas principiante = new Buscaminas(9,9);
            Scene juego = new Scene(principiante.contenido());
            ventana.setScene(juego);
        });

        Button intermedioBoton = new Button("Intermedio");
        intermedioBoton.setOnAction(event -> {
            // Comienza a medir tiempo
            inicioJuego = System.nanoTime();

            Buscaminas intermedio = new Buscaminas(16,16);
            Scene juego = new Scene(intermedio.contenido());
            ventana.setScene(juego);
        });

        Button expertoBoton = new Button("Experto");
        expertoBoton.setOnAction(event -> {
            // Comienza a medir tiempo
            inicioJuego = System.nanoTime();

            Buscaminas experto = new Buscaminas(30,16);
            Scene juego = new Scene(experto.contenido());
            ventana.setScene(juego);
        });

        // Dificultades: diseno y escena
        VBox disenoDificultades = new VBox(20);
        disenoDificultades.getChildren().addAll(seleccionar,
                principianteBoton,
                intermedioBoton,
                expertoBoton);
        disenoDificultades.setAlignment(Pos.CENTER);

        Scene dificultades = new Scene(disenoDificultades,ANCHO,ALTO);


        // Pantalla principal: elementos
        Label bienvenida = new Label();
        bienvenida.setText("¡Bienvenido a Buscaminas!");

        jugar = new Button("Jugar");
        jugar.setOnAction(e -> ventana.setScene(dificultades));

        estadisticasBoton = new Button("Estadísticas");
        estadisticasBoton.setOnAction(e -> Estadisticas.mostrar());

        // Pantalla principal: diseno y escena
        VBox disenoPrincipal = new VBox(30);
        disenoPrincipal.getChildren().addAll(bienvenida,jugar,estadisticasBoton);
        disenoPrincipal.setAlignment(Pos.CENTER);

        Scene principal = new Scene(disenoPrincipal,ANCHO,ALTO);

        // Iniciar ventana principal
        ventana.setScene(principal);
        ventana.show();
    }

    public void finalizar(boolean victoria) {

        // Crea datos del registro
        String resultado = victoria ? "Ganaste" : "Perdiste";

        long tiempoJuego = System.nanoTime() - inicioJuego;
        double segundos = tiempoJuego / 1000000000.0;
        NumberFormat formatoDuracion = new DecimalFormat("#.##");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd  H:m:s");
        Date fecha = new Date();

        // Instancia el registro y lo agrega
        
        Estadisticas.nuevoRegistro(new Registro(formatoFecha.format(fecha),
                resultado,
                Double.valueOf(formatoDuracion.format(segundos))));


        // Elementos
        Label mensajeFinal = new Label();
        mensajeFinal.setText(resultado+" el "+formatoFecha.format(fecha)+". \n" +
                "Duración de la partida: "+formatoDuracion.format(segundos)+" segundos.");

        // Pantalla final: diseno y escena
        VBox disenoFinal = new VBox(20);
        disenoFinal.getChildren().addAll(mensajeFinal,jugar,estadisticasBoton);
        disenoFinal.setAlignment(Pos.CENTER);

        Scene pantallaFinal = new Scene(disenoFinal,ANCHO,ALTO);

        ventana.setScene(pantallaFinal);
    }
}