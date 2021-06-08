package ro.uaic.info.repository;

import ro.uaic.info.entity.AppInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AppInstanceRepository {
    /**
     * AppInstanceRepository instance
     */
    private static AppInstanceRepository instanceRepository = null;
    /**
     * Stored List with AddInstances
     */
    private final List<AppInstance> instancesList = new LinkedList<>();
    /**
     * Indexer by id
     */
    private final HashMap<Integer, AppInstance> idIndex = new HashMap<>();
    /**
     * Indexed counter for each app
     */
    private final HashMap<String, Integer> maxBounder = new HashMap<>();
    /**
     * Grouped apps instances by name
     */
    private final HashMap<String, LinkedList<AppInstance>> instancesName = new HashMap<>();
    /**
     * Indexed counter for RoundRobin
     */
    private final HashMap<String, Integer> roundBounder = new HashMap<>();


    /**
     * Private constructor
     */
    private AppInstanceRepository() {
    }

    /**
     * Get instance for AppInstanceRepository
     * @return a instance of AppInstanceRepository
     */
    public static AppInstanceRepository getInstance() {
        if(instanceRepository == null) {
            instanceRepository = new AppInstanceRepository();
        }
        return instanceRepository;
    }

    /**
     * Maintain tracking of apps instance
     * @param appName appName identifier
     * @param increment the number with which it changes, usually -1 and +1
     */
    private void syncAppBounder(String appName, Integer increment) {
        // put 0 if not exist
        if(!maxBounder.containsKey(appName)) {
            maxBounder.put(appName, 0);
        }
        // apply the increment
        maxBounder.put(appName, maxBounder.get(appName) + increment);

        // clean-up after increment
        if(maxBounder.get(appName) <= 0) {
            maxBounder.remove(appName);
        }
    }

    /**
     * Add an instance in repository
     * @param appInstance instance to be added
     */
    public void add(AppInstance appInstance) {
        instancesList.add(appInstance);
        syncAppBounder(appInstance.getAppName(), 1);

        // add on instance name
        if(!instancesName.containsKey(appInstance.getAppName())) {
            instancesName.put(appInstance.getAppName(), new LinkedList<>());
        }
        instancesName.get(appInstance.getAppName()).add(appInstance);
        idIndex.put(appInstance.getId(), appInstance);
    }

    /**
     * Update the instance
     * @param appInstance appInstance object
     */
    public void update(AppInstance appInstance) {
        AppInstance updateAppInstance = idIndex.get(appInstance.getId());
        updateAppInstance.setAppName(       appInstance.getAppName());
        updateAppInstance.setRegisteredTime(appInstance.getRegisteredTime());
        updateAppInstance.setActualizedTime(appInstance.getActualizedTime());
        updateAppInstance.setState(         appInstance.getState());
        updateAppInstance.setDns(           appInstance.getDns());
        updateAppInstance.setIp(            appInstance.getIp());
        updateAppInstance.setPort(          appInstance.getPort());
        updateAppInstance.setPreferredIp(   appInstance.getPreferredIp());
    }

    /**
     * Delete app instance
     * @param appInstance appInstance object
     */
    public void delete(AppInstance appInstance) {
        if(appInstance == null) return;
        syncAppBounder(appInstance.getAppName(), -1);
        instancesList.remove(appInstance);

        // remove fom instance name
        instancesName.get(appInstance.getAppName()).remove(appInstance);
        if(instancesName.get(appInstance.getAppName()).isEmpty()) {
            instancesName.remove(appInstance.getAppName());
        }

        idIndex.remove(appInstance.getId());
    }

    /**
     * Return an instance after round robbin
     * @param appName The name for the app
     * @return AppInstance
     */
    public AppInstance getRoundInstance(String appName) {
        if(!roundBounder.containsKey(appName)) {
            roundBounder.put(appName, 0);
        }
        int current = roundBounder.get(appName);
        int counter = maxBounder.get(appName);
        current = current + 1 == counter ? 0 : current + 1;

        roundBounder.put(appName, current);
        int iter = current;
        AppInstance appInstance;
        while (true) {
            appInstance = instancesName.get(appName).get(iter);
            if(appInstance.getState().equals("UP")) {
                return appInstance;
            }
            iter = iter + 1 == counter ? 0 : iter + 1;
            if( iter == current) {
                return null;
            }
        }
    }

    /**
     * Return instance app name list
     * @return AppInstance
     */
    public List<String> getApps() {
        return new LinkedList<>(instancesName.keySet());
    }

    /**
     * Get the instance by Id
     * @param id The id
     * @return An instance
     */
    public AppInstance getInstanceById(Integer id) {
        return idIndex.get(id);
    }

    /**
     * Return all instances registered
     * @return List of instances
     */
    public List<AppInstance> getInstancesListAll() {
        return instancesList;
    }

    /**
     * Return all instance registered for an app
     * @param appName The app name
     * @return List of instance
     */
    public List<AppInstance> getInstancesListOfApp(String appName) {
        return instancesName.get(appName);
    }
}