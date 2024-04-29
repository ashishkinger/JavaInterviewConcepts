package com.java.ashish.serialization;
import java.io.IOException;


public class InheritanceSerializationTest {

    public static void main(String[] args) {
        String fileName = "subclass.ser";

        Subclass subClass = new Subclass();
        subClass.setId(10);
        subClass.setValue("Data");
        subClass.setName("Pankaj");

        try {
            SerializationUtil.serialize(subClass, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            Subclass subNew = (Subclass) SerializationUtil.deserialize(fileName);
            System.out.println("SubClass read = "+subNew);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}

