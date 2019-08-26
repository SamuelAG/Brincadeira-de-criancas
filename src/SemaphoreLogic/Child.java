package SemaphoreLogic;

public class Child extends Thread {
    private int id;
    private boolean ball;
    private int timePlaying;
    private int timeQuiet;

    public Child(int id, boolean ball, int timePlaying, int timeQuiet) {
        this.id = id;
        this.ball = ball;
        this.timePlaying = timePlaying;
        this.timeQuiet = timeQuiet;
    }

    @Override
    public void run() {
        for(int i = 0; i < timePlaying; i++) {
            for(int j = 0; j < 5000000; j++) {
                for(int k = 0; k < 100000000; k++){}
            }
            System.out.println(id + " " + i);
        }
        Basket.balls++;
        System.out.println("Thread: " + id + ". balls:" + Basket.balls);
        super.run();
    }
}
