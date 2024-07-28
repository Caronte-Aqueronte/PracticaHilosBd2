/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package usac.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import usac.hilos.DecrementThread;
import usac.hilos.IncrementThread;
import usac.hilosbd.App;
import usac.repositories.MovimientoRepository;

/**
 * FXML Controller class
 *
 * @author Luis Monterroso
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private MFXTextField txtCantInit;
    @FXML
    private MFXTextField txtIntervalo1;
    @FXML
    private MFXTextField txtCantIncre;
    @FXML
    private MFXTextField txtTiempoEje1;
    @FXML
    private MFXTextField txtIntervalo2;
    @FXML
    private MFXTextField txtCantDecre;
    @FXML
    private MFXTextField txtTiempoEje2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void iniciarEscenario(ActionEvent event) {
        try {
            //datos para el primer hilo
            int intervalo1 = Integer.parseInt(this.txtIntervalo1.getText());
            int incremento = Integer.parseInt(this.txtCantIncre.getText());
            int tiempoEje1 = Integer.parseInt(this.txtTiempoEje1.getText());
            //datos segundo hilo
            int intervalo2 = Integer.parseInt(this.txtIntervalo2.getText());
            int decremento = Integer.parseInt(this.txtCantDecre.getText());
            int tiempoEje2 = Integer.parseInt(this.txtTiempoEje2.getText());
            //cantidad inicial
            int cantidadInicial = Integer.parseInt(this.txtCantInit.getText());

            MovimientoRepository repo = new MovimientoRepository();
            repo.setInitialValue(cantidadInicial); // Valor inicial
            IncrementThread incrementThread = new IncrementThread(repo,
                    incremento, intervalo1, tiempoEje1);
            DecrementThread decrementThread = new DecrementThread(repo,
                    decremento, intervalo2, tiempoEje2);

            Map<String, Object> map
                    = App.loader.abrirDialog("bitacora", "Bitacora",
                            false, true);
            //abrimos el dialog
            FXMLLoader initLoader = (FXMLLoader) map.get("FXMLLoader");
            //obtenemos el controlador para enviarle los hilos que se comiencen en el dialog
            BitacoraController controller = (BitacoraController) initLoader.getController();
            controller.init(incrementThread, decrementThread, cantidadInicial);
            //abrimos la ventana
            Stage stage = (Stage) map.get("Stage");
            stage.showAndWait();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Dato numerico con formato invalido.").show();
        } catch (Exception ex) {

        }

    }

}
