package mx.unam.fciencias.buscaminas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class Estadisticas {

    private static Stage ventana;
    private static TableView<Registro> tabla;
    private static ObservableList<Registro> registros = FXCollections.observableArrayList();

    public static void mostrar() {
        ventana = new Stage();

        ventana.setTitle("Estadísticas");
        ventana.setHeight(300);
        ventana.initModality(Modality.APPLICATION_MODAL); //Evitar interacción con otras vetanas

        // Columna fecha
        TableColumn<Registro, String> fecha = new TableColumn<>("Fecha");
        fecha.setMinWidth(125);
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        // Columna resultado
        TableColumn<Registro, String> resultado = new TableColumn<>("Resultado");
        resultado.setMinWidth(100);
        resultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));

        // Columna duracion
        TableColumn<Registro, Double> duracion = new TableColumn<>("Duración");
        duracion.setMinWidth(100);
        duracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        // Instancia la tabla y provee las columnas
        tabla = new TableView<>();
        tabla.setItems(registros);
        tabla.getColumns().addAll(fecha, resultado, duracion);

        // Botón cerrar
        Button cerrar = new Button("Cerrar");
        cerrar.setOnAction(e -> ventana.close());

        // Diseño de escena
        VBox disenoEstadisticas = new VBox(20);
        disenoEstadisticas.getChildren().addAll(tabla,cerrar);
        disenoEstadisticas.setAlignment(Pos.CENTER);

        // Escena
        Scene escena = new Scene(disenoEstadisticas);

        ventana.setScene(escena);
        ventana.showAndWait();
    }

    public static void nuevoRegistro(Registro registro) {
        registros.add(registro);
    }
}
