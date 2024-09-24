package telran.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class MapTasks {
    
    public static void displayOccurrences(String[] strings) {
        HashMap<String, Long> map = getOccurrencesMap(strings);
        TreeMap<Long, TreeSet<String>> sortedMap = getSortedOccurrencesMap(map);
        displaySortedOccurrences(sortedMap);
    }

    private static void displaySortedOccurrences(TreeMap<Long, TreeSet<String>> sortedMap) {
        sortedMap.forEach((k, v) -> {
            v.forEach(i -> System.out.printf("%s -> %d\n", i, k));
        });
        
    }

    private static TreeMap<Long, TreeSet<String>> getSortedOccurrencesMap(HashMap<String, Long> occurrences) {
        TreeMap<Long, TreeSet<String>> map = new TreeMap<>(Comparator.reverseOrder());
        occurrences.entrySet().forEach(i -> map.computeIfAbsent(i.getValue(), k -> new TreeSet<>()).add(i.getKey()));

        return map;
    }

    private static HashMap<String, Long> getOccurrencesMap(String[] strings) {
        HashMap<String, Long> map = new HashMap<>();
        Arrays.stream(strings).forEach(s -> map.merge(s, 1l, Long::sum));

        return map;
    }
}
