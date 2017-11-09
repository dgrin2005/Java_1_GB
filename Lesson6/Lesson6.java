/**
 * Java 1. Lesson 6
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-13
 */


public class Lesson6 {
    public static void main(String[] args) {
        Cat cat1 = new Cat("Barsik");
        Cat cat2 = new Cat("Murzik", 100f, 0.5f, 3f);
        Dog dog1 = new Dog("Sharik");
        Dog dog2 = new Dog("Barbos", 400f, -1f, -1f);
        Dog dog3 = new Dog("Tuzik", 600f, -1f, -1f);
        Animal[] animals = {cat1, cat2, dog1, dog2, dog3};
        for (Animal an : animals) {
            System.out.println(an);
            an.jump(1f);
            an.jump(3f);
            an.swim(5f);
            an.swim(15f);
            an.run(100f);
            an.run(450f);
        }
    }
}

class Animal {
    private String name;
    private float limitRun;
    private float limitSwim;
    private float limitJump;

    Animal(String name) {
        this.name = name;
        limitRun = 0;
        limitSwim = 0;
        limitJump = 0;
    }
    
    Animal(String name, float limitRun, float limitSwim, float limitJump) {
        this.name = name;
        this.limitRun = (limitRun >= 0) ? limitRun : 0;
        this.limitSwim = (limitSwim >= 0) ? limitSwim : 0;
        this.limitJump = (limitJump >= 0) ? limitJump : 0;
    }

    @Override
    public String toString() {
        return getClass().getName() + " " + name + " (run: " + limitRun + ", jump: " + limitJump + ", swim: " + limitSwim + ")";
    }

    void run(float a) {
        System.out.println("run " + a + ": " + (limitRun >= a));
    }

    void swim(float a) {
        System.out.println("swim: " + a + ": " + (limitSwim >= a));
    }

    void jump(float a) {
        System.out.println("jump: " + a + ": " + (limitJump >= a));
    }
    
    void setLimitRun(float a) {
        limitRun = a;
    }
    
    void setLimitSwim(float a) {
        limitSwim = a;
    }
    
    void setLimitJump(float a) {
        limitJump = a;
    }

}

class Cat extends Animal {
    
    private final float CAT_LIMIT_RUN = 200f;
    private final float CAT_LIMIT_SWIM = 0f;
    private final float CAT_LIMIT_JUMP = 2f;
    
    Cat(String name) {
        super(name);
        setLimitRun(CAT_LIMIT_RUN);
        setLimitSwim(CAT_LIMIT_SWIM);
        setLimitJump(CAT_LIMIT_JUMP);
    }

    Cat(String name, float limitRun, float limitSwim, float limitJump) {
        super(name);
        setLimitRun((limitRun < 0) ? CAT_LIMIT_RUN : limitRun);
        setLimitSwim((limitSwim < 0) ? CAT_LIMIT_SWIM : limitSwim);
        setLimitJump((limitJump < 0) ? CAT_LIMIT_JUMP : limitJump);
    }

}

class Dog extends Animal {
    
    private final float DOG_LIMIT_RUN = 500f;
    private final float DOG_LIMIT_SWIM = 10f;
    private final float DOG_LIMIT_JUMP = 0.5f;

    Dog(String name) {
        super(name);
        setLimitRun(DOG_LIMIT_RUN);
        setLimitSwim(DOG_LIMIT_SWIM);
        setLimitJump(DOG_LIMIT_JUMP);
    }

    Dog(String name, float limitRun, float limitSwim, float limitJump) {
        super(name);
        setLimitRun((limitRun < 0) ? DOG_LIMIT_RUN : limitRun);
        setLimitSwim((limitSwim < 0) ? DOG_LIMIT_SWIM : limitSwim);
        setLimitJump((limitJump < 0) ? DOG_LIMIT_JUMP : limitJump);
    }


}
