package com.cmr.bookmarks;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseMessages {

    private static final Map<Integer, String> responseMessages = new HashMap<>();

    static {
        responseMessages.put(HttpURLConnection.HTTP_OK, "OK"); // 200
        responseMessages.put(HttpURLConnection.HTTP_CREATED, "Created"); // 201
        responseMessages.put(HttpURLConnection.HTTP_ACCEPTED, "Accepted"); // 202
        responseMessages.put(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, "Non-Authoritative Information"); // 203
        responseMessages.put(HttpURLConnection.HTTP_NO_CONTENT, "No Content"); // 204
        responseMessages.put(HttpURLConnection.HTTP_RESET, "Reset Content"); // 205
        responseMessages.put(HttpURLConnection.HTTP_PARTIAL, "Partial Content"); // 206
        responseMessages.put(HttpURLConnection.HTTP_MULT_CHOICE, "Multiple Choices"); // 300
        responseMessages.put(HttpURLConnection.HTTP_MOVED_PERM, "Moved Permanently"); // 301
        responseMessages.put(HttpURLConnection.HTTP_MOVED_TEMP, "Temporary Redirect"); // 302
        responseMessages.put(HttpURLConnection.HTTP_SEE_OTHER, "See Other"); // 303
        responseMessages.put(HttpURLConnection.HTTP_NOT_MODIFIED, "Not Modified"); // 304
        responseMessages.put(HttpURLConnection.HTTP_USE_PROXY, "Use Proxy"); // 305
        responseMessages.put(HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request"); // 400
        responseMessages.put(HttpURLConnection.HTTP_UNAUTHORIZED, "Unauthorized"); // 401
        responseMessages.put(HttpURLConnection.HTTP_PAYMENT_REQUIRED, "Payment Required"); // 402
        responseMessages.put(HttpURLConnection.HTTP_FORBIDDEN, "Forbidden"); // 403
        responseMessages.put(HttpURLConnection.HTTP_NOT_FOUND, "Not Found"); // 404
        responseMessages.put(HttpURLConnection.HTTP_BAD_METHOD, "Method Not Allowed"); // 405
        responseMessages.put(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "Not Acceptable"); // 406
        responseMessages.put(HttpURLConnection.HTTP_PROXY_AUTH, "Proxy Authentication Required"); // 407
        responseMessages.put(HttpURLConnection.HTTP_CLIENT_TIMEOUT, "Request Timeout"); // 408
        responseMessages.put(HttpURLConnection.HTTP_CONFLICT, "Conflict"); // 409
        responseMessages.put(HttpURLConnection.HTTP_GONE, "Gone"); // 410
        responseMessages.put(HttpURLConnection.HTTP_LENGTH_REQUIRED, "Length Required"); // 411
        responseMessages.put(HttpURLConnection.HTTP_PRECON_FAILED, "Precondition Failed"); // 412
        responseMessages.put(HttpURLConnection.HTTP_ENTITY_TOO_LARGE, "Request Entity Too Large"); // 413
        responseMessages.put(HttpURLConnection.HTTP_REQ_TOO_LONG, "Request-URI Too Long"); // 414
        responseMessages.put(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, "Unsupported Media Type"); // 415
        responseMessages.put(HttpURLConnection.HTTP_INTERNAL_ERROR, "Internal Server Error"); // 500
        responseMessages.put(HttpURLConnection.HTTP_NOT_IMPLEMENTED, "Not Implemented"); // 501
        responseMessages.put(HttpURLConnection.HTTP_BAD_GATEWAY, "Bad Gateway"); // 502
        responseMessages.put(HttpURLConnection.HTTP_UNAVAILABLE, "Service Unavailable"); // 503
        responseMessages.put(HttpURLConnection.HTTP_GATEWAY_TIMEOUT, "Gateway Timeout"); // 504
        responseMessages.put(HttpURLConnection.HTTP_VERSION, "HTTP Version Not Supported"); // 505
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