/**
 * Java 1. Lesson 3. Task 2.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-02
 */
import java.util.*;

public class Lesson3Task2 {
    public static void main(String[] args) {
        guess();
    }

    public static void guess() {
        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot",
                "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive",
                "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        int i = rand.nextInt(25);
        int j = 1;
        String word = words[i];
        boolean guessed = false;
        do {
            System.out.println("Попытка №" + j + ". Введите слово: ");
            String guessWord = sc.next();
            String hintWord = "";
            guessed = guessWord.equals(word);
            if (!guessed) {
                int wordLength = word.length() < guessWord.length() ? word.length() : guessWord.length();
                for (int k = 0; k < wordLength; k++) {
                    if (guessWord.charAt(k) == word.charAt(k)) hintWord = hintWord + word.charAt(k);
                    else hintWord = hintWord + "#";
                }
                while (hintWord.length()<15) {
                    hintWord = hintWord + "#";
                }
                System.out.println("Не угадали: " + hintWord);
            }
            j++;
        } while (!guessed);
        System.out.println("Угадали !!!");
    }

}
