package com.java.ashish.serialization;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Data implements Serializable {
    private static final long serialVersionUID = 73515656L;
    private String data;

    public Data(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data='" + data + '\'' +
                '}';
    }

    //serialization proxy class
    private static class DataProxy implements Serializable{
        private static final long serialVersionUID = 7351565616287L;
        private String dataProxy;
        private static final String PREFIX = "ABC";
        private static final String SUFFIX = "XYZ";

        public DataProxy(Data data) {
            this.dataProxy = PREFIX+data+SUFFIX;
        }
        private Object readResolve() throws InvalidObjectException {
            if(dataProxy.startsWith(PREFIX) && dataProxy.endsWith(SUFFIX)){
                return new Data(dataProxy.substring(3,dataProxy.length()-3));
            }else{
                throw new InvalidObjectException("Data corrupted");
            }
        }
    }

    private Object writeReplace(){
        return new DataProxy(this);
    }

    private void readObject(ObjectInputStream stream) throws InvalidObjectException{
        throw new InvalidObjectException("Proxy not used");
    }
}
