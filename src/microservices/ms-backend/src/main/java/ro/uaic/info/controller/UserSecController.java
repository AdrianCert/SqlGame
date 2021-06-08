package ro.uaic.info.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.HttpMessage.HttpMessage;
import ro.uaic.info.HttpMessage.HttpMessageNotFoundException;
import ro.uaic.info.HttpMessage.HttpMessageRequest;
import ro.uaic.info.HttpMessage.HttpMessageResponse;
import ro.uaic.info.Service.UserSecService;
import ro.uaic.info.repository.UserSecRepository;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSecController implements DispatcherController{
    private HttpMessageRequest request;
    public static String mapRegex = "/suser(.*)";
    private final UserSecService service = new UserSecService();

    @Override
    public HttpMessageResponse dispatch(HttpMessageRequest httpRequest) throws HttpMessageNotFoundException {
        String path = httpRequest.getUri();
        String method = httpRequest.getMethod();
        Pattern pattern = Pattern.compile("/suser/(.*)");
        Matcher matcher = pattern.matcher(path);
        this.request = httpRequest;
        try {
            if (matcher.matches()) {
                String appName = matcher.group(1);
                if (!appName.isEmpty()) {
                    if (method.equals(HttpMessage.METHOD.GET)) {
                        return getById(appName);  //get by id/
                    }
                    if (method.equals(HttpMessage.METHOD.POST)) {
                        return addUser(); //add
                    }
                    if (method.equals(HttpMessage.METHOD.PUT)) {
                        return updateUser(appName); //update
                    }
                    if (method.equals(HttpMessage.METHOD.DELETE)) {
                        return deleteUser(appName); //delete
                    }
                } else {
                    if (method.equals(HttpMessage.METHOD.GET)) {
                        return viewAll(); //get all
                    }
                    if (method.equals(HttpMessage.METHOD.POST)) {
                        return addUser();
                    }

                    // todo Method not allowed
                    throw new AssertionError();
                }
            }
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
            throw new HttpMessageNotFoundException();
        }
        throw new HttpMessageNotFoundException();
    }

    public  HttpMessageResponse getById(String appName) throws SQLException, JsonProcessingException {
        return HttpMessage.JsonResponse(service.getById(appName));
    }

    public HttpMessageResponse updateUser(String appName) throws SQLException, JsonProcessingException {
        return HttpMessage.JsonResponse(service.update(Integer.parseInt(appName), request.getBody()));
    }

    public HttpMessageResponse addUser() throws SQLException, JsonProcessingException {
        return HttpMessage.JsonResponse(service.add(request.getBody()));
    }

    public HttpMessageResponse deleteUser(String appName) throws SQLException {
        return HttpMessage.JsonResponse(service.delete(Integer.parseInt(appName)));
    }

    public HttpMessageResponse viewAll() throws JsonProcessingException {
        return HttpMessage.JsonResponse(service.getAll());
    }

}
