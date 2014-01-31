/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PlaylistTestWindow.java
 *
 * Created on 07.01.2010, 12:20:26
 */

package net.chilicat.m3u8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dkuffner
 */
class PlaylistTestWindow extends javax.swing.JFrame {

    private final Logger log = Logger.getLogger(getClass().getName());

    private final JLabel protocolBox;
    private final JTextField pathField;
    private final JButton browseButton, startButton;

    private ExecutorService service = Executors.newSingleThreadExecutor();
    private Future<?> currentRunningTask;

    private JEditorPane output;

    /**
     * Creates new form PlaylistTestWindow
     */
    public PlaylistTestWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        protocolBox = new JLabel("URL:");

        pathField = new JTextField();
        pathField.setText("http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8");
        pathField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton.doClick();
            }
        });

        browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFileBrowser();
            }
        });

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startParsing();
            }
        });

        output = new JEditorPane();
        output.setEditable(false);
        getRootPane().setDefaultButton(startButton);

        layoutView();
    }

    private void startParsing() {
        try {
            final URL url = getURL();

            if (currentRunningTask != null) {
                currentRunningTask.cancel(true);
            }

            output.setText("Loading...");
            currentRunningTask = service.submit(new ParsePlaylistTask(url));
        } catch (MalformedURLException e1) {
            log.log(Level.SEVERE, "", e1);
        }
    }

    private void openFileBrowser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = chooser.showDialog(this, "Open");
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile().getAbsoluteFile();
                pathField.setText(file.toURL().toString());
            } catch (MalformedURLException e1) {
                log.log(Level.SEVERE, "", e1);
            }
        }
    }

    private URL getURL() throws MalformedURLException {
        return new URL(pathField.getText());
    }

    private void layoutView() {
        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);

        top.add(protocolBox, c);
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        top.add(pathField, c);
        c.weightx = 0.0;
        top.add(browseButton, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        top.add(startButton, c);

        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(output), BorderLayout.CENTER);
    }

    private class ParsePlaylistTask extends Task<Playlist> {
        private final URL url;

        public ParsePlaylistTask(URL url) {
            this.url = url;
        }

        @Override
        public Playlist construct() throws Exception {
            InputStream in = url.openStream();
            try {
                return Playlist.parse(in);
            } finally {
                in.close();
            }
        }

        @Override
        public void success(Playlist playlist) {

            output.setText(new PlaylistFormat(playlist).format());
        }

        @Override
        public void error(Exception ex) {
            log.log(Level.SEVERE, "", ex);
            StringWriter sw = new StringWriter(100);
            PrintWriter w = new PrintWriter(sw);
            ex.printStackTrace(w);
            output.setText(sw.toString());
        }

    }

}
