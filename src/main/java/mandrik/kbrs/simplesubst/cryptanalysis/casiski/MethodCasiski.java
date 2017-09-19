package mandrik.kbrs.simplesubst.cryptanalysis.casiski;

import mandrik.kbrs.simplesubst.util.Alphabet;
import mandrik.kbrs.simplesubst.util.Language;
import mandrik.kbrs.simplesubst.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static mandrik.kbrs.simplesubst.util.GCD.gcd;

public class MethodCasiski {


    public int guessKeyLength(File file, Alphabet alphabet, int sequenceLength) throws IOException {
        HashMap<String, ArrayList<Integer>> frequency =
                getFrequencyInFile(file, alphabet, sequenceLength);

        ArrayList<Integer> gcdValues = new ArrayList<>();
        for(Map.Entry<String, ArrayList<Integer>> entry: frequency.entrySet()) {
            ArrayList<Integer> temp = new ArrayList<>();
            for(int i = 0; i < entry.getValue().size()-1; i++) {
                temp.add(entry.getValue().get(i+1) - entry.getValue().get(i));
            }
            int gcd = gcd(temp);
            if(gcd != 1) {
                gcdValues.add(gcd);
            }
        }

        return gcd(gcdValues);
    }

    public HashMap<String, ArrayList<Integer>> getFrequencyInFile
            (File file, Alphabet alphabet, int sequenceLength) throws IOException {

        HashMap<String, ArrayList<Integer>> frequency = new HashMap<>();
        String encryptText = FileUtil.readFile(file, alphabet.getCharset());

        HashMap<String, Integer> mettingSequences = new HashMap<>();

        for(int i = 0; i < encryptText.length() - sequenceLength; i++) {
            boolean existInAlphabet = true;
            for(int j = 0; j < sequenceLength; j++) {
                if(!alphabet.isCharExistInAlphabet(encryptText.charAt(i + j))) {
                    existInAlphabet = false;
                }
            }

            if(existInAlphabet) {
                String subSuquence = encryptText.substring(i, i + sequenceLength);
                if (!mettingSequences.containsKey(subSuquence)) {
                    mettingSequences.put(subSuquence, i);
                } else {
                    ArrayList<Integer> countSubSequence;
                    if (frequency.containsKey(subSuquence)) {
                        countSubSequence = frequency.get(subSuquence);
                    } else {
                        countSubSequence = new ArrayList<>();
                        countSubSequence.add(mettingSequences.get(subSuquence));
                    }
                    countSubSequence.add(i);
                    frequency.put(subSuquence, countSubSequence);
                }
            }
        }
        return frequency;
    }
}
