public class FoodBank {

    private int food;
  /*  Lock lock;
    Condition open; */

    public FoodBank() {
        this.food = 0;
    /*    lock = new ReentrantLock();
        open = lock.newCondition(); */
    }

    public void giveFood(int amount){
       // lock.lock();
        this.food = food + amount;
        System.out.println("\n---------------------------------------");
        System.out.println("FOOD PRODUCER THREAD");
        System.out.println("Producing " + amount + " items of food!");
        System.out.println("Food Bank balance is now " + food + "!");
        System.out.println("---------------------------------------\n");

       // open.signal();
        //lock.unlock();
    }

    public void takeFood(int amount){
       // lock.lock();
        try{
            while(food < amount){
                System.out.println("\n---------------------------------------");
                System.out.println("FOOD CONSUMER THREAD");
                System.out.println("Waiting for " + amount + " items of food!");
                System.out.println("---------------------------------------\n");

              //  open.await();
            }

            this.food = food - amount;
            System.out.println("\n---------------------------------------");
            System.out.println("FOOD CONSUMER THREAD");
            System.out.println("Taking " + amount + " items of food!");
            System.out.println("---------------------------------------\n");


       } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
