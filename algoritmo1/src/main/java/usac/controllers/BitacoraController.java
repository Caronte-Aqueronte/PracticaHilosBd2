/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package usac.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import usac.hilos.DecrementThread;
import usac.hilos.IncrementThread;
import usac.tools.loaders.Loader;

/**
 * FXML Controller class
 *
 * @author Luis Monterroso
 */
public class BitacoraController implements Initializable {

    @FXML
    private TextArea txt1;
    @FXML
    private TextArea txt2;
    @FXML
    private Label labelFinal;
    @FXML
    private MFXButton tbnCerrar;

    private IncrementThread incrementThread;
    private DecrementThread decrementThread;
    @FXML
    private Label labelIni;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        labelFinal.setText("...");
        labelIni.setText("...");
        txt1.setText("");
        txt2.setText("");
    }

    public void init(IncrementThread incrementThread, DecrementThread decrementThread,
            double valorInicial) {

        this.labelIni.setText("Valor inicial: "
                + String.valueOf(valorInicial)
        );
        this.incrementThread = incrementThread;
        this.decrementThread = decrementThread;

        this.incrementThread.setTextArea(txt1);
        this.decrementThread.setTextArea(txt2);
        //iniciamos los hilos
        incrementThread.start();
        decrementThread.start();
        this.monitorizarFinal();
    }

    /**
     * Monitorea si los hilos siguen vivos, si no estan vivos entonces procede a
     * colocar el nuevo valor del numero
     */
    private void monitorizarFinal() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!incrementThread.isAlive() && !decrementThread.isAlive()) {
                        Platform.runLater(() -> {
                            try {
                                labelFinal.setText(String.format(
                                        "Valor final: %s",
                                        String.valueOf(incrementThread.getRepo().getValue())));
                            } catch (SQLException ex) {
                                Logger.getLogger(BitacoraController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        break;
                    }

                }

            }
        };
        thread.start();
    }

    @FXML
    private void cerrar(ActionEvent event) {
        this.decrementThread.interrupt();
        this.incrementThread.interrupt();
        new Loader().closeWindow(txt1);
    }

}
