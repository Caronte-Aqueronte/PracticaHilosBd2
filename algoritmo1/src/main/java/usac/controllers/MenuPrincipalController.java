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
            double intervalo1 = Double.parseDouble(this.txtIntervalo1.getText());
            double incremento = Double.parseDouble(this.txtCantIncre.getText());
            double tiempoEje1 = Double.parseDouble(this.txtTiempoEje1.getText());
            //datos segundo hilo
            double intervalo2 = Double.parseDouble(this.txtIntervalo2.getText());
            double decremento = Double.parseDouble(this.txtCantDecre.getText());
            double tiempoEje2 = Double.parseDouble(this.txtTiempoEje2.getText());
            //cantidad inicial
            double cantidadInicial = Double.parseDouble(this.txtCantInit.getText());

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
