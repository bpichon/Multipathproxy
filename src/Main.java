import util.Printer;import java.io.IOException;/** * Created by Bernd on 12.11.2015. */public class Main {    public static void main(String... args) throws IOException, InterruptedException {        if (args.length > 0) {            Printer.file = args[0];        }        Thread connectionListener = new Thread(new ConnectionListener());        connectionListener.start();        connectionListener.join();    }}