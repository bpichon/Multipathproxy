import protocols.CPRequest;import java.io.IOException;import java.util.HashMap;import java.util.Map;/** * Created by Bernd on 23.11.2015. */public class RequestMonitor {    Map<String, Container> requestMap;    private static RequestMonitor instance;    public static RequestMonitor getInstance() {        return (instance == null) ? instance = new RequestMonitor() : instance;    }    private RequestMonitor() {        requestMap = new HashMap<>();    }    public byte[] getResponse(final CPRequest request) throws IOException {        final String id = request.hubId + ":" + request.hubRequestId;        Container c;        synchronized (requestMap) {            c = requestMap.get(id);            if (c == null) {                c = new Container(request.getRequest());                requestMap.put(id, c);                c.addResolver(c);            }        }        final int from = request.rangeFrom; // TODO: check if exists        final int to = request.rangeTo;     // TODO: check if exists        final byte[] body = c.put(from, to);        if (c.hasFinished()) {            //requestMap.remove(id);          // FIXME: besser mit Timeout        }        return body;    }    public int getBytesBefore(CPRequest request) {        final String id = request.hubId + ":" + request.hubRequestId;        Container c = requestMap.get(id);        return c.getBytesBefore(request.rangeFrom,  Main.MAX_HUB_RESPONSE_SIZE);    }}