
import cn.ma.cei.exchanges.cei;
import cn.ma.cei.exchanges.huobipro;
import cn.ma.cei.impl.RestfulOptions;
import cn.ma.cei.impl.RestfulRequest;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;

//import cn.ma.cei.sdk.exchanges.binance.models.ExchangeInfo;
//import cn.ma.cei.sdk.exchanges.bittrex.models.Markets;
//import cn.ma.cei.sdk.exchanges.bittrex.services.BittrexClient;
//import cn.ma.cei.sdk.exchanges.huobipro.models.LastTrade;
public class main {

    static class TestBase{
        public String a = "a";
    }
    
    static class TestCh extends TestBase {
        @JSONField(serialize = false)
        public String b = "b";
    }
    
    @FunctionalInterface
    public interface MY {

        void fun();
    }

    public static void main(String[] args) {
        TestCh c = new TestCh();
        String aaa = JSONObject.toJSONString(c);
        
        
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
        huobipro.MarketClient hbClient = new huobipro.MarketClient(options);
//        huobipro.Timestamp ts = hbClient.getTimestamp();
//        //huobipro.Symbols data = hbClient.getSymbol();
//        System.err.println(ts.data);
        huobipro.Candlestick can = hbClient.getCandlestick("btcusdt", "1min", 5);
        can.data.forEach(item -> {
            System.out.println("------");
            System.out.println(item.amount);
            System.out.println(item.close);
            System.out.println(item.open);
            System.out.println(item.high);
            System.out.println(item.low);
        });
        hbClient.placeOrder("123", "btcusdt", "buy-limit", "1.0", "1.0");

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
