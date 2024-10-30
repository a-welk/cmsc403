public class CarThread extends Thread {

    private RaceTrack gui;
    private int carIndex;
    private int position = 0;

    public CarThread(RaceTrack gui, int carIndex){
        this.gui = gui;
        this.carIndex = carIndex;
    }

    public void run(){

        // car moves bt 0-10px
        int max = 10;
        int min = 0;

        try{
            while(!gui.winner){

                if (!gui.paused){
                    int distance = (int) (Math.random() * ((max - min) +1) + min );
                    position += distance;
                    gui.moveCar(carIndex, position);
                }

                sleep(50);
            }

            this.interrupt();

        } catch (InterruptedException e){}

    }


}