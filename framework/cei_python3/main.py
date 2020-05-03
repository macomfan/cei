from impl.restfulrequest import RestfulRequest
from impl.restfulconnection import RestfulConnection
import re
from impl.jsonwrapper import JsonWrapper

# from exchanges.huobipro import *

if __name__ == '__main__':
    data = '''
        {
      "status": "ok",
      "ch": "market.ethusdt.depth.step0",
      "ts": 1550218546616,
      "tick": {
        "bids": [
          [
            122.920000000000000000,
            2.746800000000000000
          ],
    	  [
            120.300000000000000000,
            494.745900000000000000
          ]
        ],
        "asks": [
          [
            122.940000000000000000,
            67.554900000000000000
          ],
    	  [
            124.620000000000000000,
            50.000000000000000000
          ]
        ],
        "ts": 1550218546020,
        "version": 100416549839
      }
    } 
    '''
    key = "[1]"
    m = re.match("^\\[[0-9]*]$", key)
    if m is not None:
        key = key[m.pos + 1:m.endpos - 1]
    json1 = JsonWrapper()
    key_aa = json1.get_array_or_none("aa")
    json2 = JsonWrapper.parse_from_string(data)
    print(json2.get_string("ts"))
    print(json2.to_json_string())
    # ws = TestWSClient()

    # def suf(msg):
    #     print("callback " + msg)

    # ws.sub("aaa", suf)
    # ws.start()
    # ws.send("mmmmmm")

    # option = RestfulOptions()
    # marketClient = MarketClient()
    # data = marketClient.get_candlestick("btcusdt", "1min", 5)
    # print(data)
    #
    # data = marketClient.get_last_trade("btcusdt")
    # print(data)

    # asyncio.run(main())
    # place_order(None, None)
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
    # options = RestfulOptions()
    # options.api_key = "ABC"
    # options.secret_key = "abc"
    # testClient = TestClient(options)
    # timestamp = testClient.get_timestamp()
    # hClient = HuobiProClient()
    # bClient = BinanceClient()
    #
    # print('Test ts: ' + str(timestamp.ts))
    # print('H ts: ' + str(hClient.get_timestamp().ts))
    # print('B ts: ' + str(bClient.get_timestamp().ts))
    pass
