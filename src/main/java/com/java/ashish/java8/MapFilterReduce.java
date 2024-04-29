package com.java.ashish.java8;

public class MapFilterReduce {
    /**
     *  Map -> Filter -> Reduce is a common technique to reduce data, not specific to Java
     *  Lets say we have List<Person> from which we want to check how many has age > 50, byusing below steps
     *  1) Map -> convert List<Person> -> List<Integer> (Size of list still remains same)
     *  2) Filter -> apply predicate to filter the data age>50 (Size might get reduce in this step depending upon filter)
     *  3) Reduce -> reduction step equal to sql aggregation, reduce list to single number -> count/average
     */
}
