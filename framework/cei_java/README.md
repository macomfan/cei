##CEI Java

### Getting Started:
1. You must use [CEI](https://github.com/macomfan/cei) to generate the SDK code in java.  
2. Find the Java code in \<The output folder>
3. Add the SDK code to your code or open the cei_java as new project directly.

### Where is the Client
Import the required package like cn.ma.cei.sdk.exchanges.\[exchange name] which defined the Client class.  
The \[exchange name] is defined in xml \<sdk> element.  
e.g. To involve the huobi interface:
```java
import cn.ma.cei.exchanges.huobipro;
```

### Restful Interface
```java
RestfulOptions option = new RestfulOptions();
// Set option here
huobipro.MarketClient client = new huobipro.MarketClient(option); // option can be ignored
huobipro.Candlestick candlestick = client.getCandlestick("btcustd", "1min", null);
// Process candlestick returned by getCandlestick
```

### WebSocket Interface
```java
WebSocketOptions option = new WebSocketOptions();
// Set option here
huobipro.AssetChannelClient assetChannelClient = new huobipro.AssetChannelClient(option); // option can be ignored
assetChannelClient.open();
assetChannelClient.subscriptOrder("btcusdt", data -> {
    // Process data hear
});
```