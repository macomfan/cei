#### <json_value>
```xml
<json_value key="xxx" value="xxx" />
```
* Attributes  
 key:  
 value  
 copy  
  
  
#### <json_object>
```xml
<json_object key="xxx" value="xxx" model="xxxModel"/>
```
* Attributes  
> key:  
> value  
> copy  


### Examples

#### Example 1 - Get value from Json Object

```json
{
  "symbol": "btcusdt",
  "data": {
      "price": 123.456,
      "volume": 1.0
  }
}
```
```xml
<sdk>
    <model name="LastTrade">
        <string name="symbol"/>
        <decimal name="price"/>
        <decimal name="volume"/>
    </model>
</sdk>
```
```xml
<json_parser model="LastPrice">
    <json_value key="symbol" value="{symbol}"/>
    <json_object key="data">
        <json_value key="price" value="{price}"/>
        <json_value key="volume" value="{volume}"/>
    </json_object>
</json_parser>
```

#### Example 2 - Get object from Json Object

```json
{
  "symbol": "btcusdt",
  "data": {
      "price": 123.456,
      "volume": 1.0
  },
  "detail": {
    "base": "btc",
    "quote": "usdt"
  }
}
```
```xml
<sdk>
    <model name="LastTradeWithData">
        <string name="symbol"/>
        <object name="data" refer="LastTradeData"/>
        <object name="detail" refer="Detail"/>
    </model>
    
    <model name="LastTradeData">
        <decimal name="price"/>
        <decimal name="volume"/>
    </model>

    <model name="Detail">
        <string name="base"/>
        <string name="quote"/>
    </model>
</sdk>
```
```xml
<json_parser model="LastTradeWithData">
    <json_value key="symbol" value="{symbol}"/>
    <json_object key="data" value="{data}" model="LastTradeData">
        <json_value key="price" value="{price}"/>
        <json_value key="volume" value="{volume}"/>
    </json_object>
    <json_object key="detail" value="{detail}" model="Detail">
        <json_value key="base" value="{base}"/>
        <json_value key="quote" value="{quote}"/>
    </json_object>
</json_parser>
```
#### Example 3 - Get data array in Json Object
```json
{
  "exchange": "test",
  "currencies": ["btc", "eth"],
  "fee": ["1.0", "2.0"]
}
```
```xml
<sdk>
    <model name="Currency">
        <string name="exchange"/>
        <string_array name="currencies"/>
        <decimal_array name="fee"/>
    </model>
</sdk>
```
```xml
<json_parser model="Currency">
    <json_value key="exchange" value="{exchange}"/>
    <json_value key="currencies" value="{currencies}"/>
    <json_value key="fee" value="{fee}"/>
</json_parser>
```
#### Example 4 - Get object in Json Array
```json
{
	"bids": [{
		"price": 1.0,
		"volume": 1.0
	}, {
		"price": 2.0,
		"volume": 2.0
	}],
	"asks": [{
		"price": 1.0,
		"volume": 1.0
	}, {
		"price": 2.0,
		"volume": 2.0
	}]
}
```
```xml
<sdk>
    <model name="OrderBook">
        <object_array name="bids" refer="OrderEntity"/>
        <object_array name="asks" refer="OrderEntity"/>
    </model>

    <model name="OrderEntity">
        <decimal name="price"/>
        <decimal name="volume"/>
    </model>
</sdk>
```
```xml
<json_parser model="OrderBook">
    <json_object_array key="bids" value="{bids}" model="OrderEntity">
        <json_value key="price" value="{price}"/>
        <json_value key="volume" value="{volume}"/>
    </json_object_array>
    <json_object_array key="asks" value="{asks}" model="OrderEntity">
        <json_value key="price" value="{price}"/>
        <json_value key="volume" value="{volume}"/>
    </json_object_array>
</json_parser>
```
#### Example 5 - Get object array in Json Root
```json
[{
		"symbol": "btcusdt",
		"price": "1.0",
		"volume": "1.0"
	},
	{
		"symbol": "btcusdt",
		"price": "2.0",
		"volume": "2.0"
	}
]
```
```xml
<sdk>
    <model name="HistoricalTrade">
        <object_array name="data" refer="TradeEntity"/>
    </model>

    <model name="TradeEntity">
        <string name="symbol"/>
        <decimal name="price"/>
        <decimal name="volume"/>
    </model>
</sdk>
```
```xml
<json_parser model="HistoricalTrade">
    <json_object_array value="{data}" model="TradeEntity">
        <json_value key="symbol" value="{symbol}"/>
        <json_value key="price" value="{price}"/>
        <json_value key="volume" value="{volume}"/>
    </json_object_array>
</json_parser>
```

#### Example 6 - Get data array in Json array
```json
{
	"bids": [
		["1.0", "1.0"],
		["2.0", "2.0"]
	],
	"asks": [
		["3.0", "3.0"],
		["4.0", "4.0"]
	]
}
```
```xml
<sdk>
    <model name="OrderDepth">
        <object_array name="bids" refer="DepthEntity"/>
        <object_array name="asks" refer="DepthEntity"/>
    </model>
    <model name="DepthEntity">
        <string_array name="data"/>
    </model>
</sdk>
```
```xml
<json_parser model="OrderDepth">
    <json_object_array key="bids" value="{bids}" model="DepthEntity">
        <json_value key="\" value="data"/>
    </json_object_array>
    <json_object_array key="asks" value="{asks}" model="DepthEntity">
        <json_value key="\" value="data"/>
    </json_object_array>
</json_parser>
```
#### Example 7 - Use copy attribute


#### Example 8 - Use Json Path for normal value


#### Example 9 - Use Json Path for value in array








