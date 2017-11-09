/**
 * Java 1. Lesson 7. TicTacToe.
 *
 *  Крестики-нолики с использованием Swing, предварительный вариант
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-17
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Lesson7_TicTacToe_Swing{

    private int SIZE = 3;
    private final int DOTS_TO_WIN = 3;
    private boolean needPlay = false;

    private Player player1;
    private Player player2;
    private Map map;

    private int jbsLength;
    private JButton[] jbs;
    private JFrame formGame;

    public static void main(String[] args) {
        int mapSize = 0, dotsToWin = 0;

        try {
            if (args.length > 0) {mapSize = Integer.parseInt(args[0]);}
            if (args.length > 1) {dotsToWin = Integer.parseInt(args[1]);}
        }  catch (NumberFormatException ex) {
            // при некорректных параметрах берем значения по умолчанию
        }

        Lesson7_TicTacToe_Swing tictactoe = new Lesson7_TicTacToe_Swing(mapSize, dotsToWin);

    }

    Lesson7_TicTacToe_Swing(int mapSize, int dotsToWin) {

        map = new Map((mapSize > 0) ? mapSize : SIZE, (dotsToWin > 0) ? dotsToWin : DOTS_TO_WIN);
        player1 = new Human(map.getDOT_X());
        player2 = new AI(map.getDOT_O());

        formGame = new JFrame();
        formGame.setTitle("Крестики-нолики");
        formGame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        formGame.setBounds(300, 300, 400, 400);
        formNewGame();

    }

    private void formNewGame() {

        // создаем и заполняем игровое поле
        jbsLength = map.getSize() * map.getSize();
        jbs = new JButton[jbsLength];
        formGame.setLayout(new GridLayout(map.getSize(), map.getSize()));
        for (int i = 0; i < jbsLength; i++) {
            jbs[i] = new JButton("" + map.getDOT_EMPTY());
            formGame.add(jbs[i]);
            JButton jb = jbs[i];
            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (needPlay) {
                        for (int j = 0; j < jbsLength; j++) {
                            if (jbs[j] == e.getSource()) {
                                int x = ((j + 1) % map.getSize() == 0) ? map.getSize() : (j + 1) % map.getSize();
                                int y = j / map.getSize() + 1;
                                needPlay = player1.turn(j, x - 1, y - 1);
                                break;
                            }
                        }
                    }
                }
            });
        }

        // создаем верхнее меню
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Меню");
        JMenuItem newGame = new JMenuItem("Новая игра");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                needPlay = true;
                map.clearMap();
                for (int i = 0; i < jbsLength; i++) {
                    JButton jb = jbs[i];
                    jb.setText("" + map.getDOT_EMPTY());
                }
                int reply = JOptionPane.showConfirmDialog(null,"AI ходит первым?","Чей первый ход", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) needPlay = player2.turn();
            }
        });
        mainMenu.add(newGame);

        JMenuItem options = new JMenuItem("Настройки");
        options.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                //отдельное окно с настройками игры
                JFrame formOptions = new JFrame("Настройки");
                formOptions.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                formOptions.setBounds(300, 300, 200, 130);

                formOptions.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent event) {
                        formOptions.setVisible(false);
                        formGame.setVisible(true);
                    }
                });

                // Создание текстовых полей
                JTextField sizeField = new JTextField(2);
                sizeField.setToolTipText("Размер поля");
                sizeField.setText(""+map.getSize());
                JTextField dotsToWinField = new JTextField(2);
                dotsToWinField.setToolTipText("Фишек для победы");
                dotsToWinField.setText(""+map.getDotsToWin());
                JButton buttonOK = new JButton("OK");
                buttonOK.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int mapSize1 = map.getSize();
                        int dotsToWin1 = map.getDotsToWin();
                        try {
                            mapSize1 = Integer.parseInt(sizeField.getText());
                            dotsToWin1 = Integer.parseInt(dotsToWinField.getText());
                        }  catch (NumberFormatException ex) {
                            // при некорректном вводе данных параметр не меняется
                        }
                        // если пармаметры меняли в процессе игры, то игра прекращается
                        needPlay = false;
                        formOptions.setVisible(false);
                        // удаляем старые кнопки с игрового поля
                        for (int i = 0; i < jbsLength; i++) {
                            JButton jb = jbs[i];
                            formGame.remove(jb);
                        }
                        // заново создаем и заполняем игровое поле
                        map = new Map((mapSize1 > 0) ? mapSize1 : map.getSize(), (dotsToWin1 > 0) ? dotsToWin1 : map.getDotsToWin());
                        formNewGame();
                    }
                });
                // Создание панели с текстовыми полями
                JPanel contents = new JPanel();
                contents.add(new JLabel("Размер игрового поля :"));
                contents.add(sizeField);
                contents.add(new JLabel("Фишек для победы :"));
                contents.add(dotsToWinField);
                contents.add(buttonOK);
                formOptions.add(contents);

                formGame.setVisible(false);
                formOptions.setVisible(true);
            }
        });
        mainMenu.add(options);

        mainMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Выйти");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        mainMenu.add(exit);
        menuBar.add(mainMenu);
        formGame.setJMenuBar(menuBar);

        formGame.setVisible(true);
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
            clearMap();
        }

        void clearMap() {
            for (int i = 0; i < size; i++) {
                map[i][i] = DOT_EMPTY;
                for (int j = 0; j < i; j++) {
                    map[i][j] = DOT_EMPTY;
                    map[j][i] = DOT_EMPTY;
                }
            }
        }

        boolean isCellValid(int x, int y) {
            return (map[y][x] == DOT_EMPTY);
        }

        boolean isMapFull() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (map[i][j] == DOT_EMPTY) return false;
                }
            }
            return true;
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

        void setDot(int x, int y, char dotType) {
            map[y][x] = dotType;
        }

        int getSize() {
            return size;
        }

        int getDotsToWin() {
            return dots_to_win;
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
    }

    class Player {
        private char dotType;

        Player(char dotType) {
            this.dotType = dotType;
        }

        boolean turn() {
            return false;
        };

        boolean turn(int j, int x, int y) {
            return false;
        };

        char getDotType() {
            return dotType;
        }
    }


    class Human extends Player {

        Human(char dotType) {
            super(dotType);
        }

        @Override
        boolean turn(int j, int x, int y) {
            JButton jb = jbs[j];
            if (map.isCellValid(x, y)) {
                jb.setText("" + map.getDOT_X());
                map.setDot(x, y, map.getDOT_X());
                if (map.checkWin(map.getDOT_X())) {
                    JOptionPane.showMessageDialog(null, "Вы победили!", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                } else if (map.isMapFull()) {
                    JOptionPane.showMessageDialog(null, "Ничья!", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                } else
                    return player2.turn();
            } else return true;
        }

    }


    class AI extends Player {

        AI(char dotType) {
            super(dotType);
        }

        @Override
        boolean turn() {
            int[] field = {0 ,0};
            int j;
            char dotTypeAnother = map.getDOT_X();
        /* проверяем может ли выиграть игрок сдедующим ходом;
           если находим такую ячейку, то очередным ходом ставим туда фишку
        */
            if (!checkWinNextStep(map, map.getDOT_O(), field)) {
            /* проверяем может ли выиграть сдедующим ходом противник;
               если находим такую ячейку, то очередным ходом ставим туда фишку
            */
                if (!checkWinNextStep(map, dotTypeAnother, field)) {
                /* ставим фишку в произвольную пустую ячейку
                */
                    findNextStep(map, field);
                }
            }
            map.setDot(field[0], field[1], map.getDOT_O());
            j = field[1] * map.getSize() + field[0];
            JButton jb = jbs[j];
            jb.setText("" + map.getDOT_O());
            //System.out.println("Button " + (j + 1) + " (" + x + "," + y + ") pressed o");
            if (map.checkWin(getDotType())) {
                JOptionPane.showMessageDialog(null, "AI победил!", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
                return false;
            } else if (map.isMapFull()) {
                JOptionPane.showMessageDialog(null, "Ничья!", "Конец игры", JOptionPane.INFORMATION_MESSAGE);
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
}
