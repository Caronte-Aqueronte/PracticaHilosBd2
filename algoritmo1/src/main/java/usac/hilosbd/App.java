package usac.hilosbd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import usac.db.Conexion;
import usac.tools.loaders.ViewLoader;

/**
 * JavaFX App
 */
public class App extends Application {

    public static ViewLoader loader;
    private Conexion conexion;

    @Override
    public void start(Stage stage) throws IOException {
        loader = new ViewLoader();
        try {
            conexion = new Conexion();
            conexion.getConnection();
            //abrimos la view de inicio
            FXMLLoader initLoader = loader.abrirMenu("menuPrincipal",
                    null, false, false);
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Error al "
                    + "establecer conexion con la bd").show();
            ex.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error al "
                    + "inicializar la app").show();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
