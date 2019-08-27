package SemaphoreLogic;

public class Child extends Thread {
    private int idChild;
    private boolean ball;
    private int timePlaying;
    private int timeQuiet;

    public Child(){}

    public Child(int idChild, boolean ball, int timePlaying, int timeQuiet) {
        this.idChild = idChild;
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
            System.out.println(idChild + " " + i);
        }
        Basket.balls++;
        System.out.println("Thread: " + idChild + ". balls:" + Basket.balls);
        super.run();
    }

    public int getIdChild() {
        return idChild;
    }

    public void setIdChild(int idChild) {
        this.idChild = idChild;
    }

    public boolean isBall() {
        return ball;
    }

    public void setBall(boolean ball) {
        this.ball = ball;
    }

    public int getTimePlaying() {
        return timePlaying;
    }

    public void setTimePlaying(int timePlaying) {
        this.timePlaying = timePlaying;
    }

    public int getTimeQuiet() {
        return timeQuiet;
    }

    public void setTimeQuiet(int timeQuiet) {
        this.timeQuiet = timeQuiet;
    }
}
