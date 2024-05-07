package com.java.ashish.java8;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@FunctionalInterface
interface FileFilter extends Predicate<File> {}


/**
 * Types of functional interfaces
 * 1) Supplier<T>(takes no object, returns Object)-> T get()
 * 2) Consumer<T>(takes object, returns no Object)-> void accept(T t) // Biconsumer<T,U>(takes 2 object) accept(T t, U u)
 * 3) Predicate<T>(takes object, returns Boolean)-> Boolean test(T t) // BiPredicate<T,U>(takes 2 object) Boolean test(T t, U u)
 * 4) Function<T,R>(takes object, returns Object)-> R apply(T t) // BiFunction<T,U,R>(takes 2 object, returns object) R apply(T t, U u)
 *      a) Unary Operator - takes & return same type object -> interface UnaryOperator<T> extends Function<T,T>
 *      b) Binary Operator - takes 2 objects & return same type object -> interface BinaryOperator<T> extends BiFunction<T,T,T>
 */
public class FuntionalInterfaceDemo {

    /** PLease follow the sequence
        1) lamdbaDemo
        2) methodReferenceDemo
        3) processingDataWithLamdba
        4) defaultMethodDemo
     **/

    public void lambdaDemo(){
        /**
        //Java 6 anonymous class
        FileFilter filefilter = new FileFilter(){
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".java");
            }
        }**/
        // Java 8 anonymous lambda version
        FileFilter filefilter = (File file)->file.getName().endsWith(".java");
        FileFilter filefilter1 = file->file.getName().endsWith(".java");
    }

    public void methodReferenceDemo(){
        // lamdba consumer
        Consumer<String> c = s-> System.out.println(s);
        // same lamdba can be written as Method reference
        Consumer<String> c1 = System.out::println;
        // lamdba comparator
        Comparator<Integer> co = (i1,i2) -> Integer.compare(i1,i2);
        // same lamdba can be written as Method reference
        Comparator<Integer> co1 = Integer::compare;
    }

    public void processingDataWithLamdba(){
        List<Consumer> testConsumerList = new ArrayList<>();
        testConsumerList.forEach(c->System.out.println(c));
        testConsumerList.forEach(System.out::println);
    }

    public void defaultMethodDemo(){
        /**
         * Default Method - allow change old interface w/o breaking existing implementation, this allows new patterns
         *  static method is also allowed in Java 8 interfaces
         *  Ex: of new pattern
         *     default Predicate<E> and(Predicate<E> other){
         */
        Predicate<String> p1 = s-> s.length()<20;
        Predicate<String> p2 = s-> s.length()>10;
        Predicate<String> p3 = p1.and(p2);
        // Similarly
        Predicate<String> p = Predicate.isEqual("Test");
    }
}
