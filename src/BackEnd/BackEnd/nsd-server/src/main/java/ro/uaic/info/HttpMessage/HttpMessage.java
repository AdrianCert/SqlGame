package ro.uaic.info.HttpMessage;

/**
 * Class contains general information and utility of Http Message
 */
public abstract class HttpMessage {

    /**
     * Http Methods
     * HTTP defines a set of request methods to indicate the desired action to be performed for a given resource.
     * Although they can also be nouns, these request methods are sometimes referred to as HTTP verbs.
     * See <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods">Mozilla/Http/Methods</a>
     */
    public static final class METHOD {
        /**
         * The GET method requests a representation of the specified resource.
         * Requests using GET should only retrieve data.
         */
        public static final String GET = "GET";
        /**
         * The HEAD method asks for a response identical to that of a GET request, but without the response body.
         */
        public static final String HEAD = "HEAD";
        /**
         * The POST method is used to submit an entity to the specified resource,
         * often causing a change in state or side effects on the server.
         */
        public static final String POST = "POST";
        /**
         * The PUT method replaces all current representations of the target resource with the request payload.
         */
        public static final String PUT = "PUT";
        /**
         * The DELETE method deletes the specified resource.
         */
        public static final String DELETE = "DELETE";
        /**
         * The CONNECT method establishes a tunnel to the server identified by the target resource.
         */
        public static final String CONNECT = "CONNECT";
        /**
         * The OPTIONS method is used to describe the communication options for the target resource.
         */
        public static final String OPTIONS = "OPTIONS";
        /**
         * The TRACE method performs a message loop-back test along the path to the target resource.
         */
        public static final String TRACE = "TRACE";
        /**
         * The PATCH method is used to apply partial modifications to a resource.
         */
        public static final String PATCH = "PATCH";
    }

