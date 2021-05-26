package logic;

import java.io.Serializable;
import java.util.TreeMap;

public class TrieNode implements Serializable {

    private Character letter;
    private TreeMap<Character, TrieNode> children;
    private boolean isWord;
    private boolean isPrefix;

    public TrieNode(Character letter) {
        this.letter = letter;
        children = new TreeMap<>();
        isWord = false;
        isPrefix = false;
    }

    public Character getLetter() {
        return letter;
    }

    public TreeMap<Character, TrieNode> getChildren() {
        return children;
    }

    public void addChild(Character childLetter) {
        if (children.containsKey(childLetter)) {
            throw new IllegalArgumentException("Node already has child " + childLetter);
        } else {
            if (!children.isEmpty() || isWord) {
                setPrefix();
            }
            TrieNode child = new TrieNode(childLetter);
            children.put(childLetter, child);
        }
    }

    public boolean hasChild(Character childLetter) {
        return children.containsKey(childLetter);
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord() {
        isWord = true;
    }

    public boolean isPrefix() {
        return isPrefix;
    }

    public void setPrefix() {
        isPrefix = true;
    }

    public boolean isLastPrefix() {
        if (!isPrefix) {
            return false;
        } else {
            if (containsPrefix())
                return false;
        }
        return true;
    }

    public boolean containsPrefix() {
        if (hasChildren()) {
            for (TrieNode child : children.values()) {
                if (child.isPrefix || (child.isWord() && child.hasChildren()) || child.containsPrefix()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public TrieNode getChild(Character childLetter) {
        return children.get(childLetter);
    }

    @Override
    public int hashCode() {
        int hash = 19 * children.hashCode();
        if (letter != null) {
            hash += 17 * letter.hashCode();
        }
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
        TrieNode otherNode = (TrieNode) other;
        if (!children.equals(otherNode.children)) {
            return false;
        }
        if (letter == null && otherNode.letter == null) {
            return true;
        } else if (!letter.equals(otherNode.letter)) {
            return false;
        }
        return true;
    }
}
