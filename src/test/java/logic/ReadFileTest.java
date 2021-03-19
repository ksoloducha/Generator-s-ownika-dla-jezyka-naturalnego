package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileTest {

    ReadFile readFile;
    String path = "src" + File.separator + "test" + File.separator + "java" + File.separator + "logic" + File.separator + "test_files";

    @BeforeEach
    public void setUp() {
        readFile = new ReadFile();
    }

    @Test
    public void read_should_throwEmptyFileException_when_fileToReadIsEmpty() {
        String inputFilePath = path + File.separator + "empty_file.txt";
        assertThrows(EmptyFileException.class, () -> {
            readFile.read(inputFilePath);
        });
    }

    @Test
    public void read_should_throwIOException_when_fileToReadDoesNotExist() {
        String inputFilePath = path + File.separator + "nonexistent_file.txt";
        assertThrows(IOException.class, () -> {
            readFile.read(inputFilePath);
        });
    }

    @Test
    public void read_should_createDictionary_when_givenCorrectFile() {

        try {
            String inputFilePath = path + File.separator + "example_input_file.txt";
            Dictionary dictionaryFromFile;
            Dictionary expectedDictionary = new Dictionary("Example Title");
            expectedDictionary.add("Some", "example", "words", "to", "test", "ReadFile", "class", "Here", "is", "even", "more", "should", "be", "put", "in", "alphabetic", "order");
            dictionaryFromFile = readFile.read(inputFilePath);
            assertEquals(expectedDictionary, dictionaryFromFile);
        } catch (EmptyFileException e1) {
            System.out.println(e1.getLocalizedMessage());
        } catch (IOException e2) {
            System.out.println(e2.getLocalizedMessage());
        }
    }
}