
/**
 * Srv.java - Goes through each method until all text is deciphered
 *
 * @author Vance Field
 * @date 8-Oct-2015
 */
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Srv {

    private final String INVALID = "";
    private String line = "";
    private String word = "";
    private String decipheredText = "";
    private String decipheredLine = "";
    private int length = 0;
    private int lineCount = 0;
    private int lineWordCount = 0;
    private int wordCount;
    private int currentPos;
    private int key;
    private String[] a;

    /**
     * Parses lines from the file and passes it to parseWord()
     *
     * <p>Sets up array of each word from each line, in order to find the keyword easily
     * </p>
     *
     * @param s String of the file name selected
     * @throws IOException
     */
    void parseLine(String s) throws IOException {
        FileReader file = new FileReader(s);
        Scanner scan = new Scanner(file);
        while (scan.hasNext()) {                // while the file has more info
            line = scan.nextLine();
            wordCount = wordCount(line);        // counts # of words in line
            setStringArray(line);               // initializes array of each word in line
            parseWord(line);
            lineCount++;
        }
    }

    /**
     * Counts the number of words in each line
     *
     * @param line String of the current line
     * @return The word count of the current line
     */
    int wordCount(String line) {
        lineWordCount = 0;
        Scanner scn = new Scanner(line);
        scn.useDelimiter("\\s+");
        while (scn.hasNext()) {
            scn.next();                         // moves to next word
            lineWordCount++;                    // counts number of words in line
        }
        return lineWordCount;
    }

    /**
     * Initializes a[wordCount] containing each word of the current line
     *
     * @param line String of the current line
     */
    void setStringArray(String line) {
        a = new String[wordCount];
        int i = 0;
        Scanner arrayScan = new Scanner(line);
        arrayScan.useDelimiter("\\s+");
        while (arrayScan.hasNext()) {
            a[i] = arrayScan.next();
            i++;
        }
    }

    /**
     * Parses each word from the current line
     *
     * <p>
     * Sends each word to isPalindrome() to check word is a palindrome. If word
     * is a palindrome, findKeyword() is given the key. Keeps track of the
     * current position (word) of each line
     * </p>
     *
     * @param line String of the current line
     */
    void parseWord(String line) {
        currentPos = 0;                         // resets for a new line
        Scanner scanWord = new Scanner(line);   // scanWord scans the line
        scanWord.useDelimiter("\\s+");          // sets delimiter to white_space
        while (scanWord.hasNext()) {            // while the line has more info
            word = scanWord.next();                 // take the current word
            if (isPalindrome(word)) {               // if word is palindrome
                length = word.length();             // get palindrome's length
                findKeyword(line, length);          // pass line and length of palindrome to findKeyword
            }
            currentPos++;                       // increments the array index
        }
        updateDecipheredText(decipheredLine);           // will run everytime a line is done being parsed
        decipheredLine = "";
    }

    /**
     * Checks if each word is a palindrome
     *
     * @param s String of the current word
     * @return Whether the word is a palindrome or not
     */
    boolean isPalindrome(String s) {
        Stack stack = new Stack();
        Queue queue = new LinkedList();
        boolean palindrome = true;

        for (int i = 0; i < s.length(); i++) {
            stack.push(s.charAt(i));               // Sets stack with user input
            queue.add(s.charAt(i));                // Sets queue with user input
        }

        while (palindrome != false && !stack.isEmpty()) {
            if (stack.pop() != queue.remove()) {
                palindrome = false;
            }
        }
        return palindrome;
    }

    /**
     * Finds the keyword by indexing through the array of the current line
     *
     * @param line String of the current line
     * @param length Integer length of the palindrome
     */
    void findKeyword(String line, int length) {
        key = currentPos + length;
        try {
            decipheredLine += (a[key] + " ");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid key. Withholding deciphered text from line " + lineCount + ".");
            decipheredLine = INVALID;
        }
    }

    /**
     * Adds the deciphered line to the output String
     *
     * @param decipheredLine String of the deciphered line
     */
    void updateDecipheredText(String decipheredLine) {
        decipheredText += decipheredLine;
    }

    /**
     * Returns a place holder of decipheredText in order to reset decipheredText
     *
     * @return String of the deciphered text
     */
    String getDecipheredText() {
        String s = decipheredText;
        decipheredText = "";
        return s;
    }
}
