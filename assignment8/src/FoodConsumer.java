/* Alex Welk - CMSC 403 Assignment 8 - 12/18/22 */

//FoodConsumer class
public class FoodConsumer extends Thread {

    private FoodBank bank;

    //FoodConsumer object
    public FoodConsumer(FoodBank thisBank) {
        this.bank = thisBank;
    }

    //run function to take food from bank
    @Override
    public void run() {

        int max = 100;
        int min = 1;

        while(true) {
            int foodTaken = (int)(Math.random() * ((max - min) + 1) + min);
            bank.takeFood(foodTaken);

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                System.out.println("Thread interrupted, did not sleep.");
            }
        }
    }
}