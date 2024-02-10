package com.java.ashish.singleton;

import java.io.Serializable;

public class SerializedSingletonWithReadResolve implements Serializable {

    private static final long serialVersionUID = -1L;

    private SerializedSingletonWithReadResolve(){}

    private static class SingletonHelper{
        private static final SerializedSingletonWithReadResolve instance = new SerializedSingletonWithReadResolve();
    }

    public static SerializedSingletonWithReadResolve getInstance() {
        return SingletonHelper.instance;
    }

    protected Object readResolve() {
        return getInstance();
    }
}
