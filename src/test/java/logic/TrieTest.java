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
    public void insert_should_addWordToTree(){
        String word = "test";
        trie.insert(word);
        assertTrue(trie.contains(word));
    }

    /*@Test
    public void xx() {
        Trie trie = new Trie();
        trie.insert("test");
        trie.insert("ala");
        trie.insert("alamakota");
        trie.insert("alamapsa");
        trie.insert("projekt");
        //trie.print();
        trie.insert("testowanie");
        trie.insert("testing");
        trie.insert("tester");
        trie.insert("testerka");
        trie.print();


        System.out.println("-------------------");
        trie.insert("a");
        trie.insert("aa");
        trie.insert("ab");
        trie.insert("b");
        trie.insert("caa");
        trie.insert("cba");
        trie.insert("cbc");
        trie.insert("ddd");
        trie.print();
        System.out.println();
    }*/
}