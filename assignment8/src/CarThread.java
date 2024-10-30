//CarThread class to help track the position of each car relative to thread
public class CarThread extends Thread{
    private RaceTrack track;
    private int index;
    private int position = 0;

    public CarThread(RaceTrack track, int index) {
        this.track = track;
        this.index = index;
    }

    public void run() {
        try {
            while(!track.winner) {
                if(!track.pause) {
                    int distance = (int)(Math.random() * ((10 - 1) + 1) + 1);
                    position += distance;
                    track.moveCar(index, position);
                }
                sleep(50);
            }
            this.interrupt();
        }
        catch (InterruptedException e) {}
    }
}
