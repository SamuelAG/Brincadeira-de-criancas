package SemaphoreLogic;

import java.util.Date;
import java.util.concurrent.Semaphore;

public class Child extends Thread {
    private int idChild;
    private boolean ball;
    private int timePlaying;
    private int timeQuiet;
    private int timePlayingCounter;
    private int timeQuietCounter;
    private Semaphore spaces;
    private Semaphore items;
    private Semaphore mutex;
    public Child(){}

    public Child(int idChild, boolean ball, int timePlaying, int timeQuiet, Semaphore spaces, Semaphore items, Semaphore mutex) {
        this.idChild = idChild;
        this.ball = ball;
        this.timePlaying = timePlaying;
        this.timeQuiet = timeQuiet;
        this.spaces = spaces;
        this.items = items;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        while(true) {
            timeQuietCounter = timeQuiet;
            timePlayingCounter = timePlaying;
            System.out.println(idChild + " is starting, timePlaying: " + timePlaying + ", timeQuiet: " + timeQuiet);
            if(ball) {
                //MySleep();
                play();
                putAball();
                ball = false;
                quiet();
                //MySleep();
            } else {
                getAball();
                ball = true;
            }
            System.out.println("Thread: " + idChild + ". balls:" + Basket.balls);
            super.run();
        }

        //super.run();
    }

    void getAball() {
        try {
            items.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        try {
            //System.out.println("Awaiting permission to get a ball");
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("gets permission");
        Basket.balls--;
        mutex.release();
        //System.out.println("A child get a ball");
        spaces.release();
    }

    void putAball() {
        try {
            spaces.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        try {
            //System.out.println("Awaiting permission to put a ball");
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Basket.balls++;
        mutex.release();
        //System.out.println("A child put a ball");
        items.release();
    }

    private void quiet() {
        System.out.println(this.idChild + "  start quiet");
        for(int i = 0; i < timeQuietCounter; timeQuietCounter--) {
            for (int j = 0; j < 50000; j++) {
                System.out.println(j);
                for (int k = 0; k < 100000; k++) {
                    int x = 100/100 + k - j + i;
                }
            }
            //System.out.println(idChild + " is quiet " + timeQuiet);
        }
        System.out.println(this.idChild + "  stop quiet");
    }

    private void play() {
        System.out.println(this.idChild + "  start to play");
        for(int i = 0; i < timePlayingCounter; timePlayingCounter--) {
            for(int j = 0; j < 50000; j++) {
                System.out.println(j);
                for(int k = 0; k < 100000; k++) {
                    int x = 100/100 + k - j + i;
                }
            }
            //System.out.println(idChild + " is playing " + timePlaying);
        }
        System.out.println(this.idChild + "  stop play");
    }

    void MySleep() {
        Date date = new Date();
        int seconds = date.getSeconds() + timePlaying;
        while(seconds > date.getSeconds()) {
            System.out.println("LOOOOP");
            for(int i = 0; i < timePlayingCounter; timePlayingCounter--) {
                for(int j = 0; j < 50000; j++) {
                    //System.out.println(j);
                    for(int k = 0; k < 100000; k++) {
                        int x = 100/100 + k - j + i;
                    }
                }
                //System.out.println(idChild + " is playing " + timePlaying);
            }
        }
    }

    public int getTimePlayingCounter() {
        return timePlayingCounter;
    }

    public void setTimePlayingCounter(int timePlayingCounter) {
        this.timePlayingCounter = timePlayingCounter;
    }

    public int getTimeQuietCounter() {
        return timeQuietCounter;
    }

    public void setTimeQuietCounter(int timeQuietCounter) {
        this.timeQuietCounter = timeQuietCounter;
    }

    public Semaphore getItems() {
        return items;
    }

    public void setItems(Semaphore items) {
        this.items = items;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public void setMutex(Semaphore mutex) {
        this.mutex = mutex;
    }

    public Semaphore getSpaces() {
        return spaces;
    }

    public void setSpaces(Semaphore spaces) {
        this.spaces = spaces;
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
