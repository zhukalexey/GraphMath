package by.bsu.graphmath.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationProperties {

    public static final String APPLICATION_PROPERTIES_FILE = "by/bsu/graphmath/application.properties";
    public static final String KEY_VERSION = "version";
    protected static final Logger logger = Logger.getLogger(ApplicationProperties.class.getName());
    private String applicationPropertiesFile;
    private String version;
    private Properties properties;

    public ApplicationProperties() {
        this(APPLICATION_PROPERTIES_FILE);
    }

    public ApplicationProperties(String applicationPropertiesFile) {
        this.applicationPropertiesFile = applicationPropertiesFile;
    }

    public String getVersion() {
        if (version == null) {
            version = getProperty(KEY_VERSION);
            if (version == null) {
                logger.warning("Version of application is not found");
                version = "";
            }
        }
        return version;
    }

    public String getApplicationPropertiesFile() {
        return applicationPropertiesFile;
    }

    public void setApplicationPropertiesFile(String applicationPropertiesFile) {
        this.applicationPropertiesFile = applicationPropertiesFile;
    }

    public String getProperty(String key) {
        String property = getProperties().getProperty(key);
        logger.log(Level.FINE, "Property with key \"{0}\" of application = {1}", new Object[]{key, property});
        return property;
    }

    private Properties getProperties() throws IllegalStateException {
        if (properties == null) {
            if (getApplicationPropertiesFile() == null) {
                throw new IllegalStateException("Application properties file is not set");
            }
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(getApplicationPropertiesFile());
            if (inputStream == null) {
                logger.warning("Property file is not found");
            } else {
                try {
                    properties = new Properties();
                    properties.load(inputStream);
                } catch (Exception ex) {
                    logger.log(Level.WARNING, "Property file with info is not found", ex);
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException ex) {
                        logger.log(Level.WARNING, "Error when close inputStream", ex);
                    }
                }
            }
        }
        return properties;
    }
}
