package ro.uaic.info.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.HttpMessage.HttpMessage;
import ro.uaic.info.HttpMessage.HttpMessageNotFoundException;
import ro.uaic.info.HttpMessage.HttpMessageRequest;
import ro.uaic.info.HttpMessage.HttpMessageResponse;
import ro.uaic.info.repository.UserWalletRepository;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserWalletController implements DispatcherController{
    private HttpMessageRequest request;

    @Override
    public HttpMessageResponse dispatch(HttpMessageRequest httpRequest) throws HttpMessageNotFoundException {
        String path = httpRequest.getUri();
        String method = httpRequest.getMethod();
        Pattern pattern = Pattern.compile("/userW/(.*)");
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
        return HttpMessage.JsonResponse(UserWalletRepository.getById(appName));
    }

    public HttpMessageResponse updateUser(String appName) throws SQLException, JsonProcessingException {
        return HttpMessage.JsonResponse(UserWalletRepository.update(appName, request.getBody()));
    }

    public HttpMessageResponse addUser() throws SQLException, JsonProcessingException {
        return HttpMessage.JsonResponse(UserWalletRepository.add(request.getBody()));
    }

    public HttpMessageResponse deleteUser(String appName) throws SQLException {
        return HttpMessage.JsonResponse(UserWalletRepository.delete(Integer.parseInt(appName)));
    }

    public HttpMessageResponse viewAll() throws JsonProcessingException {
        return HttpMessage.JsonResponse(UserWalletRepository.getAll());
    }

}
