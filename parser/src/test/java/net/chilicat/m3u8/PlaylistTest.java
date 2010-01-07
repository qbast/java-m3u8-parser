package net.chilicat.m3u8;

import junit.framework.TestCase;

import java.io.InputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dkuffner
 */
public class PlaylistTest extends TestCase {
    String[] urls = {
   //         "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8",
     //       "http://devimages.apple.com/iphone/samples/bipbop/gear2/prog_index.m3u8",
       //     "http://devimages.apple.com/iphone/samples/bipbop/gear3/prog_index.m3u8",
         //   "http://devimages.apple.com/iphone/samples/bipbop/gear4/prog_index.m3u8",
           // "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"
    };


    public void testAppleExamples() throws Exception {
        for(String url : urls) {
            URI uri = URI.create(url);

            InputStream in = uri.toURL().openStream();
            try {
                // TODO: URIs are relative.
                Playlist playlist = Playlist.parse(in);
                Logger.getAnonymousLogger().log(Level.INFO, playlist.toString());
            } finally {
                in.close();
            }
        }
    }


    /*
    public void testPlayList() throws Exception {


        InputStream in = getClass().getResourceAsStream("net.chilicat.m3u8.playlist.m3u8");
        try {
            Playlist playList = Playlist.parse(in);
            assertNotNull(playList);
        } finally {
            in.close();
        }

    }    */



}
