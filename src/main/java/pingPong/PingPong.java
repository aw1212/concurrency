package pingPong;

public class PingPong {
    private boolean timeToPing = true;

    public synchronized void ping() {
        while (!timeToPing) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("ping");
        timeToPing = false;
        notifyAll();
    }

    public synchronized void pong() {
        while (timeToPing) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("pong");
        timeToPing = true;
        notifyAll();
    }

}
