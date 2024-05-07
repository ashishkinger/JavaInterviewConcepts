package com.java.ashish.java8;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DesignUsingJava8 {

    public static void main(String[] args){
        chainingOfConsumer();
        chainingOfPredicates();
        chainingAndComposingFunction();
        comparator();
    }

    /**
     * Adding default method on a functionalInterface provide way to chain lambdas, Don't forget to fail fast
     */
    public static void chainingOfConsumer(){
        TestConsumer<String> c1= s -> System.out.println("c1 = "+s);
        TestConsumer<String> c2= s -> System.out.println("c2 = "+s);
        TestConsumer<String> c3= c1.andThen(c2);
        c3.accept("Hello"); // whatever passed to c3, eventually passed to c1 andthen to c2
    }

    public static void chainingOfPredicates(){
        TestPredicate<String> p1= s -> s!=null;
        TestPredicate<String> p2= s -> !s.isEmpty();
        TestPredicate<String> p3= p1.andThen(p2);
        System.out.println("chaining predicate->"+p3.test("two"));
        p3.test("Hello"); // whatever passed to c3, eventually passed to c1 andthen to c2
        TestPredicate<String> p4 =p1.negate();
        System.out.println("chaining function negate->"+p4.test("two"));
    }

    public static void chainingAndComposingFunction(){
        // chaining version of andThen
        Metro metro = new Metro(20);
        TestFunction<Metro, Integer> readCelcius = m->m.getTemp();
        TestFunction<Integer, Double> celciusToFahernit = t->(t*9d/5d+32d);
        TestFunction<Metro, Double> readFahernit = readCelcius.andThen(celciusToFahernit);
        System.out.println("chaining function->"+readFahernit.apply(metro));
        /** composing function
         * Applying function ctoF on the result of readCelciusFunction , its inverting the way chaining being called
         * Composing inverts the way chaining are called
         * Composing is inverting of chaining, so not possible with Predicate (bounds boolean) & Consumer (bounds void)
         */
        TestFunction<Metro, Double> readFahernitComposing = celciusToFahernit.composing(readCelcius); // Going to apply readCelcius on calciusToFahernit
        System.out.println("composing function composing ->"+readFahernitComposing.apply(metro));

        Function<String, String> identity = s-> s;
        Function<String, String> identity1 = Function.identity(); // static method can be used as factory method on function interface
    }

    private static void comparator() {
        Person mary = new Person("Mary", 22);
        Person john = new Person("John", 25);
        Person linda = new Person("Linda", 28);
        Person james = new Person("James", 29);
        Comparator<Person> cmpName = (p1,p2) -> p1.getName().compareTo(p2.getName());
        Function<Person, String> getName = p->p.getName();
        Comparator<Person> cmpName1 = (p1,p2) -> getName.apply(p1).compareTo(getName.apply(p2));
        // This can be extracted as function & make it dependent only on function parameter
        Comparator<Person> cmpName2 = getPersonComparator(getName);
    }

    private static Comparator<Person> getPersonComparator(Function<Person, String> getName) {
        return (p1, p2) -> getName.apply(p1).compareTo(getName.apply(p2));
    }

}
@FunctionalInterface
interface TestConsumer<T>{
    public void accept(T t);
    default TestConsumer<T> andThen(TestConsumer<T> other){
        /**
         *  If other is null, we will get Null Pointer Exception(NPE) but will shoe functionalInterface only in stacktrace not actual line of code
         *  which is throwing NPE, to avoid that we use Objects.requireNonNull to get exact location of error
         */
        Objects.requireNonNull(other);
        return (T t)->{
            this.accept(t);
            other.accept(t);
        };
    }
}
@FunctionalInterface
interface TestPredicate<T>{
    public boolean test(T t);
    default TestPredicate<T> andThen(TestPredicate<T> other){
        Objects.requireNonNull(other);
        return (T t)-> this.test(t) && other.test(t);
    }
    default TestPredicate<T> negate(){
        return t-> !this.test(t);
    }
}

@FunctionalInterface
interface TestFunction<T, R>{
    public R apply(T t);

    /**
     * T = Metro, V= Double, R = Integer
     */
    default <V> TestFunction<T, V> andThen(TestFunction<R, V> other){
        Objects.requireNonNull(other);
        return (T t)-> {
            R r = this.apply(t);
            return other.apply(r);
        };
    }
    default <V> TestFunction<V, R> composing(TestFunction<V, T> other){
        Objects.requireNonNull(other);
        return (V v)-> {
            T t = other.apply(v);
            return this.apply(t);
        };
    }
}
class Metro{
    private Integer temp;
    Metro(Integer temp){
        this.temp = temp;
    }
    public Integer getTemp(){
        return temp;
    }
}

class Person{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}