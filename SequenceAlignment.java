package sequencealignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Implement the dynamic programming solution for sequence alignment.  
 * Clearly comment your code.  Make it easy to modify the formula for 
 * edit distance by simply changing the parameters  and .  
 * Load your program with a dictionary of at least 1000 English words. 
 * Prompt the user for input and then output the word in the dictionary 
 * that is closest (according to the edit distance) to the user’s input
 * @author Danny Bronshtein
 */
public class SequenceAlignment {

    /**
     * In main method, first, the program promtes the user with question to input
     * a word, gap penalty and mismatch penalty.
     * Those verables are easy to change.
     * ********************** Variables *******************************
     * userInput: User's input for word input.
     * gapPenalty: User's input for gap penalty.
     * mismatchPenalty: User's input for mismatch penalty.
     * result: Is the final result. The closest word for dictionary to user's input.
     * fileInput: Dictionary with 1000 words. 
     * wordFromDictionary: Reads the word from dictionary. 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sequence Alignment!");
        System.out.println("Please enter your word, the gap penalty and mismatch penalty.");

        String usersInput = sc.next();
        int gaPenalty = sc.nextInt();
        int mismatchPenalty = sc.nextInt();

        String result = "";
        FileInputStream fileInput = new FileInputStream("C:/Users/braunshtaind1/Documents/NetBeansProjects/SequenceAlignment/src/sequencealignment/dictionary.txt");
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fileInput));

        String wordFromDictionary = "";

        int min = Integer.MAX_VALUE;

        while((wordFromDictionary = bufferReader.readLine()) != null)
        {
            int temp = Sequence_Alignment(usersInput, wordFromDictionary, gaPenalty, mismatchPenalty);
            if(temp < min) 
            {
                min = temp;
                result = wordFromDictionary;
            }
        }

        fileInput.close();
        sc.close();
        System.out.println("Closest word to \"" + usersInput + "\": " + result);
        System.out.println("The minimum cost of the alignment is " + min);
    }
    /**
     * This method, will compare every word from dictionary to user's input.
     * Will calculate the mismatch penalty.
     * @param usersInput
     * @param closestWord
     * @param gaPenalty
     * @param mismatchPenalty
     * rows: Are the rows of arrayStrings. The length is the length of user's input.
     * columns: Are the columns of arrayStrings. The length is the length of closest word.
     * @return arrayStrings: two dimensional array.
     */
    public static int Sequence_Alignment(String usersInput, String closestWord, int gaPenalty, int mismatchPenalty)
    {
        int rows =  usersInput.length();
        int columns = closestWord.length();
        int [][] arrayStrings = new int[rows + 1][columns + 1];
        //Implement the array with gap penalty
        for (int i = 0; i <= rows; i++)
        {
            arrayStrings[i][0] = i * gaPenalty;
        }
        //Implement the array with gap penalty
        for (int j = 0; j <= columns; j++)
        {
            arrayStrings[0][j] = j * gaPenalty;
        }
        //Here, is were the dynamic programming occurs.
        //For each rows, it will go through each columns and check for minimum penalty.
        //Calculating the minimum penalty.
        for (int i = 1; i <= rows; i++)
        {
            for (int j = 1; j <= columns; j++)
            {
                if (usersInput.charAt(i - 1) != closestWord.charAt(j - 1))
                {
                    int temPenalty = Math.min(mismatchPenalty + arrayStrings[i - 1][j - 1], gaPenalty + arrayStrings[i - 1][j]);
                    arrayStrings[i][j] = Math.min(temPenalty, gaPenalty + arrayStrings[i][j - 1]);
                }
                else
                {
                    int tempPenelty = Math.min(arrayStrings[i - 1][j - 1], gaPenalty + arrayStrings[i - 1][j]);
                    arrayStrings[i][j] = Math.min(tempPenelty, gaPenalty + arrayStrings[i][j - 1]);
                }
            }
        }
        //Returns two dimensional array. 
        return arrayStrings[rows][columns];
    }
}
