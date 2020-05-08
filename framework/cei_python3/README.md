## CEI Python3

### Getting Started:
1. You must use [CEI](https://github.com/macomfan/cei) to generate the SDK code in java.  
2. Find the Java code in \<The output folder>
3. Add the SDK code to your code or open the cei_java as new project directly.

### Where is the Client
Import the required package like "from exchanges import \[exchange name]" which defined the Client class.  
The \[exchange name] is defined in xml \<sdk> element.  
e.g. To involve the huobi interface:
```python
from exchanges import huobipro
```

### Restful Interface
```python
option = RestfulOptions()
# Set option here
market_client = huobipro.MarketClient(option) #option can be ignored
candlestick = market_client.get_candlestick("btcusdt", "1min", None)
# Process candlestick returned by getCandlestick
```

### WebSocket Interface
```python
option = WebSocketOptions()
# Set option here
asset_channel_client = huobipro.AssetChannelClient(option)
asset_channel_client.open()

def on_order(order):
    # Process data hear
    pass

asset_channel_client.subscript_order("btcudtt", on_order)
```