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
public class IncrementThread extends Thread {

    private final MovimientoRepository repo;
    private TextArea textArea;
    private double increment;
    private double interval;
    private double duration;

    public IncrementThread(MovimientoRepository repo, double increment, double interval, double duration) {
        this.repo = repo;
        this.increment = increment;
        this.interval = interval;
        this.duration = duration;
    }

    @Override
    public void run() {
        double endTime = System.currentTimeMillis() + duration * 1000;
        int contador = 1;
        while (System.currentTimeMillis() < endTime) {
            try {
                boolean incrementValue = repo.incrementValue(increment);
                // Obtener la hora actual
                LocalTime horaActual = LocalTime.now();

                // Crear un formateador para la hora en formato HH:mm
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

                // Formatear la hora actual
                String horaFormateada = horaActual.format(formateador);
                if (incrementValue) {
                    this.textArea.setText(
                            String.format("%s\n%s) %s : He Incrementado el numero con exito",
                                    this.textArea.getText(), contador, horaFormateada));
                } else {
                    this.textArea.setText(
                            String.format("%s\n%s) %s : No he Incrementado el numero :(",
                                    this.textArea.getText(), contador, horaFormateada));
                }
                Thread.sleep((long) interval * 1000);
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
            contador++;
        }
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public MovimientoRepository getRepo() {
        return repo;
    }

}
