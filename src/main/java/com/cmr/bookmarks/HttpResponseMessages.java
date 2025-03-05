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

        // Additional status codes
        responseMessages.put(102, "Processing");
        responseMessages.put(207, "Multi-Status");
        responseMessages.put(226, "IM Used");
        responseMessages.put(307, "Temporary Redirect");
        responseMessages.put(308, "Permanent Redirect");
        responseMessages.put(422, "Unprocessable Entity");
        responseMessages.put(423, "Locked");
        responseMessages.put(424, "Failed Dependency");
        responseMessages.put(426, "Upgrade Required");
        responseMessages.put(428, "Precondition Required");
        responseMessages.put(429, "Too Many Requests");
        responseMessages.put(431, "Request Header Fields Too Large");
        responseMessages.put(451, "Unavailable For Legal Reasons");
        responseMessages.put(508, "Loop Detected");
        responseMessages.put(510, "Not Extended");
        responseMessages.put(511, "Network Authentication Required");

        // Additional status codes not previously included
        responseMessages.put(103, "Early Hints");
        responseMessages.put(202, "Accepted");
        responseMessages.put(203, "Non-Authoritative Information");
        responseMessages.put(204, "No Content");
        responseMessages.put(205, "Reset Content");
        responseMessages.put(418, "I'm a teapot");
        responseMessages.put(421, "Misdirected Request");
        responseMessages.put(444, "Connection Closed Without Response");
        responseMessages.put(499, "Client Closed Request");
        responseMessages.put(506, "Variant Also Negotiates");
        responseMessages.put(507, "Insufficient Storage");
    }

    public static String getMessage(int code) {
        return responseMessages.getOrDefault(code, "Unknown Response Code");
    }
}