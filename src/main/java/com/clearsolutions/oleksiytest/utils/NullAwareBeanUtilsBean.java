package com.clearsolutions.oleksiytest.utils;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public class NullAwareBeanUtilsBean extends BeanUtilsBean {

    public void copyProperty(Object dest, String name, Object value)
            throws InvocationTargetException, IllegalAccessException {
        if (value == null) return;
        super.copyProperty(dest, name, value);
    }

}
