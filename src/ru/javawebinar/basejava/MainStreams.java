package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class MainStreams {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3, 3, 2, 3};
        int[] array2 = {8, 9};

        printDigits(array1);
        printDigits(array2);

        printArray(array1);
        printArray(array2);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).
                distinct().
                sorted().
                reduce(0, (accumulate, streamElement) -> accumulate * 10 + streamElement);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers
                .stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0, toList()));
        int list = map.get(false).size();
        if (list % 2 != 0) {
            return map.get(true);
        }
        return map.get(false);
    }

    private static void printDigits(int[] array) {
        System.out.println("Source array: " + Arrays.toString(array));
        System.out.println("Unique and sorted digits of array: " +
                Arrays.toString(IntStream.of(array).distinct().sorted().toArray()));
        System.out.println("The minimum possible number from digits of array1: " + minValue(array) + "\n");
    }

    private static void printArray(int[] array) {
        List<Integer> list = Arrays.stream(array).boxed().toList();
        int sum = Arrays.stream(array).sum();
        System.out.println("Source array: " + Arrays.toString(array));
        System.out.println("Sum of digits of array: " + sum + " is " + (sum % 2 == 0 ? "even" : "odd"));
        System.out.println("Array after deleted " + (sum % 2 == 0 ? "even" : "odd")
                + " digits: " + oddOrEven(list) + "\n");
    }
}
