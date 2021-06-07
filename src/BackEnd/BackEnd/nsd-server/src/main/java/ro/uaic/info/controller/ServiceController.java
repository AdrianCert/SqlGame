package ro.uaic.info.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.HttpMessage.HttpMessage;
import ro.uaic.info.HttpMessage.HttpMessageNotFoundException;
import ro.uaic.info.HttpMessage.HttpMessageRequest;
import ro.uaic.info.HttpMessage.HttpMessageResponse;
import ro.uaic.info.Service.AppInstanceService;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller witch handle the required service discovery, registration and monitoring
 *
 * @author Adrian-Valentin Panaintecu
 */
public class ServiceController implements DispatcherController {
    /**
     * The request mapping for controller
     */
    public static String mapRegex = "/service(.*)";
    /**
     * The Http request
     */
    private HttpMessageRequest request;

    /**
     * App instance services
     */
    private final AppInstanceService service = new AppInstanceService();

    /**
     * Return a http response from inhered methods. Each method should resolve one path - method pair
     * That is assigned here and formatted after calling method
     * @param httpRequest The http request
     * @return HttpMessageResponse returned and formatted from methods
     * @throws HttpMessageNotFoundException Not found exception
     */
    @Override
    public HttpMessageResponse dispatch(HttpMessageRequest httpRequest) throws HttpMessageNotFoundException {
        String path = httpRequest.getUri();
        String method = httpRequest.getMethod();
        Pattern pattern = Pattern.compile("/service/(.*)");
        Matcher matcher = pattern.matcher(path);
        this.request = httpRequest;
        try {
            if (matcher.matches()) {
                String appName = matcher.group(1);
                if (!appName.isEmpty()) {
                    if (method.equals(HttpMessage.METHOD.GET)) {
                        return viewInstanceService(appName);
                    }
                    if (method.equals(HttpMessage.METHOD.POST)) {
                        return registerInstanceService(appName);
                    }
                    if (method.equals(HttpMessage.METHOD.PUT)) {
                        return updateInstanceService(appName);
                    }
                    if (method.equals(HttpMessage.METHOD.DELETE)) {
                        return deleteInstanceService(appName);
                    }
                } else {
                    if (method.equals(HttpMessage.METHOD.GET)) {
                        return viewAllServices();
                    }
                    if (method.equals(HttpMessage.METHOD.POST)) {
                        return registerInstanceService(appName);
                    }

                    // todo Method not allowed
                    throw new AssertionError();
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new HttpMessageNotFoundException();
        }
        throw new HttpMessageNotFoundException();
    }

    /**
     * Create operation: Add an app instance into repository
     * @param appName The app name
     * @return JsonResponse with the object created
     * @throws JsonProcessingException Exception on processing instance
     */
    public HttpMessageResponse registerInstanceService(String appName) throws JsonProcessingException {
        return HttpMessage.JsonResponse(service.add(appName, request.getBody()));
    }

    /**
     * View all App instances
     * @return JsonResponse with the list
     * @throws JsonProcessingException Exception on processing instance
     */
    public HttpMessageResponse viewAllServices() throws JsonProcessingException{
        return HttpMessage.JsonResponse(service.getAll());
    }

    /**
     * View apps list or app by id
     * @param appName required app
     * @return JsonResponse
     * @throws JsonProcessingException Exception on processing instance
     */
    public HttpMessageResponse viewInstanceService(String appName) throws JsonProcessingException {
        Pattern pattern = Pattern.compile(".+:(\\d+).*");
        Matcher matcher = pattern.matcher(appName);
        if (matcher.matches()) {
            return HttpMessage.JsonResponse(service.getAppInstance(Integer.valueOf(matcher.group(1))));
        }
        return HttpMessage.JsonResponse(service.getApp(appName));
    }

    /**
     * Update operation for an instance
     * @param appName App id
     * @return JsonResponse with updated data
     * @throws JsonProcessingException Exception on processing json
     * @throws HttpMessageNotFoundException Exception on data not found
     */
    public HttpMessageResponse updateInstanceService(String appName)
            throws JsonProcessingException, HttpMessageNotFoundException {
        StringTokenizer tkn = new StringTokenizer(appName, "?:/");
        tkn.nextToken();
        String instanceId = tkn.nextToken();
        return HttpMessage.JsonResponse(service.update(Integer.valueOf(instanceId),request.getBody()));
    }

    /**
     * Delete operation for an instance
     * @param appName App id
     * @return JsonResponse with the status
     */
    public HttpMessageResponse deleteInstanceService(String appName) {
        StringTokenizer tkn = new StringTokenizer(appName, "?:/");
        tkn.nextToken();
        String instanceId = tkn.nextToken();
        return HttpMessage.JsonResponse(service.delete(Integer.valueOf(instanceId)));
    }

}
