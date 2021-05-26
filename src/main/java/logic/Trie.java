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
        TrieNode iterator = root;
        char[] chars = new char[word.length()];
        word.getChars(0, word.length(), chars, 0);

        for (Character character : chars) {
            if (!iterator.hasChild(character)) {
                iterator.addChild(character);
            }
            iterator = iterator.getChild(character);
        }
        iterator.setWord();
        if (iterator.hasChildren()) {
            iterator.setPrefix();
        }
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
        prefix += node.getLetter();
        Collection<TrieNode> children = node.getChildren().values();

        if (!node.isWord() && !node.isPrefix()) {
            words = printChildren(children, prefix, words);
        } else if ((node.isWord() && !node.isPrefix())) {
            words += prefix + "\n";
        } else if (node.isPrefix()) {
            if (node.isLastPrefix()) {
                words += prefix;
                boolean single = isSingleWord(node);
                if (node.isWord() && !single) {
                    words += " -;";
                }
                words = printSuffixes(node, words) + "\n";
            } else {
                words = manageSuffixesAndNewPrefixes(node, words, prefix);
            }
        }
        return words;
    }

    private String printChildren(Collection<TrieNode> children, String prefix, String words) {
        for (TrieNode child : children) {
            words = print(child, prefix, words);
        }
        return words;
    }

    private boolean isSingleWord(TrieNode node) {
        Collection<TrieNode> children = node.getChildren().values();
        for (TrieNode child : children) {
            if (!child.containsPrefix() && !child.isPrefix()) {
                return false;
            }
        }
        return true;
    }

    private String printSuffixes(TrieNode node, String words) {
        Collection<TrieNode> children = node.getChildren().values();
        ArrayList<String> suffixes = new ArrayList<>();
        for (TrieNode child : children) {
            suffixes.add(printNodesSuffix(child));
        }
        if (suffixes.size() == 1 && !node.isWord()) {
            words += suffixes.get(0);
        } else {
            for (String suffix : suffixes) {
                words += " -" + suffix + ";";
            }
        }
        return words;
    }

    private String printNodesSuffix(TrieNode node) {
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

    private String manageSuffixesAndNewPrefixes(TrieNode node, String words, String prefix) {
        Collection<TrieNode> children = node.getChildren().values();
        ArrayList<TrieNode> childrenWithPrefixes = new ArrayList<>();
        ArrayList<String> suffixes = new ArrayList<>();

        for (TrieNode child : children) {
            if (!child.containsPrefix() && !child.isPrefix()) {
                suffixes.add(printNodesSuffix(child));
            } else {
                childrenWithPrefixes.add(child);
            }
        }
        if (suffixes.size() == 1 && !node.isWord()) {
            words += prefix + suffixes.get(0);
            words += "\n";
        } else if (suffixes.size() != 0) {
            words += prefix;
            if (node.isWord()) {
                words += " -;";
            }
            for (String suffix : suffixes) {
                words += " -" + suffix + ";";
            }
            words += "\n";
        } else if (node.isWord()) {
            words += prefix + "\n";
        }
        words = printChildren(childrenWithPrefixes, prefix, words);
        return words;
    }

    @Override
    public int hashCode() {
        int hash = 17 * root.hashCode() + 23;
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (getClass() != other.getClass())
            return false;
        Trie otherNode = (Trie) other;
        if (!(root.equals(otherNode.root)))
            return false;
        return true;
    }
}
