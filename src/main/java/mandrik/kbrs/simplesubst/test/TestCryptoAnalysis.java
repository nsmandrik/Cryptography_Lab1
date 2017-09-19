package mandrik.kbrs.simplesubst.test;

import mandrik.kbrs.simplesubst.cryptanalysis.casiski.MethodCasiski;
import mandrik.kbrs.simplesubst.util.Alphabet;
import mandrik.kbrs.simplesubst.util.Language;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static mandrik.kbrs.simplesubst.cryptanalysis.freqanalysis.FrequencyAnalysis.getDecodeText;
import static mandrik.kbrs.simplesubst.util.FileUtil.writeTextToFile;

public class TestCryptoAnalysis {
    public static  void main(String[] args) {

        File encryptFile = new File("C:\\Users\\Owner\\Storage\\encrypt.txt");
        File decryptFile = new File("C:\\Users\\Owner\\Storage\\analysis.txt");

        MethodCasiski method = new MethodCasiski();

        try {
            Alphabet alphabet = new Alphabet(Language.RU, StandardCharsets.UTF_8);

            int lengthKey = method.guessKeyLength(encryptFile, alphabet, 8);
            String decodedText = getDecodeText(encryptFile, alphabet, lengthKey);

            writeTextToFile(decodedText, decryptFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
