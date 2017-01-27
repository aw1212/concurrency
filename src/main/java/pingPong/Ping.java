package pingPong;

public class Ping extends Thread {
    PingPong pp;

    public Ping(PingPong pp) {
        this.pp = pp;
        start();
    }

    @Override
    public void run() {
        pp.ping();
    }

}
