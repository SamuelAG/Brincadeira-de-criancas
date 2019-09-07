package SemaphoreLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * mutex = Semaphore(1)
 * items = Semaphore(0)
 * spaces = Semaphore(buffer.size())
 *
 * When a consumer removes an item it should increment (up()) spaces. When a producer
 * arrives it should decrement (down()) spaces, at which point it might block until the next
 * consumer signals.
 *
 *
 * Consumer solution
 *
 * items.down()
 * mutex.down()
 * event = buffer.get()
 * mutex.up()
 * spaces.up()
 *
 * Producer solution
 * spaces.wait()
 * mutex.wait()
 * buffer.add(event)
 * mutex.signal()
 * items.signal()
 */

public class Park {
    private Semaphore spaces;
    private Semaphore items;
    private Semaphore mutex;
    private List<Child> list;

    public Park(int maxBalls) {
        spaces = new Semaphore(maxBalls);
        items = new Semaphore(0);
        mutex = new Semaphore(1);
        list = new ArrayList<>();
    }

    public void addChild(Child child) {
        child.setSpaces(spaces);
        child.setItems(items);
        child.setMutex(mutex);
        list.add(child);
        child.start();
    }

    public Semaphore getSpaces() {
        return spaces;
    }

    public void setSpaces(Semaphore spaces) {
        this.spaces = spaces;
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

    public List<Child> getList() {
        return list;
    }

    public void setList(List<Child> list) {
        this.list = list;
    }
}
