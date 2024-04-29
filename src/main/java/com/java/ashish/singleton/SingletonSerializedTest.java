package com.java.ashish.singleton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class SingletonSerializedTest {

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        SerializedSingleton instanceOne = SerializedSingleton.getInstance();
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream("filename.ser"));
        out.writeObject(instanceOne);
        out.close();

        // deserialize from file to object
        ObjectInput in = new ObjectInputStream(new FileInputStream("filename.ser"));
        SerializedSingleton instanceTwo = (SerializedSingleton) in.readObject();
        in.close();

        System.out.println("instanceOne hashCode="+instanceOne.hashCode());
        System.out.println("instanceTwo hashCode="+instanceTwo.hashCode());

        SerializedSingletonWithReadResolve instOne = SerializedSingletonWithReadResolve.getInstance();
        ObjectOutput out1 = new ObjectOutputStream(new FileOutputStream("file.ser"));
        out1.writeObject(instOne);
        out1.close();

        ObjectInput inp1 = new ObjectInputStream(new FileInputStream("file.ser"));
        SerializedSingletonWithReadResolve inst2 = (SerializedSingletonWithReadResolve) inp1.readObject();
        inp1.close();

        System.out.println("instOne hashCode="+instOne.hashCode());
        System.out.println("inst2 hashCode="+inst2.hashCode());

    }

}