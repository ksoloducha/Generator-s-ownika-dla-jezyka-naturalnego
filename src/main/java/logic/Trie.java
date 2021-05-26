package logic;

import java.io.Serializable;
import java.util.*;

public class Trie implements Serializable {

    private TrieNode root;

    public Trie() {
        root = new TrieNode(null);
    }

    public TrieNode getRoot() {
        return root;
    }

    public void insert(String word) {
        Character parentLetter = word.charAt(0);

        if (!root.hasChild(parentLetter)) {
            root.addChild(parentLetter);
        }
        TrieNode iterator = root.getChild(parentLetter);

        if (word.length() > 1) {
            char[] chars = new char[word.length() - 1];
            word.getChars(1, word.length(), chars, 0);

            for (Character character : chars) {
                if (!iterator.hasChild(character)) {
                    iterator.addChild(character);
                }
                iterator = iterator.getChild(character);
            }
        }
        iterator.setWord();
    }

    public boolean contains(String word) {
        TrieNode iterator = root;
        for (int i = 0; i < word.length(); i++) {
            Character letter = word.charAt(i);
            iterator = iterator.getChild(letter);
            if (iterator == null) {
                return false;
            }
        }
        return true;
    }

    public String print() {
        Collection<TrieNode> parentNodes = root.getChildren().values();
        String words = new String();

        for (TrieNode parent : parentNodes) {
            words = print(parent, new String(), words);
        }
        return words;
    }

    private String print(TrieNode node, String prefix, String words) {
        Collection<TrieNode> children = node.getChildren().values();
        prefix += node.getLetter();

        if (!node.isWord() && !node.isPrefix()) {
            for (TrieNode child : children) {
                words = print(child, prefix, words);
            }
        } else if ((node.isWord() && !node.isPrefix())) {
            words += prefix + "\n";
        } else if (node.isPrefix()) {
            words += prefix;
            boolean single = true;
            for (TrieNode child : children) {
                if (!child.containsPrefix() && !child.isPrefix()) {
                    single = false;
                }
            }
            if (node.isWord() && !single) {
                words += " -;";
            }
            if (node.isLastPrefix()) {
                ArrayList<String> suffixes = new ArrayList<>();
                for (TrieNode child : children) {
                    suffixes.add(printSuffix(child));
                }
                if (suffixes.size() == 1 && !node.isWord()) {
                    words += suffixes.get(0);
                } else {
                    for (String suffix : suffixes) {
                        words += " -" + suffix + ";";
                    }
                }
                words += "\n";
            } else {
                ArrayList<TrieNode> childrenWithPrefixes = new ArrayList<>();
                ArrayList<String> suffixes = new ArrayList<>();

                for (TrieNode child : children) {
                    if (!child.containsPrefix() && !child.isPrefix()) {
                        suffixes.add(printSuffix(child));
                    } else {
                        childrenWithPrefixes.add(child);
                    }
                }
                if (suffixes.size() == 1 && !node.isWord()) {
                    words += suffixes.get(0);
                } else {
                    for (String suffix : suffixes) {
                        words += " -" + suffix + ";";
                    }
                }
                words += "\n";
                for (TrieNode child : childrenWithPrefixes) {
                    words = print(child, prefix, words);
                }
            }
        }
        return words;
    }

    private String printSuffix(TrieNode node) {
        String suffix = node.getLetter().toString();
        if (node.hasChildren()) {
            Collection<TrieNode> children = node.getChildren().values();
            for (TrieNode child : children) {
                suffix = addToSuffix(suffix, child);
            }
        }
        return suffix;
    }

    private String addToSuffix(String suffix, TrieNode node) {
        suffix += node.getLetter();
        if (node.hasChildren()) {
            Collection<TrieNode> children = node.getChildren().values();
            for (TrieNode child : children) {
                suffix = addToSuffix(suffix, child);
            }
        }
        return suffix;
    }
}
