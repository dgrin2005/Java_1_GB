/**
 * Java 1. Lesson 7
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-16
 */


public class Lesson7 {
    public static void main(String[] args) {
        Cat[] cats = {new Cat("Barsik", 10), new Cat("Murzik", 20), new Cat("Vas'ka", 15), new Cat("Pushok", 25)};
        
        Plate plate = new Plate(50);
        
        for (Cat cat : cats) {
            System.out.println(plate);
            System.out.println(cat);
            cat.eat(plate);
            System.out.println("After eat satiety: " + cat.getSatiety());
        }
        System.out.println();
        plate.addFood(30);
        System.out.println("Add food 30");
        System.out.println();
        for (Cat cat : cats) {
            System.out.println(plate);
            System.out.println(cat);
            cat.eat(plate);
            System.out.println("After eat satiety: " + cat.getSatiety());   
        }
        
    }
}

class Cat {
    private String name;
    private int appetite;
    private boolean satiety;

    Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        this.satiety = false;
    }

    @Override
    public String toString() {
        return getClass().getName() + " " + name + " (appetite: " + appetite + ", satiety: " + satiety + ")";
    }

    public void eat(Plate p) {
        if (p.getFood() >= appetite && !satiety) {
            p.decreaseFood(appetite);
            satiety = true;
        }
    }
    
    public boolean getSatiety() {
        return satiety;
    }

}

class Plate {
    private int food;
    
    Plate(int food) {
        this.food = food;
    }
    
    @Override
    public String toString() {
        return getClass().getName() + "(food: " + food + ")";
    }
    
    public void decreaseFood(int f) {
        food -= f;
    }
    
    public int getFood() {
        return food;
    }
    
    public void addFood(int f) {
        food += f;
    }

}