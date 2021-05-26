package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    private Trie trie;

    @BeforeEach
    public void setUp() {
        trie = new Trie();
    }

    @Test
    public void getRoot_should_returnRoot() {
        TrieNode expectedRoot = new TrieNode(null);
        assertEquals(expectedRoot, trie.getRoot());
    }

    @Test
    public void insert_should_addWordToTree() {
        String word = "test";
        trie.insert(word);
        assertTrue(trie.contains(word));
    }
}