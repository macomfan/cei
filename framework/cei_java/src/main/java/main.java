
import cn.ma.cei.exchanges.test;
import cn.ma.cei.impl.RestfulOptions;
import cn.ma.cei.impl.RestfulRequest;




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
        RestfulOptions options = new RestfulOptions();
        RestfulRequest request = new RestfulRequest(options);
        request.setMethod(RestfulRequest.Method.GET);
        test.Signature.restful(request, options);
        
        int a = 0;
        
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
