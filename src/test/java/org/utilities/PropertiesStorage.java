package org.utilities;

import java.util.Properties;

public final class PropertiesStorage {

    private final static String remoteAddress = "remoteAddress";
    private final static String runInDocker = "runInDocker";
    private final static String enableVnc = "enableVnc";
    private final static String framerate = "framerate";
    private final static String enableRecording = "enableRecording";

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private Properties properties;

    private static volatile PropertiesStorage instance;

    private PropertiesStorage() {

    }
    public static PropertiesStorage getInstance() {
        PropertiesStorage result = instance;

        if(result != null) {
            return result;
        }

        synchronized (PropertiesStorage.class) {
            if(instance == null) {
                instance = new PropertiesStorage();
            }
            return instance;
        }
    }

    public String getRemoteAddress() {
        return getValueOrNull(remoteAddress);
    }

    public boolean getInstanceInDocker() {
        String booleanString =  getValueOrNull(runInDocker);

        return Boolean.parseBoolean(booleanString);
    }

    public boolean getEnableVnc() {
        String booleanString = getValueOrNull(enableVnc);

        return Boolean.parseBoolean(booleanString);
    }

    public int getFramerate() {
        String framerateString = getValueOrNull(framerate);

        return Integer.parseInt(framerateString);
    }

    public boolean getEnableRecording() {
        String booleanString = getValueOrNull(enableRecording);

        return Boolean.parseBoolean(booleanString);
    }

    private String getValueOrNull(String key) {
        if(properties == null || !properties.containsKey(key)) {
            return null;
        }
        return properties.getProperty(key);
    }
}
