package mandrik.kbrs.simplesubst.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Alphabet {

    private char firstCharacterUpperCase;
    private char firstCharacterLowerCase;
    private int countLetters;
    private Charset charset;
    private Language language;
    Map<Character, Double> frequency;


    public Alphabet(Language language, Charset charset) {
        this.charset = charset;
        this.language =  language;
        String strHelper;
        switch (language) {
            case RU:
                strHelper = "аАя";
                frequency = new HashMap<>(frequencyRU());
                break;
            case EN:
            default:
                strHelper = "aAz";
                frequency = new HashMap<>(frequencyEN());
                break;
        }
        try {
            byte[] encodeSymbols = strHelper.getBytes();
            String encodeString = new String(encodeSymbols, charset.displayName());
            firstCharacterLowerCase = encodeString.charAt(0);
            firstCharacterUpperCase = encodeString.charAt(1);
            countLetters = encodeString.charAt(2) - encodeString.charAt(0) + 1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ArrayList<Character> removableCharacters = new ArrayList<>();
        for(Character letter: frequency.keySet()) {
            if(!isCharExistInAlphabet(letter)) {
                removableCharacters.add(letter);
            }
        }
        for(Character letter: removableCharacters) {
            frequency.remove(letter);
        }
    }


    public Charset getCharset() {
        return charset;
    }

    public Map<Character, Double> getFrequency() {
        return frequency;
    }

    public Language getLanguage() {
        return language;
    }

    public char getUpperCaseOffset() {
        return firstCharacterUpperCase;
    }

    public char getLowerCaseOffset() {
        return firstCharacterLowerCase;
    }

    public int getCountLetters() {
        return countLetters;
    }

    public boolean isCharInLowerCase(char symbol) {
        return symbol >= firstCharacterLowerCase && symbol <= firstCharacterLowerCase + countLetters;
    }

    public boolean isCharInUpperCase(char symbol) {
        return symbol >= firstCharacterUpperCase && symbol <= firstCharacterUpperCase + countLetters;
    }

    public boolean isCharExistInAlphabet(char symbol) {
        return isCharInUpperCase(symbol) || isCharInLowerCase(symbol);
    }


























    public static Map.Entry<Character, Double> entry(Character key, Double value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    public static Collector<Map.Entry<Character, Double>, ?, Map<Character, Double>> entriesToMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static Collector<Map.Entry<Character, Double>,
            ?, ConcurrentMap<Character, Double>> entriesToConcurrentMap() {
        return Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public Map<Character, Double> frequencyEN() {
        return Collections.unmodifiableMap(Stream.of(
                entry('A', 0.08167),
                entry('B', 0.01492),
                entry('C', 0.02782),
                entry('D', 0.04253),
                entry('E', 0.12702),
                entry('F', 0.0228),
                entry('G', 0.02015),
                entry('H', 0.06094),
                entry('I', 0.06966),
                entry('J', 0.00153),
                entry('K', 0.00772),
                entry('L', 0.04025),
                entry('M', 0.02406),
                entry('N', 0.06749),
                entry('O', 0.07507),
                entry('P', 0.01929),
                entry('Q', 0.00095),
                entry('R', 0.05987),
                entry('S', 0.06327),
                entry('T', 0.09056),
                entry('U', 0.02758),
                entry('V', 0.00978),
                entry('W', 0.0236),
                entry('X', 0.0015),
                entry('Y', 0.01974),
                entry('Z', 0.00074)).
                collect(entriesToMap()));
    }

    public Map<Character, Double> frequencyRU() {
        return Collections.unmodifiableMap(Stream.of(
                entry('А', 0.07821),
                entry('Б', 0.01732),
                entry('В', 0.04491),
                entry('Г', 0.01698),
                entry('Д', 0.03103),
                entry('Е', 0.08567),
                entry('Ё', 0.0007),
                entry('Ж', 0.01082),
                entry('З', 0.01647),
                entry('И', 0.06777),
                entry('Й', 0.01041),
                entry('К', 0.03215),
                entry('Л', 0.04813),
                entry('М', 0.03139),
                entry('Н', 0.0685),
                entry('О', 0.11394),
                entry('П', 0.02754),
                entry('Р', 0.04234),
                entry('С', 0.05382),
                entry('Т', 0.06443),
                entry('У', 0.02882),
                entry('Ф', 0.00132),
                entry('Х', 0.00833),
                entry('Ц', 0.00333),
                entry('Ч', 0.01645),
                entry('Ш', 0.00775),
                entry('Щ', 0.00331),
                entry('Ъ', 0.00023),
                entry('Ы', 0.01854),
                entry('Ь', 0.02106),
                entry('Э', 0.0031),
                entry('Ю', 0.00544),
                entry('Я', 0.01979)).
                collect(entriesToMap()));
    }
}
