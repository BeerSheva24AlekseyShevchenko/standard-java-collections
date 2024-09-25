package telran.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTasks {

    public static void displayOccurrences(String[] strings) {
        Map<String, Integer> stringsMap = new HashMap<>();
        Arrays.stream(strings).forEach(s -> stringsMap.merge(s, 1, Integer::sum));

        Integer max = 0;
        Map<Integer, List<String>> occurrencesMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : stringsMap.entrySet()) {
            Integer occurrence = entry.getValue();
            String str = entry.getKey();
            occurrencesMap.computeIfAbsent(occurrence, i -> new ArrayList<>()).add(str);
            if (occurrence > max) {
                max = occurrence;
            }
        }

        for (int index = max, count = 0; count < occurrencesMap.size(); index--) {
            if (occurrencesMap.containsKey(index)) {
                count++;
                displayOccurrence(index, occurrencesMap.get(index));
            }
        }

    }

    private static void displayOccurrence(Integer occurrence, List<String> list) {
        list.forEach(i -> System.out.printf("%s -> %d\n", i, occurrence));
    }

    public static void displayOccurrencesStream(String[] strings) {
        Arrays.stream(strings)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    int res = Long.compare(e2.getValue(), e1.getValue());
                    return res == 0 ? e1.getKey().compareTo(e2.getKey()) : res;
                })
                .forEach(i -> System.out.printf("%s -> %d\n", i.getKey(), i.getValue()));
    }

    public static Map<Integer, Integer[]> getGroupingByNumberOfDigits(int[][] arr) {
        Map<Integer, List<Integer>> map = streamOfNumbers(arr)
                .collect(Collectors.groupingBy(n -> Integer.toString(n).length()));

        return map.entrySet().stream()
                .collect(Collectors.toMap(i -> i.getKey(), i -> i.getValue().toArray(Integer[]::new)));
    }

    private static Stream<Integer> streamOfNumbers(int[][] arr) {
        return Arrays.stream(arr)
                .flatMapToInt(Arrays::stream)
                .boxed();
    }

    public static Map<Integer, Long> getDistributionByNumberOfDigits(int[][] arr) {
        return streamOfNumbers(arr)
                .collect(Collectors.groupingBy(n -> Integer.toString(n).length(), Collectors.counting()));
    }

    public static void displayDigitsDistribution() {
        Random random = new Random();

        random.ints(1_000_000, 0, Integer.MAX_VALUE)
            .boxed()
            .<Integer>mapMulti((num, acc) -> {
                while (num > 0) {
                    acc.accept(num % 10);
                    num /= 10;
                }
            })
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .sorted((e1, e2) -> {
                int res = Long.compare(e2.getValue(), e1.getValue());
                return res == 0 ? e1.getKey().compareTo(e2.getKey()) : res;
            })
            .forEach(i -> System.out.printf("%d -> %d\n", i.getKey(), i.getValue()));
    }

    public static ParenthesesMaps getParenthesesMaps(Character[][] openCloseParentheses) {
        Map<Character, Character> openCloseMap = Arrays.stream(openCloseParentheses)
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
        Map<Character, Character> closeOpenMap = openCloseMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        return new ParenthesesMaps(openCloseMap, closeOpenMap);
    }
}
