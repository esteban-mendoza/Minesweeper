package mx.unam.fciencias.buscaminas;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Buscaminas implementa dos métodos:
 * 1. mostrar, que regresa un objeto tipo Pane, con una cuadrícula
 * de casillas (botones). Aleatoriamente se elige el 18% de las casillas para
 * ser bombas; después se determina el número de bombas que rodean a las 
 * casillas que no son bombas y se modifica el texto en esas casillas.
 * 
 * 2. getVecinos, que regresa una lista de las casillas vecinas a una 
 * casilla en particular. 
 * 
 * @author Jorge Esteban Mendoza Ortiz (418002863)
 */

public class Buscaminas {

    public static int restantesGanar = 0;

    private static Casilla[][] cuadricula;

    private static int xCasillas;
    private static int yCasillas;

    private final int CASILLA_TAMANO = 35;

    private int ancho;
    private int alto;

    /**
     * Constructor que recibe el número de casillas en el eje horizontal
     * y el número casillas en el eje vertical deseados.
     * 
     * @param xCasillas - número de casillas horizontales
     * @param yCasillas - número de casillas verticales
     */
    public Buscaminas(int xCasillas, int yCasillas) {
        this.xCasillas = xCasillas;
        this.yCasillas = yCasillas;

        this.ancho = CASILLA_TAMANO * xCasillas;
        this.alto = CASILLA_TAMANO * yCasillas;

        this.cuadricula = new Casilla[xCasillas][yCasillas];
    }

    /**
     * El método crea una cuadrícula de casillas, asigna aleatoriamente
     * su estatus de bomba, cuenta el número de bombas alrededor de cada casilla,
     * cuenta el número de casillas que no son bombas (contador que se usa
     * para determinar la victoria) y crea dicho texto.
     * 
     * @return Diseño que se utiliza para crear la escena del juego.
     */
    public Parent contenido() {
        Pane diseno = new Pane();
        diseno.setPrefSize(ancho, alto);

        // Generar cuadrícula de casillas. Probabilidad de una mina = 18%
        for (int y = 0; y < yCasillas; y++) {
            for (int x = 0; x < xCasillas; x++) {
                Casilla casilla = new Casilla(x, y, Math.random() <= 0.15);

                cuadricula[x][y] = casilla;
                diseno.getChildren().add(casilla);
            }
        }


        for (int y = 0; y < yCasillas; y++) {
            for (int x = 0; x < xCasillas; x++) {
                Casilla casilla = cuadricula[x][y];

                if (casilla.getBomba())
                    continue;
                else
                    restantesGanar++;

                int bombas = 0;

                for (Casilla vecino : getVecinos(casilla)) {
                    if (vecino.getBomba())
                        bombas++;
                }

                if (bombas > 0)
                    casilla.getTexto().setText(String.valueOf(bombas));
            }
        }

        return diseno;
    }

    /**
     * Método que regresa las casillas vecinas de cualquier casilla dada.
     * 
     * @param casilla - La casilla cuyas casillas vecinas se evalúan.
     * @return Lista de las casillas vecinas.
     */
    public static List<Casilla> getVecinos(Casilla casilla) {
        List<Casilla> vecinos = new ArrayList<>();

        int[][] puntos = new int[][] {
              {-1,-1},{-1,0},{-1,1},
              {0,-1},{0,1},
              {1,-1},{1,0},{1,1}
        };

        for (int i = 0; i < puntos.length; i++) {
            int dx = puntos[i][0];
            int dy = puntos[i][1];

            int nuevoX = casilla.getX() + dx;
            int nuevoY = casilla.getY() + dy;

            if (nuevoX >= 0 && nuevoX < xCasillas
                    && nuevoY >= 0 && nuevoY < yCasillas) {
                vecinos.add(cuadricula[nuevoX][nuevoY]);
            }
        }

        return vecinos;
    }
}