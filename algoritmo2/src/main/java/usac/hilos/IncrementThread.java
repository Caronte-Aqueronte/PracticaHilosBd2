/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usac.hilos;

import java.sql.SQLException;
import javafx.scene.control.TextArea;
import usac.repositories.MovimientoRepository;

/**
 *
 * @author Luis Monterroso
 */
public class IncrementThread extends Thread {

    private final MovimientoRepository repo;
    private TextArea textArea;
    private int increment;
    private long interval;
    private long duration;

    public IncrementThread(MovimientoRepository repo, int increment, long interval, long duration) {
        this.repo = repo;
        this.increment = increment;
        this.interval = interval;
        this.duration = duration;
    }

    @Override
    public void run() {
        long endTime = System.currentTimeMillis() + duration * 1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                boolean incrementValue = repo.incrementValue(increment);
                if (incrementValue) {
                    this.textArea.setText(
                            String.format("%s\n%s > He Incrementado el numero con exito",
                                    this.textArea.getText(), System.currentTimeMillis()));
                } else {
                    this.textArea.setText(
                            String.format("%s\n%s > No he Incrementado el numero :(",
                                    this.textArea.getText(), System.currentTimeMillis()));
                }
                Thread.sleep(interval * 1000);
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
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