    /**
     * Standard headers
     * HTTP headers let the client and the server pass additional information with an HTTP request or response.
     * An HTTP header consists of its case-insensitive name followed by a colon (:), then by its value.
     *
     * See <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers">Mozilla/Http/Headers</a>
     */
    public static final class HEADER {
        // Authentication
        /**
         * Defines the authentication method that should be used to access a resource.
         */
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
        /**
         * Contains the credentials to authenticate a user-agent with a server.
         */
        public static final String AUTHORIZATION = "Authorization";
        /**
         * Defines the authentication method that should be used to access a resource behind a proxy server.
         */
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        /**
         * Contains the credentials to authenticate a user agent with a proxy server.
         */
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        // Caching
        /**
         * The time, in seconds, that the object has been in a proxy cache.
         */
        public static final String AGE = "Age";
        /**
         * Directives for caching mechanisms in both requests and responses.
         */
        public static final String CACHE_CONTROL = "Cache-Control";
        /**
         * Clears browsing data (e.g. cookies, storage, cache) associated with the requesting website.
         */
        public static final String CLEAR_SITE_DATA = "Clear-Site-Data";
        /**
         * The date/time after which the response is considered stale.
         */
        public static final String EXPIRES = "Expires";
        /**
         * Implementation-specific header that may have various effects anywhere along the request-response chain.
         * Used for backwards compatibility with HTTP/1.0 caches where the Cache-Control header is not yet present.
         */
        public static final String PRAGMA = "Pragma";
        /**
         * General warning information about possible problems.
         */
        public static final String WARNING = "Warning";
        // Client hints
        /**
         * Servers can advertise support for Client Hints using the Accept-CH header field or an equivalent
         * HTML &lt;meta&gt; element with http-equiv attribute ([HTML]).
         */
        public static final String ACCEPT_CH = "Accept-CH";
        /**
         * Servers can ask the client to remember the set of Client Hints that the server supports for a specified
         * period of time, to enable delivery of Client Hints on subsequent requests to the server’s origin
         */
        public static final String ACCEPT_CH_LIFETIME = "Accept-CH-Lifetime";
        /**
         * Indicates that the request has been conveyed in early data.
         */
        public static final String EARLY_DATA = "Early-Data";
        /**
         * Technically a part of Device Memory API, this header represents an approximate amount of RAM client has.
         */
        public static final String DEVICE_MEMORY = "Device-Memory";
        /**
         * A boolean that indicates the user agent's preference for reduced data usage.
         */
        public static final String SAVE_DATA = "Save-Data";
        /**
         * A number that indicates the layout viewport width in CSS pixels. The provided pixel value is a number
         * rounded to the smallest following integer (i.e. ceiling value).
         */
        public static final String VIEWPORT_WIDTH = "Viewport-Width";
        /**
         * The Width request header field is a number that indicates the desired resource width
         * in physical pixels (i.e. intrinsic size of an image). The provided pixel value is
         * a number rounded to the smallest following integer (i.e. ceiling value).
         */
        public static final String WIDTH= "Width";
        // Conditionals
        /**
         * The last modification date of the resource, used to compare several versions of the same resource.
         * It is less accurate than ETag, but easier to calculate in some environments.
         * Conditional requests using If-Modified-Since and If-Unmodified-Since use this value
         * to change the behavior of the request.
         */
        public static final String LAST_MODIFIED = "Last-Modified";
        /**
         * A unique string identifying the version of the resource. Conditional requests using If-Match
         * and If-None-Match use this value to change the behavior of the request.
         */
        public static final String ETAG = "ETag";
        /**
         * Makes the request conditional, and applies the method only
         * if the stored resource matches one of the given ETags.
         */
        public static final String IF_MATCH = "If-Match";
        /**
         * Makes the request conditional, and applies the method only if the stored resource doesn't match any of
         * the given ETags. This is used to update caches (for safe requests), or to prevent to upload
         * a new resource when one already exists.
         */
        public static final String IF_NONE_MATCH = "If-None-Match";
        /**
         * Makes the request conditional, and expects the resource to be transmitted only if it has been modified
         * after the given date. This is used to transmit data only when the cache is out of date.
         */
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        /**
         * Makes the request conditional, and expects the resource to be transmitted only if it has not been modified
         * after the given date. This ensures the coherence of a new fragment of a specific range with previous ones,
         * or to implement an optimistic concurrency control system when modifying existing documents.
         */
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        /**
         * Determines how to match request headers to decide whether a cached response can be used rather
         * than requesting a fresh one from the origin server.
         */
        public static final String VARY = "Vary";
        // Connection management
        /**
         * Controls whether the network connection stays open after the current transaction finishes.
         */
        public static final String CONNECTION = "Connection";
        /**
         * Controls how long a persistent connection should stay open.
         */
        public static final String KEEP_ALIVE = "Keep-Alive";
        // Content negotiation
        /**
         * Informs the server about the types of data that can be sent back.
         */
        public static final String ACCEPT = "Accept";
        /**
         * The encoding algorithm, usually a compression algorithm, that can be used on the resource sent back.
         */
        public static final String ACCEPT_ENCODING= "Accept-Encoding";
        /**
         * Informs the server about the human language the server is expected to send back.
         * This is a hint and is not necessarily under the full control of the user: the server should
         * always pay attention not to override an explicit user choice (like selecting a language from a dropdown).
         */
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        // Controls
        /**
         * Indicates expectations that need to be fulfilled by the server to properly handle the request.
         */
        public static final String EXPECT = "Expect";
        /**
         *
         */
        public static final String MAX_FORWARDS = "Max-Forwards";
        // Cookies
        /**
         * Contains stored HTTP cookies previously sent by the server with the Set-Cookie header.
         */
        public static final String COOKIE = "Cookie";
        /**
         * Send cookies from the server to the user-agent.
         */
        public static final String SET_COOKIE = "Set-Cookie";
        /**
         * Contains an HTTP cookie previously sent by the server with the Set-Cookie2 header,
         * but has been obsoleted. Use Cookie instead.
         */
        public static final String COOKIE2 = "Cookie2";
        /**
         * Sends cookies from the server to the user-agent, but has been obsoleted. Use Set-Cookie instead.
         */
        public static final String SET_COOKIE2 = "Set-Cookie2";
        // CORS
        /**
         * Indicates whether the response can be shared.
         */
        public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        /**
         * Indicates whether the response to the request can be exposed when the credentials flag is true.
         */
        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        /**
         * Used in response to a preflight request to indicate which
         * HTTP headers can be used when making the actual request.
         */
        public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        /**
         * Specifies the methods allowed when accessing the resource in response to a preflight request.
         */
        public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        /**
         * Indicates which headers can be exposed as part of the response by listing their names.
         */
        public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        /**
         * Indicates how long the results of a preflight request can be cached.
         */
        public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
        /**
         * Used when issuing a preflight request to let the server know which
         * HTTP headers will be used when the actual request is made.
         */
        public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
        /**
         * Used when issuing a preflight request to let the server know which
         * HTTP headers will be used when the actual request is made.
         */
        public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
        /**
         * Indicates where a fetch originates from
         */
        public static final String ORIGIN = "Origin";
        /**
         * Specifies origins that are allowed to see values of attributes retrieved via features of
         * the Resource Timing API, which would otherwise be reported as zero due to cross-origin restrictions.
         */
        public static final String TIMING_ALLOW_ORIGIN = "Timing-Allow-Origin";
        // Downloads
        /**
         * Indicates if the resource transmitted should be displayed inline (default behavior without the header),
         * or if it should be handled like a download and the browser should present a “Save As” dialog.
         */
        public static final String CONTENT_DISPOSITION = "Content-Disposition";
        // Message body information
        /**
         * The size of the resource, in decimal number of bytes.
         */
        public static final String CONTENT_LENGTH = "Content-Length";
        /**
         * Indicates the media type of the resource.
         */
        public static final String CONTENT_TYPE = "Content-Type";
        /**
         * Used to specify the compression algorithm.
         */
        public static final String CONTENT_ENCODING = "Content-Encoding";
        /**
         * Describes the human language(s) intended for the audience, so that it allows
         * a user to differentiate according to the users' own preferred language.
         */
        public static final String CONTENT_LANGUAGE = "Content-Language";
        /**
         * Indicates an alternate location for the returned data.
         */
        public static final String CONTENT_LOCATION = "Content-Location";
        // Proxies
        /**
         * Contains information from the client-facing side of proxy servers that is altered
         * or lost when a proxy is involved in the path of the request.
         */
        public static final String FORWARDED = "Forwarded";
        /**
         * Identifies the originating IP addresses of a client connecting to a web server
         * through an HTTP proxy or a load balancer.
         */
        public static final String X_FORWARDED_FOR = "X-Forwarded-For";
        /**
         * Identifies the original host requested that a client used to connect to your proxy or load balancer.
         */
        public static final String X_FORWARDED_HOST = "X-Forwarded-Host";
        /**
         * Identifies the protocol (HTTP or HTTPS) that a client used to connect to your proxy or load balancer.
         */
        public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
        /**
         * Added by proxies, both forward and reverse proxies, and can appear in the request headers
         * and the response headers
         */
        public static final String VIA = "Via";
        // Redirects
        /**
         * Indicates the URL to redirect a page to.
         */
        public static final String LOCATION = "Location";
        // Request context
        /**
         * Contains an Internet email address for a human user who controls the requesting user agent.
         */
        public static final String FROM = "From";
        /**
         * Specifies the domain name of the server (for virtual hosting), and (optionally)
         * the TCP port number on which the server is listening.
         */
        public static final String HOST = "Host";
        /**
         * The address of the previous web page from which a link to the currently requested page was followed.
         */
        public static final String REFERER = "Referer";
        /**
         * Governs which referrer information sent in the Referer header should be included with requests made.
         */
        public static final String REFERRER_POLICY = "Referrer-Policy";
        /**
         * Contains a characteristic string that allows the network protocol peers to identify the application type,
         * operating system, software vendor or software version of the requesting software user agent.
         * See also the Firefox user agent string reference.
         */
        public static final String USER_AGENT = "User-Agent";
        // Response context
        /**
         * Lists the set of HTTP request methods supported by a resource.
         */
        public static final String ALLOW = "Allow";
        /**
         * Contains information about the software used by the origin server to handle the request.
         */
        public static final String SERVER = "Server";
        // Range requests
        /**
         * Indicates if the server supports range requests, and if so in which unit the range can be expressed.
         */
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        /**
         * Indicates the part of a document that the server should return.
         */
        public static final String RANGE = "Range";
        /**
         * Creates a conditional range request that is only fulfilled if the given etag or date matches
         * the remote resource. Used to prevent downloading two ranges from incompatible version of the resource.
         */
        public static final String IF_RANGE = "If-Range";
        /**
         * Indicates where in a full body message a partial message belongs.
         */
        public static final String CONTENT_RANGE = "Content-Range";
        // Security
        /**
         * Allows a server to declare an embedder policy for a given document.
         */
        public static final String CROSS_ORIGIN_EMBEDDER_POLICY = "Cross-Origin-Embedder-Policy";
        /**
         * Prevents other domains from opening/controlling a window.
         */
        public static final String CROSS_ORIGIN_OPENER_POLICY = "Cross-Origin-Opener-Policy";
        /**
         * Prevents other domains from reading the response of the resources to which this header is applied.
         */
        public static final String CROSS_ORIGIN_RESOURCE_POLICY = "Cross-Origin-Resource-Policy";
        /**
         * Controls resources the user agent is allowed to load for a given page.
         */
        public static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";
        /**
         * Allows web developers to experiment with policies by monitoring, but not enforcing, their effects.
         * These violation reports consist of JSON documents sent via an HTTP POST request to the specified URI.
         */
        public static final String CONTENT_SECURITY_POLICY_REPORT_ONLY = "Content-Security-Policy-Report-Only";
        /**
         * Allows sites to opt in to reporting and/or enforcement of Certificate Transparency requirements, which
         * prevents the use of misused certificates for that site from going unnoticed.
         * When a site enables the Expect-CT header, they are requesting that Chrome check
         * that any certificate for that site appears in public CT logs.
         */
        public static final String EXPECT_CT = "Expect-CT";
        /**
         * Provides a mechanism to allow and deny the use of browser features in its own frame,
         * and in iframes that it embeds.
         */
        public static final String FEATURE_POLICY = "Feature-Policy";
        /**
         * Provides a mechanism to allow web applications to isolate their origins.
         */
        public static final String ORIGIN_ISOLATION = "Origin-Isolation";
        /**
         * Force communication using HTTPS instead of HTTP.
         */
        public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
        /**
         * Sends a signal to the server expressing the client’s preference for an encrypted and authenticated response,
         * and that it can successfully handle the upgrade-insecure-requests directive.
         */
        public static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
        /**
         * Disables MIME sniffing and forces browser to use the type given in Content-Type.
         */
        public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
        /**
         * The X-Download-Options HTTP header indicates that the browser (Internet Explorer) should not display
         * the option to "Open" a file that has been downloaded from an application, to prevent phishing attacks
         * as the file otherwise would gain access to execute in the context of the application.
         */
        public static final String X_DOWNLOAD_OPTIONS= "X-Download-Options";
        /**
         * Indicates whether a browser should be allowed to render a page in a &lt;frame&gt;, &lt;iframe&gt;, &lt;embed&gt; or &lt;object&gt;.
         */
        public static final String X_FRAME_OPTIONS = "X-Frame-Options";
        /**
         * Specifies if a cross-domain policy file (crossdomain.xml) is allowed. The file may define a policy
         * to grant clients, such as Adobe's Flash Player (now obsolete), Adobe Acrobat, Microsoft Silverlight
         * (now obsolete), or Apache Flex, permission to handle data across domains that would otherwise be restricted
         * due to the Same-Origin Policy. See the Cross-domain Policy File Specification for more information.
         */
        public static final String X_PERMITTED_CROSS_DOMAIN_POLICIES = "X-Permitted-Cross-Domain-Policies";
        /**
         * May be set by hosting environments or other frameworks and contains information about them while not
         * providing any usefulness to the application or its visitors.
         * Unset this header to avoid exposing potential vulnerabilities
         */
        public static final String X_POWERED_BY = "X-Powered-By";
        /**
         * Enables cross-site scripting filtering.
         */
        public static final String X_XSS_PROTECTION = "X-XSS-Protection";
        // HTTP Public Key Pinning
        /**
         * Associates a specific cryptographic public key with a certain web server to decrease the risk
         * of MITM attacks with forged certificates.
         */
        public static final String PUBLIC_KEY_PINS = "Public-Key-Pins";
        /**
         * Sends reports to the report-uri specified in the header and does still allow clients
         * to connect to the server even if the pinning is violated.
         */
        public static final String PUBLIC_KEY_PINS_REPORT_ONLY = "Public-Key-Pins-Report-Only";
        // Fetch metadata request headers
        /**
         * It is a request header that indicates the relationship between a request initiator's origin and its
         * target's origin. It is a Structured Header whose value is a token with possible values cross-site,
         * same-origin, same-site, and none.
         */
        public static final String SEC_FETCH_SITE = "Sec-Fetch-Site";
        /**
         * It is a request header that indicates the request's mode to a server. It is a Structured Header whose
         * value is a token with possible values cors, navigate, nested-navigate, no-cors, same-origin, and websocket.
         */
        public static final String SEC_FETCH_MODE = "Sec-Fetch-Mode";
        /**
         * It is a request header that indicates whether or not a navigation request was triggered by user activation.
         * It is a Structured Header whose value is a boolean so possible values are ?0 for false and ?1 for true.
         */
        public static final String SEC_FETCH_USER = "Sec-Fetch-User";
        /**
         * It is a request header that indicates the request's destination to a server.
         * It is a Structured Header whose value is a token with possible values audio,
         * audioworklet, document, embed, empty, font, image, manifest, object, paintworklet,
         * report, script, serviceworker, sharedworker, style, track, video, worker, xslt, and nested-document.
         */
        public static final String SEC_FETCH_DEST = "Sec-Fetch-Dest";
        // Server-sent events
        /**
         * ...
         */
        public static final String LAST_EVENT_IDX = "Last-Event-ID";
        /**
         * Defines a mechanism that enables developers to declare a network error reporting policy.
         */
        public static final String NEL = "NEL";
        /**
         * ...
         */
        public static final String PING_FROM = "Ping-From";
        /**
         * ...
         */
        public static final String PING_TO = "Ping-To";
        /**
         * Used to specify a server endpoint for the browser to send warning and error reports to.
         */
        public static final String REPORT_TO = "Report-To";
        // Transfer coding
        /**
         * Specifies the form of encoding used to safely transfer the resource to the user.
         */
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        /**
         * Specifies the transfer encodings the user agent is willing to accept.
         */
        public static final String TE = "TE";
        /**
         * Allows the sender to include additional fields at the end of chunked message.
         */
        public static final String TRAILER = "Trailer";
        // WebSockets
        /**
         * ...
         */
        public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
        /**
         * ...
         */
        public static final String SEC_WEBSOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";
        /**
         * ...
         */
        public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
        /**
         * ...
         */
        public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        /**
         * ...
         */
        public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
        // Other
        /**
         * A client can express the desired push policy for a request by sending
         * an Accept-Push-Policy header field in the request.
         */
        public static final String ACCEPT_PUSH_POLICY = "Accept-Push-Policy";
        /**
         * A client can send the Accept-Signature header field to indicate intention to take advantage
         * of any available signatures and to indicate what kinds of signatures it supports.
         */
        public static final String ACCEPT_SIGNATURE = "Accept-Signature";
        /**
         * Used to list alternate ways to reach this service.
         */
        public static final String ALT_SVC = "Alt-Svc";
        /**
         * Contains the date and time at which the message was originated.
         */
        public static final String DATE = "Date";
        /**
         * Tells the browser that the page being loaded is going to want to perform a large allocation.
         */
        public static final String LARGE_ALLOCATION = "Large-Allocation";
        /**
         * The Link entity-header field provides a means for serialising one or more links in HTTP headers.
         * It is semantically equivalent to the HTML link element.
         */
        public static final String LINK = "Link";
        /**
         * A Push-Policy defines the server behavior regarding push when processing a request.
         */
        public static final String PUSH_POLICY = "Push-Policy";
        /**
         * Indicates how long the user agent should wait before making a follow-up request.
         */
        public static final String RETRY_AFTER = "Retry-After";
        /**
         * The Signature header field conveys a list of signatures for an exchange, each one accompanied
         * by information about how to determine the authority of and refresh that signature.
         */
        public static final String SIGNATURE = "Signature";
        /**
         * The Signed-Headers header field identifies an ordered list of response header
         * fields to include in a signature.
         */
        public static final String SIGNED_HEADERS = "Signed-Headers";
        /**
         * Communicates one or more metrics and descriptions for the given request-response cycle.
         */
        public static final String SERVER_TIMING = "Server-Timing";
        /**
         * Used to remove the path restriction by including this header in the response of the Service Worker script.
         */
        public static final String SERVICE_WORKER_ALLOWED = "Service-Worker-Allowed";
        /**
         * Links generated code to a source map.
         */
        public static final String SOURCEMAP = "SourceMap";
        /**
         * The relevant RFC document for the Upgrade header field is RFC 7230, section 6.7. The standard establishes
         * rules for upgrading or changing to a different protocol on the current client, server, transport protocol
         * connection. For example, this header standard allows a client to change from HTTP 1.1 to HTTP 2.0, assuming
         * the server decides to acknowledge and implement the Upgrade header field. Neither party is required
         * to accept the terms specified in the Upgrade header field. It can be used in both client and server headers.
         * If the Upgrade header field is specified, then the sender MUST also send the Connection header field
         * with the upgrade option specified.
         */
        public static final String UPGRADE = "Upgrade";
        /**
         * Controls DNS prefetching, a feature by which browsers proactively perform domain name resolution
         * on both links that the user may choose to follow as well as URLs for items referenced by the document,
         * including images, CSS, JavaScript, and so forth.
         */
        public static final String X_DNS_PREFETCH_CONTROL = "X-DNS-Prefetch-Control";
        /**
         * ...
         */
        public static final String X_FIREFOX_SPDY = "X-Firefox-Spdy";
        /**
         * ...
         */
        public static final String X_PINGBACK = "X-Pingback";
        /**
         * ...
         */
        public static final String X_REQUESTED_WITH = "X-Requested-With";
        /**
         * The X-Robots-Tag HTTP header is used to indicate how a web page is to be indexed within public search
         * engine results. The header is effectively equivalent to &lt; meta name="robots" content="..."&gt;.
         */
        public static final String X_ROBOTS_TAG = "X-Robots-Tag";
        /**
         * Used by Internet Explorer to signal which document mode to use.
         */
        public static final String X_UA_COMPATIBLE = "X-UA-Compatible";
    }

