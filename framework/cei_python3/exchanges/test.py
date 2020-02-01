from impl.jsonwrapper import JsonWrapper
from impl.restfulconnection import RestfulConnection
from impl.restfulrequest import RestfulRequest


class Order:
    order_id = None


class MarketClient:
    def get_exchange_info(self):
        request = RestfulRequest(self.options)
        request.set_url(self.options.url)
        request.set_target("/api/v1/exchangeInfo")
        request.set_method(RestfulRequest.Method.GET)
        request.add_header("accept-encoding", "gzip")
        response = RestfulConnection.query(request)
        exchange_info_var = ExchangeInfo()
        root_obj = response.get_json()
        exchange_info_var.exchange_name = root_obj.get_string("ExchangeName")
        exchange_info_var.timestamp = root_obj.get_long("Timestamp")
        for item in root_obj.get_items("Symbols"):
            symbols_var = Symbols()
            symbols_var.name = item.get_string("Name")
            symbols_var.status = item.get_string("Status")
            symbols_var.ocoallowed = item.get_boolean("OCOAllowed")
            symbols_var.precision = item.get_integer("Precision")
            if exchange_info_var.symbols is None:
                exchange_info_var.symbols = list()
            exchange_info_var.symbols.append(symbols_var)
        return exchange_info_var

    def get_last_trade(self, symbol):
        request = RestfulRequest(self.options)
        request.set_url(self.options.url)
        request.set_target("/api/v1/lastTrade")
        request.set_method(RestfulRequest.Method.GET)
        request.add_header("accept-encoding", "gzip")
        request.add_query_string("symbol", symbol)
        response = RestfulConnection.query(request)
        last_trade_var = LastTrade()
        root_obj = response.get_json()
        last_trade_var.price = root_obj.get_decimal("Price")
        last_trade_var.volume = root_obj.get_decimal("Volume")
        last_trade_var.timestamp = root_obj.get_long("Timestamp")
        last_trade_var.symbol = root_obj.get_string("Symbol")
        return last_trade_var

    def place_order(self, account_id, price):
        request = RestfulRequest(self.options)
        request.set_url(self.options.url)
        request.set_target("/api/v1/placeOrder")
        request.set_method(RestfulRequest.Method.POST)
        json_builder = JsonWrapper()
        json_builder.add_json_string("account-id", account_id)
        json_builder.add_json_decimal("price", price)
        request.set_post_body(json_builder)
        Signature.restful(request)
        response = RestfulConnection.query(request)
        order_var = Order()
        root_obj = response.get_json()
        order_var.order_id = root_obj.get_integer("OrderId")
        return order_var



class Symbols:
    name = None
    status = None
    ocoallowed = None
    precision = None


class LastTrade:
    price = None
    volume = None
    timestamp = None
    symbol = None


class ExchangeInfo:
    exchange_name = None
    timestamp = None
    symbols = None


