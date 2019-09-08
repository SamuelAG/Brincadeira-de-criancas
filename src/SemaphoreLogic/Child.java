package SemaphoreLogic;

import java.util.concurrent.Semaphore;

public class Child extends Thread {
    private int idChild;
    private boolean ball;
    private int timePlaying;
    private int timeQuiet;
    private Semaphore spaces;
    private Semaphore items;
    private Semaphore mutex;

    private int auxTp;
    private int auxTq;

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
            timeQuiet = 20;
            timePlaying = 20;
            System.out.println(idChild + " is starting, timePlaying: " + timePlaying + ", timeQuiet: " + timeQuiet);
            if(ball) {
                play();
                putAball();
                ball = false;
                quiet();
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
            System.out.println("Awaiting permission to get a ball");
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gets permission");
        Basket.balls--;
        mutex.release();
        System.out.println("A child get a ball");
        spaces.release();
    }

    void putAball() {
        try {
            spaces.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        try {
            System.out.println("Awaiting permission to put a ball");
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Basket.balls++;
        mutex.release();
        System.out.println("A child put a ball");
        items.release();
    }

    private void quiet() {
        auxTq = timeQuiet;
        System.out.println(this.idChild + "  start quiet");
        for(int i = 0; i < timeQuiet; timeQuiet--) {
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
        auxTp = timePlaying;
        System.out.println(this.idChild + "  start to play");
        for(int i = 0; i < timePlaying; timePlaying--) {
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

    public int getAuxTp() {
        return auxTp;
    }

    public void setAuxTp(int auxTp) {
        this.auxTp = auxTp;
    }

    public int getAuxTq() {
        return auxTq;
    }

    public void setAuxTq(int auxTq) {
        this.auxTq = auxTq;
    }

}
