from exchanges import huobipro

if __name__ == '__main__':
    market_client = huobipro.MarketClient()
    last_trade = market_client.get_last_trade("btcusdt")
    print(last_trade.ts)

    market_channel_client = huobipro.MarketChannelClient()
    market_channel_client.open()


    def on_candlestick_sub(data):
        print("--OnCandlestick Sub--")
        print(data.id)
        print(data.close)


    market_channel_client.subscript_candlestick("btcusdt", "1min", on_candlestick_sub)


    def on_candlestick_req(data):
        print("--OnCandlestick Req--")
        print(data.data[0].id)
        print(data.data[0].close)


    market_channel_client.request_candlestick("btcusdt", "1min", on_candlestick_req)

    asset_order_v2_channel_client = huobipro.AssetOrderV2ChannelClient()


    def on_order(data):
        print(data.event_type)


    def on_trade(data):
        print(data.trade_id)
        print(data.trade_price)


    asset_order_v2_channel_client.set_handler(on_order, on_trade)
    asset_order_v2_channel_client.open()
    asset_order_v2_channel_client.subscript_order("btcusdt", lambda data: print(data.code))
    asset_order_v2_channel_client.subscript_trade_clearing("btcusdt", lambda data: print(data.code))
