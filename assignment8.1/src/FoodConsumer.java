public class FoodConsumer extends Thread {

    private FoodBank bank;

    public FoodConsumer(FoodBank initBank){
        this.bank = initBank;
    }



    @Override
    public void run(){
        int max = 100;
        int min = 1;



        while(true){
            int foodSubtraction = (int) (Math.random() * ((max - min) +1) + min );

            bank.takeFood(foodSubtraction);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                System.out.println("Interrupted! Did not sleep(100)!");
            }
        }


    }


}