package SemaphoreLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Park {
    private Semaphore semaphore;
    private Semaphore mutex;
    private List<Child> list;

    public Park(int maxBalls) {
        semaphore = new Semaphore(maxBalls);
        mutex = new Semaphore(1);
        list = new ArrayList<>();
    }

    public void addChild(Child child) {
        child.setSemaphore(semaphore);
        child.setMutex(mutex);
        list.add(child);
        child.start();
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public void setMutex(Semaphore mutex) {
        this.mutex = mutex;
    }

    public List<Child> getList() {
        return list;
    }

    public void setList(List<Child> list) {
        this.list = list;
    }
}
