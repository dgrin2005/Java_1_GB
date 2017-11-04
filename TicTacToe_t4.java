/**
 * Java 1. Lesson 4. TicTacToe. Task 4.
 *
 *  �������� ������ ��� ����������� ���� ������������� ������� � ������������ ����� ������������������,
 *  ������� AI
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-07
 */

import java.util.*;

public class TicTacToe_t4 {

    final int SIZE = 5;
    final int DOTS_TO_WIN = 4;
    final char DOT_EMPTY = '.';
    final char DOT_X = 'x';
    final char DOT_O = 'o';
    char[][] map = new char[SIZE][SIZE];
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    public static void main(String[] args) {
        new TicTacToe_t4();
    }

    TicTacToe_t4() {
        initMap();
        boolean play = true;
        boolean moveFirstAI;
        String firstMove;
        do {
            System.out.println("��� ����� ������? (�� - 1, AI - 0)");
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
        System.out.println("��������-������ �� ���� " + SIZE + "x" + SIZE);
        System.out.println("����� ��� ������ " + DOTS_TO_WIN);
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
        int i, j;
        char cell;
        boolean findCell = false;
        /* ��������� ����� �� �������� ��������� ����� AI;
           ���� ������� ����� ������, �� ��������� ����� ������ ���� �
        */
        i = 0;
        while (i < SIZE && !findCell) {
            j = 0;
            while (j < SIZE && !findCell) {
                cell = map[i][j];
                if (cell == DOT_EMPTY) {
                    map[i][j] = DOT_O;
                    findCell = checkWin(DOT_O);
                    if (findCell) {
                        x = j;
                        y = i;
                    }
                    map[i][j] = cell;
                }
                j++;
            }
            i++;
        }
        if (!findCell) {
            /* ��������� ����� �� �������� ��������� ����� �������;
               ���� ������� ����� ������, �� ��������� ����� ������ ���� �
            */
            i = 0;
            while (i < SIZE && !findCell) {
                j = 0;
                while (j < SIZE && !findCell) {
                    cell = map[i][j];
                    if (cell == DOT_EMPTY) {
                        map[i][j] = DOT_X;
                        findCell = checkWin(DOT_X);
                        if (findCell) {
                            x = j;
                            y = i;
                        }
                        map[i][j] = cell;
                    }
                    j++;
                }
                i++;
            }
            if (!findCell) {
                /* ���������, ���� �� ��� ������ � ����� ������������� � ��� �;
                   ���� ������� ����� ������, �� ��������� ����� ������ � � �������� ������
                */
                i = 0;
                while (i < SIZE && !findCell) {
                    j = 0;
                    while (j < SIZE && !findCell) {
                        if (map[i][j] != DOT_EMPTY) {
                            int i1 = i - 1;
                            while (i1 <= i + 1 && !findCell) {
                                int j1 = j - 1;
                                while (j1 <= j + 1 && !findCell) {
                                    if (i1 > 0 && i1 < SIZE && j1 > 0 && j1 < SIZE) {
                                        cell = map[i1][j1];
                                        if (cell == DOT_EMPTY) {
                                            findCell = true;
                                            if (findCell) {
                                                x = j1;
                                                y = i1;
                                            }
                                        }
                                    }
                                    j1++;
                                }
                                i1++;
                            }
                        }
                        j++;
                    }
                    i++;
                }
                if (!findCell) {
                    /* AI ����� ������, �.�. ����� � �������������� � ��� � ��� ���; �������� ��������� � � �������� ����
                    */
                    i = SIZE / 2;
                    j = SIZE / 2;
                    cell = map[i][j];
                    if (cell == DOT_EMPTY) {
                        findCell = true;
                        if (findCell) {
                            x = j;
                            y = i;
                        }
                    }
                    if (!findCell) {
                    /* ������ � � ������������ ������ ������
                    */
                        do {
                            x = rand.nextInt(SIZE);
                            y = rand.nextInt(SIZE);
                        } while (!isCellValid(x, y));
                    }
                }
            }
        }
        map[y][x] = DOT_O;
        System.out.println("��� AI: " + (x + 1) + ", " + (y + 1));
        if (checkWin(DOT_O)) {
            System.out.println("AI �������!");
            return false;
        } else if (isMapFull()) {
            System.out.println("�����!");
            return false;
        } else return true;
    }

    boolean humanTurn() {
        int x, y;
        do {
            System.out.println("������� X � Y (1.." + SIZE + "):");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = DOT_X;
        if (checkWin(DOT_X)) {
            System.out.println("�� ��������!");
            return false;
        } else if (isMapFull()) {
            System.out.println("�����!");
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
        int i, j, k;
        boolean result;
        /* ��������� ��� ��������� ������������ � �������������� "�������" ������ DOTS_TO_WIN
         */
        for (i = 0; i <= SIZE - DOTS_TO_WIN; i++) {
            for (j = 0; j < SIZE; j++) {
                result = true;
                k = 0;
                while (k < DOTS_TO_WIN && result) {
                    result = map[i + k][j] == dot;
                    k++;
                }
                if (result) return true;
                result = true;
                k = 0;
                while (k < DOTS_TO_WIN && result) {
                    result = map[j][i + k] == dot;
                    k++;
                }
                if (result) return true;
            }
        }
        /* ��������� ��� ��������� ������������ "�������" ������ DOTS_TO_WIN
         */
        for (i = 0; i <= SIZE - DOTS_TO_WIN; i++) {
            for (j = 0; j <= SIZE - DOTS_TO_WIN; j++) {
                result = true;
                k = 0;
                while (k < DOTS_TO_WIN && result) {
                    result = map[i + k][j + k] == dot;
                    k++;
                }
                if (result) return true;
                result = true;
                k = 0;
                while (k < DOTS_TO_WIN && result) {
                    result = map[i + DOTS_TO_WIN - k - 1][j + k] == dot;
                    k++;
                }
                if (result) return true;
            }
        }
        return false;
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

