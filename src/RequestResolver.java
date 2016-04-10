import http.HeaderLine;import http.Request;import http.Response;import util.TimeMeasure;import util.Utility;import java.io.IOException;import java.io.InputStream;import java.io.OutputStream;import java.net.*;import java.text.ParseException;/** * Created by Bernd on 29.11.2015. */public class RequestResolver extends Thread {    public static final byte[] NOT_FOUND_404_RESPONSE = (HeaderLine.HTTP_1_1 + " 404 Not Found\r\n\r\n").getBytes();    final private Container requestContainer;    public RequestResolver(final Container requestContainer) {        this.requestContainer = requestContainer;    }    @Override    public void run() {        try {            Response response = resolveRequest(requestContainer.request);            requestContainer.response = response;        } catch (MalformedURLException ex) {            ex.printStackTrace();            requestContainer.response = new Response.BadRequest();        } catch (UnknownHostException ex) {            ex.printStackTrace();            requestContainer.response = new Response.NotFound();        } catch (IOException e) {            e.printStackTrace();            requestContainer.response = new Response.InternalServerError();        }    }    private Response resolveRequest(final Request request) throws IOException {        Socket socket;        Response response;        TimeMeasure time = new TimeMeasure();        /* Sending Request */        final URL url = new URL(request.requestLine.requestTarget);        socket = new Socket(url.getHost(), (url.getPort() < 0) ? 80 : url.getPort()); // Wenn kein Port angegeben ist, dann Standard Http 80        time.print("After Socket: " + ": " + request.requestLine.toString());        /* Send request */        final OutputStream internetOut = socket.getOutputStream();        internetOut.write(request.getBytes());        internetOut.flush();        //socket.shutdownOutput();        /* Receive response */        final InputStream internetIn = socket.getInputStream();        response = new Response();        byte[] oldBuffer = null;        byte[] buffer = new byte[1024*8];        int length;        try {            while ((length = internetIn.read(buffer)) >= 0) {                final byte[] messageBuffer = (oldBuffer != null && oldBuffer.length > 0) ? Utility.join(oldBuffer, oldBuffer.length, buffer, length) : buffer;                oldBuffer = response.parse(messageBuffer, length + ((oldBuffer == null) ? 0 : oldBuffer.length));                if (oldBuffer == null) {                    break;                }            }        } catch (ParseException ex) {            ex.printStackTrace();            response = null;            System.err.println("Parsing-Error!");            // TODO: handle error?        } catch (UnknownHostException ex) {            ex.printStackTrace();            return new Response.NotFound();        } finally {            if (socket != null) {                try {                    socket.close();                } catch (IOException ex) {                    ex.printStackTrace();                }            }        }        time.print("Resolved, Parsed Request: " + request.requestLine.toString());        return response;    }}