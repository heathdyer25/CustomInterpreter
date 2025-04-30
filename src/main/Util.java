package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utilities for our application.
 *
 * @author Heath Dyer
 */
public abstract class Util {
    /**
     * Max character length of identifiers in our language
     */
    public static final int MAX_IDLEN = 60;
    /**
     * Max character length of
     */
    public static final int MAX_INTLEN = 20;
    /**
     * Max character length of strings in our language
     */
    public static final int MAX_STRLEN = 1000;

    /**
     * Prints message to standard error and returns program with failing status.
     *
     * @param msg Message to print to standard error
     */
    public static void fail(String msg) {
        System.err.println(msg);
        System.exit(1);
    }

    /**
     * Reads all input in standard input and returns the string
     *
     * @return Returns string of all input from standard in
     * @throws IOException Failure while reading standard input
     */
    public static String getInput() throws IOException {
        //Get all input from standard in
        BufferedReader inputReader = null;
        StringBuilder input = new StringBuilder();
        try {
            inputReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = inputReader.readLine()) != null) {
                input.append(line);
            }
        }
        //Catch IO errors
        catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        //Close input stream if not closed
        finally {
            if (inputReader != null) {
                inputReader.close();
            }
        }
        return input.toString();
    }

    /**
     * Reads all input from a file
     *
     * @param filename Filename to read from
     * @return Returns file contents as string
     * @throws IOException Throws exception if cannot read file
     */
    public static String getInputFromFile(String filename) throws IOException {
        FileReader reader = null;
        StringBuilder input = new StringBuilder();
        try {
            reader = new FileReader(filename);
            int c;
            while ((c = reader.read()) != -1) {
                input.append((char) c);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return input.toString();
    }
}
