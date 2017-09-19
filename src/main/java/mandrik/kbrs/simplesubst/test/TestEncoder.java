package mandrik.kbrs.simplesubst.test;

import mandrik.kbrs.simplesubst.encoder.SimpleSubstitution;
import mandrik.kbrs.simplesubst.util.Language;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestEncoder {
    public static  void main(String[] args) {
        File file = new File("C:\\Users\\Owner\\Storage\\text.txt");
        File encryptFile = new File("C:\\Users\\Owner\\Storage\\encrypt.txt");
        File decryptFile = new File("C:\\Users\\Owner\\Storage\\decrypt.txt");


        SimpleSubstitution simpleSubstitution =
                new SimpleSubstitution(Language.RU, StandardCharsets.UTF_8);
        try {
            simpleSubstitution.encryptFile(file, encryptFile);
            simpleSubstitution.decryptFile(encryptFile, decryptFile);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
