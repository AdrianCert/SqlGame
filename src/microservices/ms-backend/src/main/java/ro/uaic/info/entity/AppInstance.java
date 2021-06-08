package ro.uaic.info.entity;

import java.util.Date;

/**
 * Model for AppInstance
 */
public class AppInstance {
    /**
     * Instances Id
     */
    private Integer id;
    /**
     * App Name
     */
    private String appName;
    /**
     * Time when registered has been done
     */
    private Date registeredTime;
    /**
     * Time when update has been
     */
    private Date actualizedTime;
    /**
     * The state for this instance (up/down)
     */
    private String state;
    /**
     * Domain Name
     */
    private String dns;
    /**
     * Internet Protocol Address
     */
    private String ip;
    /**
     * Application Instance Port
     */
    private Integer port;
    /**
     * Boolean value for the preferred using ip value
     */
    private Boolean preferredIp;


    /**
     * Getter for id
     * @return Integer with id value
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter for appName
     * @return String with appName value
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Getter for registeredTime
     * @return Date with registeredTime value
     */
    public Date getRegisteredTime() {
        return registeredTime;
    }

    /**
     * Getter for actualizedTime
     * @return Date with actualizedTime value
     */
    public Date getActualizedTime() {
        return actualizedTime;
    }

    /**
     * Getter for state
     * @return String with state value
     */
    public String getState() {
        return state;
    }

    /**
     * Getter for dns
     * @return String with dns value
     */
    public String getDns() {
        return dns;
    }

    /**
     * Getter for ip
     * @return String with ip value
     */
    public String getIp() {
        return ip;
    }

    /**
     * Getter for port
     * @return Integer with port value
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Getter for preferredIp
     * @return Boolean with preferredIp value
     */
    public Boolean getPreferredIp() {
        return preferredIp;
    }

    /**
     * Setter for id
     * @param id The id value
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Setter for appName
     * @param appName The appName value
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Setter for registeredTime
     * @param registeredTime The registeredTime value
     */
    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    /**
     * Setter for actualizedTime
     * @param actualizedTime The actualizedTime value
     */
    public void setActualizedTime(Date actualizedTime) {
        this.actualizedTime = actualizedTime;
    }

    /**
     * Setter for state
     * @param state The state value
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Setter for dns
     * @param dns The dns value
     */
    public void setDns(String dns) {
        this.dns = dns;
    }

    /**
     * Setter for ip
     * @param ip The ip value
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Setter for port
     * @param port The port value
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * Setter for preferredIp
     * @param preferredIp The preferredIp value
     */
    public void setPreferredIp(Boolean preferredIp) {
        this.preferredIp = preferredIp;
    }
}
