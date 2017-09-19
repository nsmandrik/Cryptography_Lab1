package mandrik.kbrs.simplesubst.encoder;

import mandrik.kbrs.simplesubst.util.Alphabet;
import mandrik.kbrs.simplesubst.util.Language;

import java.io.*;
import java.nio.charset.Charset;

import static mandrik.kbrs.simplesubst.util.FileUtil.readFile;
import static mandrik.kbrs.simplesubst.util.FileUtil.writeTextToFile;

public class SimpleSubstitution {

    private static String defaultKeyEN = "MANDRIK";
    private static String defaultKeyRU = "МАНДРИК";

    private String key;
    private int countSymbols;
    private Charset charset;
    private Alphabet alphabet;

    public SimpleSubstitution(Language language, Charset charset) {
        this.key = getDefaultKeyByLanguage(language);
        this.charset = charset;
        this.alphabet = new Alphabet(language, charset);
        this.countSymbols = this.alphabet.getCountLetters();
    }

    public SimpleSubstitution(Language language, String key, Charset charset) {
        this.key = key.toUpperCase();
        this.charset = charset;
        this.alphabet = new Alphabet(language, charset);
        this.countSymbols = this.alphabet.getCountLetters();
    }


    private String getDefaultKeyByLanguage(Language language) {
        String key;
        switch (language) {
            case RU:
                key = defaultKeyRU;
                break;
            case EN:
                key = defaultKeyEN;
                break;
            default:
                key = "";
        }
        return key;
    }


    public void encryptFile(File inputFile, File outputFile) throws IOException {

        String text = readFile(inputFile,charset);

        StringBuilder encryptTextBuilder = new StringBuilder();

        for(int i = 0; i < text.length(); i++) {
            char toAppend = text.charAt(i);
            if(alphabet.isCharInLowerCase(text.charAt(i))) {

                int num = ((text.charAt(i) + key.charAt(i % key.length()) -
                        alphabet.getLowerCaseOffset() - alphabet.getUpperCaseOffset()) % countSymbols);
                toAppend = (char) (num + alphabet.getLowerCaseOffset());

            } else if(alphabet.isCharInUpperCase(text.charAt(i))) {

                int num = ((text.charAt(i) + key.charAt(i % key.length()) -
                        (2 * alphabet.getUpperCaseOffset())) % countSymbols);
                toAppend = (char) (num + alphabet.getUpperCaseOffset());
            }
            encryptTextBuilder.append(toAppend);
        }

        writeTextToFile(encryptTextBuilder.toString(), outputFile);
    }


    public void decryptFile(File inputFile, File outputFile) throws IOException {

        String encryptText = readFile(inputFile,charset);

        StringBuilder decryptTextBuilder = new StringBuilder();

        for(int i = 0; i < encryptText.length();i++) {
            char toAppend = encryptText.charAt(i);
            if( alphabet.isCharInLowerCase(encryptText.charAt(i))) {

                int num = ((encryptText.charAt(i) - key.toLowerCase().charAt(i % key.length()) + countSymbols) % countSymbols);
                toAppend = (char) (num + alphabet.getLowerCaseOffset());

            } else if( alphabet.isCharInUpperCase(encryptText.charAt(i))) {

                int num = ((encryptText.charAt(i) - key.charAt(i % key.length()) + countSymbols) % countSymbols);
                toAppend = (char) (num + alphabet.getUpperCaseOffset());

            }
            decryptTextBuilder.append(toAppend);
        }

        writeTextToFile(decryptTextBuilder.toString(), outputFile);
    }

}
