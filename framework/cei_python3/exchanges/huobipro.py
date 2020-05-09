from impl.ceiutils import CEIUtils
from impl.jsonchecker import JsonChecker
from impl.jsonwrapper import JsonWrapper
from impl.restfulconnection import RestfulConnection
from impl.restfuloptions import RestfulOptions
from impl.restfulrequest import RestfulRequest
from impl.stringwrapper import StringWrapper
from impl.websocketconnection import WebSocketConnection
from impl.websocketevent import WebSocketEvent
from impl.websocketoptions import WebSocketOptions


class SymbolsData:
    def __init__(self):
        self.base_currency = None
        self.quote_currency = None
        self.price_precision = None
        self.amount_precision = None
        self.symbol_partition = None
        self.symbol = None
        self.state = None
        self.value_precision = None
        self.min_order_amt = None
        self.max_order_amt = None
        self.min_order_value = None
        self.leverage_ratio = None


class CandlestickData:
    def __init__(self):
        self.id = None
        self.amount = None
        self.count = None
        self.open = None
        self.close = None
        self.low = None
        self.high = None
        self.vol = None


class OrderID:
    def __init__(self):
        self.status = None
        self.data = None


class Timestamp:
    def __init__(self):
        self.timestamp = None


class AggregatedMarketData:
    def __init__(self):
        self.id = None
        self.amount = None
        self.count = None
        self.open = None
        self.close = None
        self.low = None
        self.high = None
        self.vol = None
        self.bid_price = None
        self.bid_size = None
        self.ask_price = None
        self.ask_size = None


class Currencies:
    def __init__(self):
        self.status = None
        self.data = None


class Quote:
    def __init__(self):
        self.price = None
        self.amount = None


class Depth:
    def __init__(self):
        self.status = None
        self.ch = None
        self.ts = None
        self.bids = None
        self.asks = None


class Trade:
    def __init__(self):
        self.id = None
        self.trade_id = None
        self.price = None
        self.amount = None
        self.direction = None
        self.ts = None


class Candlestick:
    def __init__(self):
        self.status = None
        self.data = None


class OrderUpdate:
    def __init__(self):
        self.event_type = None
        self.symbol = None
        self.order_id = None
        self.client_order_id = None
        self.order_price = None
        self.order_size = None
        self.type = None
        self.order_status = None
        self.order_create_time = None
        self.trade_price = None
        self.trade_volume = None
        self.trade_id = None
        self.trade_time = None
        self.aggressor = None
        self.remain_amt = None
        self.last_act_time = None


class AccountData:
    def __init__(self):
        self.id = None
        self.state = None
        self.type = None
        self.subtype = None


class Account:
    def __init__(self):
        self.status = None
        self.data = None


class Symbols:
    def __init__(self):
        self.status = None
        self.data = None


class LastTrade:
    def __init__(self):
        self.status = None
        self.ch = None
        self.ts = None
        self.ts_in_tick = None
        self.id_in_tick = None
        self.data = None


class BestQuote:
    def __init__(self):
        self.status = None
        self.ch = None
        self.ts = None
        self.best_bid_price = None
        self.best_bid_amount = None
        self.best_ask_price = None
        self.best_ask_amount = None


