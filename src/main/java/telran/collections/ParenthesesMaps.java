package telran.collections;

import java.util.Map;

public record ParenthesesMaps(Map<Character, Character> openCloseMap, Map<Character, Character> closeOpenMap) {
}
