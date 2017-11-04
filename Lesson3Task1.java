/**
 * Java 1. Lesson 3. Task 1.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-02
 */
import java.util.*;

public class Lesson3Task1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean repeat;
        do {
            guess();
            int needRepeat;
            do {
                System.out.println("��������� ���� ��� ���? 1 - �� / 0 - ���");
                needRepeat = sc.nextInt();
            } while (needRepeat > 1 || needRepeat < 0);
            repeat = needRepeat == 1;
        }  while (repeat);
    }

    public static void guess() {
        Scanner sc = new Scanner(System.in);
        int i = 1;
        Random rand = new Random();
        int number = rand.nextInt(10);
        boolean guessed = false;
        do {
            System.out.println("������� �" + i + ". ������� �����: ");
            int guessNumber = sc.nextInt();
            i++;
            guessed = guessNumber == number;
            if (!guessed)
                System.out.println((number < guessNumber) ? "���� ����� ������ �����������." : "���� ����� ������ �����������.");
        } while (i <= 3 && !guessed);
        System.out.println((guessed) ? "������� !!!" : "�� ������� !");
    }

}
