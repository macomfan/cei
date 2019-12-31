from impl.restfulrequest import RestfulRequest
from impl.restfulconnection import RestfulConnection
import exchanges.test
from exchanges.test import MarketClient as huobi_M
from impl.signaturetool import SignatureTool

import requests

if __name__ == '__main__':
    aaa = LastTrade()
    SignatureTool.Constant.ASC = "ccc"

    request = RestfulRequest()
    request.set_method(RestfulRequest.Method.GET)
    response = RestfulConnection.query(request)
    json_wrapper = response.get_json()
    exchange_name = json_wrapper.get_string("exchangeName")

    request.add_query_string("aaa", 1)
    request.add_query_string("bbb", 2)
    request.add_query_string("ccc", 3)
    print(request.build_query_string())
    #RestfulConnection.query(request)
    client = Test.MarketClient()

    pass
