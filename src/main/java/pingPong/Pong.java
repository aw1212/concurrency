package pingPong;

public class Pong extends Thread {
    PingPong pp;

    public Pong(PingPong pp) {
        this.pp = pp;
        start();
    }

    @Override
    public void run() {
        pp.pong();
    }
}
