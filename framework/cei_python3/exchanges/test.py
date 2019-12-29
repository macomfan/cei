from impl.restfulrequest import RestfulRequest
from impl.restfuloption import RestfulOption
from impl.restfulconnection import RestfulConnection


class Test:
    class ExchangeInfo:
        def __init__(self):
            self.exchange_name = None
            self.timestamp = None
            self.symbols = None

    class Symbol:
        def __init__(self):
            self.name = None
            self.status = None
            self.occ_allowed = None

    class MarketClient:
        def __init__(self, option: RestfulOption):
            self.__option = option
            pass

        def get_exchange_info(self):
            request = RestfulRequest(self.__option)
            request.set_target("/api/v1/exchangeInfo")
            response = RestfulConnection.query(request)
            return response
