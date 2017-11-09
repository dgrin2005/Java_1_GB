/**
 * Java 1. Lesson 5. TicTacToe.
 *
 *  ��������-������ � �������������� ���
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-09
 */

import java.util.*;

public class Lesson5_TicTacToe {


    public static void main(String[] args) {

        int size = 0;
        int dots_to_win = 0;
        boolean moveFirstAI = true;
        boolean demo_mode = false;
        boolean continueGame;

        do {
            size = askIntGreater0("������� ������ �������� ����:");
            dots_to_win = askIntGreater0("������� ���������� ����� ��� ������ :");
            demo_mode = askYesNo("�������� ����� ? (Human vs. AI - 0, AI vs. AI - 1)");
            if (!demo_mode) {
                moveFirstAI = askYesNo("��� ����� ������? (�� - 0, AI - 1)");
            }
            TicTacToe game = new TicTacToe(size, dots_to_win, demo_mode, moveFirstAI);
            game.play();
            continueGame = askYesNo("������ ������? (�� - 1, ��� - 0)");
        } while (continueGame);


    }

    public static boolean askYesNo(String question) {
        Scanner sc = new Scanner(System.in);
        boolean returnAnswer;
        String answer;
        do {
            System.out.println(question);
            answer = sc.next();
        } while(!(answer.equals("0") || answer.equals("1")));
        returnAnswer = answer.equals("1");
        return returnAnswer;
    }

    public static int askIntGreater0(String question) {
        Scanner sc = new Scanner(System.in);
        int answer = 0;
        boolean enter;
        do {
            try {
                System.out.println(question);
                answer = sc.nextInt();
                enter = false;
            } catch (InputMismatchException ex) {
                System.out.println("������ �����");
                enter = true;
                sc.next();
            }
        } while(enter || answer <= 0);
        return answer;
    }
}


class TicTacToe {

    private int size;
    private int dots_to_win;
    private final char DOT_EMPTY = '.';
    private final char DOT_X = 'x';
    private final char DOT_O = 'o';
    private boolean demo_mode;
    private boolean moveFirstAI;

    private char[][] map;

    TicTacToe(int size, int dots_to_win, boolean demo_mode, boolean moveFirstAI) {
        this.size = size;
        this.dots_to_win = dots_to_win;
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            map[i][i] = DOT_EMPTY;
            for (int j = 0; j < i; j++) {
                map[i][j] = DOT_EMPTY;
                map[j][i] = DOT_EMPTY;
            }
        }
        this.demo_mode = demo_mode;
        this.moveFirstAI = moveFirstAI;
        System.out.println("��������-������ �� ���� " + size + "x" + size);
        System.out.println("����� ��� ������ " + dots_to_win);
        if (demo_mode) {
            System.out.println("����� AI vs. AI");
        } else {
            System.out.println("����� Human vs. AI");
            System.out.println("������ ��� " + ((moveFirstAI) ? "AI" : "���"));
        }
        System.out.println();
    }

    private void printMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    private boolean AITurn(char dotType) {

        int[] field = {0 ,0};
        int i, j;
        char dotTypeAnother = (dotType == DOT_O ? DOT_X : DOT_O);
        /* ��������� ����� �� �������� ����� ��������� �����;
           ���� ������� ����� ������, �� ��������� ����� ������ ���� �����
        */
        if (!checkWinNextStep(dotType, field)) {
            /* ��������� ����� �� �������� ��������� ����� ���������;
               ���� ������� ����� ������, �� ��������� ����� ������ ���� �����
            */
            if (!checkWinNextStep(dotTypeAnother, field)) {
                /* ������ ����� � ������������ ������ ������
                */
                findNextStep(field);
            }
        }
        map[field[1]][field[0]] = dotType;
        System.out.println("��� AI (" + dotType + "): " + (field[0] + 1) + ", " + (field[1] + 1));
        if (checkWin(dotType)) {
            System.out.println("AI (" + dotType + ") �������!");
            return false;
        } else if (isMapFull()) {
            System.out.println("�����!");
            return false;
        } else return true;
    }

    private boolean checkWinNextStep(char dot, int[] field) {
        int i, j;
        boolean findCell = false;
        i = 0;
        while (i < size && !findCell) {
            j = 0;
            while (j < size && !findCell) {
                if (map[i][j] == DOT_EMPTY) {
                    map[i][j] = dot;
                    findCell = checkWin(dot);
                    if (findCell) {
                        field[0] = j; //x
                        field[1] = i; //y
                    }
                    map[i][j] = DOT_EMPTY;
                }
                j++;
            }
            i++;
        }
        return findCell;
    }

    private void findNextStep(int[] field) {
        Random rand = new Random();
        /* ������ ����� � ������������ ������ ������
        */
        do {
            field[0] = rand.nextInt(size);
            field[1] = rand.nextInt(size);
        } while (!isCellValid(field[0], field[1]));
    }

    private boolean humanTurn(char dotType) {
        int x = 0, y = 0;
        Scanner sc = new Scanner(System.in);
        boolean enter;
        do {
            try {
                System.out.println("������� X � Y (1.." + size + "):");
                x = sc.nextInt() - 1;
                y = sc.nextInt() - 1;
                enter = false;
            } catch (InputMismatchException ex) {
                System.out.println("������ �����");
                enter = true;
                sc.next();
            }
        } while (!isCellValid(x, y) || enter);
        map[y][x] = dotType;
        if (checkWin(dotType)) {
            System.out.println("�� ��������!");
            return false;
        } else if (isMapFull()) {
            System.out.println("�����!");
            return false;
        } else return true;
    }

    private boolean isCellValid(int x, int y) {
        if (x < 0 || y < 0 || x >= size || y >= size)
            return false;
        if (map[y][x] == DOT_EMPTY)
            return true;
        return false;

    }

    private boolean checkWin(char dot) {
        int i, j, k;
        boolean result;
        /* ��������� ��� ��������� ������������ � �������������� "�������" ������ dots_to_win
         */
        for (i = 0; i <= size - dots_to_win; i++) {
            for (j = 0; j < size; j++) {
                result = true;
                k = 0;
                while (k < dots_to_win && result) {
                    result = map[i + k][j] == dot;
                    k++;
                }
                if (result) return true;
                result = true;
                k = 0;
                while (k < dots_to_win && result) {
                    result = map[j][i + k] == dot;
                    k++;
                }
                if (result) return true;
            }
        }
        /* ��������� ��� ��������� ������������ "�������" ������ DOTS_TO_WIN
         */
        for (i = 0; i <= size - dots_to_win; i++) {
            for (j = 0; j <= size - dots_to_win; j++) {
                result = true;
                k = 0;
                while (k < dots_to_win && result) {
                    result = map[i + k][j + k] == dot;
                    k++;
                }
                if (result) return true;
                result = true;
                k = 0;
                while (k < dots_to_win && result) {
                    result = map[i + dots_to_win - k - 1][j + k] == dot;
                    k++;
                }
                if (result) return true;
            }
        }
        return false;
    }

    private boolean isMapFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    void play() {
        boolean needPlay = true;

        if (demo_mode) {
            while (needPlay) {
                needPlay = AITurn(DOT_X);
                printMap();
                if (!needPlay) break;
                needPlay = AITurn(DOT_O);
                printMap();
            }
        } else {
            while (needPlay) {
                needPlay = (moveFirstAI) ? AITurn(DOT_O) : humanTurn(DOT_X);
                printMap();
                if (!needPlay) break;
                needPlay = (moveFirstAI) ? humanTurn(DOT_X) : AITurn(DOT_O);
                printMap();
            }
        }
    }

}