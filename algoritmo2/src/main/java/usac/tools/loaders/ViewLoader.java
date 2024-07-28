/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usac.tools.loaders;


import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.io.IOException;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import usac.hilosbd.App;

/**
 * Se encarga de cargar ventanas padre, en srcrollpanes, ventanas modales, etc
 *
 * @author Luis Monterroso
 */
public class ViewLoader extends Loader {

    /**
     * Coloca una vita adentro de un ScrollPane
     *
     * @param contenedor
     * @param view
     * @return
     * @throws java.lang.Exception
     */
    public FXMLLoader abrirMenuEnScrollPane(MFXScrollPane contenedor, String view)
            throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/" + view + ".fxml"));
        // Realizar operaciones de interfaz de usuario en el hilo de la interfaz de usuario
        Platform.runLater(() -> {
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace(); // Manejar la excepción adecuadamente
            }
            contenedor.setFitToHeight(true);
            contenedor.setFitToWidth(true);
            contenedor.setContent(parent);
        });
        return loader;
    }


    /**
     * Abre el menu que recibe como parametro.
     *
     * @param view
     * @param elemento Nodo que servira para cerrar la ventana desde donde se
     * llamo el metodo, si es null no se cerrara la ventana actual
     * @param maximized
     * @param resizable
     * @return
     * @throws java.lang.Exception
     */
    public FXMLLoader abrirMenu(String view, Node elemento, boolean maximized,
            boolean resizable) throws Exception {
        Map<String, Object> ventana = this.abrirVista(view, "", maximized, resizable,
                true);
        FXMLLoader loader = (FXMLLoader) ventana.get("FXMLLoader");
        Stage stage = (Stage) ventana.get("Stage");
        //si el elemento no es nulo entonces obtenemos su ventana y la cerramos
        if (elemento != null) {
            Stage stageActual = (Stage) elemento.getScene().getWindow();
            stageActual.close();

        }
        //mostrar la ventana
        stage.show();
        return loader;
    }

    /**
     * Abre la una vista en modo dialog.
     *
     * @param view
     * @param titulo
     * @param maximized
     * @param resizable
     * @return Map <String, Object> retrona un Map con las dos partes que
     * componen el dialog, la clave "Stage" devuelve un objeto Stage que es la
     * ventana cargada, la clave "FXMLLoader" devuelve un objeto FXMLLoader que
     * contiene el controlador de la ventana.
     */
    public Map<String, Object> abrirDialog(String view,
            String titulo, boolean maximized, boolean resizable) throws Exception {
        Map<String, Object> ventana = this.abrirVista(view, titulo, maximized, resizable,
                true);
        Stage stage = (Stage) ventana.get("Stage");//cremos la ventana
        stage.initModality(Modality.APPLICATION_MODAL);
        return ventana;
    }
}
