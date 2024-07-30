/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usac.hilos;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextArea;
import usac.repositories.MovimientoRepository;

/**
 *
 * @author Luis Monterroso
 */
public class DecrementThread extends Thread {

    private final MovimientoRepository repo;
    private TextArea textArea;
    private double decrement;
    private double intervalSeconds;
    private double durationSeconds;

    public DecrementThread(MovimientoRepository repo, double decrement, double intervalSeconds, double durationSeconds) {
        this.repo = repo;
        this.decrement = decrement;
        this.intervalSeconds = intervalSeconds;
        this.durationSeconds = durationSeconds;
    }

    @Override
    public void run() {
        double endTime = System.currentTimeMillis() + durationSeconds * 1000;
        int contador = 1;
        while (System.currentTimeMillis() < endTime) {
            try {
                boolean decrementValue = repo.decrementValue(decrement);
                // Obtener la hora actual
                LocalTime horaActual = LocalTime.now();

                // Crear un formateador para la hora en formato HH:mm
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

                // Formatear la hora actual
                String horaFormateada = horaActual.format(formateador);
                //adjuntamos si se pudo o no hacer el update
                if (decrementValue) {
                    this.textArea.setText(
                            String.format("%s\n%s) %s : He Decrementado el numero con exito",
                                    this.textArea.getText(), contador, horaFormateada));
                } else {
                    this.textArea.setText(
                            String.format("%s\n%s) %s : No he Decrementado el numero :(",
                                    this.textArea.getText(), contador, horaFormateada));
                }
                Thread.sleep((long) intervalSeconds * 1000);
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
            contador++;
        }
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public double getDecrement() {
        return decrement;
    }

    public void setDecrement(double decrement) {
        this.decrement = decrement;
    }

    public double getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(double intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public double getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(double durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public MovimientoRepository getRepo() {
        return repo;
    }

}
