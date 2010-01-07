package net.chilicat.m3u8;

import junit.framework.TestCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dkuffner
 */
public class PatternTest extends TestCase {
    public void testEmpty() {

    }

    public void testDateTime() throws Exception {
        String line = "#EXT-X-PROGRAM-DATE-TIME:2009-12-30T12:10:01+0100"; //"#EXT-X-PROGRAM-DATE-TIME:2001-01-01 10:15:30  ";
        M3uConstants.Patterns.toDate(line, 0);
    }


    public void testEXT_KEY() throws Exception {
        System.out.println(M3uConstants.Patterns.EXT_X_KEY.pattern());
    }


    public void testEXTINF_NO_MATCH() throws Exception {
        // not matching:
        Pattern patter = M3uConstants.Patterns.EXTINF;

        String[] list = new String[]{
                "#EXTINF:A200,Title",
                "#EXTINF:200A,Title",
                "#  EXTINF:200,Title",
                "#EXTI NF:200,Title",
        };

        int index = 0;
        for (String param : list) {
            System.out.println("Process: '" + param + "'");
            Matcher matcher = patter.matcher(param);

            matcher.find();
            printGroups(index, matcher);

            // assertEquals("Index: " + index, true, matcher.find());
            assertEquals("Index: " + index, false, matcher.matches());
            index++;
        }
    }


    public void testEXTINF() throws Exception {
        Pattern patter = M3uConstants.Patterns.EXTINF;

        String[] list = {
                "#EXTINF:200,Title",
                "\t  #EXTINF \t : \t 200 \t , \t Title\t",
                //"#EXTINF:200,",
        };

        int index = 0;
        for (String param : list) {
            System.out.println("Process: '" + param + "'");
            Matcher matcher = patter.matcher(param);
            assertEquals("Index: " + index, true, matcher.find());
            assertEquals("Index: " + index, true, matcher.matches());
            assertEquals("Index: " + index, 200, Integer.valueOf(matcher.group(1).trim()).intValue());
            assertEquals("Index: " + index, "Title", matcher.group(2).trim());

            printGroups(index, matcher);

            index++;
        }
    }

    private void printGroups(int index, Matcher matcher) {
        if (matcher.find()) {
            System.out.println("Pattern: " + matcher.pattern());
            System.out.println("Group count: " + matcher.groupCount());
            for (int i = 0; i < matcher.groupCount(); i++) {
                System.out.println("\t" + index + ":" + i + " '" + matcher.group(i) + "'");
            }
        } else {
            System.out.println("No matches");
        }
    }

}
