
import cn.ma.cei.exchanges.cei;
import cn.ma.cei.exchanges.huobipro;
import cn.ma.cei.impl.JsonWrapper;
import cn.ma.cei.impl.RestfulOptions;
import cn.ma.cei.impl.RestfulRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import java.util.List;

//import cn.ma.cei.sdk.exchanges.binance.models.ExchangeInfo;
//import cn.ma.cei.sdk.exchanges.bittrex.models.Markets;
//import cn.ma.cei.sdk.exchanges.huobipro.models.LastTrade;
public class main {

    static class TestBase {

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

    public static void main(String[] args) throws InterruptedException {
        String jsonData = "[{\n" +
                "\t\"price\": \"0.1\",\n" +
                "\t\"qty\": \"0.1\"\n" +
                "}, {\n" +
                "\t\"price\": \"0.2\",\n" +
                "\t\"qty\": \"0.2\"\n" +
                "}]";
        JsonWrapper jsonParse = JsonWrapper.parseFromString(jsonData);


//        TestWebsocket tws = new TestWebsocket();
//        tws.start("aaa");
//        Thread.sleep(1000);
//        tws.subscriptCandlestick(null, null, null);
        
//        String data = "{\n"
//      + "  \"status\": \"ok\",\n"
//      + "  \"ch\": \"market.btcusdt.kline.1day\",\n"
//      + "  \"ts\": 1550197964381,\n"
//      + "  \"data\": [\n"
//      + "    {\n"
//      + "      \"id\": 1550160000,\n"
//      + "      \"open\": 3600.770000000000000000,\n"
//      + "      \"close\": 3602.380000000000000000,\n"
//      + "      \"low\": 3575.000000000000000000,\n"
//      + "      \"high\": 3612.190000000000000000,\n"
//      + "      \"amount\": 4562.766744240224615720,\n"
//      + "      \"vol\": 16424799.084153959200406053550000000000000000,\n"
//      + "      \"count\": 28891\n"
//      + "    },\n"
//      + "    {\n"
//      + "      \"id\": 1550073600,\n"
//      + "      \"open\": 3594.850000000000000000,\n"
//      + "      \"close\": 3600.790000000000000000,\n"
//      + "      \"low\": 3570.000000000000000000,\n"
//      + "      \"high\": 3624.300000000000000000,\n"
//      + "      \"amount\": 14514.049885396099061006,\n"
//      + "      \"vol\": 52311364.004324447892964650980000000000000000,\n"
//      + "      \"count\": 99003\n"
//      + "    }\n"
//      + "  ]\n"
//      + "}";
        String data = "{\n"
                + "	\"name\": \"jqw\",\n"
                + "	\"age\": 18,\n"
                + "	\"data\": {\n"
                + "		\"aaa\": \"aaa\",\n"
                + "		\"bbb\": [\"3\", \"4\"]\n"
                + "	},\n"
                + "	\"object_array\": [{\n"
                + "		\"d\": 1\n"
                + "	}, {\n"
                + "		\"d\": 2\n"
                + "	}],\n"
                + "	\"array\": [\"1\", \"2\"]\n"
                + "}";
        JSONObject jsonObject = (JSONObject) JSON.parse(data);
        Object jjj = jsonObject.get("status");
        JsonWrapper currentJsonWrapper = JsonWrapper.parseFromString(data);
        //JsonWrapper.JsonPath jsp = new JsonWrapper.JsonPath(currentJsonWrapper, "\\ch\\data\\[0]\\id");
        try {
            JsonWrapper a = currentJsonWrapper.getObject("\\data");
            System.err.println("got");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

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
        options.apiKey = "cf06e0de-e36d577a-vf25treb80-3ba8a";
        options.secretKey = "4ce65690-8beb908c-62d43826-dfa2e";
//        huobipro.AccountClient accountClient = new huobipro.AccountClient(options);
//        huobipro.Account account = accountClient.getAccounts();

//        huobipro.MarketClient hbClient = new huobipro.MarketClient(options);
//        huobipro.BestQuote bq = hbClient.getBestQuote("btcusdt");
//        System.out.println("huobi");
//        System.out.println(bq.bestBidPrice);
//        System.out.println(bq.bestBidAmount);
//        System.out.println(bq.bestAskPrice);
//        System.out.println(bq.bestAskAmount);
//
//        huobipro.BinanceClient biClient = new huobipro.BinanceClient(options);
//        System.out.println("binance");
//        huobipro.BestQuote bq1 = biClient.getBestQuote("ETHBTC");
//        System.out.println(bq1.bestBidPrice);
//        System.out.println(bq1.bestBidAmount);
//        System.out.println(bq1.bestAskPrice);
//        System.out.println(bq1.bestAskAmount);
//        System.out.println("done");
//        huobipro.Timestamp ts = hbClient.getTimestamp();
//        //huobipro.Symbols data = hbClient.getSymbol();
//        System.err.println(ts.data);
//        huobipro.Candlestick can = hbClient.getCandlestick("btcusdt", "1min", 5l);
//        can.data.forEach(item -> {
//            System.out.println("------");
//            System.out.println(item);
//            System.out.println(item.close);
//            System.out.println(item.open);
//            System.out.println(item.high);
//            System.out.println(item.low);
//        });
        //hbClient.placeOrder("123", "btcusdt", "buy-limit", "1.0", "1.0");

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
