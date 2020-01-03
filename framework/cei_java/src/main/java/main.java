
import cn.ma.cei.exchanges.cei;
import cn.ma.cei.impl.RestfulOptions;
import cn.ma.cei.impl.RestfulRequest;
import java.math.BigDecimal;

//import cn.ma.cei.sdk.exchanges.binance.models.ExchangeInfo;
//import cn.ma.cei.sdk.exchanges.bittrex.models.Markets;
//import cn.ma.cei.sdk.exchanges.bittrex.services.BittrexClient;
//import cn.ma.cei.sdk.exchanges.huobipro.models.LastTrade;
public class main {

    @FunctionalInterface
    public interface MY {

        void fun();
    }

    public static void main(String[] args) {
//        RestfulOptions options = new RestfulOptions();
//        RestfulRequest request = new RestfulRequest(options);
//        options.apiKey = "ABC";
//        options.secretKey = "abc";
//        request.setUrl("https://127.0.0.1");
//        request.setTarget("/Target");
//        request.setMethod(RestfulRequest.Method.GET);
//        request.addQueryString("Test", "Test");
//        test.Signature.restful(request, options);
//        RestfulOptions options = new RestfulOptions();
//        options.apiKey = "ABC";
//        options.secretKey = "abc";
        RestfulOptions options = new RestfulOptions();
        options.apiKey = "ABC";
        options.secretKey = "abc";
        cei.TestClient testClient = new cei.TestClient(options);
        System.out.println(testClient.getTimestamp().ts);

//        cn.ma.cei.sdk.exchanges.binance.services.MarketClient client = new cn.ma.cei.sdk.exchanges.binance.services.MarketClient();
//        ExchangeInfo ex = client.getExchangeInfo();
//        int a = 0;
//        cn.ma.cei.sdk.exchanges.huobipro.services.MarketClient client = new cn.ma.cei.sdk.exchanges.huobipro.services.MarketClient();
//        LastTrade lt = client.getLastTrade("btcusdt");
//        int a = 0;
//        BittrexClient client = new BittrexClient();
//        Markets markets = client.getMarkets();
//        int a = 0;
        //cn.ma.cei.sdk.clients.binance.MarketClient client1 = new cn.ma.cei.sdk.clients.binance.MarketClient();
        // System.out.println(client1.getKLine());
    }
}
