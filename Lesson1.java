public class Lesson1 {

// Гринштейн Дмитрий
    
    public static void main(String[] args) {
    
        byte data_byte = 10;
        short data_short = 1000;
        int data_int = 10000;
        long data_long = 1000000L;
        float data_float = 10.2f;
        double data_double = 100.456;
        boolean data_boolean = true;
        char data_char = 'd';
        
        System.out.println("a * (b + (c / d)) = " + task3(10, 15, 20, 3));
        System.out.println(task4(10, 7));
        task5(8);
        System.out.println(task6(-3));
        task7("World");
        task8(1900);
    
    }
    
    public static float task3(float a, float b, float c, float d) {
        return a * (b + (c / d));
    }
    
    public static boolean task4(int a, int b) {
        int c = a + b;
        return (c >= 10) && (c <= 20);
    }

    public static void task5(int a) {
        if (a >= 0) {
            System.out.println("Положительное число.");
        } else {
            System.out.println("Отрицательное число.");
        }
    }
    
    public static boolean task6(int a) {
        return (a < 0);
    }
    
    public static void task7(String a) {
        System.out.println("Привет, " + a + "!");
    }
    
    public static void task8(int a) {
        if ((a%4 == 0) && ((a%100 != 0) || (a%400 ==0))) {
            System.out.println("Год " + a + " - високосный.");
        } else {
            System.out.println("Год " + a + " - невисокосный.");
        }
    }
    
    
}