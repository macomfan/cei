
public class main {
    @FunctionalInterface
    public interface MY {
        void fun();
    }
    
    public static void main(String[] args) {
        cn.ma.cei.sdk.clients.huobi.MarketClient client = new cn.ma.cei.sdk.clients.huobi.MarketClient();
        System.out.println(client.getHistoryKLine());

        cn.ma.cei.sdk.clients.binance.MarketClient client1 = new cn.ma.cei.sdk.clients.binance.MarketClient();
       // System.out.println(client1.getKLine());
    }
}