class MarketClient:
    def __init__(self, option=None):
        self.__option = RestfulOptions()
        self.__option.url = "https://api.huobi.so"
        if option is not None:
            self.__option.set_from(option)

    def get_timestamp(self):
        request = RestfulRequest(self.__option)
        request.set_target("/v1/common/timestamp")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        json_checker = JsonChecker()
        json_checker.check_not_equal("stauts", "ok", root_obj)
        json_checker.report_error()
        timestamp_var = Timestamp()
        timestamp_var.timestamp = root_obj.get_int("data")
        return timestamp_var

    def get_symbol(self):
        request = RestfulRequest(self.__option)
        request.set_target("/v1/common/symbols")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        json_checker = JsonChecker()
        json_checker.check_not_equal("stauts", "ok", root_obj)
        json_checker.report_error()
        symbols_var = Symbols()
        symbols_var.status = root_obj.get_string("status")
        obj = root_obj.get_array("data")
        for item in obj.array():
            symbols_data_var = SymbolsData()
            symbols_data_var.base_currency = item.get_string("base-currency")
            symbols_data_var.quote_currency = item.get_string("quote-currency")
            symbols_data_var.price_precision = item.get_int("price-precision")
            symbols_data_var.amount_precision = item.get_int("amount-precision")
            symbols_data_var.symbol_partition = item.get_string("symbol-partition")
            symbols_data_var.symbol = item.get_string("symbol")
            symbols_data_var.state = item.get_string("state")
            symbols_data_var.value_precision = item.get_int("value-precision")
            symbols_data_var.min_order_amt = item.get_decimal("min-order-amt")
            symbols_data_var.max_order_amt = item.get_decimal("max-order-amt")
            symbols_data_var.min_order_value = item.get_decimal("min-order-value")
            symbols_data_var.leverage_ratio = item.get_int("leverage-ratio")
            if symbols_var.data is None:
                symbols_var.data = list()
            symbols_var.data.append(symbols_data_var)
        return symbols_var

    def get_currencies(self):
        request = RestfulRequest(self.__option)
        request.set_target("/v1/common/currencys")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        currencies_var = Currencies()
        currencies_var.status = root_obj.get_string("status")
        currencies_var.data = root_obj.get_string_array("data")
        return currencies_var

    def get_candlestick(self, symbol, period, size):
        request = RestfulRequest(self.__option)
        request.set_target("/market/history/kline")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        request.add_query_string("period", period)
        request.add_query_string("size", str(size))
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        candlestick_var = Candlestick()
        candlestick_var.status = root_obj.get_string("status")
        obj = root_obj.get_array("data")
        for item in obj.array():
            candlestick_data_var = CandlestickData()
            candlestick_data_var.id = item.get_int("id")
            candlestick_data_var.amount = item.get_decimal("amount")
            candlestick_data_var.count = item.get_int("count")
            candlestick_data_var.open = item.get_decimal("open")
            candlestick_data_var.close = item.get_decimal("close")
            candlestick_data_var.low = item.get_decimal("low")
            candlestick_data_var.high = item.get_decimal("high")
            candlestick_data_var.vol = item.get_decimal("vol")
            if candlestick_var.data is None:
                candlestick_var.data = list()
            candlestick_var.data.append(candlestick_data_var)
        return candlestick_var

    def get_last_trade(self, symbol):
        request = RestfulRequest(self.__option)
        request.set_target("/market/trade")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        last_trade_var = LastTrade()
        last_trade_var.status = root_obj.get_string("status")
        last_trade_var.ts = root_obj.get_int("ts")
        last_trade_var.ch = root_obj.get_string("ch")
        obj = root_obj.get_object("tick")
        last_trade_var.ts_in_tick = obj.get_int("ts")
        last_trade_var.id_in_tick = obj.get_int("id")
        obj0 = obj.get_array("data")
        for item in obj0.array():
            trade_var = Trade()
            trade_var.amount = item.get_decimal("amount")
            trade_var.price = item.get_decimal("price")
            trade_var.ts = item.get_int("ts")
            trade_var.id = item.get_int("id")
            trade_var.direction = item.get_string("direction")
            trade_var.trade_id = item.get_int("trade-id")
            if last_trade_var.data is None:
                last_trade_var.data = list()
            last_trade_var.data.append(trade_var)
        return last_trade_var

    def get_market_depth(self, symbol, depth, merge_type):
        request = RestfulRequest(self.__option)
        request.set_target("/market/depth")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        request.add_query_string("depth", str(depth))
        request.add_query_string("type", merge_type)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        depth_var = Depth()
        depth_var.status = root_obj.get_string("status")
        depth_var.ts = root_obj.get_int("ts")
        depth_var.ch = root_obj.get_string("ch")
        obj = root_obj.get_object("tick")
        obj0 = obj.get_array("bids")
        for item in obj0.array():
            quote_var = Quote()
            quote_var.price = item.get_decimal("[0]")
            quote_var.amount = item.get_decimal("[1]")
            if depth_var.bids is None:
                depth_var.bids = list()
            depth_var.bids.append(quote_var)
        obj1 = obj.get_array("asks")
        for item2 in obj1.array():
            quote_var3 = Quote()
            quote_var3.price = item2.get_decimal("[0]")
            quote_var3.amount = item2.get_decimal("[1]")
            if depth_var.asks is None:
                depth_var.asks = list()
            depth_var.asks.append(quote_var3)
        return depth_var

    def get_best_quote(self, symbol):
        request = RestfulRequest(self.__option)
        request.set_target("/market/depth")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        request.add_query_string("depth", "5")
        request.add_query_string("type", "step0")
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        best_quote_var = BestQuote()
        best_quote_var.status = root_obj.get_string("status")
        best_quote_var.ts = root_obj.get_int("ts")
        obj = root_obj.get_object("tick")
        obj0 = obj.get_object("bids")
        obj1 = obj0.get_object("[0]")
        best_quote_var.best_bid_price = obj1.get_decimal("[0]")
        best_quote_var.best_bid_amount = obj1.get_decimal("[1]")
        obj2 = obj.get_object("asks")
        obj3 = obj2.get_object("[0]")
        best_quote_var.best_ask_price = obj3.get_decimal("[0]")
        best_quote_var.best_ask_amount = obj3.get_decimal("[1]")
        best_quote_var.ch = root_obj.get_string("ch")
        return best_quote_var

    def get_latest_aggregated_ticker(self, symbol):
        request = RestfulRequest(self.__option)
        request.set_target("/market/detail/merged")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        aggregated_market_data_var = AggregatedMarketData()
        obj = root_obj.get_object("data")
        aggregated_market_data_var.id = obj.get_int("id")
        aggregated_market_data_var.amount = obj.get_decimal("amount")
        aggregated_market_data_var.count = obj.get_int("count")
        aggregated_market_data_var.open = obj.get_decimal("open")
        aggregated_market_data_var.close = obj.get_decimal("close")
        aggregated_market_data_var.low = obj.get_decimal("low")
        aggregated_market_data_var.high = obj.get_decimal("high")
        aggregated_market_data_var.vol = obj.get_decimal("vol")
        obj0 = obj.get_object("bid")
        aggregated_market_data_var.bid_price = obj0.get_decimal("[0]")
        aggregated_market_data_var.bid_size = obj0.get_decimal("[1]")
        obj1 = obj.get_object("ask")
        aggregated_market_data_var.ask_price = obj1.get_decimal("[0]")
        aggregated_market_data_var.ask_size = obj1.get_decimal("[1]")
        return aggregated_market_data_var


