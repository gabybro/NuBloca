package ro.nubloca.Networking;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

public class HttpBodyGet extends HttpEntityEnclosingRequestBase {
    public final static String METHOD_NAME = "BODYGET";

    public HttpBodyGet() {
        super();
    }

    public HttpBodyGet(final URI uri) {
        super();
        setURI(uri);
    }

    /**
     * @throws IllegalArgumentException if the uri is invalid.
     */
    public HttpBodyGet(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}