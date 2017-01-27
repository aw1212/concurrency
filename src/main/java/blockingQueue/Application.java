package blockingQueue;

public class Application {

    private final BoundedBlockingQueue<String> items;

    public Application() {
        items = new BoundedBlockingQueue<>(1);
    }

    public void run() throws InterruptedException {
        Thread adder1 = new Adder();
        Thread adder2 = new Adder();
        adder1.start();
        Thread getter1 = new Getter();
        Thread getter2 = new Getter();
        getter1.start();
        getter2.start();
        new Getter().start();
        new Getter().start();
        new Getter().start();
        while(adder1.isAlive() && adder2.isAlive() && getter1.isAlive() && getter2.isAlive()) {
        }
        System.out.println("all done");
    }

    private class Adder extends Thread {

        @Override
        public void run() {
            try {
                items.put("bla");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    private class Getter extends Thread {

        @Override
        public void run() {
            try {
                items.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
