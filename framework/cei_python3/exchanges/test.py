from impl.ceiutils import CEIUtils
from impl.jsonwrapper import JsonWrapper
from impl.restfulconnection import RestfulConnection
from impl.restfuloptions import RestfulOptions
from impl.restfulrequest import RestfulRequest
from impl.websocketaction import WebSocketAction
from impl.websocketconnection import WebSocketConnection
from impl.websocketoptions import WebSocketOptions


class Order:
    def __init__(self):
        self.order_id = None


class MarketClient:
    def __init__(self, options=None):
        self.__options = RestfulOptions()
        self.__options.url = "https://api.huobipro.com"
        if options is not None:
            self.__options.set_from(options)

    def get_exchange_info(self):
        request = RestfulRequest(self.__options)
        request.set_target("/api/v1/exchangeInfo")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = response.get_json()
        exchange_info_var = ExchangeInfo()
        exchange_info_var.exchange_name = root_obj.get_string("ExchangeName")
        exchange_info_var.timestamp = root_obj.get_long("Timestamp")
        symbols_obj = root_obj.get_object("Symbols")
        for item in symbols_obj:
            symbols_var = Symbols()
            symbols_var.name = item.get_string("Name")
            symbols_var.status = item.get_string("Status")
            symbols_var.ocoallowed = item.get_boolean("OCOAllowed")
            symbols_var.precision = item.get_long("Precision")
            if exchange_info_var.symbols is None:
                exchange_info_var.symbols = list()
            exchange_info_var.symbols.append(symbols_var)
        return exchange_info_var

    def get_last_trade(self, symbol):
        request = RestfulRequest(self.__options)
        request.set_target("/api/v1/lastTrade")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        response = RestfulConnection.query(request)
        root_obj = response.get_json()
        last_trade_var = LastTrade()
        last_trade_var.price = root_obj.get_decimal("Price")
        last_trade_var.volume = root_obj.get_decimal("Volume")
        last_trade_var.timestamp = root_obj.get_long("Timestamp")
        last_trade_var.symbol = root_obj.get_string("Symbol")
        return last_trade_var

    def place_order(self, account_id, price):
        request = RestfulRequest(self.__options)
        request.set_target("/api/v1/placeOrder")
        request.set_method(RestfulRequest.Method.POST)
        ts = CEIUtils.get_now("")
        json_result = JsonWrapper()
        json_result.add_json_string("account-id", account_id)
        json_result.add_json_number("price", price)
        request.set_post_body(ts)
        response = RestfulConnection.query(request)
        root_obj = response.get_json()
        order_var = Order()
        order_var.order_id = root_obj.get_long("\\[0]")
        return order_var


class HistoricalTrade:
    def __init__(self):
        self.data = None


class WSClient(WebSocketConnection):
    def __init__(self):
        super().__init__()
        self.__option = None

    def connect(self, symbol):
        on_ping_action = WebSocketAction()
    
        def on_ping_action_trigger(msg):
            root_obj = msg.get_json()
        on_ping_action.set_trigger(on_ping_action_trigger)
    
        def on_ping_action_action(msg):
            ts = CEIUtils.get_now("")
            json_result = JsonWrapper()
            json_result.add_json_string("op", "pong")
            json_result.add_json_string("ts", ts)
            self.send_ws(json_result.to_json_string())
        on_ping_action.set_action(on_ping_action_action)
        self.register_persistent_action(on_ping_action)
        self.connect_ws("ws", self.option)

    def request_kline(self, symbol, onkline):
        onkline_action = WebSocketAction()
    
        def onkline_action_trigger(msg):
            root_obj = msg.get_json()
        onkline_action.set_trigger(onkline_action_trigger)
    
        def onkline_action_action(msg):
            root_obj = msg.get_json()
            trade_entity_var = TradeEntity()
            trade_entity_var.price = root_obj.get_decimal("price")
            onkline.invoke(trade_entity_var)
        onkline_action.set_action(onkline_action_action)
        self.register_disposable_action(onkline_action)
        abc = CEIUtils.get_now("")
        self.send_ws(abc)


class Symbols:
    def __init__(self):
        self.name = None
        self.status = None
        self.ocoallowed = None
        self.precision = None


class Authentication:
    def __init__(self):
        pass

    @staticmethod
    def restful(request, option):
        timestamp = CEIUtils.get_now("")
        request.add_query_string("accessKeyId", option.api_key)
        request.add_query_string("signatureMethod", "HmacSHA256")
        request.add_query_string("signatureVersion", "2")
        request.add_query_string("timestamp", timestamp)
        query_string = CEIUtils.combine_query_string(request, CEIUtils.Constant.ASC, "&")
        method = CEIUtils.get_request_info(request, CEIUtils.Constant.METHOD, CEIUtils.Constant.UPPERCASE)
        host = CEIUtils.get_request_info(request, CEIUtils.Constant.HOST, CEIUtils.Constant.NONE)
        target = CEIUtils.get_request_info(request, CEIUtils.Constant.TARGET, CEIUtils.Constant.NONE)
        hmacsha256 = CEIUtils.hmacsha256(buffer.to_string(), option.secret_key)
        result = CEIUtils.base64(hmacsha256)
        request.add_query_string("Signature", result)


class LastTrade:
    def __init__(self):
        self.price = None
        self.volume = None
        self.timestamp = None
        self.symbol = None


class ExchangeInfo:
    def __init__(self):
        self.exchange_name = None
        self.timestamp = None
        self.symbols = None


class TradeEntity:
    def __init__(self):
        self.price = None
        self.volume = None


