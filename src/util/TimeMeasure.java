package util;

/**
 * Created by Bernd on 27.12.2015.
 */
public class TimeMeasure {

    final long start;

    public TimeMeasure() {
        start = System.nanoTime();
    }

    public void print(final String text) {
        System.out.printf("### TIME: %s: %d ms\n", text, (System.nanoTime() - start)/1_000_000);
    }

}
