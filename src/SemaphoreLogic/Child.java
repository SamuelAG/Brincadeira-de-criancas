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
    private CallBack callBack;

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
            //System.out.println("Awaiting permission to get a ball");
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("gets permission");
        Basket.balls--;
        callBack.addToLog(this.idChild + "  pegou uma bola do cesto!\nBolas no cesto: " + Basket.balls + "\n");
        callBack.updateBalls();
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
        callBack.addToLog(this.idChild + "  colocou uma bola no cesto!\nBolas no cesto: " + Basket.balls + "\n");
        callBack.updateBalls();
        mutex.release();
        //System.out.println("A child put a ball");
        items.release();
    }

    private void quiet() {
        callBack.addToLog(this.idChild + "  está quieta\n");
        for(int i = 0; i < timeQuietCounter; timeQuietCounter--) {
            for (int j = 0; j < 50000; j++) {
                System.out.println(j);
                for (int k = 0; k < 100000; k++) {
                    int x = 100/100 + k - j + i;
                }
            }
            //System.out.println(idChild + " is quiet " + timeQuiet);
            callBack.methodToCallBack();
        }
        callBack.addToLog(this.idChild + "  não está mais quieta!\n");
    }

    private void play() {

        callBack.addToLog(this.idChild + "  está brincando!\n");
        for(int i = 0; i < timePlayingCounter; timePlayingCounter--) {
            for(int j = 0; j < 50000; j++) {
                System.out.println(j);
                for(int k = 0; k < 100000; k++) {
                    int x = 100/100 + k - j + i;
                }
            }
            //System.out.println(idChild + " is playing " + timePlaying);
            callBack.methodToCallBack();
        }
        callBack.addToLog(this.idChild + "  terminou de brincar :(\n");
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
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
