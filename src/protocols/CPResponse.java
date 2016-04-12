package protocols;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Bernd on 05.04.2016.
 */
public class CPResponse {

    public static final String GLOBAL_LAST = "g";
    public static final String LOCAL_LAST = "l";
    public static final String NOT_LAST= "n";

    int requestId;
    int requestPart;
    int responsePart;
    String isLast;
    byte[] content;
    private int duration;

    public CPResponse(int requestId, int requestPart, int responsePart, String isLast, int duration, byte[] content) {
        this.requestId = requestId;
        this.requestPart = requestPart;
        this.responsePart = responsePart;
        this.isLast = isLast;
        this.duration = duration;
        this.content = content;
    }

    public byte[] getBytes() {
        /* Header-Line extrahieren. PR <length> <id> <requestPart> <responsePart> <isLast> \r\n bytes */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write("PR".getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write(("" + content.length).getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write(("" + requestId).getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write(("" + requestPart).getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write(("" + responsePart).getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write((isLast).getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write(("" + duration).getBytes("US-ASCII"));
            out.write(13);  //\r
            out.write(10);  //\n
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            // Ignore, unwichtig
        }
        return out.toByteArray();
    }
}
