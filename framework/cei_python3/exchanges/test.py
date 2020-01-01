from impl.restfulconnection import RestfulConnection
from impl.restfulrequest import RestfulRequest


class LastTradeTick:
    ts = None


class MarketClient:
    def get_symbol(self):
        request = RestfulRequest(self.options)
        request.set_url(self.options.url)
        request.set_method(RestfulRequest.Method.GET)
        request.add_header("accept-encoding", "gzip")
        response = RestfulConnection.query(request)
        symbols_var = Symbols()
        root_obj = response.get_json()
        symbols_var.status = root_obj.get_string("status")
        for item in root_obj.get_items("data"):
            symbols_data_var = SymbolsData()
            symbols_data_var.base_currency = item.get_string("base-currency")
            if symbols_var.data is None:
                symbols_var.data = list()
            symbols_var.data.append(symbols_data_var)
        return symbols_var

    def get_last_trade(self, symbol):
        request = RestfulRequest(self.options)
        request.set_url(self.options.url)
        request.set_method(RestfulRequest.Method.GET)
        request.add_header("accept-encoding", "gzip")
        request.add_query_string("symbol", symbol)
        request.add_query_string("test", "test")
        response = RestfulConnection.query(request)
        last_trade_var = LastTrade()
        root_obj = response.get_json()
        last_trade_var.status = root_obj.get_string("status")
        last_trade_tick_var = LastTradeTick()
        tick_obj = root_obj.get_object("tick")
        last_trade_var.tick = last_trade_tick_var
        last_trade_tick_var.ts = tick_obj.get_string("ts")
        return last_trade_var



class SymbolsData:
    base_currency = None
    quote_currency = None


class Symbols:
    status = None
    data = None


class LastTrade:
    status = None
    tick = None


