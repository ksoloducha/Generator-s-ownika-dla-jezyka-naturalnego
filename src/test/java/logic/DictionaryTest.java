package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    Dictionary dictionary;
    String path = "src" + File.separator + "test" + File.separator + "java" + File.separator + "logic" + File.separator + "test_files";

    @BeforeEach
    public void setUp() {
        dictionary = new Dictionary("Test");
        dictionary.add("Some", "sample", "words", "to", "test");
    }

    @Test
    public void saveToFile_should_saveTitleAndWordsToGivenFile_when_givenCorrectPath() {
        String outputFileName = path + File.separator + "out.bin";
        try {
            dictionary.saveToFile(outputFileName);
        } catch (FileNotFoundException e1) {
            System.out.println(e1.getLocalizedMessage());
        } catch (IOException e2) {
            System.out.println(e2.getLocalizedMessage());
        }
        File testOutput = new File(outputFileName);
        assertTrue(testOutput.exists());
    }

    @Test
    public void saveToFile_should_throwFileNotFountException_when_pathToOutputFileIsIncorrect() {
        String outputFileName = path + File.separator + "nonexistent_directory" + File.separator + "out.bin";
        assertThrows(FileNotFoundException.class, () -> {
            dictionary.saveToFile(outputFileName);
        });
    }

    @Test
    public void createFromFile_should_createDictionaryFromFile_when_correctFileExists() {
        String outputFileName = path + File.separator + "out.bin";
        try {
            dictionary.saveToFile(outputFileName);
            Dictionary dictionaryFromFile = Dictionary.createFromFile(outputFileName);
            assertEquals(dictionary, dictionaryFromFile);
        } catch (FileNotFoundException | ClassNotFoundException e1) {
            System.out.println(e1.getLocalizedMessage());
        } catch (IOException e2) {
            System.out.println(e2.getLocalizedMessage());
        }
    }

    @Test
    public void createFromFile_should_throwFileNotFountException_when_pathToOutputFileIsIncorrect() {
        String outputFileName = path + File.separator + "nonexistent_directory" + File.separator + "out.bin";
        assertThrows(FileNotFoundException.class, () -> {
            dictionary.createFromFile(outputFileName);
        });
    }
}