package telran.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
