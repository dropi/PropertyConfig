package org.example;

import org.example.annotation.Config;
import org.example.exception.ConfigurationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.Properties;

public class ConfigFactory<T> {

    public static <S> S createConfig(Class<S> configClass) {
        return new ConfigFactory<>(configClass).create();
    }

    private final Class<T> configClass;
    private final Properties configProperties = new Properties();

    private ConfigFactory(Class<T> configClass) {
        this.configClass = configClass;
    }

    private T create() {
        validateIsInterface();
        readConfigFile(getConfigFilePath());
        return createInstance();
    }

    private void validateIsInterface() {
        if (!configClass.isInterface())
            throw new ConfigurationException("Provided class is not an interface: " + configClass.getName());
    }

    private String getConfigFilePath() {
        Config annotation = configClass.getAnnotation(Config.class);
        if (annotation == null)
            throw new ConfigurationException("Provided class is not annotated: " + configClass.getName());
        return annotation.value();
    }

    private void readConfigFile(String configFilePath) {
        try (InputStream fileInputStream = new FileInputStream(configFilePath)) {
            configProperties.load(fileInputStream);
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public T createInstance() {
        return (T) Proxy.newProxyInstance(configClass.getClassLoader(),
                new Class<?>[] { configClass },
                new PropertyInvocationHandler(configProperties));
    }

}
