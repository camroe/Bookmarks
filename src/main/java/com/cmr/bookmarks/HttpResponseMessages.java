package com.cmr.bookmarks;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseMessages {

    private static final Map<Integer, String> responseMessages = new HashMap<>();

    static {
        responseMessages.put(HttpURLConnection.HTTP_OK, "OK");
        responseMessages.put(HttpURLConnection.HTTP_CREATED, "Created");
        responseMessages.put(HttpURLConnection.HTTP_ACCEPTED, "Accepted");
        responseMessages.put(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, "Non-Authoritative Information");
        responseMessages.put(HttpURLConnection.HTTP_NO_CONTENT, "No Content");
        responseMessages.put(HttpURLConnection.HTTP_RESET, "Reset Content");
        responseMessages.put(HttpURLConnection.HTTP_PARTIAL, "Partial Content");
        responseMessages.put(HttpURLConnection.HTTP_MULT_CHOICE, "Multiple Choices");
        responseMessages.put(HttpURLConnection.HTTP_MOVED_PERM, "Moved Permanently");
        responseMessages.put(HttpURLConnection.HTTP_MOVED_TEMP, "Temporary Redirect");
        responseMessages.put(HttpURLConnection.HTTP_SEE_OTHER, "See Other");
        responseMessages.put(HttpURLConnection.HTTP_NOT_MODIFIED, "Not Modified");
        responseMessages.put(HttpURLConnection.HTTP_USE_PROXY, "Use Proxy");
        responseMessages.put(HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        responseMessages.put(HttpURLConnection.HTTP_UNAUTHORIZED, "Unauthorized");
        responseMessages.put(HttpURLConnection.HTTP_PAYMENT_REQUIRED, "Payment Required");
        responseMessages.put(HttpURLConnection.HTTP_FORBIDDEN, "Forbidden");
        responseMessages.put(HttpURLConnection.HTTP_NOT_FOUND, "Not Found");
        responseMessages.put(HttpURLConnection.HTTP_BAD_METHOD, "Method Not Allowed");
        responseMessages.put(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "Not Acceptable");
        responseMessages.put(HttpURLConnection.HTTP_PROXY_AUTH, "Proxy Authentication Required");
        responseMessages.put(HttpURLConnection.HTTP_CLIENT_TIMEOUT, "Request Timeout");
        responseMessages.put(HttpURLConnection.HTTP_CONFLICT, "Conflict");
        responseMessages.put(HttpURLConnection.HTTP_GONE, "Gone");
        responseMessages.put(HttpURLConnection.HTTP_LENGTH_REQUIRED, "Length Required");
        responseMessages.put(HttpURLConnection.HTTP_PRECON_FAILED, "Precondition Failed");
        responseMessages.put(HttpURLConnection.HTTP_ENTITY_TOO_LARGE, "Request Entity Too Large");
        responseMessages.put(HttpURLConnection.HTTP_REQ_TOO_LONG, "Request-URI Too Long");
        responseMessages.put(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, "Unsupported Media Type");
        responseMessages.put(HttpURLConnection.HTTP_INTERNAL_ERROR, "Internal Server Error");
        responseMessages.put(HttpURLConnection.HTTP_NOT_IMPLEMENTED, "Not Implemented");
        responseMessages.put(HttpURLConnection.HTTP_BAD_GATEWAY, "Bad Gateway");
        responseMessages.put(HttpURLConnection.HTTP_UNAVAILABLE, "Service Unavailable");
        responseMessages.put(HttpURLConnection.HTTP_GATEWAY_TIMEOUT, "Gateway Timeout");
        responseMessages.put(HttpURLConnection.HTTP_VERSION, "HTTP Version Not Supported");
    }

    public static String getMessage(int code) {
        return responseMessages.getOrDefault(code, "Unknown Response Code");
    }
}