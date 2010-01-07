package net.chilicat.m3u8;

import javax.swing.*;

/**
* @author dkuffner
*/
abstract class Task<T> implements Runnable {
    public final void run() {
        final T value;
        try {
            value = construct();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    success(value);
                }
            });
        } catch (Exception e) {
            error(e);
        }
    }

    public abstract T construct() throws Exception;

    public abstract void success(T value);

    public abstract void error(Exception ex);
}
