package logic;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class ReadFile {

    public Dictionary read(String inputFilePath) throws IOException, EmptyFileException {
        try {
            Scanner scanner = new Scanner(new File(inputFilePath));
            if (!scanner.hasNext()) {
                throw new EmptyFileException("Input file is empty");
            }
            Dictionary dictionaryFromFile = new Dictionary(scanner.nextLine());
            while (scanner.hasNext()) {
                String word = scanner.next().replaceAll("[\\p{Punct}]", "");;
                dictionaryFromFile.add(word);
            }
            return dictionaryFromFile;
        } catch (IOException e) {
            throw e;
        }
    }
}
