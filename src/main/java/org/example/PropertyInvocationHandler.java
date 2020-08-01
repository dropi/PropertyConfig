package org.example;

import org.example.annotation.Key;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;

public class PropertyInvocationHandler implements InvocationHandler {

    private final Properties properties;

    public PropertyInvocationHandler(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return properties.getProperty(getPropertyName(method));
    }

    private String getPropertyName(Method method) {
        Key annotation = method.getAnnotation(Key.class);
        if (annotation != null)
            return annotation.value();
        else
            return method.getName();
    }
}
