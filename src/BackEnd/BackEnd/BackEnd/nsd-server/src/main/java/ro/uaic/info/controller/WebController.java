package ro.uaic.info.controller;

import ro.uaic.info.HttpMessage.HttpMessageNotFoundException;
import ro.uaic.info.HttpMessage.HttpMessageRequest;
import ro.uaic.info.HttpMessage.HttpMessageResponse;

/**
 * Controller witch handle the web interface
 *
 * @author Adrian-Valentin Panaintecu
 */
public class WebController implements DispatcherController {
    /**
     * The request mapping for controller
     */
    public static String mapRegex = "/(.*)";

    /**
     * Return a http response from inhered methods. Each method should resolve one path - method pair
     * That is assigned here and formatted after calling method
     * @param httpRequest The http request
     * @return HttpMessageResponse returned and formatted from methods
     * @throws HttpMessageNotFoundException Not found exception
     */
    @Override
    public HttpMessageResponse dispatch(HttpMessageRequest httpRequest) throws HttpMessageNotFoundException {

        throw new HttpMessageNotFoundException();
    }
}
