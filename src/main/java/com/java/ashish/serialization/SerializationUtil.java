package com.java.ashish.serialization;

import java.io.*;

public class SerializationUtil {
    public static Object deserialize(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        Object object = ois.readObject();
        ois.close();
        return object;
    }
    public static void serialize(Object object, String filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(object);
        oos.close();
    }
}