    /**
     * Get the Standard Reason Phrase
     * @param status The status code value
     * @return The Standard Phrase according each status code
     */
    public static String getStandardReasonPhrase(Integer status) {
        if (status == 100) return "Continue";
        if (status == 101) return "Switching Protocols";
        if (status == 102) return "Processing";
        if (status == 103) return "Early Hints";
        if (status == 200) return "OK";
        if (status == 201) return "Created";
        if (status == 202) return "Accepted";
        if (status == 203) return "Non-Authoritative Information";
        if (status == 204) return "No Content";
        if (status == 205) return "Reset Content";
        if (status == 206) return "Partial Content";
        if (status == 207) return "Multi-Status";
        if (status == 208) return "Already Reported";
        if (status == 226) return "IM Used";
        if (status == 300) return "Multiple Choices";
        if (status == 301) return "Moved Permanently";
        if (status == 302) return "Found";
        if (status == 303) return "See Other";
        if (status == 304) return "Not Modified";
        if (status == 305) return "Use Proxy";
        if (status == 306) return "Switch Proxy";
        if (status == 307) return "Temporary Redirect";
        if (status == 308) return "Permanent Redirect";
        if (status == 400) return "Bad Request";
        if (status == 401) return "Unauthorized";
        if (status == 402) return "Payment Required";
        if (status == 403) return "Forbidden";
        if (status == 404) return "Not Found";
        if (status == 405) return "Method Not Allowed";
        if (status == 406) return "Not Acceptable";
        if (status == 407) return "Proxy Authentication Required";
        if (status == 408) return "Request Timeout";
        if (status == 409) return "Conflict";
        if (status == 410) return "Gone";
        if (status == 411) return "Length Required";
        if (status == 412) return "Precondition Failed";
        if (status == 413) return "Payload Too Large";
        if (status == 414) return "URI Too Long";
        if (status == 415) return "Unsupported Media Type";
        if (status == 416) return "Range Not Satisfiable";
        if (status == 417) return "Expectation Failed";
        if (status == 418) return "I'm a teapot";
        if (status == 421) return "Misdirected Request";
        if (status == 422) return "Unprocessable Entity";
        if (status == 423) return "Locked";
        if (status == 424) return "Failed Dependency";
        if (status == 425) return "Too Early";
        if (status == 426) return "Upgrade Required";
        if (status == 428) return "Precondition Required";
        if (status == 429) return "Too Many Requests";
        if (status == 431) return "Request Header Fields Too Large";
        if (status == 451) return "Unavailable For Legal Reasons";
        if (status == 500) return "Internal Server Error";
        if (status == 501) return "Not Implemented";
        if (status == 502) return "Bad Gateway";
        if (status == 503) return "Service Unavailable";
        if (status == 504) return "Gateway Timeout";
        if (status == 505) return "HTTP Version Not Supported";
        if (status == 506) return "Variant Also Negotiates";
        if (status == 507) return "Insufficient Storage";
        if (status == 508) return "Loop Detected";
        if (status == 510) return "Not Extended";
        if (status == 511) return "Network Authentication Require";
        return "";
    }

    public static HttpMessageResponse JsonResponse(String body) {
        return new HttpMessageResponseBuilder()
                .status(200)
                .header(HttpMessage.HEADER.CONTENT_TYPE, "text/json")
                .body(body);
    }
}
