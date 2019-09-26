package SemaphoreLogic;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.concurrent.Semaphore;

public class Child extends Thread {
    private int idChild;
    private boolean ball;
    private int timePlaying;
    private int timeQuiet;
    private int timePlayingCounter;
    private int timeQuietCounter;
    private CallBack callBack;
    private ChildState childState;

    public Child(){}


    @Override
    public void run() {
        while(true) {
            timeQuietCounter = timeQuiet;
            timePlayingCounter = timePlaying;
            if(ball) {
                cpuBound(timePlayingCounter);
                putAball();
                ball = false;
                callBack.quiet(this);
                cpuBound(timeQuietCounter);
            } else {
                getAball();
                callBack.playing(this);
                ball = true;
            }
            super.run();
        }
    }

    void getAball() {
        if(Park.items.availablePermits() == 0) {
            callBack.addToLog("O cesto está sem bola! Criança " + idChild + " Bloqueada!\n");
            callBack.blockNoBall(this);
        }
        try {
            Park.items.acquire();
            callBack.addToLog("ACORDOU " + idChild);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Park.mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Basket.balls--;
        callBack.getABall();
        Park.mutex.release();
        Park.spaces.release();
        callBack.addToLog("Criança " + this.idChild + "  pegou uma bola do cesto!\nBolas no cesto: " + Basket.balls + "\n");
    }

    void putAball() {
        if (Park.spaces.availablePermits() == 0) {
            callBack.addToLog("O cesto está cheio! Criança " + idChild + " Bloqueada!\n");
            callBack.blockFullBasket(this);
        }
        try {
            Park.spaces.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Park.mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Basket.balls++;
        callBack.putABall();
        Park.mutex.release();
        Park.items.release();
        callBack.addToLog(Park.items.availablePermits() + "Items\n");
        callBack.addToLog("Criança " + this.idChild + "  colocou uma bola no cesto!\nBolas no cesto: " + Basket.balls + "\n");

    }

    public void cpuBound(long tempo) {
        if(ball) callBack.addToLog("Criança " + this.idChild + "  está brincando!\n");
        else callBack.addToLog("Criança " + this.idChild + "  está quieta\n");

        long tempoAtual = System.currentTimeMillis();
        long tempoDecorrido = 0, milisegundos = 0;
        while (tempoDecorrido < tempo) {
            milisegundos = (System.currentTimeMillis() - tempoAtual);
            if( (milisegundos / 1000) > tempoDecorrido) {
                if(ball) timePlayingCounter--;
                else timeQuietCounter--;
                callBack.methodToCallBack();
            }
            if(ball && timePlayingCounter == 0) {
                timePlayingCounter = timePlaying;
                break;
            }
            if(ball && timeQuietCounter == 0) {
                timeQuietCounter = timeQuiet;
                break;
            }
            tempoDecorrido = milisegundos / 1000;
        }

        if(ball) callBack.addToLog("Criança " + this.idChild + "  terminou de brincar :(\n");
        else callBack.addToLog("Criança " + this.idChild + "  não está mais quieta!\n");
    }

    private void quiet() {
        //callBack.addToLog(this.idChild + "  está quieta\n");
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
        //callBack.addToLog(this.idChild + "  não está mais quieta!\n");
    }

    private void play() {
        //callBack.addToLog(this.idChild + "  está brincando!\n");
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
        //callBack.addToLog(this.idChild + "  terminou de brincar :(\n");
    }

    public ChildState getChildState() {
        return childState;
    }

    public void setChildState(ChildState childState) {
        this.childState = childState;
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
