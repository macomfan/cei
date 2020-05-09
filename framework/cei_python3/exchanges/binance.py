from impl.jsonwrapper import JsonWrapper
from impl.restfulconnection import RestfulConnection
from impl.restfuloptions import RestfulOptions
from impl.restfulrequest import RestfulRequest


class Trade:
    def __init__(self):
        self.id = None
        self.price = None
        self.qty = None
        self.quote_qty = None
        self.time = None
        self.is_buyer_maker = None
        self.is_best_match = None


class RateLimits:
    def __init__(self):
        self.rate_limit_type = None
        self.interval = None
        self.interval_num = None
        self.limit = None


class Symbol:
    def __init__(self):
        self.symbol = None
        self.status = None
        self.base_asset = None
        self.base_asset_precision = None
        self.quote_asset = None
        self.quote_precision = None
        self.order_types = None
        self.iceberg_allowed = None
        self.oco_allowed = None


class CandlestickData:
    def __init__(self):
        self.open_time = None
        self.open = None
        self.high = None
        self.low = None
        self.close = None
        self.volume = None
        self.close_time = None
        self.quote_asset_volume = None
        self.number_of_trades = None
        self.taker_buy_base_asset_volume = None
        self.taker_buy_quote_asset_volume = None


class CandlestickDataList:
    def __init__(self):
        self.data = None


class DepthEntity:
    def __init__(self):
        self.price = None
        self.qty = None


class AggregateTrade:
    def __init__(self):
        self.id = None
        self.price = None
        self.qty = None
        self.timestamp = None
        self.first_trade_id = None
        self.last_trade_id = None
        self.is_buyer_maker = None
        self.is_best_match = None


class AggregateTradeList:
    def __init__(self):
        self.data = None


class ExchangeInfo:
    def __init__(self):
        self.timezone = None
        self.server_time = None
        self.rate_limits = None
        self.symbols = None


class TradeList:
    def __init__(self):
        self.data = None


class Timestamp:
    def __init__(self):
        self.server_time = None


class Depth:
    def __init__(self):
        self.last_update_id = None
        self.bids = None
        self.asks = None


