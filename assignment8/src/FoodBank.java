/* Alex Welk - CMSC 403 Assignment 8 - 12/18/22 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FoodBank {

    private int food;

    //FoodBank object
    public FoodBank() {
        this.food = 0;
    }

    //takeFood function
    public synchronized void takeFood(int amount) {
            if(this.food > 0 && this.food - amount > 0) {
                this.food -= amount;
            System.out.println("Taking " + amount + " items of food."); }
    }

    //giveFood function
    public synchronized void giveFood(int amount) {
        this.food += amount;
        System.out.println("Giving " + amount + " items of food.");
    }
}