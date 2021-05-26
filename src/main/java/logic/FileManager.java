package logic;

import java.io.*;
import java.util.ArrayList;
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

    public void saveWordsToFile(Dictionary dictionaryToSave, String outputFilePath) throws FileExistsException, IOException {
        try {
            File outputFile = new File(outputFilePath);
            if (outputFile.exists()) {
                throw new FileExistsException("Given filename already exists");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            writer.write(dictionaryToSave.getWords());
            writer.close();
        } catch (IOException e) {
            throw e;
        }
    }

    public void saveToBinFile(String outputFile, Dictionary dictionary) throws FileExistsException, IOException {
        File outFile = new File(outputFile);
        if (outFile.exists()) {
            throw new FileExistsException("Given filename already exists");
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream((new FileOutputStream(outputFile)))) {
            outputStream.writeObject(dictionary);
        } catch (FileNotFoundException e1) {
            throw e1;
        } catch (IOException e2) {
            throw e2;
        }
    }

    public static Dictionary createFromBinFile(String inputFilePath) throws ClassNotFoundException, IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(inputFilePath))) {
            Dictionary dictionaryFromFile = (Dictionary) inputStream.readObject();
            return dictionaryFromFile;
        } catch (FileNotFoundException | ClassNotFoundException e1) {
            throw e1;
        } catch (IOException e2) {
            throw e2;
        }
    }
}
