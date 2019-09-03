package SemaphoreLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Park {
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex;
    private List<Child> list;

    public Park(int maxBalls) {
        empty = new Semaphore(maxBalls);
        full = new Semaphore(0);
        mutex = new Semaphore(1);
        list = new ArrayList<>();
    }

    public void addChild(Child child) {
        child.setEmpty(empty);
        child.setFull(full);
        child.setMutex(mutex);
        list.add(child);
        child.start();
    }

    public Semaphore getEmpty() {
        return empty;
    }

    public void setEmpty(Semaphore empty) {
        this.empty = empty;
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
