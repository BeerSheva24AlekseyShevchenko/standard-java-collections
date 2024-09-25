package telran.collections;

import static org.junit.jupiter.api.Assertions.*;
import static telran.collections.MapTasks.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class MapTasksTest {

    @Test
    void displayOccurrencesTest() {
        String[] arr = { "lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm" };
        displayOccurrences(arr);
    }

    @Test
    void displayOccurrencesStreamTest() {
        String[] arr = { "lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm" };
        displayOccurrencesStream(arr);
    }

    @Test
    void displayDigitsDistributionTest() {
        displayDigitsDistribution();
    }

    @Test
    void getGroupingByNumberOfDigitsTest() {
        int[][] arr = { { 100, 1, 50 }, { 20, 30 }, { 1 } };
        Map<Integer, Integer[]> map = getGroupingByNumberOfDigits(arr);
        Integer[][] expected = { { 1, 1 }, { 50, 20, 30 }, { 100 } };
        assertArrayEquals(expected[0], map.get(1));
        assertArrayEquals(expected[1], map.get(2));
        assertArrayEquals(expected[2], map.get(3));
    }

    @Test
    void distributionByNumberOfDigitsTest() {
        int[][] arr = { { 100, 1, 50 }, { 20, 30 }, { 1 } };
        Map<Integer, Long> map = MapTasks.getDistributionByNumberOfDigits(arr);
        assertEquals(2, map.get(1));
        assertEquals(3, map.get(2));
        assertEquals(1, map.get(3));
    }

    @Test
    void getParenthesesMapsTest() {
        Character[][] openCloseParentheses = {
            {'[', ']'}, {'(', ')'}, {'{', '}'}
        };
        ParenthesesMaps maps = MapTasks.getParenthesesMaps(openCloseParentheses);
        Map<Character, Character> openCloseMap = maps.openCloseMap();
        Map<Character, Character> closeOpenMap = maps.closeOpenMap();
        assertEquals(']', openCloseMap.get('['));
        assertEquals(')', openCloseMap.get('('));
        assertEquals('}', openCloseMap.get('{'));
        assertEquals('[', closeOpenMap.get(']'));
        assertEquals('(', closeOpenMap.get(')'));
        assertEquals('{', closeOpenMap.get('}'));
    }
}
