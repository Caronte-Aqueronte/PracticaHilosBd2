/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usac.tools.loaders;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Luis Monterroso
 */
public class Loader {

    /**
     * Abre la una vista en una nueva ventana.
     *
     * @param view
     * @param titulo
     * @param maximized
     * @param resizable
     * @param useSpring
     * @return Map <String, Object> retrona un Map con las dos partes que
     * componen el dialog, la clave "Stage" devuelve un objeto Stage que es la
     * ventana cargada, la clave "FXMLLoader" devuelve un objeto FXMLLoader que
     * contiene el controlador de la ventana.
     */
    protected Map<String, Object> abrirVista(String view,
            String titulo, boolean maximized, boolean resizable,
            boolean useSpring) throws IOException {
        //mapa de los datos de la ventana y el loader encargado de abrir la ventana
        Map<String, Object> ventana = new HashMap<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fxml/" + view + ".fxml")
        );
        Parent parent = loader.load();//crear un pareinte
        //creacion de la ventana y seteo del cerrado con esc
        Scene scene = new Scene(parent);
        this.setCloseWithKeyBoard(scene, parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        //configuracion del icono, titulo, maximizacion y tamanio
        stage.setTitle(titulo);
        stage.setResizable(resizable);
        stage.setMaximized(maximized);
        ventana.put("Stage", stage);
        ventana.put("FXMLLoader", loader);
        return ventana;
    }

    private void setCloseWithKeyBoard(Scene scene, Node nodo) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                Stage sb = (Stage) nodo.getScene().getWindow();
                sb.close();
            }
        });
    }

    /**
     * Cierra una ventana a partir de un nodo hijo de la vista que se desea
     * cerrar
     *
     * @param nodo hijo de la ventanaque se desea cerrar
     */
    public void closeWindow(Node nodo) {
        //codigo para cerrar la ventana actual
        Stage stageActual = (Stage) nodo.getScene().getWindow();//obtenemos la ventana actual
        stageActual.close();
    }
}
