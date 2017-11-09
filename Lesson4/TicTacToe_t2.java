/**
 * Java 1. Lesson 4. TicTacToe. Task 2.
 *
 *  переделана проверка победы для поля 3х3
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-07
 */

import java.util.*;

public class TicTacToe_t2 {

    final int SIZE = 3;
    final int DOTS_TO_WIN = 3;
    final char DOT_EMPTY = '.';
    final char DOT_X = 'x';
    final char DOT_O = 'o';
    char[][] map = new char[SIZE][SIZE];
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    public static void main(String[] args) {
        new TicTacToe_t2();
    }

    TicTacToe_t2() {
        initMap();
        boolean play = true;
        boolean moveFirstAI;
        String firstMove;
        do {
            System.out.println("Кто ходит первый? (Вы - 1, AI - 0)");
            firstMove = sc.next();
        } while(!(firstMove.equals("0") || firstMove.equals("1")));
        moveFirstAI = firstMove.equals("0");
        while (play) {
            if (moveFirstAI) play = AITurn();
            else play = humanTurn();
            printMap();
            if (!play) break;
            if (moveFirstAI) play = humanTurn();
            else play = AITurn();
            printMap();
        }
    }

    void initMap() {
        for (int i = 0; i < SIZE; i++) {
            map[i][i] = DOT_EMPTY;
            for (int j = 0; j < i; j++) {
                map[i][j] = DOT_EMPTY;
                map[j][i] = DOT_EMPTY;
            }
        }
        System.out.println("Крестики-нолики на поле " + SIZE + "x" + SIZE);
        System.out.println("Фишек для побелы " + DOTS_TO_WIN);
    }

    void printMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    boolean AITurn() {
        int x = 0, y = 0;
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
           } while (!isCellValid(x, y));
        map[y][x] = DOT_O;
        System.out.println("Ход AI: " + (x + 1) + ", " + (y + 1));
        if (checkWin(DOT_O)) {
            System.out.println("AI победил!");
            return false;
        } else if (isMapFull()) {
            System.out.println("Ничья!");
            return false;
        } else return true;
    }

    boolean humanTurn() {
        int x, y;
        do {
            System.out.println("Введите X и Y (1.." + SIZE + "):");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = DOT_X;
        if (checkWin(DOT_X)) {
            System.out.println("Вы победили!");
            return false;
        } else if (isMapFull()) {
            System.out.println("Ничья!");
            return false;
        } else return true;
    }

    boolean isCellValid(int x, int y) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE)
            return false;
        if (map[y][x] == DOT_EMPTY)
            return true;
        return false;

    }

    boolean checkWin(char dot) {
        int i, j;
        boolean res1, res2, res3 = true, res4 = true;
        for (i = 0; i < SIZE; i++) {
            res3 = map[i][i] == dot && res3;
            res4 = map[SIZE - i - 1][i] == dot && res3;
            res1 = true;
            res2 = true;
            for (j = 0; j < SIZE; j++) {
                res1 = map[i][j] == dot && res1;
                res2 = map[j][i] == dot && res2;
            }
            if (res1 || res2) return true;
        }
        return res3 || res4;
    }

    boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

}

