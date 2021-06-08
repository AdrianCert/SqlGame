package ro.uaic.info.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.HttpMessage.HttpMessage;
import ro.uaic.info.HttpMessage.HttpMessageNotFoundException;
import ro.uaic.info.HttpMessage.HttpMessageRequest;
import ro.uaic.info.HttpMessage.HttpMessageResponse;
import ro.uaic.info.Service.AppInstanceService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller witch handle the required service instance
 *
 * @author Adrian-Valentin Panaintecu
 */
public class InstanceController implements DispatcherController {
    /**
     * The request mapping for controller
     */
    public static String mapRegex = "/instance(.*)";

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
        Pattern pattern = Pattern.compile("/instance/(.*)");
        Matcher matcher = pattern.matcher(path);
        try {
            if (matcher.matches() && method.equals(HttpMessage.METHOD.GET)) {
                String appName = matcher.group(1);
                return appName.isEmpty() ? getAppList() : getInstance(appName);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        throw new HttpMessageNotFoundException();
    }

    /**
     * Return list of registered app
     * @return JsonResponse with the list
     * @throws JsonProcessingException Exception on processing
     */
    public HttpMessageResponse getAppList() throws JsonProcessingException {
        return HttpMessage.JsonResponse(service.getAppsName());
    }

    /**
     * Return an instance of an app
     * @param appName app name
     * @return JsonResponse with the app
     * @throws JsonProcessingException Exception on json processing
     */
    public HttpMessageResponse getInstance(String appName) throws JsonProcessingException {
        return HttpMessage.JsonResponse(service.get(appName));
    }
}
