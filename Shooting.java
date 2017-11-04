/**
 * Java 1. Lesson 8. Shooting
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-21
 */

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Shooting extends JFrame{

    public final int FIELD_SIZE = 600; // размер поля
    //public final int TARGET_INIT_X = FIELD_SIZE / 2; // начальное положение цели
    //public final int TARGET_INIT_Y = FIELD_SIZE / 2; // начальное положение цели
    public final int TARGET_SIZE = 30; // размер цели
    public final int TARGET_SPEED = 3; // скорость цели
    public final int AIM_INIT_X = FIELD_SIZE / 2; // начальное положение прицела
    public final int AIM_INIT_Y = FIELD_SIZE / 2; // начальное положение прицела
    public final int AIM_SIZE = 50; // размер прицела
    public final int AIM_SPEED = 10; // // скорость прицела
    public final int LEFT_SHOT_INIT_X = 0; // начальное положение левого выстрела
    public final int LEFT_SHOT_INIT_Y = FIELD_SIZE / 2; // начальное положение левого выстрела
    public final int LEFT_SHOT_SIZE = 10; // размер левого выстрела
    public final int LEFT_SHOT_SPEED = 8; // скорость левого выстрела
    public final int RIGHT_SHOT_INIT_X = FIELD_SIZE; // начальное положение правого выстрела
    public final int RIGHT_SHOT_INIT_Y = FIELD_SIZE / 2; // начальное положение правого выстрела
    public final int RIGHT_SHOT_SIZE = 10; // размер правого выстрела
    public final int RIGHT_SHOT_SPEED = 8; // скорость правого выстрела
    public final int STAR_QUANTITY = 200; //количество звезд
    public final int STAR_SIZE = 2;  // размер звезды
    public final int STAR_SPEED = 4;  // скорость звезды
    public final String TEXT_HELP = "    Press arrows for moving, Spacebar for shooting.";
    public final String TEXT_CONTINUE = "    Press Enter to continue.";

    private Field field = new Field();
    private Targ targ = null;
    private Aim aim = null;
    private Shot shLeft;
    private Shot shRight;
    private Star[] stars = new Star[STAR_QUANTITY];
    private boolean makeShotLeft = false;
    private boolean makeShotRight = false;
    private boolean makeHit = false;
    private int shotX, shotY;
    private int dx, dy;
    private int shots = 0, hits = 0;

    public static void main(String[] args) {
        Shooting sh = new Shooting();
    }

    Shooting() {

        setTitle("Shooting");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(FIELD_SIZE, FIELD_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);

        // кнопки верхней панели
        JPanel buttons = new JPanel();
        JButton startButton = new JButton();
        startButton.setText("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < STAR_QUANTITY; i++) {
                    stars[i] = new Star(0,0, STAR_SPEED, STAR_SIZE);
                }
                targ = new Targ(getRandomCoordinate(), getRandomCoordinate(), TARGET_SPEED, TARGET_SIZE);
                aim = new Aim(AIM_INIT_X, AIM_INIT_Y, AIM_SPEED, AIM_SIZE);
                makeShotLeft = false;
                makeShotRight = false;
                makeHit = false;
                field.grabFocus();
                field.repaint();
            }
        });
        JButton quitButton = new JButton();
        quitButton.setText("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttons.setLayout(new GridLayout());
        buttons.add(startButton);
        buttons.add(quitButton);
        setLayout(new BorderLayout());
        add(buttons, BorderLayout.NORTH);

        // надпись на нижней панели
        JPanel score = new JPanel();
        JLabel scoreText = new JLabel();
        scoreText.setText("Shoots: " + shots + "     Hits: " + hits + TEXT_HELP);
        score.add(scoreText);
        add(score, BorderLayout.SOUTH);

        // закрашиваеи поле и привязываем нажатие кнопок
        field.setBackground(Color.BLACK);
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                aim.moving(e.getKeyText(e.getKeyCode()));
                switch (e.getKeyText(e.getKeyCode())) {
                    case "Space": {
                        if (targ != null) {
                            makeShotLeft = true;
                            makeShotRight = true;
                            shLeft = new Shot(LEFT_SHOT_INIT_X, LEFT_SHOT_INIT_Y, LEFT_SHOT_SPEED, LEFT_SHOT_SIZE);
                            shRight = new Shot(RIGHT_SHOT_INIT_X, RIGHT_SHOT_INIT_Y, RIGHT_SHOT_SPEED, RIGHT_SHOT_SIZE);
                            shotX = aim.getX();
                            shotY = aim.getY();
                            shots++;
                            scoreText.setText("Shoots: " + shots + "     Hits: " + hits + TEXT_HELP);
                        }
                        break;
                    }
                    case "Enter": {
                        if (targ == null) {
                            targ = new Targ(getRandomCoordinate(), getRandomCoordinate(), TARGET_SPEED, TARGET_SIZE);
                            makeShotLeft = false;
                            makeShotRight = false;
                            makeHit = false;
                            field.grabFocus();
                            field.repaint();
                            scoreText.setText("Shoots: " + shots + "     Hits: " + hits + TEXT_HELP);
                        }
                        break;
                    }
                }
                field.repaint();
            }
        });
        add(field,BorderLayout.CENTER);

        setVisible(true);

        while(true) {
            // ход
            if (targ != null) {
                //движение звезд
                for(int i = 0; i < STAR_QUANTITY; i++) {
                    stars[i].moving();
                }
                //движение цели
                targ.moving();
                //движение левого выстрела
                if (makeShotLeft) {
                    makeShotLeft = !shLeft.moving(shotX, shotY);
                    if (!makeShotLeft) {
                        //вычисляем расстояние от выстрела до цели
                        dx = (shLeft.getX() < targ.getX()) ? (targ.getX() - shLeft.getX()) : (shLeft.getX() - targ.getX());
                        dy = (shLeft.getY() < targ.getY()) ? (targ.getY() - shLeft.getY()) : (shLeft.getY() - targ.getY());
                        makeHit = (dx <= TARGET_SIZE / 2) && (dy <= TARGET_SIZE / 2); //попали в цель или нет
                    }
                }
                //движение правого выстрела
                if (makeShotRight) {
                    makeShotRight = !shRight.moving(shotX, shotY);
                    if (!makeShotRight) {
                        //вычисляем расстояние от выстрела до цели
                        dx = (shRight.getX() < targ.getX()) ? (targ.getX() - shRight.getX()) : (shRight.getX() - targ.getX());
                        dy = (shRight.getY() < targ.getY()) ? (targ.getY() - shRight.getY()) : (shRight.getY() - targ.getY());
                        makeHit = (dx <= TARGET_SIZE / 2) && (dy <= TARGET_SIZE / 2); //попали в цель или нет
                    }
                }
                if (makeHit) {
                    // попали в цель
                    targ = null;
                    hits++;
                    scoreText.setText("Shoots: " + shots + "     Hits: " + hits + TEXT_CONTINUE);
                }
            }

            // задержка 5 млс
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            field.repaint();
        }
    }

    class Field extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            //рисуем звезды, если начали играть (т.е. определили прицел)
            if (aim != null) drawStars(g);

            //рисуем цель
            if (targ != null) drawTarg(g);

            //рисуем попадание
            if (makeHit) drawHit(g);

            //рисуем прицел
            if (aim != null) drawAim(g);

            //рисуем левый выстрел
            if (makeShotLeft) drawShot(g, shLeft.getX() - LEFT_SHOT_SIZE / 2, shLeft.getY() - LEFT_SHOT_SIZE / 2, LEFT_SHOT_SIZE);

            //рисуем правый выстрел
            if (makeShotRight) drawShot(g, shRight.getX() - RIGHT_SHOT_SIZE / 2, shRight.getY() - RIGHT_SHOT_SIZE / 2, RIGHT_SHOT_SIZE);

        }

        void drawStars(Graphics g){
            g.setColor(Color.LIGHT_GRAY);
            for(int i = 0; i < STAR_QUANTITY; i++) {
                g.fillOval(stars[i].getX() - STAR_SIZE / 2, stars[i].getY() - STAR_SIZE / 2, STAR_SIZE, STAR_SIZE);
            }
        }

        void drawTarg(Graphics g){
            g.setColor(Color.GREEN);
            int npoints = 3;
            int xpoints[] = new int[npoints];
            int ypoints[] = new int[npoints];
            xpoints[0] = targ.getX();
            ypoints[0] = targ.getY() - TARGET_SIZE / 3 * 2;
            xpoints[1] = targ.getX() - TARGET_SIZE / 2;
            ypoints[1] = targ.getY() - TARGET_SIZE / 6;
            xpoints[2] = targ.getX() + TARGET_SIZE / 2;
            ypoints[2] = targ.getY() - TARGET_SIZE / 6;
            g.fillOval(targ.getX() - TARGET_SIZE / 2, targ.getY() - TARGET_SIZE / 2, TARGET_SIZE, TARGET_SIZE / 3);
            g.fillPolygon(xpoints, ypoints, npoints);
        }

        void drawHit(Graphics g) {
            g.setColor(Color.RED);
            g.fillOval(shotX - TARGET_SIZE / 2, shotY - TARGET_SIZE / 2, TARGET_SIZE, TARGET_SIZE);
            g.setColor(Color.YELLOW);
            g.fillOval(shotX - TARGET_SIZE / 2 + TARGET_SIZE / 6, shotY - TARGET_SIZE / 2 + TARGET_SIZE / 6, TARGET_SIZE / 3 * 2, TARGET_SIZE / 3 * 2);
            g.setColor(Color.WHITE);
            g.fillOval(shotX - TARGET_SIZE / 2 + TARGET_SIZE / 3, shotY - TARGET_SIZE / 2 + TARGET_SIZE / 3, TARGET_SIZE /3, TARGET_SIZE / 3);
        }

        void drawAim(Graphics g) {
            g.setColor(Color.BLUE);
            g.drawLine(aim.getX(), aim.getY() - AIM_SIZE / 2, aim.getX(), aim.getY() - AIM_SIZE / 6);
            g.drawLine(aim.getX(), aim.getY() + AIM_SIZE / 6, aim.getX(), aim.getY() + AIM_SIZE / 2);
            g.drawLine(aim.getX() - AIM_SIZE / 2, aim.getY(), aim.getX() - AIM_SIZE / 6, aim.getY());
            g.drawLine(aim.getX() + AIM_SIZE / 6, aim.getY(), aim.getX() + AIM_SIZE / 2, aim.getY());
        }

        void drawShot(Graphics g, int x, int y, int size) {
            g.setColor(Color.YELLOW);
            g.fillOval(x, y, size, size);
        }

    }

    //случайные начальные координаты цели
    private int getRandomCoordinate() {
        Random rand = new Random();
        return rand.nextInt(FIELD_SIZE - TARGET_SIZE * 2) + TARGET_SIZE;
    }
}

