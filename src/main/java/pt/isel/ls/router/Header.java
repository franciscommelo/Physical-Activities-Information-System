package pt.isel.ls.router;

import java.util.HashMap;


public class Header {

    String stringHeader;
    HashMap<String, String> headers = new HashMap<>();

    public Header(String stringHeader) {
        this.stringHeader = stringHeader;
        setHeaders();
    }

    /**
     * add all to an Hashmap with with a key value "Column name" and a list with all values.
     */
    private void setHeaders() {
        String[] header = stringHeader.split("\\|");
        for (String s : header) {
            String[] vals = s.split(":");
            headers.put(vals[0], vals[1]);
        }
    }

    /**
     * @return HashMap headers
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
