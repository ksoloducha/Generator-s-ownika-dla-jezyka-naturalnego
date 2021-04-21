package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    Dictionary dictionary;
    String path = "src" + File.separator + "test" + File.separator + "java" + File.separator + "logic" + File.separator + "test_files";
    String title = "Test";

    @BeforeEach
    public void setUp() {
        dictionary = new Dictionary(title);
    }

    @Test
    public void saveToFile_should_saveTitleAndWordsToGivenFile_when_givenCorrectPath() {
        String outputFileName = path + File.separator + "out.bin";
        dictionary.add("Some", "sample", "words", "to", "test");
        try {
            dictionary.saveToBinFile(outputFileName);
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
            dictionary.saveToBinFile(outputFileName);
        });
    }

    @Test
    public void createFromFile_should_createDictionaryFromFile_when_correctFileExists() {
        String outputFileName = path + File.separator + "out.bin";
        dictionary.add("Some", "sample", "words", "to", "test");
        try {
            dictionary.saveToBinFile(outputFileName);
            Dictionary dictionaryFromFile = Dictionary.createFromBinFile(outputFileName);
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
            dictionary.createFromBinFile(outputFileName);
        });
    }

    @Test
    public void getWords_should_returnTreeSetOfWordsInDictionary() {
        TreeSet<String> expectedWords = new TreeSet<>();
        expectedWords.add("words");
        expectedWords.add("in");
        expectedWords.add("tree");
        dictionary.add("words", "in", "tree");
        assertEquals(expectedWords, dictionary.getWords());
    }

    @Test
    public void getTitle_should_returnDictionaryTitle() {
        assertEquals(title, dictionary.getTitle());
    }

    @Test
    public void contains_should_returnTrue_when_dictionaryContainsGivenWord() {
        String expectedWord = "expected";
        dictionary.add(expectedWord);
        assertTrue(dictionary.contains(expectedWord));
    }

    @Test
    public void contains_should_returnFalse_when_dictionaryDoesNotContainGivenWord() {
        String notAddedWord = "not_added";
        assertFalse(dictionary.contains(notAddedWord));
    }
}