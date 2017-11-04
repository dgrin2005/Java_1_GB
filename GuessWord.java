/**
 * Java 1. Lesson 8. Guess Word
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-21
 */

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuessWord extends JFrame{

    private final String[] WORDS = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot",
            "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive",
            "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
    private String word = "";
    private final int FIELD_SIZE_X = 700; // размер поля
    private final int FIELD_SIZE_Y = 130; // размер поля
    private int attempt = 0;
    private final int LENGTH = 15;
    private boolean guessed = true;
    private JButton[] jbs;
    private JLabel attempts;
    private JPanel field;
    private JPanel buttons;
    private JPanel guess;

    public static void main(String[] args) {
        GuessWord gw = new GuessWord();
    }

    GuessWord() {

        setTitle("Guess the word");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(FIELD_SIZE_X, FIELD_SIZE_Y);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        field = new JPanel();
        jbs = new JButton[LENGTH];
        for (int j = 0; j < LENGTH; j++) {
            jbs[j] = new JButton();
            field.add(jbs[j], BorderLayout.CENTER);
        }
        add(field, BorderLayout.CENTER);

        buttons = new JPanel();
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guessed = false;
                attempt = 1;
                attempts.setText("Attempt N: " + attempt + "     Guess the word :");
                for (int j = 0; j < LENGTH; j++) {
                    jbs[j].setText("#");
                    jbs[j].setBackground(Color.ORANGE);
                }
                Random rand = new Random();
                int i = rand.nextInt(25);
                word = WORDS[i];
            }
        });
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttons.setLayout(new GridLayout());
        buttons.add(startButton);
        buttons.add(quitButton);
        add(buttons, BorderLayout.NORTH);

        guess = new JPanel();
        JTextField guessWord = new JTextField(15);
        guessWord.setToolTipText("Guess the word");
        guessWord.setText("");
        attempts = new JLabel("Attempt N: " + attempt + "     Guess the word :");
        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!guessed) {
                    String gWord = guessWord.getText();
                    String hintWord = "";
                    guessed = gWord.equals(word);
                    if (!guessed) {
                        int wordLength = word.length() < gWord.length() ? word.length() : gWord.length();
                        for (int k = 0; k < wordLength; k++) {
                            if (gWord.charAt(k) == word.charAt(k)) hintWord = hintWord + word.charAt(k);
                            else hintWord = hintWord + "#";
                        }
                        while (hintWord.length() < 15) {
                            hintWord = hintWord + "#";
                        }
                        for (int j = 0; j < LENGTH; j++) {
                            if (hintWord.charAt(j) == '#') jbs[j].setBackground(Color.ORANGE);
                            else jbs[j].setBackground(Color.GREEN);
                            jbs[j].setText("" + hintWord.charAt(j));
                        }
                        attempt++;
                        attempts.setText("Attempt N: " + attempt + "     Guess the word :");
                    } else {
                        int wordLength = word.length();
                        for (int k = 0; k < wordLength; k++) {
                            hintWord = hintWord + word.charAt(k);
                        }
                        while (hintWord.length() < 15) {
                            hintWord = hintWord + "!";
                        }
                        for (int j = 0; j < LENGTH; j++) {
                            jbs[j].setBackground(Color.GREEN);
                            jbs[j].setText("" + hintWord.charAt(j));
                        }
                        JOptionPane.showMessageDialog(null, "Guessed !", "End game", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else JOptionPane.showMessageDialog(null, "Press Start button to begin !", "Begin game", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        guess.add(attempts);
        guess.add(guessWord);
        guess.add(buttonOK);
        add(guess, BorderLayout.SOUTH);
        setVisible(true);
    }
}
