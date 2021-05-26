package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TrieNodeTest {

    TrieNode node;
    Character letter = 'a';

    @BeforeEach
    public void setUp() {
        node = new TrieNode(letter);
    }

    @Test
    public void getLetter_should_returnLetter() {
        assertEquals(letter, node.getLetter());
    }

    @Test
    public void getChildren_should_returnEmptyHashMap_when_nodeHasNoChildren() {
        HashMap<Character, TrieNode> expectedChildren = new HashMap<>();
        assertEquals(expectedChildren, node.getChildren());
    }

    @Test
    public void getChildren_should_returnHashMapWithChildren_when_nodeHasChildren() {
        HashMap<Character, TrieNode> expectedChildren = new HashMap<>();
        Character expectedChildLetter = 'c';
        TrieNode expectedChild = new TrieNode(expectedChildLetter);
        expectedChildren.put(expectedChildLetter, expectedChild);

        node.addChild(expectedChildLetter);

        assertEquals(expectedChildren, node.getChildren());
    }

    @Test
    public void addChild_should_throwIllegalArgumentException_when_nodeAlreadyHasChildWithGivenLetter() {
        Character doubleChildLetter = 'c';
        node.addChild(doubleChildLetter);

        assertThrows(IllegalArgumentException.class, () -> {
            node.addChild(doubleChildLetter);
        });
    }

    @Test
    public void addChild_should_addChild_when_letterIsCorrect() {
        HashMap<Character, TrieNode> expectedChildren = new HashMap<>();
        Character expectedChildLetter = 'c';
        TrieNode expectedChild = new TrieNode(expectedChildLetter);
        expectedChildren.put(expectedChildLetter, expectedChild);

        node.addChild(expectedChildLetter);

        assertEquals(expectedChildren, node.getChildren());
    }

    @Test
    public void hasChild_should_returnFalse_when_NodeDoesNotHaveChildWithGivenLetter() {
        Character nonExistingChildLetter = 'x';
        assertFalse(node.hasChild(nonExistingChildLetter));
    }

    @Test
    public void hasChild_should_returnTrue_when_NodeDoesHaveChildWithGivenLetter() {
        Character childLetter = 'c';
        node.addChild(childLetter);
        assertTrue(node.hasChild(childLetter));
    }

    @Test
    public void isWord_should_returnIsWord() {
        assertFalse(node.isWord());
        node.setWord();
        assertTrue(node.isWord());
    }

    @Test
    public void setWord_should_setIsWordAsTrue() {
        node.setWord();
        assertTrue(node.isWord());
    }

    @Test
    public void isPrefix_should_returnIsPrefix() {
        assertFalse(node.isPrefix());
        node.setPrefix();
        assertTrue(node.isPrefix());
    }

    @Test
    public void setPrefix_should_setIsPrefixAsTrue() {
        node.setPrefix();
        assertTrue(node.isPrefix());
    }

    @Test
    public void isLastPrefix_should_returnFalse_when_nodeIsNotLastPrefix() {
        node.addChild('l');
        node.getChild('l').addChild('a');
        node.addChild('b');
        node.getChild('l').addChild('e');
        assertFalse(node.isLastPrefix());
        assertTrue(node.getChild('l').isLastPrefix());
    }

    @Test
    public void has_Children_should_returnFalse_when_childrenIsEmpty() {
        assertFalse(node.hasChildren());
    }

    @Test
    public void has_Children_should_returnTrue_when_childrenIsNotEmpty() {
        Character childLetter = 'c';
        node.addChild(childLetter);
        assertTrue(node.hasChildren());
    }

    @Test
    public void getChild_should_returnNull_when_nodeDoesNotHaveChildWithGivenLetter() {
        Character nonExistingChildLetter = 'x';
        assertEquals(null, node.getChild(nonExistingChildLetter));
    }

    @Test
    public void getChild_should_returnChildNode_when_nodeHasChildWithGivenLetter() {
        Character childLetter = 'b';
        TrieNode expectedChild = new TrieNode(childLetter);
        node.addChild(childLetter);
        assertEquals(expectedChild, node.getChild(childLetter));
    }

    @Test
    public void containsPrefix_should_returnFalse_when_nodesChildrenArentPrefixes() {
        node.addChild('l');
        node.getChild('l').addChild('a');
        assertFalse(node.containsPrefix());
    }

    @Test
    public void containsPrefix_should_returnTrue_when_anyNodesChildIsPrefix() {
        node.addChild('l');
        node.getChild('l').addChild('a');
        node.getChild('l').addChild('e');
        assertTrue(node.containsPrefix());
    }
}