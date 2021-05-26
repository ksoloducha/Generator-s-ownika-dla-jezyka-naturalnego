package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

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
    public void getWords_should_returnTreeSetOfWordsInDictionary() {
        String expectedWords = "in\ntree\nwords\n";
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