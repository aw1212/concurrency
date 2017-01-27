package blockingQueue;

import java.util.ArrayList;
import java.util.List;

public class BoundedBlockingQueue<E> {

    private final int capacity;
    private final List<E> storage;

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        storage = new ArrayList<>(capacity);
    }

    public synchronized void put(E thing) throws InterruptedException {
        while (storage.size() >= capacity) {
            System.out.println("queue is full");
            wait();
        }
        storage.add(thing);
        System.out.println("added thing");
        notifyAll();
    }

    public synchronized E get() throws InterruptedException {
        while (storage.isEmpty()) {
            System.out.println("queue is empty");
            wait();
        }
        E removed = storage.remove(0);
        System.out.println("removed thing");
        notifyAll();
        return removed;
    }

}
