package protocols;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Bernd on 05.04.2016.
 */
public class CPResponse {

    int requestId;
    int part;
    byte[] content;

    public CPResponse(int requestId, int part, byte[] content) {
        this.requestId = requestId;
        this.part = part;
        this.content = content;
    }

    public byte[] getBytes() {
        /* Header-Line extrahieren. PR <length> <id> <part> \r\n bytes */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write("PR".getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write(("" + content.length).getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write(("" + requestId).getBytes("US-ASCII"));
            out.write(32);  //Space
            out.write(("" + part).getBytes("US-ASCII"));
            out.write(13);  //\r
            out.write(10);  //\n
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
