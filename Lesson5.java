/**
 * Java 1. Lesson 5
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-08-09
 */
 
 import java.util.*;
 
class Lesson5 {
    public static void main(String[] args) {
        
        Person[] persArray = new Person[5];
        persArray[0] = new Person("Ivanov Ivan", "Engineer", "iiva@mailbox.com", "892312312", 30000, 30);
        persArray[1] = new Person("Petov Petr", "Manager", "ppet@mailbox.com", "892312574", 35000, 28);
        persArray[2] = new Person("Kuznetsova Elena", "Seller", "ekuz@mailbox.com", "892312659", 20000, 34);
        persArray[3] = new Person("Ivanova Olga", "Driver", "oiva@mailbox.com", "892312325", 15000, 43);
        persArray[4] = new Person("Sidorov Vasiliy", "Accountant", "vsid@mailbox.com", "892312987", 50000, 50);
        
        System.out.println("Вывод из собственного метода класса");
        for (Person person : persArray) {
            if (person.getAge() > 40) {
                person.printPerson();
            }
        }
        System.out.println();
        System.out.println("Вывод переопределением метода toString()");
        for (Person person : persArray) {
            if (person.getAge() > 40) {
                System.out.println(person);
            }
        }
    }

}

class Person {
    private String name;
    private String job;
    private String email;
    private String phone;
    private int salary;
    private int age;
        
    Person(String name, String job, String email, String phone, int salary, int age) {
        this.name = name;
        this.job = job;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }
        
    void printPerson() {
        System.out.println("{" + "ФИО=" + name + ", должность=" + job+ ", e-mail=" + email + ", телефон=" + phone + ", зарплата=" + salary + ", возарст=" + age +"}");
    }
    
    @Override
    public String toString() {
        return "{" + "ФИО=" + name + ", должность=" + job+ ", e-mail=" + email + ", телефон=" + phone + ", зарплата=" + salary + ", возарст=" + age +"}";
    }
    
    void setName(String name) {
        this.name = name;
    }
    
    void setJob(String job) {
        this.job = job;
    }
    
    void setEMail(String email) {
        this.email = email;
    }
    
    void setPhone(String phone) {
        this.phone = phone;
    }
    
    void setSalary(int salary) {
        this.salary = salary;
    }
    
    void setAge(int age) {
        this.age = age;
    }
    
    String getName() {
        return name;
    }
    
    String getJob() {
        return job;
    }
    
    String getEMail() {
        return email;
    }
    
    String getPhone() {
        return phone;
    }
    
    int getSalary() {
        return salary;
    }
    
    int getAge() {
        return age;
    }
}