// класс Движущийся Объект
class MovingObject {

    public final int FIELD_SIZE = 600;
    private int x;
    private int y;
    private int speed;
    private int size;

    MovingObject(int x, int y, int speed, int size) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSize() {
        return size;
    }

    public void moveUp(int border) {
        y -= speed;
        if (y < border) y = border;
    }
    public void moveDown(int border) {
        y += speed;
        if (y > FIELD_SIZE - border) y = FIELD_SIZE - border;
    }
    public void moveLeft(int border) {
        x -= speed;
        if (x < border) x = border;
    }
    public void moveRight(int border) {
        x += speed;
        if (x > FIELD_SIZE - border) x = FIELD_SIZE - border;
    }

    public void moving() {
    }

    public void moving(String key) {
    }

    public boolean moving(int newX, int newY) {
        return false;
    }
}

// класс Цель
class Targ extends MovingObject{

    Targ(int x, int y, int speed, int size) {
        super (x, y, speed, size);
    }

    @Override
    public void moving() {
        Random rand = new Random();
        int number = rand.nextInt(4);
        switch (number) {
            case 0: {
                moveDown(getSize() * 2);
                break;
            }
            case 1: {
                moveUp(getSize() * 2);
                break;
            }
            case 2: {
                moveRight(getSize() * 2);
                break;
            }
            case 3: {
                moveLeft(getSize() * 2);
                break;
            }
        };
    }

}

