package protocols;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Bernd on 05.04.2016.
 */
public class CPResponse {

    int requestId;
    int requestPart;
    int responsePart;
    boolean isLast;
    byte[] content;

    public CPResponse(int requestId, int requestPart, int responsePart, boolean isLast, byte[] content) {
        this.requestId = requestId;
        this.requestPart = requestPart;
        this.responsePart = responsePart;
        this.isLast = isLast;
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
            out.write((isLast ? "t" : "f").getBytes("US-ASCII"));
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
