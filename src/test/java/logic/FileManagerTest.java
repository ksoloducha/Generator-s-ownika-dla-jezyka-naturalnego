package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    FileManager fileManager;
    String path = "src" + File.separator + "test" + File.separator + "java" + File.separator + "logic" + File.separator + "test_files";

    @BeforeEach
    public void setUp() {
        fileManager = new FileManager();
    }

    @Test
    public void read_should_throwEmptyFileException_when_fileToReadIsEmpty() {
        String inputFilePath = path + File.separator + "empty_file.txt";
        assertThrows(EmptyFileException.class, () -> {
            fileManager.read(inputFilePath);
        });
    }

    @Test
    public void read_should_throwIOException_when_fileToReadDoesNotExist() {
        String inputFilePath = path + File.separator + "nonexistent_file.txt";
        assertThrows(IOException.class, () -> {
            fileManager.read(inputFilePath);
        });
    }

    @Test
    public void read_should_createDictionary_when_givenCorrectFile() {
        try {
            String inputFilePath = path + File.separator + "example_input_file.txt";
            Dictionary dictionaryFromFile;
            Dictionary expectedDictionary = new Dictionary("Example Title");
            expectedDictionary.add("Some", "example", "words", "to", "test", "ReadFile", "class", "Here", "is", "even", "more", "should", "be", "put", "in", "alphabetic", "order");
            dictionaryFromFile = fileManager.read(inputFilePath);
            assertEquals(expectedDictionary, dictionaryFromFile);
        } catch (EmptyFileException e1) {
            System.out.println(e1.getLocalizedMessage());
        } catch (IOException e2) {
            System.out.println(e2.getLocalizedMessage());
        }
    }

    @Test
    public void saveWordsToFile_should_FileExistsException_when_fileCanNotBeCreated() {
        path += File.separator + "example_dictionary_to_file.txt";
        Dictionary dictionary = new Dictionary("Dictionary to file");
        assertThrows(FileExistsException.class, () -> {
            fileManager.saveWordsToFile(dictionary, path);
        });
    }

    @Test
    public void saveWordsToFile_should_throwIOException_when_fileCanNotBeCreated() {
        String path = "nonexistent_folder" + File.separator + "output_file.txt";
        Dictionary dictionary = new Dictionary("Dictionary to file");
        assertThrows(IOException.class, () -> {
            fileManager.saveWordsToFile(dictionary, path);
        });
    }

    @Test
    public void saveWordsToFile_should_saveWordsFromDictionaryToFile_when_given_correctPath() {
        String title = "Example dictionary to file";
        Dictionary dictionary = new Dictionary(title);
        path += File.separator + dictionary.getTitle().toLowerCase().replaceAll("[\\s]", "_") + ".txt";
        File file = new File(path);
        file.delete();
        TreeSet<String> wordsToDictionary = new TreeSet<>();
        wordsToDictionary.add("some");
        wordsToDictionary.add("example");
        wordsToDictionary.add("words");
        wordsToDictionary.add("to");
        wordsToDictionary.add("write");
        wordsToDictionary.add("in");
        wordsToDictionary.add("file");
        TreeSet<String> expectedWordsFromFile = new TreeSet<>();
        expectedWordsFromFile.add("some");
        expectedWordsFromFile.add("example");
        expectedWordsFromFile.add("w -ords; -rite;");
        expectedWordsFromFile.add("to");
        expectedWordsFromFile.add("in");
        expectedWordsFromFile.add("file");
        for (String word : wordsToDictionary) {
            dictionary.add(word);
        }
        try {
            fileManager.saveWordsToFile(dictionary, path);
            Scanner scan = new Scanner(new File(path));
            for (String word : expectedWordsFromFile) {
                assertEquals(word, scan.nextLine());
            }
        } catch (FileExistsException e1) {
            System.out.println(e1.getLocalizedMessage());
        } catch (IOException e2) {
            System.out.println(e2.getLocalizedMessage());
        }
    }

    @Test
    public void saveToBinFile_should_saveTitleAndWordsToGivenFile_when_givenCorrectPath() {
        Dictionary dictionary = new Dictionary("title");
        String outputFileName = path + File.separator + "out.bin";
        File file = new File(outputFileName);
        file.delete();
        dictionary.add("Some", "sample", "words", "to", "test");
        try {
            fileManager.saveToBinFile(outputFileName, dictionary);
        } catch (FileExistsException e1) {
            System.out.println(e1.getLocalizedMessage());
        } catch (FileNotFoundException e2) {
            System.out.println(e2.getLocalizedMessage());
        } catch (IOException e3) {
            System.out.println(e3.getLocalizedMessage());
        }
        File testOutput = new File(outputFileName);
        assertTrue(testOutput.exists());
    }

    @Test
    public void saveToBinFile_should_throwFileNotFountException_when_pathToOutputFileIsIncorrect() {
        Dictionary dictionary = new Dictionary("title");
        String outputFileName = path + File.separator + "nonexistent_directory" + File.separator + "out.bin";
        assertThrows(FileNotFoundException.class, () -> {
            fileManager.saveToBinFile(outputFileName, dictionary);
        });
    }

    @Test
    public void saveToBinFile_should_throwFileExistsException_when_fileAlreadyExists() {
        Dictionary dictionary = new Dictionary("title");
        String outputFileName = path + File.separator + "out.bin";
        assertThrows(FileExistsException.class, () -> {
            fileManager.saveToBinFile(outputFileName, dictionary);
        });
    }

    @Test
    public void createFromBinFile_should_createDictionaryFromFile_when_correctFileExists() {
        Dictionary dictionary = new Dictionary("title");
        String outputFileName = path + File.separator + "out.bin";
        File file = new File(outputFileName);
        file.delete();
        dictionary.add("Some", "sample", "words", "to", "test");
        try {
            fileManager.saveToBinFile(outputFileName, dictionary);
            Dictionary dictionaryFromFile = fileManager.createFromBinFile(outputFileName);
            assertEquals(dictionary, dictionaryFromFile);
        } catch (FileExistsException e1) {
            System.out.println(e1.getLocalizedMessage());
        } catch (FileNotFoundException | ClassNotFoundException e2) {
            System.out.println(e2.getLocalizedMessage());
        } catch (IOException e3) {
            System.out.println(e3.getLocalizedMessage());
        }
    }

    @Test
    public void createFromBinFile_should_throwFileNotFountException_when_pathToOutputFileIsIncorrect() {
        Dictionary dictionary = new Dictionary("title");
        String outputFileName = path + File.separator + "nonexistent_directory" + File.separator + "out.bin";
        assertThrows(FileNotFoundException.class, () -> {
            fileManager.createFromBinFile(outputFileName);
        });
    }
}