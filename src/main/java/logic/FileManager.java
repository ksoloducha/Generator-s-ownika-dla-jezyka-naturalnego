package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class FileManager {

    public Dictionary read(String inputFilePath) throws IOException, EmptyFileException {
        try {
            Scanner scanner = new Scanner(new File(inputFilePath));
            if (!scanner.hasNext()) {
                throw new EmptyFileException("Input file is empty");
            }
            String title = "";
            while (title == "") {
                title = scanner.nextLine().trim();
            }
            Dictionary dictionaryFromFile = new Dictionary(title);
            while (scanner.hasNext()) {
                String word = scanner.next().replaceAll("[\\p{Punct}]", "");
                if (word != "")
                    dictionaryFromFile.add(word);
            }
            return dictionaryFromFile;
        } catch (IOException e) {
            throw e;
        }
    }

    public void saveWordsToFile(Dictionary dictionaryToSave, String outputFilePath) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            TreeSet<String> words = dictionaryToSave.getWords();
            for (String word : words) {
                writer.write(word + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw e;
        }
    }
}
