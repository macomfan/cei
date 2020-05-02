import cn.ma.cei.exchanges.test;

import java.math.BigDecimal;

public class testMain {
    public static void main(String[] args) throws InterruptedException {
//        test.WSClient wsClient = new test.WSClient();
//        wsClient.connect("request", "echo");
//        wsClient.requestEcho("bbbb", new BigDecimal("111"), 111L, false, (echo)->{
//            System.err.println(echo.name);
//        });
        test.WSClient wsClient = new test.WSClient();
        wsClient.open("event", "test", connection -> {
            System.out.println("connected");
        });
        wsClient.requestEcho("abc", new BigDecimal("123.123"), 123L, false, (data -> {
            System.out.println("On Echo1");
        }));
        wsClient.requestEcho("abc", new BigDecimal("123.123"), 123L, false, (data -> {
            System.out.println("On Echo2");
        }));
        Thread.sleep(1000);
        wsClient.requestEcho("abc", new BigDecimal("123.123"), 123L, false, (data -> {
            System.out.println("On Echo3");
        }));
    }
}
