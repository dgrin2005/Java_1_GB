/**
Homework Lesson 2
@author Dmitry Grinshteyn
*/

public class Lesson2 {


    public static void main(String[] args) {

        System.out.println("Задание 1.");
        int[] arr_task1 = {1, 1, 0, 0, 1, 0 , 1, 0, 0};
        System.out.println("Начальный массив: ");
        printArray(arr_task1);
        doTask1(arr_task1);
        System.out.println("Преобразованный массив: ");
        printArray(arr_task1);
        System.out.println("");

        System.out.println("Задание 2.");
        int[] arr_task2 = new int[8];
        doTask2(arr_task2);
        printArray(arr_task2);
        System.out.println("");

        System.out.println("Задание 3.");
        int[] arr_task3 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        System.out.println("Начальный массив: ");
        printArray(arr_task3);
        doTask3(arr_task3);
        System.out.println("Преобразованный массив: ");
        printArray(arr_task3);
        System.out.println("");

        System.out.println("Задание 4.");
        int[][] arr_task4 = new int[8][8];
        doTask4(arr_task4);
        for (int[] i : arr_task4) printArray(i);
        System.out.println("");

        System.out.println("Задание 5.");
        int[] arr_task5 = {11, 12, 25, 3, 18, 1};
        doTask5(arr_task5);
        System.out.println("");

        System.out.println("Задание 6.");
        int[] arr_task6_1 = {1, 2, 3, 10, 4, 3, 3, 3, 3};
        System.out.println("Массив: ");
        printArray(arr_task6_1);
        System.out.println(doTask6(arr_task6_1));
        int[] arr_task6_2 = {1, 1, 1, 2, 1};
        System.out.println("Массив: ");
        printArray(arr_task6_2);
        System.out.println(doTask6(arr_task6_2));
        int[] arr_task6_3 = {2, 1, 1, 2, 1};
        System.out.println("Массив: ");
        printArray(arr_task6_3);
        System.out.println(doTask6(arr_task6_3));
        System.out.println("");

        System.out.println("Задание 7.");
        int[] arr_task7_1 = {1, 2, 3, 10, 4, 3, 3, 3, 3};
        int n_task7 = -4;
        System.out.println("Начальный массив: ");
        printArray(arr_task7_1);
        doTask7(arr_task7_1, n_task7);
        System.out.println("Массив после сдвига на " + (n_task7<0? -n_task7 + " позиций вправо: ":n_task7 + " позиций влево: "));
        printArray(arr_task7_1);
        int[] arr_task7_2 = {1, 2, 3, 10, 4, 3, 3, 3, 3};
        n_task7 = 6;
        System.out.println("Начальный массив: ");
        printArray(arr_task7_2);
        doTask7(arr_task7_2, n_task7);
        System.out.println("Массив после сдвига на " + (n_task7<0? -n_task7 + " позиций вправо: ":n_task7 + " позиций влево: "));
        printArray(arr_task7_2);
        System.out.println("");

    }

    public static void doTask1(int[] arr) {
        int arr_length = arr.length;
        for (int i = 0; i < arr_length; i++) {
            if (arr[i] == 0) arr[i] = 1;
            else arr[i] = 0;
        }
    }

    public static void doTask2(int[] arr) {
        int arr_length = arr.length;
        for (int i = 0; i < arr_length; i++) arr[i] = i * 3;
    }

    public static void doTask3(int[] arr) {
        int arr_length = arr.length;
        for (int i = 0; i < arr_length; i++) {
            if (arr[i] < 6) arr[i] *= 2;
        }
    }

    public static void doTask4(int[][] arr) {
        int arr_length = arr.length;
        for (int i = 0; i < arr_length; i++) {
            arr[i][i] = 1;
            arr[arr_length-i-1][i] = 1;
        }
    }

    public static void doTask5(int[] arr) {
        int i, arr_length = arr.length;
        if (arr_length > 0) {
            int min = arr[0];
            int max = arr[0];
            for (i = 1; i < arr_length; i++) {
                if (min > arr[i]) min = arr[i];
                if (max < arr[i]) max = arr[i];
            }
            System.out.println("Минимум = " + min);
            System.out.println("Максимум = " + max);
        } else {
            System.out.println("Нет элементов в массиве.");
        }
    }

    public static boolean doTask6(int[] arr) {
        int leftSum, rightSum;
        int leftIndex, rightIndex;
        int arr_length = arr.length;
        leftIndex = 1;
        rightIndex = 1;
        leftSum = arr[leftIndex-1];
        rightSum = arr[arr_length-rightIndex];
        while (leftIndex+rightIndex < arr_length) {
            if (leftSum<=rightSum){
                leftIndex++;
                leftSum = leftSum + arr[leftIndex-1];
            } else {
                rightIndex++;
                rightSum = rightSum + arr[arr_length-rightIndex];
            }
        }
        return (leftSum == rightSum);
    }

    public static void doTask7(int[] arr, int n) {
        int i, j, arr_length = arr.length;
        int a;
        if (arr_length > 0) {
            if (n > 0) { //сдвигаем влево
                for (i = 1; i<= n; i++) {
                    a = arr[0];
                    for (j = 1; j<arr_length; j++) {
                        arr[j-1] = arr [j];
                    }
                    arr[arr_length-1] = a;
                }
            } else { //сдвигаем вправо
                for (i = -1; i>= n; i--) {
                    a = arr[arr_length-1];
                    for (j = arr_length-1; j>0; j--) {
                        arr[j] = arr [j-1];
                    }
                    arr[0] = a;
                }
            }
        } else {
            System.out.println("Нет элементов в массиве.");
        }
    }

    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }
}