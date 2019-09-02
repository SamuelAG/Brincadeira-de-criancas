package SemaphoreLogic;

import java.util.concurrent.Semaphore;

public class Child extends Thread {
    private int idChild;
    private boolean ball;
    private int timePlaying;
    private int timeQuiet;
    private Semaphore semaphore;
    private Semaphore mutex;

    public Child(){}

    public Child(int idChild, boolean ball, int timePlaying, int timeQuiet, Semaphore semaphore, Semaphore mutex) {
        this.idChild = idChild;
        this.ball = ball;
        this.timePlaying = timePlaying;
        this.timeQuiet = timeQuiet;
        this.semaphore = semaphore;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        System.out.println(idChild + " is starting");
        try {
            System.out.println(idChild + " is waiting permission");
            semaphore.acquire();
            System.out.println(idChild + " gets a permission");
            for(int i = 0; i < timePlaying;) {
                for(int j = 0; j < 5000; j++) {
                    //System.out.println(j);
                    for(int k = 0; k < 1000; k++){}
                }
                System.out.println(idChild + " is playing " + timePlaying);
                timePlaying--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread: " + idChild + ". balls:" + Basket.balls);
        super.run();
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public void setMutex(Semaphore mutex) {
        this.mutex = mutex;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
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
