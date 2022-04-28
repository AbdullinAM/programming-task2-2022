import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RLE {
    public String packager(String words) {
        LinkedHashMap<Character, Integer> charsCount = new LinkedHashMap<>();
        StringBuilder charLine = new StringBuilder();

        if (words.isEmpty()) return "";

        char[] characters = words.toCharArray();
        char previousChar = characters[0];

        charsCount.put(previousChar, 0);
        char lastChar = characters[0];
        int lastCharCount = 0;
        for (char ch : characters) {
            if (Character.isDigit(ch))
                throw new IllegalArgumentException("Input string cannot contain digit sequences");
            if (!charsCount.containsKey(ch)) charsCount.put(ch, 1);
            if (ch != previousChar) {
                if (charsCount.get(ch) > 1) {
                    charLine.append(charsCount.get(ch));
                    charLine.append(ch);
                    charsCount.put(ch, 1);
                    previousChar = ch;
                }
                if (charsCount.get(previousChar) > 1) {
                    charLine.append(charsCount.get(previousChar));
                    charLine.append(previousChar);
                    charsCount.put(previousChar, 1);
                    previousChar = ch;
                } else {
                    charLine.append(previousChar);
                    charsCount.put(previousChar, 1);
                    previousChar = ch;
                }
            } else charsCount.put(ch, charsCount.get(ch) + 1);
            lastChar = ch;
            lastCharCount = charsCount.get(ch);
        }
        if (lastCharCount > 1) {
            charLine.append(lastCharCount);
            charLine.append(lastChar);
        } else charLine.append(lastChar);

        return charLine.toString();
    }

    public String unpackager(String words) {
        StringBuilder unpack = new StringBuilder();
        char[] characters = words.toCharArray();
        List<Integer> digitCollector = new ArrayList<>();
        int number = 0;
        for (char ch : characters) {
            if (Character.isDigit(ch)) {
                digitCollector.add(Integer.parseInt(String.valueOf(ch)));
            } else if (!digitCollector.isEmpty()) {
                for (int digit : digitCollector) {
                    number = number * 10 + digit;
                }
                do {
                    unpack.append(ch);
                    number -= 1;
                } while (number > 0);
                digitCollector.clear();
            } else {
                unpack.append(ch);
            }
        }
        return unpack.toString();
    }
}