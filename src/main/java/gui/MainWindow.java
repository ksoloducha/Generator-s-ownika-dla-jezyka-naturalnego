package gui;

import logic.Dictionary;
import logic.EmptyFileException;
import logic.FileExistsException;
import logic.FileManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainWindow extends JFrame implements ActionListener, KeyListener {

    private Dictionary dictionary;
    private FileManager fileManager = new FileManager();
    private boolean dictionaryCreated = false;
    private boolean wordEntered = false;
    private JButton enterNewTextFile, enterExistingBinaryDictionary, findWord, generateWordList, createBinFile, help;
    private JLabel dictionaryCreatedLabel;
    private JTextField searchedWord;
    private JPanel mainPanel, loadingPanel, workingPanel, helpPanel, textPanel, wordPanel;
    private JTextArea textFile, wordList;
    private JFileChooser textFileChooser, binaryDictionaryChooser;
    private int width = 600;
    private int height = 800;

    public MainWindow() {
        this.setSize(width, height);
        this.setTitle("Generator słownika dla języka naturalnego");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        placeOnScreen();
        addComponents();
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
    }

    private void placeOnScreen() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int x = (dimension.width / 2) - (this.getWidth() / 2);
        int y = (dimension.height / 2) - (this.getHeight() / 2);
        this.setLocation(x, y);
    }

    private void addComponents() {
        mainPanel = new JPanel(new GridLayout(5, 1));
        loadingPanel = new JPanel(new GridBagLayout());
        workingPanel = new JPanel(new GridBagLayout());
        textPanel = new JPanel(new BorderLayout());
        wordPanel = new JPanel(new BorderLayout());
        helpPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(6, 0, 6, 0);

        enterNewTextFile = new JButton("Wybierz plik tekstowy");
        enterNewTextFile.addActionListener(this);
        enterNewTextFile.setActionCommand("load text file");

        enterExistingBinaryDictionary = new JButton("Wybierz istniejący słownik");
        enterExistingBinaryDictionary.addActionListener(this);
        enterExistingBinaryDictionary.setActionCommand("load binary dictionary");

        dictionaryCreatedLabel = new JLabel("Nie utworzono słownika");
        dictionaryCreatedLabel.setForeground(Color.RED);

        searchedWord = new JTextField(18);
        searchedWord.setToolTipText("Szukane słowo");
        documentListenerCreation(searchedWord);
        searchedWord.addKeyListener(this);

        findWord = new JButton("Szukaj");
        findWord.addActionListener(this);
        findWord.setActionCommand("find word");

        generateWordList = new JButton("Generuj listę słów");
        generateWordList.addActionListener(this);
        generateWordList.setActionCommand("generate word list");

        createBinFile = new JButton("Zapisz słownik");
        createBinFile.addActionListener(this);
        createBinFile.setActionCommand("create bin file");

        textFile = new JTextArea();
        textFile.setLineWrap(true);
        textFile.setWrapStyleWord(true);
        textFile.setEditable(false);
        JScrollPane scrollbarTextFile = new JScrollPane(textFile, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        wordList = new JTextArea();
        wordList.setLineWrap(true);
        wordList.setWrapStyleWord(true);
        wordList.setEditable(false);
        JScrollPane scrollbarWordList = new JScrollPane(wordList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        help = new JButton("Pomoc");
        help.addActionListener(this);
        help.setActionCommand("help");

        constraints.gridx = 0;
        constraints.gridy = 0;
        loadingPanel.add(enterNewTextFile, constraints);
        constraints.gridy = 1;
        loadingPanel.add(enterExistingBinaryDictionary, constraints);
        constraints.gridy = 2;
        loadingPanel.add(dictionaryCreatedLabel, constraints);
        constraints.gridy = 2;
        workingPanel.add(searchedWord, constraints);
        constraints.gridx = 1;
        workingPanel.add(findWord, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        workingPanel.add(generateWordList, constraints);
        constraints.gridy = 4;
        workingPanel.add(createBinFile, constraints);
        textPanel.add(scrollbarTextFile);
        wordPanel.add(scrollbarWordList);
        constraints.gridy = 7;
        helpPanel.add(help, constraints);

        loadingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Wybór pliku wejściowego"));
        workingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Operacje na słownikach"));
        helpPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Instrukcja korzystania z programu"));
        textPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Podgląd pliku tekstowego"));
        wordPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Podgląd listy słów"));

        mainPanel.add(loadingPanel);
        mainPanel.add(workingPanel);
        mainPanel.add(textPanel);
        mainPanel.add(wordPanel);
        mainPanel.add(helpPanel);
        this.add(mainPanel);

        textFileChooser = new JFileChooser();
        textFileChooser.setCurrentDirectory(new File("." + File.separator + "text_files"));
        textFileChooser.setDialogTitle("Wybierz plik tekstowy");
        textFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        binaryDictionaryChooser = new JFileChooser();
        binaryDictionaryChooser.setCurrentDirectory(new File("." + File.separator + "bin_dictionaries"));
        binaryDictionaryChooser.setDialogTitle("Wybierz istniejący słownik");
        binaryDictionaryChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    private void documentListenerCreation(JTextField textFieldToUpdate) {
        textFieldToUpdate.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                if (textFieldToUpdate.getText().equals("")) {
                    wordEntered = false;
                } else {
                    wordEntered = true;
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        switch (command) {
            case "load text file":
                textFile.setText("");
                wordList.setText("");
                int textFileResponse = textFileChooser.showOpenDialog(this);
                if (textFileResponse == JFileChooser.APPROVE_OPTION) {
                    String inputFilePath = textFileChooser.getSelectedFile().getPath();
                    try {
                        dictionary = fileManager.read(inputFilePath);
                        dictionaryCreation(true);
                        BufferedReader reader = new BufferedReader(new FileReader(textFileChooser.getSelectedFile()));
                        String text = null;
                        while ((text = reader.readLine()) != null) {
                            textFile.append(text + "\n");
                        }
                    } catch (EmptyFileException e1) {
                        showErrorDialog("Wybrany plik jest pusty", "Error");
                        dictionaryCreation(false);
                    } catch (IOException e2) {
                        showErrorDialog("Wystąpił błąd podczas otwierania pliku", "Error");
                        dictionaryCreation(false);
                    }
                }
                break;
            case "load binary dictionary":
                textFile.setText("");
                wordList.setText("");
                int loadBinaryDictionaryResponse = binaryDictionaryChooser.showOpenDialog(this);
                if (loadBinaryDictionaryResponse == JFileChooser.APPROVE_OPTION) {
                    String inputFilePath = binaryDictionaryChooser.getSelectedFile().getPath();
                    try {
                        dictionary = Dictionary.createFromBinFile(inputFilePath);
                        dictionaryCreation(true);
                    } catch (FileNotFoundException | ClassNotFoundException e1) {
                        showErrorDialog("Wystąpił błąd podczas otwierania pliku", "Error");
                        dictionaryCreation(false);
                    } catch (IOException e2) {
                        showErrorDialog("Wystąpił błąd podczas otwierania pliku", "Error");
                        dictionaryCreation(false);
                    }
                }
                break;
            case "find word":
                searchWord();
                break;
            case "generate word list":
                if (dictionaryCreated) {
                    String outputFilePath = "word_lists" + File.separator + dictionary.getTitle() + ".txt";
                    try {
                        fileManager.saveWordsToFile(dictionary, outputFilePath);
                        BufferedReader reader = new BufferedReader(new FileReader(outputFilePath));
                        String text = null;
                        while ((text = reader.readLine()) != null) {
                            wordList.append(text + "\n");
                        }
                        showInformationDialog("Lista słów została zapisana w katalogu word_lists", "Lsta słów " + dictionary.getTitle());
                    } catch (FileExistsException e1) {
                        showErrorDialog("Plik o nazwie " + dictionary.getTitle() + ".txt już istnieje", "Error");
                    } catch (IOException e2) {
                        showErrorDialog("Wystąpił błąd podczas tworzenia pliku", "Error");
                    }
                } else {
                    showErrorDialog("Błąd! Brak słownika", "Error");
                }
                break;
            case "create bin file":
                if (dictionaryCreated) {
                    String outputFilePath = "bin_dictionaries" + File.separator + dictionary.getTitle() + ".ser";
                    try {
                        dictionary.saveToBinFile(outputFilePath);
                        showInformationDialog("Słownik został zapisany w katalogu bin_dictionaries", "Binarny słownik " + dictionary.getTitle());
                    } catch (FileExistsException e1) {
                        showErrorDialog("Plik o nazwie " + dictionary.getTitle() + ".ser już istnieje", "Error");
                    } catch (IOException e2) {
                        showErrorDialog("Wystąpił błąd podczas tworzenia pliku", "Error");
                    }
                } else {
                    showErrorDialog("Błąd! Brak słownika", "Error");
                }
                break;
            case "help":
                showHelp();
                break;
        }
    }

    private void dictionaryCreation(boolean success) {
        if (success) {
            dictionaryCreated = true;
            dictionaryCreatedLabel.setText("Słownik \"" + dictionary.getTitle() + "\" utworzony");
            dictionaryCreatedLabel.setForeground(Color.GREEN);
        } else {
            dictionaryCreated = false;
            dictionaryCreatedLabel.setText("Nie udało się utworzyć słownika");
            dictionaryCreatedLabel.setForeground(Color.RED);
        }
    }

    private void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showInformationDialog(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHelp() {
        String message = String.join("\n",
                "Działanie programu polega na generowaniu słownika na podstawie danych wczytanych",
                "z wybranego pliku. Możliwe jest wczytywanie zarówno plików tekstowych, jak i gotowych",
                "słowników wygenerowanych przez program. W tym celu należy odpowiednio kliknąć",
                "przycisk \"Wybierz plik tekstowy\" lub \"Wybierz istniejący słownik\", a następnie",
                "wybrać plik z listy. W przypadku wybrania pliku tekstowego, pojawi się on w oknie",
                "\"Podgląd pliku tekstowego\".\n",
                "Po utworzeniu słownika możliwe są do wykonania poniższe operacje:",
                "  - wyszukiwanie słów,",
                "    Szukane słowo należy wprowadzic do pola tekstowego, a następnie nacisnąć \"Szukaj\".",
                "  - generowanie listy słów,",
                "    W tym celu należy wybrać przycisk \"Generuj listę słów\". Utworzona lista zostanie",
                "    zapisana w pliku o nazwie [tytuł].txt i umieszczona w folderze \"word_lists\". Jej",
                "    podgląd będzie widoczny w polu \"Podgląd listy słów\"",
                "  - zapisanie utworzonego słownika.",
                "    W cely zapisania słownika należy wybrać przycisk \"Zapisz słownik\". Słownik zostanie",
                "    zapisany w pliku [tytuł].ser i umieszczony w folderze \"bin_dictionaries\". Przy",
                "    kolejnym wyborze pliku wejściowego będzie mógł być wczytany do programu.");

        JOptionPane.showMessageDialog(this, message, "Pomoc", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            searchWord();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void searchWord() {
        if (dictionaryCreated && wordEntered) {
            String word = searchedWord.getText();
            int n = word.split(" ").length;
            if (n != 1) {
                showErrorDialog("Należy podać jedno słowo", "Error");
            } else if (dictionary.contains(word)) {
                showInformationDialog("Słownik \"" + dictionary.getTitle() + "\" zawiera słowo " + word, "Wyniki wyszukiwania słowa");
            } else {
                showInformationDialog("Słownik \"" + dictionary.getTitle() + "\" nie zawiera słowa " + word, "Wyniki wyszukiwania słowa");
            }
        } else if (!dictionaryCreated) {
            showErrorDialog("Błąd! Brak słownika", "Error");
        }
    }
}