class TradingClient:
    def __init__(self, option=None):
        self.__option = RestfulOptions()
        self.__option.url = "https://api.huobi.so"
        if option is not None:
            self.__option.set_from(option)

    def place_order(self, account_id, symbol, order_type, amount, price):
        request = RestfulRequest(self.__option)
        post_msg = JsonWrapper()
        post_msg.add_json_string("account-Id", account_id)
        post_msg.add_json_string("symbol", symbol)
        post_msg.add_json_string("orderType", order_type)
        post_msg.add_json_string("amount", amount)
        post_msg.add_json_string("price", price)
        request.set_target("/v1/order/orders/place")
        request.set_method(RestfulRequest.Method.POST)
        request.set_post_body(post_msg.to_json_string())
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        order_idvar = OrderID()
        order_idvar.status = root_obj.get_string("status")
        order_idvar.data = root_obj.get_int("data")
        return order_idvar

    def cancel_order(self, order_id):
        request = RestfulRequest(self.__option)
        request.set_target(CEIUtils.string_replace("/v1/order/orders/{0}/submitcancel", str(order_id)))
        request.set_method(RestfulRequest.Method.POST)
        Procedures.restful_auth(request, self.__option)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        order_idvar = OrderID()
        order_idvar.status = root_obj.get_string("status")
        order_idvar.data = root_obj.get_int("data")
        return order_idvar


