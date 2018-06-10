package mx.unam.fciencias.buscaminas;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Casilla extiende a StackPane, una clase que amontona uno tras otro varios
 * componentes para formar botones con formas compuestas. Las casillas
 * tienen coordenadas enteras (x,y), se sabe si son bombas o no e incluyen 
 * texto, que puede ser el vacío, el número de bombas alrededor de la casilla
 * o una X para denotar las bombas. La clase Casilla también incluye una 
 * referencia estática a la aplicación principal.
 * 
 * @author Jorge Esteban Mendoza Ortiz (418002863)
 */
public class Casilla extends StackPane {

    private static BuscaminasApp aplicacion;

    private final int CASILLA_TAMANO = 35;
    
    private int x, y;
    private boolean bomba;
    private boolean abierta = false;

    private Rectangle borde = new Rectangle(CASILLA_TAMANO - 2, CASILLA_TAMANO - 2);
    private Text texto = new Text();

    public Casilla(int x, int y, boolean bomba) {
        this.x = x;
        this.y = y;
        this.bomba = bomba;

        borde.setStroke(Color.LIGHTGRAY);

        texto.setFont(Font.font(18));
        texto.setText(bomba ? "X" : "");
        texto.setVisible(false);

        getChildren().addAll(borde, texto);
        setTranslateX(x * CASILLA_TAMANO);
        setTranslateY(y * CASILLA_TAMANO);

        setOnMouseClicked(e -> abrir());
    }

    public static void setAplicacion(BuscaminasApp aplicacion) {
        Casilla.aplicacion = aplicacion;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean getBomba() {
        return this.bomba;
    }

    public Text getTexto() {
        return this.texto;
    }

    /**
     * El método abrir() aglutina la parte más importante de la lógica del juego.
     * Si una casilla ya está abierta, no se hace nada; si una casilla evaluada 
     * es bomba, se reinicia el contador de casillas faltantes y se llama el 
     * método Buscaminas.finalizar(victoria:falso); si no es un caso anterior,
     * la casilla se abre, el texto se hace visible y se disminuye el contador
     * de casillas faltantes para concluir el juego; después, si el 
     * contador ha llegado a 0, se llama Buscaminas.finalizar(victoria:verdadero).
     * Si el texto de la casilla es vacío, se abren las casillas alrededor
     * de la casilla evaluada.
     * 
     */
    public void abrir() {
        if (abierta)
            return;

        // Perder
        if (bomba) {
            Buscaminas.restantesGanar = 0;
            aplicacion.finalizar(false);
            return;
        }

        abierta = true;
        texto.setVisible(true);
        borde.setFill(null);

        Buscaminas.restantesGanar--;

        // Ganar
        if (Buscaminas.restantesGanar == 0) {
            aplicacion.finalizar(true);
        }

        if (texto.getText().isEmpty()) {
            for (Casilla vecino : Buscaminas.getVecinos(this)) {
                vecino.abrir();
            }
        }
    }
}