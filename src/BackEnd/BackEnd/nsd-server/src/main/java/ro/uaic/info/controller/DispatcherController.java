package ro.uaic.info.controller;

import ro.uaic.info.HttpMessage.HttpMessageNotFoundException;
import ro.uaic.info.HttpMessage.HttpMessageRequest;
import ro.uaic.info.HttpMessage.HttpMessageResponse;

/**
 * DispatcherControllers contains dispatch method and mapRegex static field for handling http request
 */
public interface DispatcherController {

    /**
     * Return a http response from inhered methods. Each method should resolve one path - method pair
     * That is assigned here and formatted after calling method
     * @param httpRequest The http request
     * @return HttpMessageResponse returned and formatted from methods
     * @throws HttpMessageNotFoundException Not found exception
     */
    HttpMessageResponse dispatch(HttpMessageRequest httpRequest) throws HttpMessageNotFoundException;
}
