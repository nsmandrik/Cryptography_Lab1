package mandrik.kbrs.simplesubst.cryptanalysis.freqanalysis;

import mandrik.kbrs.simplesubst.util.Alphabet;
import mandrik.kbrs.simplesubst.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class FrequencyAnalysis {

    public static String getDecodeText(File file, Alphabet alphabet, int lengthKey) throws IOException {
        String text = FileUtil.readFile(file, alphabet.getCharset());
        StringBuilder decodeText = new StringBuilder(text);
        for(int iter = 0; iter < lengthKey; iter++) {
            int[] frequency = new int[alphabet.getCountLetters()];
            for(int i = iter; i < text.length(); i+=lengthKey) {
                int offset = 0;
                if(alphabet.isCharInLowerCase(text.charAt(i))) {
                    offset = alphabet.getLowerCaseOffset();
                } else if(alphabet.isCharInUpperCase(text.charAt(i))) {
                    offset = alphabet.getUpperCaseOffset();
                }

                if(alphabet.isCharExistInAlphabet(text.charAt(i))) {
                    frequency[text.charAt(i) - offset] ++;
                }
            }

            int countSymbolsInText = 0;
            for (int aFrequency : frequency) {
                countSymbolsInText += aFrequency;
            }


            Map<Character, Double> perfectFrequency = alphabet.getFrequency();


            Map<Character, Double> frequencyMap = new HashMap<>();
            int temp = 0;
            for(Character letter: perfectFrequency.keySet()) {
                frequencyMap.put(letter, (double) frequency[temp] / countSymbolsInText);
                temp++;
            }

            int minOffset = 0;
            double minResult = Double.MAX_VALUE;
            for(int i = 0; i < alphabet.getCountLetters(); i++) {
                double sum = 0;
                for(Character letter: perfectFrequency.keySet()) {
                    int offsetLetter = letter + i;
                    if(offsetLetter >= alphabet.getUpperCaseOffset()  + alphabet.getCountLetters()) {
                        offsetLetter -= alphabet.getCountLetters();
                    }
                    sum += Math.pow(perfectFrequency.get(letter) - frequencyMap.get((char) offsetLetter), 2.0);
                }
                if(minResult > sum) {
                    minResult = sum;
                    minOffset = i;
                }
            }

            for(int i = iter; i < text.length(); i+=lengthKey) {
                char decodeLetter = text.charAt(i);
                if(alphabet.isCharInLowerCase(text.charAt(i))) {
                    if(text.charAt(i) - minOffset < alphabet.getLowerCaseOffset()) {
                        decodeLetter = (char)(text.charAt(i) - minOffset + alphabet.getCountLetters());
                    } else {
                        decodeLetter = (char)(text.charAt(i) - minOffset);
                    }
                } else if(alphabet.isCharInUpperCase(text.charAt(i))) {
                    if(text.charAt(i) - minOffset < alphabet.getUpperCaseOffset()) {
                        decodeLetter = (char)(text.charAt(i) - minOffset + alphabet.getCountLetters());
                    } else {
                        decodeLetter = (char)(text.charAt(i) - minOffset);
                    }
                }

                decodeText.setCharAt(i, decodeLetter);
            }
        }
        return decodeText.toString();
    }

}