// класс Прицел
class Aim extends MovingObject{

    Aim(int x, int y, int speed, int size) {
        super (x, y, speed, size);
    }

    @Override
    public void moving(String key) {
           switch (key) {
                case "Up" : {
                    moveUp(0);
                    break;
                }
                case "Down": {
                    moveDown(0);
                    break;
                }
                case "Left": {
                    moveLeft(0);
                    break;
                }
                case "Right": {
                    moveRight(0);
                    break;
                }
           }

    }

}

// класс Выстрел
class Shot extends MovingObject{
    Shot(int x, int y, int speed, int size) {
        super (x, y, speed, size);
    }

    @Override
    public boolean moving(int newX, int newY) {
        boolean finishMove = true;
        if (newX > getX()) {
            moveRight(newX);
            finishMove = false;
        }
        if (newX < getX()) {
            moveLeft(newX);
            finishMove = false;
        }
        if (newY > getY()) {
            moveDown(newY);
            finishMove = false;
        }
        if (newY < getY()) {
            moveUp(newY);
            finishMove = false;
        }
        return finishMove;
    }

    public void moveUp(int newY) {
        if (newY <= getY() - getSpeed()){
            setY(getY() - getSpeed());
        } else {
            setY(newY);
        }
    }
    public void moveDown(int newY) {
        if (newY >= getY() + getSpeed()){
            setY(getY() + getSpeed());
        } else {
            setY(newY);
        }
    }
    public void moveLeft(int newX) {
        if (newX <= getX() - getSpeed()){
            setX(getX() - getSpeed());
        } else {
            setX(newX);
        }
    }
    public void moveRight(int newX) {
        if (newX >= getX() + getSpeed()){
            setX(getX() + getSpeed());
        } else {
            setX(newX);
        }
    }
}

// класс Звезда
class Star extends MovingObject {

    Star(int x, int y, int speed, int size) {
        super (x, y, speed, size);
        setX(getRandomCoordinate());
        setY(getRandomCoordinate());
    }

    private int getRandomCoordinate() {
        Random rand = new Random();
        return rand.nextInt(FIELD_SIZE);
    }

    @Override
    public void moving() {

        float dx = getX() - FIELD_SIZE / 2;
        float dy = getY() - FIELD_SIZE / 2;
        float d = (dx < 0 ? -dx : dx) + (dy < 0 ? -dy : dy);
        if (d != 0) {
            setX(getX() + (int) ((float)getSpeed() * dx / d));
            setY(getY() + (int) ((float)getSpeed() * dy / d));
        }
        if (getX() <= 0 || getX() >= FIELD_SIZE || getY() <= 0 || getY() >= FIELD_SIZE) {
            setX(getRandomCoordinate());
            setY(getRandomCoordinate());
        }

    }
}
