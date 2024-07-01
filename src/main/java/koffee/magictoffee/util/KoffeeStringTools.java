package koffee.magictoffee.util;

import java.util.ArrayList;
import java.util.List;

public class KoffeeStringTools {

    public static List<String> splitIntoLines(String input, int maxLineLength) {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        String[] words = input.split(" ");

        int currentLineLength = 0;
        for (String word : words) {
            // Calculate the length of the word excluding formatting codes
            int wordLength = getWordLengthWithoutFormatting(word);

            // If adding the word would exceed the max line length, start a new line
            if (currentLineLength + wordLength + 1 > maxLineLength) {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder();
                currentLineLength = 0;
            }

            // Add the word to the current line
            currentLine.append(word).append(" ");
            currentLineLength += wordLength + 1;
        }

        // Add the last line
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }

    public static int getWordLengthWithoutFormatting(String word) {
        int length = 0;
        boolean isFormattingCode = false;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == '§') {
                isFormattingCode = true;
            } else if (isFormattingCode) {
                isFormattingCode = false;
            } else {
                length++;
            }
        }

        return length;
    }
}
