package ro.uaic.info;


import ro.uaic.info.controller.*;
import ro.uaic.info.HttpMessage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ServerThread is responsible for communication with the connected client
 */
public class ServerThread implements Runnable {

    /**
     * Client Connected Socket
     */
    private Socket socket;

    /**
     * Collection of active controllers
     */
    private final static Collection<Class<? extends DispatcherController>> dispatcherControllers = Stream.of(
        ServiceController.class,
        InstanceController.class,
        WebController.class,
        UserController.class,
        BoardController.class,
        BoardMembershipController.class,
        BoardPublishController.class,
        HistoryController.class,
        PaymentController.class,
        PostController.class,
        QuestionController.class,
        QuestionAnswearController.class,
        QuestionSecController.class,
        RoleController.class,
        SchemaLocationController.class,
        SchemaTableController.class,
        UserPermisionController.class,
        UserSecController.class,
        UserWalletController.class,
        WalletController.class
    ).collect(Collectors.toCollection(LinkedList::new));

    /**
     * Current
     */
    private HttpMessageRequest httpRequest;

    /**
     * The constructor for ServerThread
     * @param socket The socket connected to the client
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * The starting point for the client thread
     */
    @Override
    public void run() {
        try {
            // initiate the buffer
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // read http request
            httpRequest = HttpMessageRequest.of(bufferedReader);
            // build the http response
            HttpMessageResponse httpResponse = dispatch(httpRequest);
            httpResponse.addHeader(HttpMessage.HEADER.CONNECTION, httpRequest.getHeader(HttpMessage.HEADER.CONNECTION));
            httpResponse.addHeader(HttpMessage.HEADER.HOST, httpRequest.getHeader(HttpMessage.HEADER.HOST));
            // send the http response
            httpResponse.send(socket);

        } catch (IOException e) {
            System.err.println("[ERROR] Server Client Thread:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("[ERROR] Socket Close: " + e.getMessage());
            }
        }
    }

    /**
     * Iterate through declared DispatcherController locking for one controller responsible.
     * The responsible controller is found by it mapping filed ( regex component): mapRegex
     * @param request The Http request to be handled
     * @return HttpMessageResponse returned from controller
     */
    private HttpMessageResponse dispatch(HttpMessageRequest request) {
        String path = request.getUri();
        Constructor<? extends DispatcherController> constructor;
        DispatcherController dispatcherController;
        try {
            for( Class<? extends DispatcherController> c : dispatcherControllers) {
                if(path.matches((String) c.getField("mapRegex").get(null))) {
                    constructor = c.getConstructor();
                    constructor.setAccessible(true);
                    dispatcherController = constructor.newInstance();
                    return dispatcherController.dispatch(request);
                }
            }
        } catch (NoSuchFieldException
                | NoSuchMethodException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException e) {
            e.printStackTrace();
            return InternalServerError(e.getMessage());
        } catch (HttpMessageNotFoundException e) {
            return NotFound();
        }

        return NotFound();
    }

    /**
     * Generate 404 - Not Found Http Response
     * @return HttpMessageResponse for 404 response
     */
    private HttpMessageResponse NotFound() {
        Character quo = '"';
        return new HttpMessageResponseBuilder()
                .status(404)
                .header(HttpMessage.HEADER.CONTENT_TYPE, "text/json")
                .header(HttpMessage.HEADER.CONNECTION, httpRequest.getHeader(HttpMessage.HEADER.CONNECTION))
                .header(HttpMessage.HEADER.HOST, httpRequest.getHeader(HttpMessage.HEADER.HOST))
                .body(new StringBuilder().append('{')
                        // add timestamp
                        .append(quo).append("timestamp").append(quo).append(':')
                        .append(quo).append(new Date().toString()).append(quo).append(',')
                        // add path
                        .append(quo).append("path").append(quo).append(':')
                        .append(quo).append(httpRequest.getUri()).append(quo).append(',')
                        // add status
                        .append(quo).append("status").append(quo).append(':')
                        .append(quo).append("404").append(quo)
                        .append('}').toString());
    }

    /**
     * Generate 500 - Internal Server Error Http Response
     * @param message The error message
     * @return HttpMessageResponse for 500 response
     */
    private HttpMessageResponse InternalServerError(String message) {
        Character quo = '"';
        return new HttpMessageResponseBuilder()
                .status(505)
                .header(HttpMessage.HEADER.CONTENT_TYPE, "text/json")
                .header(HttpMessage.HEADER.CONNECTION, httpRequest.getHeader(HttpMessage.HEADER.CONNECTION))
                .header(HttpMessage.HEADER.HOST, httpRequest.getHeader(HttpMessage.HEADER.HOST))
                .body(new StringBuilder().append('{')
                        // add timestamp
                        .append(quo).append("timestamp").append(quo).append(':')
                        .append(quo).append(new Date().toString()).append(quo).append(',')
                        // add message
                        .append(quo).append("message").append(quo).append(':')
                        .append(quo).append(message).append(quo).append(',')
                        // add path
                        .append(quo).append("path").append(quo).append(':')
                        .append(quo).append(httpRequest.getUri()).append(quo)
                        // add status
                        .append(quo).append("status").append(quo).append(':')
                        .append(quo).append("505").append(quo)
                        .append('}').toString());
    }
}
