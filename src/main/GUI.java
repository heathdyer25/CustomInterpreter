package main;

import expressions.Expression;
import interpreter.Interpreter;
import lexer.Lexer;
import parser.Parser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static parser.Desugar.desugar;

public class GUI extends JFrame {
    /**
     * Where the code goes
     */
    private final JTextPane codeArea;
    /**
     * Where the output goes
     */
    private final JTextPane outputArea;
    /**
     * Divider
     */
    private final JSplitPane splitPane;
    /**
     * File chooser
     */
    private JFileChooser fileChooser;
    /**
     * Application name
     */
    public static final String APPLICATION_NAME = "CSC417 Language IDE v0.1";
    /**
     * Option for enabling lexical scoping
     */
    JCheckBoxMenuItem lexicalOption;
    /**
     * Option for enabling tracing option
     */
    JCheckBoxMenuItem tracingOption;


    public GUI() {
        //Set up GUI
        setTitle(APPLICATION_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));

        // Set up text areas
        codeArea = new JTextPane();
        codeArea.setBackground(new Color(20, 20, 20));
        codeArea.setForeground(Color.WHITE);
        outputArea = new JTextPane();
        outputArea.setBackground(new Color(20, 20, 20));
        outputArea.setForeground(Color.WHITE);
        outputArea.setEditable(false);

        // Create menu bar
        createMenuBar();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(codeArea),
                new JScrollPane(outputArea));
        splitPane.setResizeWeight(0.5);

        // Set up the run button
        JButton runButton = new JButton("Run");

        // Layout
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
        add(runButton, BorderLayout.SOUTH);


        // Keep divider in the middle on resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(0.5);
            }
        });

        // Add file choose for saving and loading
        addFileChooser();

        // Add action listener for the run button
        runButton.addActionListener(e -> executeCode());

    }

    /**
     * Sets the default directory to the current directory where the code is located
     */
    private void addFileChooser() {
        // File chooser for open/save dialogs
        fileChooser = new JFileChooser();
        // Add file extension filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSC417 Files (*.417)", "417");
        fileChooser.setFileFilter(filter);
        // Get the current working directory (where the Java process was started)
        File currentDirectory = new File(System.getProperty("user.dir"));
        if (currentDirectory.exists() && currentDirectory.isDirectory()) {
            fileChooser.setCurrentDirectory(currentDirectory);
        } else {
            // Fallback to default user home directory if the current directory is invalid
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        }
    }

    /**
     * Create menu bar for GUI
     */
    private void createMenuBar() {
        // Create menu
        JMenuBar menuBar = new JMenuBar();
        //File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        // Options menu

        JMenu optionsMenu = new JMenu("Options");
        lexicalOption = new JCheckBoxMenuItem("Enable Lexical Scoping");
        lexicalOption.setSelected(true);
        tracingOption = new JCheckBoxMenuItem("Enable Tracing");
        optionsMenu.add(lexicalOption);
        optionsMenu.add(tracingOption);

        //Set menu bar
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);


        // Action listeners for menu items
        newItem.addActionListener(e -> newFile());
        openItem.addActionListener(e -> openFile());
        saveItem.addActionListener(e -> saveFile());

    }

    /**
     * Action for creating a new file (clears contents)
     */
    private void newFile() {
        codeArea.setText("");
        outputArea.setText("");
        System.out.println("New file created.");
    }

    /**
     * Action for opening a new file
     */
    private void openFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                codeArea.setText(content);  // Load file content into code editor
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
            }
        }
    }

    /**
     * Action for saving file contents
     */
    private void saveFile() {
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Files.write(file.toPath(), codeArea.getText().getBytes());
                JOptionPane.showMessageDialog(this, "File saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
            }
        }
    }

    /**
     * Executes code in the context of the interpreter
     */
    private void executeCode() {
        try {
            Lexer lexer = new Lexer();
            Parser parser = new Parser();
            Interpreter interpreter = new Interpreter(tracingOption.isSelected(), lexicalOption.isSelected());
            Expression result = interpreter.evaluate(desugar(parser.parse(lexer.lex(codeArea.getText()))), interpreter.getInitialEnv());

            outputArea.setText(result.toString());
            outputArea.setForeground(Color.WHITE);
        } catch (Exception e) {
            outputArea.setText(e.getMessage());
            outputArea.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI ide = new GUI();
            ide.setVisible(true);
        });
    }
}
