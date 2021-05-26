package logic;

import java.io.*;

public class Dictionary implements Serializable {

    private String title;
    private Trie words;

    public Dictionary(String title) {
        this.title = title;
        words = new Trie();
    }

    public void add(String... words) {
        for (String word : words) {
            this.words.insert(word.toLowerCase());
        }
    }

    public String getWords() {
        return words.print();
    }

    public String getTitle() {
        return title;
    }

    public boolean contains(String word) {
        return words.contains(word.toLowerCase());
    }

    @Override
    public int hashCode() {
        int hashCode = title.hashCode() * 17 + words.hashCode() * 23;
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof logic.Dictionary)) {
            return false;
        }
        Dictionary otherDictionary = (Dictionary) other;
        return otherDictionary.title.equals(this.title) && otherDictionary.words.equals(this.words);
    }
}
