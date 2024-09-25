package telran.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
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

    public static void displayDigitsDistribution(int[] arr) {
        final int DISPLAY_LIMIT = 10;
        Map<Integer, Long> map = getMapIntToOccurrences(arr);

        AtomicLong maxOccurrence = new AtomicLong(0);
        Map<Long, List<Integer>> occurrencesMap = getMapOccurrencesToInt(map, i -> {
            if (i.getValue() > maxOccurrence.get()) {
                maxOccurrence.set(i.getValue());
            }
        });

        displayMapOccurrences(occurrencesMap, DISPLAY_LIMIT, maxOccurrence.get());
    }

    private static Map<Integer, Long> getMapIntToOccurrences(int[] arr) {
        return Arrays.stream(arr).boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static Map<Long, List<Integer>> getMapOccurrencesToInt(Map<Integer, Long> map,
            Consumer<Map.Entry<Integer, Long>> callback) {
        return map.entrySet().stream()
                .peek(callback)
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
    }

    private static void displayMapOccurrences(Map<Long, List<Integer>> occurrencesMap, int limit, long occurrence) {
        int count = 0;
        while (occurrence > 0 && count < limit) {
            if (occurrencesMap.containsKey(occurrence)) {
                List<Integer> values = occurrencesMap.get(occurrence);
                displayListOccurrences(values, occurrence, limit);
                count += values.size();
            }
            occurrence--;
        }
    }

    private static void displayListOccurrences(List<Integer> values, long occurrence, int limit) {
        values.stream().limit(limit).forEach(i -> System.out.printf("%d -> %d\n", i, occurrence));
    }

    public static ParenthesesMaps getParenthesesMaps(Character[][] openCloseParentheses) {
        return Arrays.stream(openCloseParentheses).collect(
            () -> new ParenthesesMaps(new HashMap<>(), new HashMap<>()),
            (acc, item) -> {
                acc.openCloseMap().put(item[0], item[1]);
                acc.closeOpenMap().put(item[1], item[0]);
            },
            (left, right) -> {
                left.openCloseMap().putAll(right.openCloseMap());
                left.closeOpenMap().putAll(right.closeOpenMap());
            }
        );
    }
}
