/**
 * Java 1. Lesson 6. TicTacToe.
 *
 *  Крестики-нолики с использованием ООП (вариант 2)
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-09
 */

import java.util.*;

public class Lesson6_TicTacToe {

    private int size = 0;
    private int dots_to_win = 0;
    private boolean moveFirstAI = true;
    private boolean demo_mode = false;
    private boolean continueGame;
    
    private Map map;
    private Player player1;
    private Player player2;

    public static void main(String[] args) {

        Lesson6_TicTacToe tictactoe = new Lesson6_TicTacToe();

    }

    Lesson6_TicTacToe() {
        do {
            size = askIntGreater0("Введите размер игрового поля:");
            dots_to_win = askIntGreater0("Введите количество фишек для победы :");
            map = new Map(size, dots_to_win);
            demo_mode = askYesNo("Выберите режим ? (Human vs. AI - 0, AI vs. AI - 1)");
            if (!demo_mode) {
                moveFirstAI = askYesNo("Кто ходит первый? (Вы - 0, AI - 1)");
                System.out.println(map);
                player1 = (!moveFirstAI) ? new Human(map.getDOT_X()) : new AI(map.getDOT_O());
                player2 = (moveFirstAI) ? new Human(map.getDOT_X()) : new AI(map.getDOT_O());
            } else {
                player1 = new AI(map.getDOT_X());
                player2 = new AI(map.getDOT_O());
            }
            play();
            continueGame = askYesNo("Начать заново? (Да - 1, Нет - 0)");
        } while (continueGame);
    }

    private void play() {
        boolean needPlay = true;
        while (needPlay) {
            needPlay = player1.turn(map);
            System.out.println(map);
            if (!needPlay) break;
            needPlay = player2.turn(map);
            System.out.println(map);
        }
    }

    private static boolean askYesNo(String question) {
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

    private static int askIntGreater0(String question) {
        Scanner sc = new Scanner(System.in);
        int answer = 0;
        boolean enter;
        do {
            try {
                System.out.println(question);
                answer = sc.nextInt();
                enter = false;
            } catch (InputMismatchException ex) {
                System.out.println("Ошибка ввода");
                enter = true;
                sc.next();
            }
        } while(enter || answer <= 0);
        return answer;
    }
}

class Map {
    private int size;
    private int dots_to_win;
    private char[][] map;
    private final char DOT_EMPTY = '.';
    private final char DOT_X = 'x';
    private final char DOT_O = 'o';

    Map(int size, int dots_to_win) {
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
    }

    int getSize() {
        return size;
    }
    
    void setDot(int x, int y, char dotType) {
        map[y][x] = dotType;
    }
    
    char getDot(int x, int y) {
        return map[y][x];
    }
    
    char getDOT_EMPTY() {
        return DOT_EMPTY;
    }
    
    char getDOT_X() {
        return DOT_X;
    }
    
    char getDOT_O() {
        return DOT_O;
    }

    @Override
    public String toString() {
        String result = "  ";
        for (int i = 0; i < size; i++)  result += (i + 1) + " ";
        result += "\n";
        for (int i = 0; i < size; i++) {
            result += (i + 1) + " ";
            for (int j = 0; j < size; j++)
                result += map[i][j] + " ";
            result += "\n";
        }
        return result;
    }

    boolean isMapFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }
    
    boolean isCellValid(int x, int y) {
        if (x < 0 || y < 0 || x >= size || y >= size)
            return false;
        if (map[y][x] == DOT_EMPTY)
            return true;
        return false;

    }
    
    boolean checkWin(char dot) {
        int i, j, k;
        boolean result;
        /* проверяем все возможные вертикальные и горизонатльные "полоски" длиной dots_to_win
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
        /* проверяем все возможные диагональные "полоски" длиной dots_to_win
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
}

class Player {
    private char dotType;

    Player(char dotType) {
        this.dotType = dotType;
    }

    boolean turn(Map map) {
        return false; 
    }
    
    char getDotType() {
        return dotType;
    }
}


class Human extends Player {
    
    Human(char dotType) {
        super(dotType);
    }

    @Override
    boolean turn(Map map) {
        int x = 0;
        int y = 0;
        boolean enter;
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("Введите X и Y (1.." + map.getSize() + "):");
                x = sc.nextInt() - 1;
                y = sc.nextInt() - 1;
                enter = false;
            } catch (InputMismatchException ex) {
                System.out.println("Ошибка ввода");
                enter = true;
                sc.next();
            }
        } while (!map.isCellValid(x, y) || enter);
        map.setDot(x, y, getDotType());
        if (map.checkWin(getDotType())) {
            System.out.println("Вы победили!");
            return false;
        } else if (map.isMapFull()) {
            System.out.println("Ничья!");
            return false;
        } else return true; 
    }

}


class AI extends Player {
    
    AI(char dotType) {
        super(dotType);
    }

    @Override
    boolean turn(Map map) {
        int[] field = {0 ,0};
        char dotTypeAnother = (getDotType() == map.getDOT_O() ? map.getDOT_X() : map.getDOT_O());
        /* проверяем может ли выиграть игрок сдедующим ходом;
           если находим такую ячейку, то очередным ходом ставим туда фишку
        */
        if (!checkWinNextStep(map, getDotType(), field)) {
            /* проверяем может ли выиграть сдедующим ходом противник;
               если находим такую ячейку, то очередным ходом ставим туда фишку
            */
            if (!checkWinNextStep(map, dotTypeAnother, field)) {
                /* ставим фишку в произвольную пустую ячейку
                */
                findNextStep(map, field);
            }
        }
        map.setDot(field[0], field[1], getDotType());
        System.out.println("Ход AI (" + getDotType() + "): " + (field[0] + 1) + ", " + (field[1] + 1));
        if (map.checkWin(getDotType())) {
            System.out.println("AI (" + getDotType() + ") победил!");
            return false;
        } else if (map.isMapFull()) {
            System.out.println("Ничья!");
            return false;
        } else return true; 
    }
    
    private boolean checkWinNextStep(Map map, char dot, int[] field) {
        int i, j;
        boolean findCell = false;
        i = 0;
        while (i < map.getSize() && !findCell) {
            j = 0;
            while (j < map.getSize() && !findCell) {
                if (map.getDot(j, i) == map.getDOT_EMPTY()) {
                    map.setDot(j, i, dot);
                    findCell = map.checkWin(dot);
                    if (findCell) {
                        field[0] = j; //x
                        field[1] = i; //y
                    }
                    map.setDot(j, i, map.getDOT_EMPTY());
                }
                j++;
            }
            i++;
        }
        return findCell;
    }

    private void findNextStep(Map map, int[] field) {
        Random rand = new Random();
        /* ставим фишку в произвольную пустую ячейку
        */
        do {
            field[0] = rand.nextInt(map.getSize());
            field[1] = rand.nextInt(map.getSize());
        } while (!map.isCellValid(field[0], field[1]));
    }

}