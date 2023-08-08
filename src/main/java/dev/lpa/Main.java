package dev.lpa;

import java.time.LocalDate;
import java.util.*;

public class Main {

    private static Map<String, Purchase> purchases = new LinkedHashMap<>();
    private static NavigableMap<String, Student> students = new TreeMap<>();

    public static void main(String[] args) {

        Course jmc = new Course("jmc101", "Java Master Class", "Java");
        Course python = new Course("pyt101", "Python Master Class", "Python");

        addPurchase("Mary Martin", jmc, 129.99);
        addPurchase("Andy Martin", jmc, 139.99);
        addPurchase("Mary Martin", python, 149.99);
        addPurchase("Joe Jones", jmc, 149.99);
        addPurchase("Bill Brown", python, 119.99);

        purchases.forEach((key, value) -> System.out.println(key + ": " + value));
//        jmc101_1: Purchase[courseId=jmc101, studentId=1, price=129.99, yr=2023, dayOfYear=1]
//        jmc101_2: Purchase[courseId=jmc101, studentId=2, price=139.99, yr=2023, dayOfYear=2]
//        pyt101_1: Purchase[courseId=pyt101, studentId=1, price=149.99, yr=2023, dayOfYear=3]
//        jmc101_3: Purchase[courseId=jmc101, studentId=3, price=149.99, yr=2023, dayOfYear=4]
//        pyt101_4: Purchase[courseId=pyt101, studentId=4, price=119.99, yr=2023, dayOfYear=5]
//        → The LinkedHasMap's natural order is the order of addition.
        System.out.println("-".repeat(20));
        students.forEach((key, value) -> System.out.println(key + ": " + value));
//        Andy Martin: [2] : Java Master Class
//        Bill Brown: [4] : Python Master Class
//        Joe Jones: [3] : Java Master Class
//        Mary Martin: [1] : Java Master Class, Python Master Class
//        → The TreeMap's natural order is by alphabetical order.

        NavigableMap<LocalDate, List<Purchase>> datedPurchases = new TreeMap<>();
        addPurchase("Chuck Cheese", python, 119.99);
        addPurchase("Davy Jones", jmc, 139.99);
        addPurchase("Eva East", python, 139.99);
        addPurchase("Fred Forker", jmc, 139.99);
        addPurchase("Greg Brady", python, 129.99);

        for (Purchase p : purchases.values()) {
            datedPurchases.compute(p.purchaseDate(), (pdate, plist) -> {
                List<Purchase> list = (plist == null) ? new ArrayList<>() : plist;
                list.add(p);
                return list;
            });
        }

        datedPurchases.forEach((key, value) -> System.out.println(key  + ": " + value));
//        2023-01-02: [Purchase[courseId=jmc101, studentId=6, price=139.99, yr=2023, dayOfYear=2]]
//        2023-01-04: [Purchase[courseId=pyt101, studentId=5, price=119.99, yr=2023, dayOfYear=4]]
//        2023-01-05: [Purchase[courseId=pyt101, studentId=7, price=139.99, yr=2023, dayOfYear=5]]
//        2023-01-06: [Purchase[courseId=jmc101, studentId=3, price=149.99, yr=2023, dayOfYear=6], Purchase[courseId=jmc101, studentId=8, price=139.99, yr=2023, dayOfYear=6]]
//        2023-01-07: [Purchase[courseId=pyt101, studentId=1, price=149.99, yr=2023, dayOfYear=7]]
//        2023-01-08: [Purchase[courseId=jmc101, studentId=2, price=139.99, yr=2023, dayOfYear=8]]
//        2023-01-09: [Purchase[courseId=jmc101, studentId=1, price=129.99, yr=2023, dayOfYear=9]]
//        2023-01-10: [Purchase[courseId=pyt101, studentId=9, price=129.99, yr=2023, dayOfYear=10]]
//        2023-01-14: [Purchase[courseId=pyt101, studentId=4, price=119.99, yr=2023, dayOfYear=14]]

        int currentYear = LocalDate.now().getYear();

        LocalDate firstDay = LocalDate.ofYearDay(currentYear, 1);
        LocalDate week1 =  firstDay.plusDays(7);
        Map<LocalDate, List<Purchase>> week1Purchases = datedPurchases.headMap(week1);
        Map<LocalDate, List<Purchase>> week2Purchases = datedPurchases.tailMap(week1);

//        System.out.println("-".repeat(20));
//        week1Purchases.forEach((key, value) -> System.out.println(key + ": " + value));
//        2023-01-02: [Purchase[courseId=jmc101, studentId=2, price=139.99, yr=2023, dayOfYear=2], Purchase[courseId=jmc101, studentId=3, price=149.99, yr=2023, dayOfYear=2]]
//        2023-01-03: [Purchase[courseId=pyt101, studentId=1, price=149.99, yr=2023, dayOfYear=3]]
//        2023-01-04: [Purchase[courseId=jmc101, studentId=8, price=139.99, yr=2023, dayOfYear=4]]
//        2023-01-06: [Purchase[courseId=pyt101, studentId=5, price=119.99, yr=2023, dayOfYear=6]]
//        2023-01-07: [Purchase[courseId=jmc101, studentId=1, price=129.99, yr=2023, dayOfYear=7], Purchase[courseId=pyt101, studentId=4, price=119.99, yr=2023, dayOfYear=7]]
//        System.out.println("-".repeat(20));
//        week2Purchases.forEach((key, value) -> System.out.println(key + ": " + value));
//        2023-01-11: [Purchase[courseId=jmc101, studentId=6, price=139.99, yr=2023, dayOfYear=11], Purchase[courseId=pyt101, studentId=7, price=139.99, yr=2023, dayOfYear=11], Purchase[courseId=pyt101, studentId=9, price=129.99, yr=2023, dayOfYear=11]]

        displayStats(1, week1Purchases);
//        --------------------
//        2023-01-01: [Purchase[courseId=jmc101, studentId=2, price=139.99, yr=2023, dayOfYear=1]]
//        2023-01-03: [Purchase[courseId=jmc101, studentId=1, price=129.99, yr=2023, dayOfYear=3], Purchase[courseId=jmc101, studentId=6, price=139.99, yr=2023, dayOfYear=3], Purchase[courseId=pyt101, studentId=9, price=129.99, yr=2023, dayOfYear=3]]
//        2023-01-07: [Purchase[courseId=jmc101, studentId=8, price=139.99, yr=2023, dayOfYear=7]]
//        Week 1 purchases = {jmc101=4, pyt101=1}

        displayStats(2, week2Purchases);
//        --------------------
//        2023-01-10: [Purchase[courseId=pyt101, studentId=4, price=119.99, yr=2023, dayOfYear=10]]
//        2023-01-11: [Purchase[courseId=pyt101, studentId=5, price=119.99, yr=2023, dayOfYear=11]]
//        2023-01-12: [Purchase[courseId=jmc101, studentId=3, price=149.99, yr=2023, dayOfYear=12]]
//        2023-01-13: [Purchase[courseId=pyt101, studentId=7, price=139.99, yr=2023, dayOfYear=13]]
//        2023-01-14: [Purchase[courseId=pyt101, studentId=1, price=149.99, yr=2023, dayOfYear=14]]
//        Week 2 purchases = {jmc101=1, pyt101=4}

        System.out.println("----------------");

        LocalDate lastDate = datedPurchases.lastKey();
        var previousEntry = datedPurchases.lastEntry();

        while(previousEntry != null) {
            List<Purchase> lastDaysData = previousEntry.getValue();
            System.out.println(lastDate + " purchases : " + lastDaysData.size());

            LocalDate prevDate = datedPurchases.lowerKey(lastDate);
            previousEntry = datedPurchases.lowerEntry(lastDate);
            lastDate = prevDate;
//            2023-01-14 purchases : 1
//            2023-01-11 purchases : 1
//            2023-01-08 purchases : 2
//            2023-01-06 purchases : 3
//            2023-01-05 purchases : 1
//            2023-01-03 purchases : 1
//            2023-01-01 purchases : 1
        }

        System.out.println("-".repeat(15));
        var reversed = datedPurchases.descendingMap();

        LocalDate firstDate = reversed.firstKey();
        var nextEntry = reversed.firstEntry();
//        var nextEntry = reversed.pollFirstEntry();

        while (nextEntry != null) {
            List<Purchase> lastDaysData = nextEntry.getValue();
            System.out.println(firstDate + " purchases : " + lastDaysData.size());

            LocalDate nextDate = reversed.higherKey(firstDate);
            nextEntry = reversed.higherEntry(firstDate);
//            nextEntry = reversed.pollFirstEntry();
            firstDate = nextDate;
        }
//        ----------------
//        2023-01-14 purchases : 1
//        2023-01-11 purchases : 1
//        2023-01-10 purchases : 1
//        2023-01-08 purchases : 2
//        2023-01-06 purchases : 1
//        2023-01-04 purchases : 1
//        2023-01-03 purchases : 1
//        2023-01-02 purchases : 1
//        2023-01-01 purchases : 1
//        ---------------
//        2023-01-14 purchases : 1
//        2023-01-11 purchases : 1
//        2023-01-10 purchases : 1
//        2023-01-08 purchases : 2
//        2023-01-06 purchases : 1
//        2023-01-04 purchases : 1
//        2023-01-03 purchases : 1
//        2023-01-02 purchases : 1
//        2023-01-01 purchases : 1

        System.out.println("-".repeat(17));
        datedPurchases.forEach((key, value) -> System.out.println(key + ": " + value));
//        2023-01-01: [Purchase[courseId=jmc101, studentId=1, price=129.99, yr=2023, dayOfYear=1]]
//        2023-01-02: [Purchase[courseId=pyt101, studentId=7, price=139.99, yr=2023, dayOfYear=2]]
//        2023-01-04: [Purchase[courseId=pyt101, studentId=4, price=119.99, yr=2023, dayOfYear=4]]
//        2023-01-05: [Purchase[courseId=jmc101, studentId=8, price=139.99, yr=2023, dayOfYear=5]]
//        2023-01-08: [Purchase[courseId=pyt101, studentId=1, price=149.99, yr=2023, dayOfYear=8], Purchase[courseId=jmc101, studentId=3, price=149.99, yr=2023, dayOfYear=8]]
//        2023-01-09: [Purchase[courseId=pyt101, studentId=5, price=119.99, yr=2023, dayOfYear=9], Purchase[courseId=jmc101, studentId=6, price=139.99, yr=2023, dayOfYear=9]]
//        2023-01-13: [Purchase[courseId=jmc101, studentId=2, price=139.99, yr=2023, dayOfYear=13]]
//        2023-01-14: [Purchase[courseId=pyt101, studentId=9, price=129.99, yr=2023, dayOfYear=14]]


    }

    private static void addPurchase(String name, Course course, double price) {

        Student existingStudent = students.get(name);
        if (existingStudent == null) {
            existingStudent = new Student(name, course);
            students.put(name, existingStudent);
        } else {
            existingStudent.addCourse(course);
        }

        int day = new Random().nextInt(1, 15);
        String key = course.courseId() + "_" + existingStudent.getId();

        int year = LocalDate.now().getYear();
        Purchase purchase = new Purchase(course.courseId(), existingStudent.getId(), price, year, day);
        purchases.put(key, purchase);
    }

    private static void displayStats(int period, Map<LocalDate, List<Purchase>> periodData) {

        System.out.println("-".repeat(20));
        Map<String, Integer> weeklyCounts = new TreeMap<>();
        periodData.forEach((key, value) -> {
            System.out.println(key + ": " + value);
            for (Purchase p : value) {
                weeklyCounts.merge(p.courseId(), 1, Integer::sum);
            }
        });
        System.out.printf("Week %d purchases = %s%n", period, weeklyCounts);
    }
}
