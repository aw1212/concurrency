package pingPong;

public class PingPongRunner {

    public static void main(String[] args) {
        PingPong pingPong = new PingPong();
        for (int i = 0; i < 3; i++) {
            new Ping(pingPong);
        }
        for (int i = 0; i < 3; i++) {
            new Pong(pingPong);
        }
    }

}
