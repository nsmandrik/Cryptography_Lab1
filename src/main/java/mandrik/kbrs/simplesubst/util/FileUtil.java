package mandrik.kbrs.simplesubst.util;

import java.io.*;
import java.nio.charset.Charset;

public class FileUtil {

    public static void writeTextToFile(String text, File file) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(file);
        out.print(text);
        out.close();
    }


    public static String readFile(File file, Charset charset)  throws IOException {
        String text;
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(file), charset.displayName()))) {
            StringBuilder textBuilder = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                textBuilder.append(line);
                textBuilder.append(System.lineSeparator());
                line = reader.readLine();
            }
            text = textBuilder.toString();
            reader.close();
        }
        return text;
    }
}
