package logic;

import java.io.*;
import java.util.TreeSet;

public class Dictionary implements Serializable {

    private String title;
    private TreeSet<String> words;

    public Dictionary(String title) {
        this.title = title;
        words = new TreeSet<>();
    }

    public void add(String... words) {
        for (String word : words) {
            this.words.add(word.toLowerCase());
        }
    }

    public void setWords(TreeSet<String> words) {
        this.words = words;
    }

    public void saveToBinFile(String outputFile) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream((new FileOutputStream(outputFile)))) {
            outputStream.writeObject(this);
        } catch (FileNotFoundException e1) {
            throw e1;
        } catch (IOException e2) {
            throw e2;
        }
    }

    public static Dictionary createFromBinFile(String inputFilePath) throws ClassNotFoundException, IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(inputFilePath))) {
            Dictionary dictionaryFromFile = (Dictionary) inputStream.readObject();
            return dictionaryFromFile;
        } catch (FileNotFoundException | ClassNotFoundException e1) {
            throw e1;
        } catch (IOException e2) {
            throw e2;
        }
    }

    public TreeSet<String> getWords() {
        return words;
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
