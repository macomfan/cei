import cn.ma.cei.exchanges.test;

import java.math.BigDecimal;

public class testMain {
    public static void main(String[] args) {
        test.MarketClient client = new test.MarketClient();
        test.LastTrade lastTrade = client.getLastTrade("eosusdt");
        System.out.println(lastTrade.symbol);

        test.Order order = client.placeOrder("123", new BigDecimal("112.3"));
        System.out.println(order.orderId.toString());
    }
}
