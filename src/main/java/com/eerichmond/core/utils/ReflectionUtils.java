package com.eerichmond.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

public final class ReflectionUtils {
	private ReflectionUtils() { }
	
    public static boolean hasProperty(Object o, String propertyName) {
        if (o == null || propertyName == null) {
            return false;
        }

        BeanInfo beanInfo;
        
        try {
            beanInfo = java.beans.Introspector.getBeanInfo(o.getClass());
        }
        catch (IntrospectionException e) {
            return false;
        }

        for(PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            if (propertyName.equals(property.getName())) {
                return true;
            }
        }
        return false;
    }
}
