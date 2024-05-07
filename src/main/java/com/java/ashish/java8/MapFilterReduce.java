package com.java.ashish.java8;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapFilterReduce {
    /**
     *  Map -> Filter -> Reduce is a common technique to reduce data, not specific to Java
     *  Lets say we have List<Person> from which we want to check how many has age > 50, byusing below steps
     *  1) Map -> convert List<Person> -> List<Integer> (Size of list still remains same)
     *  2) Filter -> apply predicate to filter the data age>50 (Size might get reduce in this step depending upon filter)
     *  3) Reduce -> reduction step equal to sql aggregation, reduce list to single number -> count/average
     */

    /**
     * How Java does Map/Filter/Reduce -> Using streams
     * Stream -> like a collection but not a collection, gives way to efficiently process large & small data
     * Stream works in parallel to leverage computing power of CPU's & pipelined to avoid unnecessary intermediate operations
     * Stream -> An object on which one can define operations
     *           An object that does not hold data (like collection)
     *           An object that should not change data it processes (as we might be running in parallel)
     *           An object able to process data in one pass
     *           An object optimise from algo point of view and can run in parallel
     */

    public void streamExamples() throws Exception {
        List<Person> persons= new ArrayList<>();
        Consumer<Person> c1 = Person::getName;
        persons.stream().forEach(c1.andThen(Person::getAge)); // c1 consosts object; andThen method is used for chaining of operations; its a default method of consumers
        persons.stream().filter(p->p.getAge()>50); // Filter based on predicate condition of age >50
        Predicate<String> p = Predicate.isEqual("two");
        Stream<String> stream1 = Stream.of("one","two", "three");
        Stream<String> stream2 = stream1.filter(p);
        /**
         * what does stream2 holds ???
         *  Filtered data ??(Wrong)
         *  Stream does not hold data, its just a declaration of stream, no data is processed, as call is lazy
         *  All stream methods which return Stream object are called lazily & called Intermediary operation & not final operation.
         *  Only final / terminal operations process data
         */

        List<Integer> p1 = Arrays.asList(1,2,3,4);
        List<Integer> p2 = new ArrayList<>();
        p1.stream().peek(p2::add); // will not trigger processing as peek returns stream // Intermediary operations
        System.out.println(p2.size()); // Result will be 0
        p1.stream().forEach(p2::add); // foreach returns void so final operation
        System.out.println(p2.size()); // Result will be 4

        /**
         * map -> intermediary operation returns Stream<R>
         * flatmap -> intermediary operation returns Stream<Stream<R>>, flattens every stream to return single stream
         * Reduction --> 2 types
         * 1) Aggregation -> min , max, avg etc
         *      Integer sum = p1.stream().reduce(0,((age1, age2)-> age1+age2)); reduce (identity, binaryOperator)
         *      here 0 -> identity which can be considered as a default value, which if binary operator is empty should be return else
         *      binary operator will return i.e. sum in above case
         *      p1 size = 0 -> identity operator will return
         *              = 1 -> identity + number in list
         *              > 1 -> binary operator will perform and return the data
         *
         *    Optionals :: It means "might be no result"
         *     For sum above identity operation will work but for operation like max it's not suitable, we can use optional in that case
         *     Optional<Integer> max = p1.stream().max(Comparator.naturalOrder());
         *     Integer value = max.isPresent()? max.get() :0;
         *     Integer value1 = max.orElse(0);--> default value
         *     Integer value2 = max.orElseThrow(Exception :: new);--> lazy construct
         *
         *    Terminal Operations :: of below types; they trigger processing of data
         *     available : max(), min(), count()
         *     Boolean : allMatch(), anyMatch(), noneMatch()
         *     Optional : findFirst(), findAny()
         *     Optional<String> person = persons.stream().map(personName-> personName.getName()).filter(personName->personName.length()>20).findFirst();
         *     As terminal operation trigger computation in one go, so if 1st value itself is >20, it will stop converting hence nice optimization
         *     as previous steps also not run fully
         *
         * 2) Collectors :: Mutable reduction, instead of aggregating elements, this reduction put them in container
         * String result = persons.stream().filter(pa-> pa.getAge()>20).map(Person::getName).collect(Collectors.joining(","))
         *   Give all person name whose age >20 comma separated
         * Stream can be used for 1 terminal operation only, but its light weight object so creating stream() is a low cost thing
         */
        Integer sum = p1.stream().reduce(0,((age1, age2)-> age1+age2));
        Optional<Integer> max = p1.stream().max(Comparator.naturalOrder());
        Integer value = max.isPresent()? max.get() :0;
        Integer value1 = max.orElse(0);
        Integer value2 = max.orElseThrow(Exception :: new);

        Optional<String> person = persons.stream().map(Person::getName).filter(personName->personName.length()>20).findFirst();

        // Collectors
        String result = persons.stream().filter(pa-> pa.getAge()>20).map(Person::getName).collect(Collectors.joining(","));
        List<String> resultList = persons.stream().filter(pa-> pa.getAge()>20).map(Person::getName).toList(); // In list
        Map<Integer, List<Person>> resultMap = persons.stream().filter(pa-> pa.getAge()>20).collect(Collectors.groupingBy(Person::getAge)); // In Map
        // Further Downstream operations like collectors counting
        Map<Integer, Long> resultLong = persons.stream().filter(pa-> pa.getAge()>20).collect(Collectors.groupingBy(Person::getAge, Collectors.counting())); // In Map
        // Change to Map<Integer, PersonName
        Map<Integer, List<String>> resultNameMap = persons.stream().filter(pa-> pa.getAge()>20).
                collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList()))); // In List
        Map<Integer, Set<String>> resultNameSetMap = persons.stream().filter(pa-> pa.getAge()>20).
                collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toCollection(TreeSet::new)))); // In set
        Map<Integer, String> resultNameStringMap = persons.stream().filter(pa-> pa.getAge()>20).
                collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.joining(",")))); // In string
    }

    class Person{
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
