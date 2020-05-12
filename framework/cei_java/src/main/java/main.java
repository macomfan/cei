import cei.exchanges.binance;
import cei.exchanges.huobipro;
import cei.impl.RestfulOptions;
import cei.impl.RestfulRequest;

import java.util.LinkedList;
import java.util.List;

public class main {

    public static void main(String[] args) {
        RestfulOptions option = new RestfulOptions();
        option.apiKey = "vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A";
        option.secretKey = "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j";
        RestfulRequest request = new RestfulRequest(option);
        request.addQueryString("symbol", "LTCBTC");
//        request.addQueryString("side", "BUY");
//        request.addQueryString("type", "LIMIT");
//        request.addQueryString("timeInForce","GTC");
//        request.addQueryString("quantity","1");
//        request.addQueryString("price","0.1");
//        request.addQueryString("recvWindow","5000");
//        request.addQueryString("timestamp","1499827319559");

//        binance.Procedures.restfulAuth(request, option);
        option.url = "https://127.0.0.1";
        request.setMethod(RestfulRequest.Method.GET);
        request.setTarget("/aaa/aaa");
        huobipro.Procedures.restfulAuth(request, option);

        huobipro.MarketClient marketClient = new huobipro.MarketClient();
        huobipro.LastTrade lastTrade = marketClient.getLastTrade("btcusdt");
        System.out.println(lastTrade.ts);

//        huobipro.MarketChannelClient marketChannelClient = new huobipro.MarketChannelClient();
//        marketChannelClient.open();
//        marketChannelClient.subscriptCandlestick("btcusdt", "1min", data -> {
//            System.out.println("--OnCandlestick Sub--");
//            System.out.println(data.id);
//            System.out.println(data.close);
//        });
//
//        marketChannelClient.requestCandlestick("btcusdt", "1min", data -> {
//            System.out.println("--OnCandlestick Req--");
//            System.out.println(data.data.get(0).id);
//            System.out.println(data.data.get(0).close);
//        });
//
//        huobipro.AssetOrderV2ChannelClient assetOrderV2ChannelClient = new huobipro.AssetOrderV2ChannelClient();
//        assetOrderV2ChannelClient.setHandler(
//                onOrder -> {
//                    System.out.println(onOrder.eventType);
//                    System.out.println(onOrder.orderId);
//                }, onTrade -> {
//                    System.out.println(onTrade.tradeId);
//                    System.out.println(onTrade.tradePrice);
//                });
//
//        assetOrderV2ChannelClient.open();
//        assetOrderV2ChannelClient.subscriptOrder("btcusdt", data -> System.out.println(data.code));
//        assetOrderV2ChannelClient.subscriptTradeClearing("btcusdt", data -> System.out.println(data.code));

        binance.MarketClient binanceMarket = new binance.MarketClient();
        binance.Depth depth = binanceMarket.getDepth("BNBBTC", 10l);
        System.out.println(depth.bids.get(0).price);

        binance.CandlestickChannel candlestickChannelClient = new binance.CandlestickChannel();
        candlestickChannelClient.setOnCandlestickHandler(streamData -> {
            System.out.println(streamData.symbol);
        });
        List<String> symbols = new LinkedList<>();
        symbols.add("bnbbtc");
        symbols.add("ethbtc");
        candlestickChannelClient.open(symbols, "1m");


    }
}
