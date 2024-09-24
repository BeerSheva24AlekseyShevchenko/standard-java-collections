package telran.collections;

import static telran.collections.MapTasks.*;

import org.junit.jupiter.api.Test;

public class MapTasksTest {

    @Test
    void displayOccurrencesTest() {
        String[] arr = { "lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm" };

        displayOccurrences(arr);
    }
}
