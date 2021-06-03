package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.HttpMessage.HttpMessageNotFoundException;
import ro.uaic.info.entity.AppInstance;
import ro.uaic.info.repository.AppInstanceRepository;

import java.util.Date;

/**
 * Service witch handle the required service discovery, registration and monitoring
 *
 * @author Adrian-Valentin Panaintecu
 */
public class AppInstanceService {
    /**
     * Repository we operate with
     */
    private AppInstanceRepository repository = AppInstanceRepository.getInstance();
    /**
     * Json Object Mapper
     */
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Json with status key OK
     */
    public final static String JSON_OK = "{ \"status\": \"OK\" }";

    /**
     * Add an instance
     * @param appName Instance Name
     * @param json Instance Body
     * @return Created object serialised json
     * @throws JsonProcessingException Exception on Json processing
     */
    public String add(String appName, String json) throws JsonProcessingException {
        AppInstance model = objectMapper.readValue(json, AppInstance.class);
        model.setId(repository.getInstancesListAll().size() + 1);
        model.setActualizedTime(new Date());
        model.setRegisteredTime(new Date());
        if(model.getAppName() == null) {
            model.setAppName(appName);
        }
        if(model.getState() == null) {
            model.setState("UP");
        }
        repository.add(model);
        return objectMapper.writeValueAsString(model);
    }

    /**
     * Get all instances
     * @return json with the list
     * @throws JsonProcessingException Exception on json processing
     */
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(repository.getInstancesListAll());
    }

    /**
     * Get all instance for an app
     * @param appName The appName
     * @return List of instance
     * @throws JsonProcessingException Exception on json serialisation
     */
    public String getApp(String appName) throws JsonProcessingException {
        return objectMapper.writeValueAsString(repository.getInstancesListOfApp(appName));
    }

    /**
     * Get instance by id
     * @param appId App id
     * @return AppInstance serialised json
     * @throws JsonProcessingException Exception on json serialisation
     */
    public String getAppInstance(Integer appId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(repository.getInstanceById(appId));
    }

    /**
     * Get round robbin instances
     * @param appName App Name
     * @return One instance of app
     * @throws JsonProcessingException Exception on json serialisation
     */
    public String get(String appName) throws JsonProcessingException {
        return objectMapper.writeValueAsString(repository.getRoundInstance(appName));
    }

    /**
     * Get list of apps
     * @return json with the list
     * @throws JsonProcessingException Exception on json processing
     */
    public String getAppsName() throws JsonProcessingException {
        return objectMapper.writeValueAsString(repository.getApps());
    }

    /**
     * Update an instance app
     * @param appId App id
     * @param appInfo App body info
     * @return Updated instance as json
     * @throws JsonProcessingException Exception on json processing
     * @throws HttpMessageNotFoundException The app instance not found on repository
     */
    public String update(Integer appId, String appInfo)
            throws JsonProcessingException, HttpMessageNotFoundException {
        AppInstance model = objectMapper.readValue(appInfo, AppInstance.class);
        AppInstance updateModel = repository.getInstanceById(appId);
        if( updateModel == null) {
            throw new HttpMessageNotFoundException("appId not found");
        }
        updateModel.setActualizedTime(new Date());
        if( model.getAppName() != null) {
            updateModel.setAppName(model.getAppName());
        }
        if( model.getState() != null) {
            updateModel.setState(model.getState());
        }
        if( model.getDns() != null) {
            updateModel.setDns(model.getDns());
        }
        if( model.getIp() != null) {
            updateModel.setIp(model.getIp());
        }
        if( model.getIp() != null) {
            updateModel.setPort(model.getPort());
        }
        if( model.getPreferredIp() != null) {
            updateModel.setPreferredIp(model.getPreferredIp());
        }

        repository.update(updateModel);
        return objectMapper.writeValueAsString(updateModel);
    }

    /**
     * Delete an app instance
     * @param id the app id
     * @return JSON RESPONSE WITH OK
     */
    public String delete(Integer id) {
        repository.delete(repository.getInstanceById(id));
        return JSON_OK;
    }
}
