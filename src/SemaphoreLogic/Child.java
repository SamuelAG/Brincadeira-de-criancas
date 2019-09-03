package SemaphoreLogic;

import java.util.concurrent.Semaphore;

public class Child extends Thread {
    private int idChild;
    private boolean ball;
    private int timePlaying;
    private int timeQuiet;
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex;

    public Child(){}

    public Child(int idChild, boolean ball, int timePlaying, int timeQuiet, Semaphore empty, Semaphore full, Semaphore mutex) {
        this.idChild = idChild;
        this.ball = ball;
        this.timePlaying = timePlaying;
        this.timeQuiet = timeQuiet;
        this.empty = empty;
        this.full = full;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        System.out.println(idChild + " is starting");
        if(ball)
            putAball();
        else
            getAball();
        for(int i = 0; i < timePlaying;) {
            for(int j = 0; j < 5000; j++) {
                //System.out.println(j);
                for(int k = 0; k < 1000; k++){}
            }
            System.out.println(idChild + " is playing " + timePlaying);
            timePlaying--;
        }
        ball = !ball;
        System.out.println("Thread: " + idChild + ". balls:" + Basket.balls);
        super.run();
    }

    void getAball() {
        try {
            full.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        try {
            System.out.println("Awaiting permission");
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gets permission");
        Basket.balls--;
        mutex.release();
        System.out.println("A child get a ball");
        empty.release();
    }

    void putAball() {
        try {
            empty.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        try {
            System.out.println("Awaiting permission");
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Basket.balls++;
        mutex.release();
        System.out.println("A child put a ball");
        full.release();
    }

    public Semaphore getFull() {
        return full;
    }

    public void setFull(Semaphore full) {
        this.full = full;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public void setMutex(Semaphore mutex) {
        this.mutex = mutex;
    }

    public Semaphore getEmpty() {
        return empty;
    }

    public void setEmpty(Semaphore empty) {
        this.empty = empty;
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
