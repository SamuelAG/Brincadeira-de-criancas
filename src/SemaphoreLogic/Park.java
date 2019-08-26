package SemaphoreLogic;

import java.util.concurrent.Semaphore;

public class Park {
    private Semaphore semaphore;
    int mutex;

    public Park(int maxBalls) {
        semaphore = new Semaphore(maxBalls);
        mutex = 0;
    }

}
