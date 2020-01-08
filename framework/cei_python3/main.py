from impl.restfulrequest import RestfulRequest
from impl.restfulconnection import RestfulConnection
from impl.signaturetool import SignatureTool
from exchanges.cei import *

import requests


def place_order(account_id, price):
    print(account_id + price)


if __name__ == '__main__':

    place_order(None, None)
    # lll = list()
    # lll.append()
    # SignatureTool.Constant.ASC = "ccc"
    #
    #
    # request = RestfulRequest()
    # request.set_method(RestfulRequest.Method.GET)
    # response = RestfulConnection.query(request)
    # json_wrapper = response.get_json()
    # exchange_name = json_wrapper.get_string("exchangeName")
    #
    # request.add_query_string("aaa", 1)
    # request.add_query_string("bbb", 2)
    # request.add_query_string("ccc", 3)
    # print(request.build_query_string())
    # #RestfulConnection.query(request)
    # client = Test.MarketClient()
    options = RestfulOptions()
    options.api_key = "ABC"
    options.secret_key = "abc"
    testClient = TestClient(options)
    timestamp = testClient.get_timestamp()
    hClient = HuobiProClient()
    bClient = BinanceClient()

    print('Test ts: ' + str(timestamp.ts))
    print('H ts: ' + str(hClient.get_timestamp().ts))
    print('B ts: ' + str(bClient.get_timestamp().ts))
    pass