class AccountClient:
    def __init__(self, option=None):
        self.__option = RestfulOptions()
        self.__option.url = "https://api.huobi.so"
        if option is not None:
            self.__option.set_from(option)

    def get_accounts(self):
        request = RestfulRequest(self.__option)
        request.set_target("/v1/account/accounts")
        request.set_method(RestfulRequest.Method.GET)
        Procedures.restful_auth(request, self.__option)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        account_var = Account()
        account_var.status = root_obj.get_string("status")
        account_data_var = AccountData()
        obj = root_obj.get_object("data")
        account_data_var.id = obj.get_int("id")
        account_data_var.state = obj.get_string("state")
        account_data_var.type = obj.get_string("type")
        account_data_var.subtype = obj.get_string("subtype")
        return account_var


class MarketChannelClient:
    def __init__(self, option=None):
        self.__option = WebSocketOptions()
        self.__option.url = "wss://api.huobi.pro/ws"
        if option is not None:
            self.__option.set_from(option)
        self.__connection = WebSocketConnection(self.__option)

    def open(self):
        on_any_message_event = WebSocketEvent(True)
    
        def on_any_message_event_trigger(msg):
            return True
        on_any_message_event.set_trigger(on_any_message_event_trigger)
    
        def on_any_message_event_event(connection, msg):
            decoded = CEIUtils.gzip(msg.get_bytes())
            msg.upgrade(decoded)
        on_any_message_event.set_event(on_any_message_event_event)
        self.__connection.register_event(on_any_message_event)
        on_ping_event = WebSocketEvent(True)
    
        def on_ping_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("op", "ping", root_obj)
            return json_checker.complete()
        on_ping_event.set_trigger(on_ping_event_trigger)
    
        def on_ping_event_event(connection, msg):
            ts = CEIUtils.get_now("Unix_ms")
            json_result = JsonWrapper()
            json_result.add_json_string("op", "pong")
            json_result.add_json_string("ts", ts)
            connection.send(json_result.to_json_string())
        on_ping_event.set_event(on_ping_event_event)
        self.__connection.register_event(on_ping_event)
        self.__connection.connect("")

    def subscript_candlestick(self, symbol, period, on_candlestick):
        on_candlestick_event = WebSocketEvent(False)
    
        def on_candlestick_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("ch", CEIUtils.string_replace("market.{0}.kline.{1}", symbol, period), root_obj)
            return json_checker.complete()
        on_candlestick_event.set_trigger(on_candlestick_event_trigger)
    
        def on_candlestick_event_event(connection, msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            candlestick_data_var = CandlestickData()
            obj = root_obj.get_object("tick")
            candlestick_data_var.id = obj.get_int("id")
            candlestick_data_var.amount = obj.get_decimal("amount")
            candlestick_data_var.count = obj.get_int("count")
            candlestick_data_var.open = obj.get_decimal("open")
            candlestick_data_var.close = obj.get_decimal("close")
            candlestick_data_var.low = obj.get_decimal("low")
            candlestick_data_var.high = obj.get_decimal("high")
            candlestick_data_var.vol = obj.get_decimal("vol")
            on_candlestick(candlestick_data_var)
        on_candlestick_event.set_event(on_candlestick_event_event)
        self.__connection.register_event(on_candlestick_event)
        ts = CEIUtils.get_now("Unix_ms")
        json = JsonWrapper()
        json.add_json_string("sub", CEIUtils.string_replace("market.{0}.kline.{1}", symbol, period))
        json.add_json_string("id", ts)
        self.__connection.send(json.to_json_string())

    def request_candlestick(self, symbol, period, on_candlestick):
        on_candlestick_event = WebSocketEvent(False)
    
        def on_candlestick_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("ch", CEIUtils.string_replace("market.{0}.kline.{1}", symbol, period), root_obj)
            return json_checker.complete()
        on_candlestick_event.set_trigger(on_candlestick_event_trigger)
    
        def on_candlestick_event_event(connection, msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            candlestick_data_var = CandlestickData()
            obj = root_obj.get_object("tick")
            candlestick_data_var.id = obj.get_int("id")
            candlestick_data_var.amount = obj.get_decimal("amount")
            candlestick_data_var.count = obj.get_int("count")
            candlestick_data_var.open = obj.get_decimal("open")
            candlestick_data_var.close = obj.get_decimal("close")
            candlestick_data_var.low = obj.get_decimal("low")
            candlestick_data_var.high = obj.get_decimal("high")
            candlestick_data_var.vol = obj.get_decimal("vol")
            on_candlestick(candlestick_data_var)
        on_candlestick_event.set_event(on_candlestick_event_event)
        self.__connection.register_event(on_candlestick_event)
        ts = CEIUtils.get_now("Unix_ms")
        json = JsonWrapper()
        json.add_json_string("req", CEIUtils.string_replace("market.{0}.kline.{1}", symbol, period))
        json.add_json_string("id", ts)
        self.__connection.send(json.to_json_string())

    def subscript_market_depth(self, symbol, type_u, on_depth):
        on_depth_event = WebSocketEvent(True)
    
        def on_depth_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("ch", CEIUtils.string_replace("market.{0}.depth.{1}", symbol, type_u), root_obj)
            return json_checker.complete()
        on_depth_event.set_trigger(on_depth_event_trigger)
    
        def on_depth_event_event(connection, msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            depth_var = Depth()
            depth_var.ch = root_obj.get_string("ch")
            obj = root_obj.get_object("tick")
            obj0 = obj.get_array("bids")
            for item in obj0.array():
                quote_var = Quote()
                quote_var.price = item.get_decimal("[0]")
                quote_var.amount = item.get_decimal("[1]")
                if depth_var.bids is None:
                    depth_var.bids = list()
                depth_var.bids.append(quote_var)
            obj1 = obj.get_array("asks")
            for item2 in obj1.array():
                quote_var3 = Quote()
                quote_var3.price = item2.get_decimal("[0]")
                quote_var3.amount = item2.get_decimal("[1]")
                if depth_var.asks is None:
                    depth_var.asks = list()
                depth_var.asks.append(quote_var3)
            on_depth(depth_var)
        on_depth_event.set_event(on_depth_event_event)
        self.__connection.register_event(on_depth_event)
        ts = CEIUtils.get_now("Unix_ms")
        json = JsonWrapper()
        json.add_json_string("sub", CEIUtils.string_replace("market.{0}.depth.{1}", symbol, type_u))
        json.add_json_string("id", ts)
        self.__connection.send(json.to_json_string())

    def request_depth(self, symbol, type_u, on_depth):
        on_depth_event = WebSocketEvent(True)
    
        def on_depth_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("ch", CEIUtils.string_replace("market.{0}.depth.{1}", symbol, type_u), root_obj)
            return json_checker.complete()
        on_depth_event.set_trigger(on_depth_event_trigger)
    
        def on_depth_event_event(connection, msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            depth_var = Depth()
            depth_var.ch = root_obj.get_string("ch")
            obj = root_obj.get_object("tick")
            obj0 = obj.get_array("bids")
            for item in obj0.array():
                quote_var = Quote()
                quote_var.price = item.get_decimal("[0]")
                quote_var.amount = item.get_decimal("[1]")
                if depth_var.bids is None:
                    depth_var.bids = list()
                depth_var.bids.append(quote_var)
            obj1 = obj.get_array("asks")
            for item2 in obj1.array():
                quote_var3 = Quote()
                quote_var3.price = item2.get_decimal("[0]")
                quote_var3.amount = item2.get_decimal("[1]")
                if depth_var.asks is None:
                    depth_var.asks = list()
                depth_var.asks.append(quote_var3)
            on_depth(depth_var)
        on_depth_event.set_event(on_depth_event_event)
        self.__connection.register_event(on_depth_event)
        ts = CEIUtils.get_now("Unix_ms")
        json = JsonWrapper()
        json.add_json_string("sub", CEIUtils.string_replace("market.{0}.depth.{1}", symbol, type_u))
        json.add_json_string("id", ts)
        self.__connection.send(json.to_json_string())


class AssetChannelClient:
    def __init__(self, option=None):
        self.__option = WebSocketOptions()
        self.__option.url = "wss://api.huobi.pro/ws/v2"
        if option is not None:
            self.__option.set_from(option)
        self.__connection = WebSocketConnection(self.__option)

    def open(self):
        on_ping_event = WebSocketEvent(True)
    
        def on_ping_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("action", "ping", root_obj)
            return json_checker.complete()
        on_ping_event.set_trigger(on_ping_event_trigger)
    
        def on_ping_event_event(connection, msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            ts = Timestamp()
            obj = root_obj.get_object("data")
            ts.timestamp = obj.get_int("ts")
            json_result = JsonWrapper()
            json_result.add_json_string("op", "pong")
            json_result.add_json_number("ts", ts.timestamp)
            connection.send(json_result.to_json_string())
        on_ping_event.set_event(on_ping_event_event)
        self.__connection.register_event(on_ping_event)
        self.__connection.connect("")

    def subscript_order(self, symbol, on_order):
        on_order_event = WebSocketEvent(True)
    
        def on_order_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("action", "push", root_obj)
            json_checker.check_equal("ch", CEIUtils.string_replace("orders#{0}", symbol), root_obj)
            return json_checker.complete()
        on_order_event.set_trigger(on_order_event_trigger)
    
        def on_order_event_event(connection, msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            order_update_var = OrderUpdate()
            obj = root_obj.get_object("data")
            order_update_var.event_type = obj.get_string_or_none("eventType")
            order_update_var.symbol = obj.get_string_or_none("symbol")
            order_update_var.order_id = obj.get_int_or_none("orderId")
            order_update_var.client_order_id = obj.get_string_or_none("clientOrderId")
            order_update_var.type = obj.get_string_or_none("type")
            order_update_var.order_price = obj.get_string_or_none("orderPrice")
            order_update_var.order_size = obj.get_string_or_none("orderSize")
            order_update_var.type = obj.get_string_or_none("type")
            order_update_var.order_status = obj.get_string_or_none("orderStatus")
            order_update_var.order_create_time = obj.get_int_or_none("orderCreateTime")
            order_update_var.trade_price = obj.get_string_or_none("tradePrice")
            order_update_var.trade_volume = obj.get_string_or_none("tradeVolume")
            order_update_var.trade_id = obj.get_int_or_none("tradeId")
            order_update_var.trade_time = obj.get_int_or_none("tradeTime")
            order_update_var.aggressor = obj.get_bool_or_none("aggressor")
            order_update_var.remain_amt = obj.get_string_or_none("remainAmt")
            order_update_var.last_act_time = obj.get_string_or_none("lastActTime")
            on_order(order_update_var)
        on_order_event.set_event(on_order_event_event)
        self.__connection.register_event(on_order_event)
        json = JsonWrapper()
        json.add_json_string("action", "sub")
        json.add_json_string("ch", CEIUtils.string_replace("orders#{0}", symbol))
        self.__connection.send(json.to_json_string())


class Procedures:
    def __init__(self):
        pass

    @staticmethod
    def restful_auth(request, option):
        timestamp = CEIUtils.get_now("%Y':'%m':'%d'T'#H':'%M':'%S")
        request.add_query_string("AccessKeyId", option.api_key)
        request.add_query_string("SignatureMethod", "HmacSHA256")
        request.add_query_string("SignatureVersion", "2")
        request.add_query_string("Timestamp", timestamp)
        query_string = CEIUtils.combine_query_string(request, CEIUtils.Constant.ASC, "&")
        method = CEIUtils.get_request_info(request, CEIUtils.Constant.METHOD, CEIUtils.Constant.UPPERCASE)
        host = CEIUtils.get_request_info(request, CEIUtils.Constant.HOST, CEIUtils.Constant.NONE)
        target = CEIUtils.get_request_info(request, CEIUtils.Constant.TARGET, CEIUtils.Constant.NONE)
        buffer = StringWrapper()
        buffer.append_string_item(method)
        buffer.append_string_item(host)
        buffer.append_string_item(target)
        buffer.append_string_item(query_string)
        buffer.combine_string_items("\\n")
        hmacsha256 = CEIUtils.hmacsha256(buffer.to_string(), option.secret_key)
        result = CEIUtils.base64(hmacsha256)
        request.add_query_string("Signature", result)


