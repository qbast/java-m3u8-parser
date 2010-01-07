package net.chilicat.m3u8;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    new PlaylistTestWindow().setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "", e);
        } catch (InvocationTargetException e) {
            log.log(Level.SEVERE, "", e);
        }
    }
}