class MarketClient:
    def __init__(self, option=None):
        self.__option = RestfulOptions()
        self.__option.url = "https://api.binance.com"
        if option is not None:
            self.__option.set_from(option)

    def get_aggregate_trades(self, symbol, from_id, start_time, end_time, limit):
        request = RestfulRequest(self.__option)
        request.set_target("/api/v3/aggTrades")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        request.add_query_string("fromId", str(from_id))
        request.add_query_string("startTime", str(start_time))
        request.add_query_string("startTime", str(end_time))
        request.add_query_string("limit", str(limit))
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        aggregate_trade_list_var = AggregateTradeList()
        for item in root_obj.array():
            aggregate_trade_var = AggregateTrade()
            aggregate_trade_var.id = item.get_int("a")
            aggregate_trade_var.price = item.get_decimal("p")
            aggregate_trade_var.qty = item.get_decimal("q")
            aggregate_trade_var.first_trade_id = item.get_int("f")
            aggregate_trade_var.last_trade_id = item.get_int("l")
            aggregate_trade_var.timestamp = item.get_int("T")
            aggregate_trade_var.is_buyer_maker = item.get_bool("m")
            aggregate_trade_var.is_best_match = item.get_bool("M")
            if aggregate_trade_list_var.data is None:
                aggregate_trade_list_var.data = list()
            aggregate_trade_list_var.data.append(aggregate_trade_var)
        return aggregate_trade_list_var

    def get_candlestick_data(self, symbol, interval, start_time, end_time, limit):
        request = RestfulRequest(self.__option)
        request.set_target("/api/v3/klines")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        request.add_query_string("interval", interval)
        request.add_query_string("startTime", str(start_time))
        request.add_query_string("endTime", str(end_time))
        request.add_query_string("limit", str(limit))
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        candlestick_data_list_var = CandlestickDataList()
        for item in root_obj.array():
            candlestick_data_var = CandlestickData()
            candlestick_data_var.open = item.get_decimal("\\[0]")
            candlestick_data_var.high = item.get_decimal("\\[1]")
            if candlestick_data_list_var.data is None:
                candlestick_data_list_var.data = list()
            candlestick_data_list_var.data.append(candlestick_data_var)
        return candlestick_data_list_var

    def get_depth(self, symbol, limit):
        request = RestfulRequest(self.__option)
        request.set_target("/api/v3/depth")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        request.add_query_string("limit", str(limit))
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        depth_var = Depth()
        depth_var.last_update_id = root_obj.get_int("lastUpdateId")
        obj = root_obj.get_array("bids")
        for item in obj.array():
            depth_entity_var = DepthEntity()
            depth_entity_var.price = item.get_decimal("[0]")
            depth_entity_var.qty = item.get_decimal("[1]")
            if depth_var.bids is None:
                depth_var.bids = list()
            depth_var.bids.append(depth_entity_var)
        obj0 = root_obj.get_array("asks")
        for item1 in obj0.array():
            depth_entity_var2 = DepthEntity()
            depth_entity_var2.price = item1.get_decimal("[0]")
            depth_entity_var2.qty = item1.get_decimal("[1]")
            if depth_var.asks is None:
                depth_var.asks = list()
            depth_var.asks.append(depth_entity_var2)
        return depth_var

    def get_exchange_info(self):
        request = RestfulRequest(self.__option)
        request.set_target("/api/v3/exchangeInfo")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        exchange_info_var = ExchangeInfo()
        exchange_info_var.timezone = root_obj.get_string("timezone")
        exchange_info_var.server_time = root_obj.get_int("serverTime")
        obj = root_obj.get_array("rateLimits")
        for item in obj.array():
            rate_limits_var = RateLimits()
            rate_limits_var.rate_limit_type = item.get_string("rateLimitType")
            rate_limits_var.interval = item.get_string("interval")
            rate_limits_var.interval_num = item.get_int("intervalNum")
            rate_limits_var.limit = item.get_int("limit")
            if exchange_info_var.rate_limits is None:
                exchange_info_var.rate_limits = list()
            exchange_info_var.rate_limits.append(rate_limits_var)
        obj0 = root_obj.get_array("symbols")
        for item1 in obj0.array():
            symbol_var = Symbol()
            symbol_var.symbol = item1.get_string("symbol")
            symbol_var.status = item1.get_string("status")
            symbol_var.base_asset = item1.get_string("baseAsset")
            symbol_var.base_asset_precision = item1.get_int("baseAssetPrecision")
            symbol_var.quote_asset = item1.get_string("quoteAsset")
            symbol_var.quote_precision = item1.get_int("quotePrecision")
            symbol_var.order_types = item1.get_string_array("orderTypes")
            symbol_var.iceberg_allowed = item1.get_bool("icebergAllowed")
            symbol_var.oco_allowed = item1.get_bool("ocoAllowed")
            if exchange_info_var.symbols is None:
                exchange_info_var.symbols = list()
            exchange_info_var.symbols.append(symbol_var)
        return exchange_info_var

    def historical_trades(self, symbol, limit, from_id):
        request = RestfulRequest(self.__option)
        request.set_target("/api/v3/historicalTrades")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        request.add_query_string("limit", str(limit))
        request.add_query_string("fromId", str(from_id))
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        trade_list_var = TradeList()
        for item in root_obj.array():
            trade_var = Trade()
            trade_var.id = item.get_int("id")
            trade_var.price = item.get_decimal("price")
            trade_var.qty = item.get_decimal("qty")
            trade_var.quote_qty = item.get_decimal("quoteQty")
            trade_var.time = item.get_int("time")
            trade_var.is_buyer_maker = item.get_bool("isBuyerMaker")
            trade_var.is_best_match = item.get_bool("isBestMatch")
            if trade_list_var.data is None:
                trade_list_var.data = list()
            trade_list_var.data.append(trade_var)
        return trade_list_var

    def get_timestamp(self):
        request = RestfulRequest(self.__option)
        request.set_target("/api/v3/time")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        timestamp_var = Timestamp()
        timestamp_var.server_time = root_obj.get_int("serverTime")
        return timestamp_var

    def get_trade(self, symbol, limit):
        request = RestfulRequest(self.__option)
        request.set_target("/api/v3/trades")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("symbol", symbol)
        request.add_query_string("limit", str(limit))
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        trade_list_var = TradeList()
        for item in root_obj.array():
            trade_var = Trade()
            trade_var.id = item.get_int("id")
            trade_var.price = item.get_decimal("price")
            trade_var.qty = item.get_decimal("qty")
            trade_var.quote_qty = item.get_decimal("quoteQty")
            trade_var.time = item.get_int("time")
            trade_var.is_buyer_maker = item.get_bool("isBuyerMaker")
            trade_var.is_best_match = item.get_bool("isBestMatch")
            if trade_list_var.data is None:
                trade_list_var.data = list()
            trade_list_var.data.append(trade_var)
        return trade_list_var


class Procedures:
    def __init__(self):
        pass


