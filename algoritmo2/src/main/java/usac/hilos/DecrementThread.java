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
public class DecrementThread extends Thread {

    private final MovimientoRepository repo;
    private TextArea textArea;
    private int decrement;
    private long intervalSeconds;
    private long durationSeconds;

    public DecrementThread(MovimientoRepository dao, int decrement, long intervalSeconds, long durationSeconds) {
        this.repo = dao;
        this.decrement = decrement;
        this.intervalSeconds = intervalSeconds;
        this.durationSeconds = durationSeconds;
    }

    @Override
    public void run() {
        long endTime = System.currentTimeMillis() + durationSeconds * 1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                boolean decrementValue = repo.decrementValue(decrement);
                //adjuntamos si se pudo o no hacer el update
                if (decrementValue) {
                    this.textArea.setText(
                            String.format("%s\n%s > He Decrementado el numero con exito",
                                    this.textArea.getText(), System.currentTimeMillis()));
                } else {
                    this.textArea.setText(
                            String.format("%s\n%s > No he Decrementado el numero :(",
                                    this.textArea.getText(), System.currentTimeMillis()));
                }
                Thread.sleep(intervalSeconds * 1000);
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getDecrement() {
        return decrement;
    }

    public void setDecrement(int decrement) {
        this.decrement = decrement;
    }

    public long getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(long intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
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
