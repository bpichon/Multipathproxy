package util;

import java.io.*;

/**
 * Created by Bernd on 10.03.2016.
 */
public class Printer {

    public static String file = null;

    private static Printer instance;

    private BufferedWriter writer;

    public static Printer getInstance() {
        if (instance == null) {
            try {
                instance = new Printer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private Printer() throws IOException {
        if (file != null) {
            FileWriter fw = new FileWriter("output.txt", true);
            writer = new BufferedWriter(fw);
        }
    }

    public void print(String s) throws IOException {
        if (writer != null)
            writer.write(s);
    }

    public void print(byte[] b) throws IOException {
        if (writer != null)
            writer.write(new String(b, "US-ASCII"));
    }

    public void flush() throws IOException {
        if (writer != null)
            writer.flush();
    }

    public void close() throws IOException {
        if (writer != null)
            writer.close();
    }
}
