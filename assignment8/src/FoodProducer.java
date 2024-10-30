/* Alex Welk - CMSC 403 Assignment 8 - 12/18/22 */

//FoodProducer class
public class FoodProducer extends Thread {

    private FoodBank bank;

    //FoodProducer object
    public FoodProducer(FoodBank thisBank) {
        this.bank = thisBank;
    }


    //run function to give food
    @Override
    public void run() {

        int max = 100;
        int min = 1;

        while(true) {
            int foodGiven = (int) (Math.random() * ((max - min) +1) + min );
            bank.giveFood(foodGiven);

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                System.out.println("Thread interrupted, did not sleep.");
            }
        }
    }
}
